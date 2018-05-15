package com.wojo.Vault.Service.impl;

import com.wojo.Vault.Database.DBManager;
import com.wojo.Vault.Database.Model.Account;
import com.wojo.Vault.Database.Model.Payment;
import com.wojo.Vault.Exception.ExecuteStatementException;
import com.wojo.Vault.Service.PaymentService;
import org.junit.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class PaymentServiceImplTest {

    private static final String PAYMENT_TABLE_NAME = "payment";
    private static final String ACCOUNT_TABLE_NAME = "account";

    @BeforeClass
    public static void setup() throws ExecuteStatementException {
        Configuration.connectionToTestDatabase();
        Configuration.disableForeignKeyCheck();
        Configuration.truncateTable(PAYMENT_TABLE_NAME);
        Configuration.truncateTable(ACCOUNT_TABLE_NAME);
    }

    @AfterClass
    public static void clearDatabaseAndDisconnect() throws ExecuteStatementException {
        Configuration.disableForeignKeyCheck();
        Configuration.truncateTable(PAYMENT_TABLE_NAME);
        Configuration.truncateTable(ACCOUNT_TABLE_NAME);
        Configuration.enableForeignKeyCheck();
        DBManager.dbDisconnect();
    }

    private static final String SENDER_NAME = "Sender";
    private static final Account SENDER_ACCOUNT = new Account(
            "10",
            "1",
            "87655678098712345678098765",
            new BigDecimal("77000.00")
    );

    private static final String RECIPIENT_NAME = "Recipient";
    private static final Account RECIPIENT_ACCOUNT = new Account(
            "20",
            "3",
            "33225678098712345678098755",
            new BigDecimal("77000.00")
    );

    private static final Payment PAYMENT_FROM_SENDER_TO_RECIPIENT_DATA = new Payment(
            "1",
            SENDER_ACCOUNT.getAccountId(),
            RECIPIENT_ACCOUNT.getAccountId(),
            RECIPIENT_NAME,
            RECIPIENT_ACCOUNT.getNumber(),
            new BigDecimal("1000.00"),
            "title",
            LocalDateTime.now()
    );

    private static final String OUTSIDE_ACCOUNT_ID = "20";
    private static final Payment RECENT_DEPOSIT = new Payment(
            "2",
            OUTSIDE_ACCOUNT_ID,
            SENDER_ACCOUNT.getAccountId(),
            SENDER_NAME,
            SENDER_ACCOUNT.getNumber(),
            new BigDecimal("3500.00"),
            "Recent deposit",
            LocalDateTime.now().minusHours(5)
    );

    private static final Payment RECENT_DEBIT = new Payment(
            "3",
            SENDER_ACCOUNT.getAccountId(),
            RECIPIENT_ACCOUNT.getAccountId(),
            RECIPIENT_NAME,
            RECIPIENT_ACCOUNT.getNumber(),
            new BigDecimal("500.00"),
            "Recent debit",
            LocalDateTime.now().minusHours(4)
    );

    private static final Payment THIRD_PAYMENT = new Payment(
            "4",
            OUTSIDE_ACCOUNT_ID,
            SENDER_ACCOUNT.getAccountId(),
            SENDER_NAME,
            SENDER_ACCOUNT.getNumber(),
            new BigDecimal("5000.00"),
            "Third payment",
            LocalDateTime.now().minusMonths(2)
    );

    private static final Payment FOURTH_PAYMENT = new Payment(
            "5",
            SENDER_ACCOUNT.getAccountId(),
            RECIPIENT_ACCOUNT.getAccountId(),
            RECIPIENT_NAME,
            RECIPIENT_ACCOUNT.getNumber(),
            new BigDecimal("1000.00"),
            "Fourth payment",
            LocalDateTime.now().minusMonths(3)
    );


    @Before
    public void insertDataToTest() throws ExecuteStatementException {
        String updateStatement = "INSERT INTO account (ACCOUNT_ID, PERSON_ID, NUMBER, VALUE) VALUES (?, ?, ?, ?)";

        assertTrue(DBManager.dbExecuteUpdate(updateStatement, Arrays.asList(
                SENDER_ACCOUNT.getAccountId(),
                SENDER_ACCOUNT.getPersonId(),
                SENDER_ACCOUNT.getNumber(),
                SENDER_ACCOUNT.getValue().toString()
        )) == 1);

        assertTrue(DBManager.dbExecuteUpdate(updateStatement, Arrays.asList(
                RECIPIENT_ACCOUNT.getAccountId(),
                RECIPIENT_ACCOUNT.getPersonId(),
                RECIPIENT_ACCOUNT.getNumber(),
                RECIPIENT_ACCOUNT.getValue().toString()
        )) == 1);

        assertTrue(insertPayment(RECENT_DEPOSIT));
        assertTrue(insertPayment(RECENT_DEBIT));
        assertTrue(insertPayment(THIRD_PAYMENT));
        assertTrue(insertPayment(FOURTH_PAYMENT));
    }

    private boolean insertPayment(Payment payment) throws ExecuteStatementException {
        String updateStatement = "INSERT INTO payment " +
                "(PAYMENT_ID, SENDER_ACCOUNT_ID, RECIPIENT_ACCOUNT_ID, RECIPIENT_NAME, " +
                "RECIPIENT_NUMBER, AMOUNT, TITLE, CREATE_TIME) " +
                "VALUES " +
                "(?, ?, ?, ?, ?, ?, ?, ?)";

        return DBManager.dbExecuteUpdate(updateStatement, Arrays.asList(
                payment.getPaymentId(),
                payment.getSenderAccountId(),
                payment.getRecipientAccountId(),
                payment.getRecipientName(),
                payment.getRecipientNumber(),
                payment.getAmount().toString(),
                payment.getTitle(),
                payment.getDate().toString()
        )) == 1;
    }

    @After
    public void clearTable() throws ExecuteStatementException {
        Configuration.disableForeignKeyCheck();
        Configuration.truncateTable(PAYMENT_TABLE_NAME);
        Configuration.truncateTable(ACCOUNT_TABLE_NAME);
    }

    private static final PaymentService paymentService = new PaymentServiceImpl();

    @Test
    public void shouldSendTransfer() {
        assertTrue(paymentService.sendTransfer(
                SENDER_ACCOUNT,
                RECIPIENT_NAME,
                RECIPIENT_ACCOUNT.getNumber(),
                PAYMENT_FROM_SENDER_TO_RECIPIENT_DATA.getTitle(),
                PAYMENT_FROM_SENDER_TO_RECIPIENT_DATA.getAmount()
        ));
    }

    @Test
    public void shouldNotSendTransfer() {
        assertFalse(paymentService.sendTransfer(null,
                null,
                null,
                null,
                null));
    }

    @Test
    public void shouldReturnCorrectInsideTransferData() {
        Set<List<String>> transferInsideData = paymentService.getTransferInsideData(
                SENDER_ACCOUNT.getAccountId(),
                RECIPIENT_ACCOUNT.getAccountId(),
                RECIPIENT_ACCOUNT.getNumber(),
                PAYMENT_FROM_SENDER_TO_RECIPIENT_DATA.getAmount()
        ).keySet();

        final int expectedSize = 2;
        assertEquals(expectedSize, transferInsideData.size());
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotReturnTransferInsideData() {
        paymentService.getTransferInsideData(null,
                null,
                null,
                null);
    }

    @Test
    public void shouldReturnCorrectOutsideTransferData() {
        Set<List<String>> transferOutsideData = paymentService.getTransferOutsideData(
                SENDER_ACCOUNT.getAccountId(),
                SENDER_ACCOUNT.getNumber(),
                PAYMENT_FROM_SENDER_TO_RECIPIENT_DATA.getAmount()
        ).keySet();

        final int expectedSize = 1;
        assertEquals(expectedSize, transferOutsideData.size());
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotReturnTransferOutsideData() {
        paymentService.getTransferOutsideData(null,
                null,
                null);
    }

    @Test
    public void shouldFindAll() {
        List<Payment> allPayments = paymentService.findAll(SENDER_ACCOUNT.getAccountId());

        /*
          expected payments:
                   RECENT_DEPOSIT,
                   RECENT_DEBIT,
                   THIRD_PAYMENT,
                   FOURTH_PAYMENT
        */
        final int expectedSize = 4;
        assertEquals(expectedSize, allPayments.size());

        allPayments.forEach(payment -> {
            if (payment.getPaymentId().equals(RECENT_DEPOSIT.getPaymentId())) {
                Assert.assertTrue(RECENT_DEPOSIT.equals(payment));
            } else if (payment.getPaymentId().equals(RECENT_DEBIT.getPaymentId())) {
                Assert.assertTrue(RECENT_DEBIT.equals(payment));
            } else if (payment.getPaymentId().equals(THIRD_PAYMENT.getPaymentId())) {
                Assert.assertTrue(THIRD_PAYMENT.equals(payment));
            } else {
                Assert.assertTrue(FOURTH_PAYMENT.equals(payment));
            }
        });
    }

    @Test
    public void shouldNotFindForBadParameter() {
        final String badAccountId = "-2";
        final String nullId = null;

        final int expectedSize = 0;
        assertEquals(expectedSize, paymentService.findAll(badAccountId).size());
        assertEquals(expectedSize, paymentService.findAll(nullId).size());
    }

    @Test
    public void shouldReturnLastThreePayment() {
        List<Payment> lastThree = paymentService.getLastThreePayment(SENDER_ACCOUNT.getAccountId());

        assertEquals(3, lastThree.size());
    }

    @Test
    public void shouldNotReturnLastThreeForBadParameter() {
        final String badAccountId = "-20";
        final String nullId = null;

        assertEquals(0, paymentService.getLastThreePayment(badAccountId).size());
        assertEquals(0, paymentService.getLastThreePayment(nullId).size());
    }

    @Test
    public void shouldReturnRecentDeposit() {
        Payment foundedRecentDeposit = paymentService.getRecentDeposit(SENDER_ACCOUNT.getAccountId());
        assertTrue(RECENT_DEPOSIT.equals(foundedRecentDeposit));
    }

    @Test
    public void shouldNotFindRecentDeposit() {
        final String badAccountId = "-1";
        final String nullId = null;

        assertTrue(paymentService.getRecentDeposit(badAccountId) == null);
        assertTrue(paymentService.getRecentDeposit(nullId) == null);
    }

    @Test
    public void shouldReturnRecentDebit() {
        Payment foundedRecentDebit = paymentService.getRecentDebit(SENDER_ACCOUNT.getAccountId());
        assertTrue(RECENT_DEBIT.equals(foundedRecentDebit));
    }

    @Test
    public void shouldNotFindRecentDebit() {
        final String badAccountId = "-1";
        final String nullId = null;

        assertTrue(paymentService.getRecentDebit(badAccountId) == null);
        assertTrue(paymentService.getRecentDebit(nullId) == null);
    }

}