package com.wojo.Vault.Service.impl;

import com.wojo.Vault.Database.DBManager;
import com.wojo.Vault.Database.Model.Account;
import com.wojo.Vault.Database.Model.CashFlow;
import com.wojo.Vault.Database.Model.Payment;
import com.wojo.Vault.Exception.ExecuteStatementException;
import com.wojo.Vault.Service.CashFlowService;
import org.junit.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CashFlowServiceImplTest {

    private static final String PAYMENT_TABLE_NAME = "payment";

    @BeforeClass
    public static void setup() throws ExecuteStatementException {
        Configuration.connectionToTestDatabase();
        Configuration.disableForeignKeyCheck();
        Configuration.truncateTable(PAYMENT_TABLE_NAME);
    }

    @AfterClass
    public static void clearDatabaseAndDisconnect() throws SQLException {
        Configuration.disableForeignKeyCheck();
        Configuration.truncateTable(PAYMENT_TABLE_NAME);
        Configuration.enableForeignKeyCheck();
        DBManager.dbDisconnect();
    }

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

    private static final Payment THIS_MONTH = new Payment(
            "30",
            SENDER_ACCOUNT.getAccountId(),
            RECIPIENT_ACCOUNT.getAccountId(),
            RECIPIENT_NAME,
            RECIPIENT_ACCOUNT.getNumber(),
            new BigDecimal("300.00"),
            "this month",
            LocalDateTime.now()
    );

    private static final Payment MONTH_AGO = new Payment(
            "31",
            SENDER_ACCOUNT.getAccountId(),
            RECIPIENT_ACCOUNT.getAccountId(),
            RECIPIENT_NAME,
            RECIPIENT_ACCOUNT.getNumber(),
            new BigDecimal("400.00"),
            "a month ago",
            LocalDateTime.now().minusMonths(1)
    );

    private static final Payment TWO_MONTH_AGO = new Payment(
            "32",
            SENDER_ACCOUNT.getAccountId(),
            RECIPIENT_ACCOUNT.getAccountId(),
            RECIPIENT_NAME,
            RECIPIENT_ACCOUNT.getNumber(),
            new BigDecimal("500.00"),
            "a two month ago",
            LocalDateTime.now().minusMonths(2)
    );

    private static final Payment A_MORE_THAN_THREE_MONTH_AGO = new Payment(
            "33",
            SENDER_ACCOUNT.getAccountId(),
            RECIPIENT_ACCOUNT.getAccountId(),
            RECIPIENT_NAME,
            RECIPIENT_ACCOUNT.getNumber(),
            new BigDecimal("600.00"),
            "this month first",
            LocalDateTime.now().minusMonths(3).minusDays(1)
    );

    @Before
    public void insertDataToTests() throws ExecuteStatementException {
        assertTrue(insertPayment(THIS_MONTH));
        assertTrue(insertPayment(MONTH_AGO));
        assertTrue(insertPayment(TWO_MONTH_AGO));
        assertTrue(insertPayment(A_MORE_THAN_THREE_MONTH_AGO));
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
    }

    private CashFlowService cashFlowService = new CashFlowServiceImpl();

    @Test
    public void getLastThreeMonthCashFlowTest() {
        List<CashFlow> lastThreeMonthFlow =
                cashFlowService.getLastThreeMonthCashFlow(SENDER_ACCOUNT.getAccountId());

        final int expectedSize = 3;
        assertEquals(expectedSize, lastThreeMonthFlow.size());

        Month currentMonth = LocalDateTime.now().getMonth();
        lastThreeMonthFlow.forEach(cashFlow -> {
            if (cashFlow.getMonth().compareTo(currentMonth) == 0) {
                assertEquals(0,
                        cashFlow.getBalance().negate().compareTo(THIS_MONTH.getAmount()));
            } else if (cashFlow.getMonth().compareTo(currentMonth.minus(1)) == 0) {
                assertEquals(0,
                        cashFlow.getBalance().negate().compareTo(MONTH_AGO.getAmount()));
            } else {
                assertEquals(0,
                        cashFlow.getBalance().negate().compareTo(TWO_MONTH_AGO.getAmount()));
            }
        });
    }

    @Test
    public void lastThreeMonthCashFlowForBadParameterTest() {
        final String badAccountId = "-20";

        BigDecimal expected = new BigDecimal("0.00");
        assertEquals(expected, BigDecimal.valueOf(
                cashFlowService.getLastThreeMonthCashFlow(badAccountId).stream()
                        .mapToDouble(i -> Double.valueOf(i.getBalance().toString()))
                        .sum()).setScale(2, RoundingMode.CEILING)
        );
    }

    @Test
    public void lastMonthCashFlowTest() {
        assertEquals(THIS_MONTH.getAmount().negate(),
                cashFlowService.getLastMothFlow(SENDER_ACCOUNT.getAccountId()).getBalance());
    }

    @Test
    public void lastMonthFlowForBadParameterTest() {
        final String badAccountId = "-30";

        BigDecimal expected = new BigDecimal("0.00");
        assertEquals(expected, cashFlowService.getLastMothFlow(badAccountId).getBalance());
    }
}