package com.wojo.Vault.Database.DAO.Impl;

import com.wojo.Vault.Database.DAO.PaymentDAO;
import com.wojo.Vault.Database.DBManager;
import com.wojo.Vault.Database.Model.Payment;
import com.wojo.Vault.Exception.ExecuteStatementException;
import org.junit.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.Assert.*;

public class PaymentDAOImplTest {

    private PaymentDAO paymentDAO = new PaymentDAOImpl();

    private static final String DISABLE_FOREIGN_KEY_CHECK = "SET FOREIGN_KEY_CHECKS = 0";
    private static final String ENABLE_FOREIGN_KEY_CHECK = "SET FOREIGN_KEY_CHECKS = 1";

    @BeforeClass
    public static void connectionToTestDatabase() throws ExecuteStatementException {
        DBManager.setTestConnectionPath();
        DBManager.dbConnection();

        DBManager.dbExecuteUpdate(DISABLE_FOREIGN_KEY_CHECK, null);

        String updateStatement = "TRUNCATE TABLE payment";
        DBManager.dbExecuteUpdate(updateStatement, null);
    }

    @AfterClass
    public static void clearDatabaseAndDisconnect() throws ExecuteStatementException {
        String updateStatement = "TRUNCATE TABLE payment";
        DBManager.dbExecuteUpdate(updateStatement, null);

        DBManager.dbExecuteUpdate(ENABLE_FOREIGN_KEY_CHECK, null);

        DBManager.dbDisconnect();
    }

    private static final String FIRST_ACCOUNT_ID = "77";
    private static final String SECOND_ACCOUNT_ID = "32";
    private static final String THIRD_ACCOUNT_ID = "31";

    private static final Payment FIRST_PAYMENT = new Payment(
            "1",
            FIRST_ACCOUNT_ID,
            SECOND_ACCOUNT_ID,
            "Second",
            "78900987789009877890098778",
            new BigDecimal("765.25"),
            "First transfer",
            LocalDateTime.now().minusDays(5)
    );


    private static final Payment SECOND_PAYMENT = new Payment(
            "2",
            FIRST_ACCOUNT_ID,
            THIRD_ACCOUNT_ID,
            "Third",
            "12340987789009877890098778",
            new BigDecimal("130.50"),
            "Second transfer",
            LocalDateTime.now().minusDays(12)
    );

    private static final Payment THIRD_PAYMENT = new Payment(
            "3",
            SECOND_ACCOUNT_ID,
            FIRST_ACCOUNT_ID,
            "First",
            "56780987789009877890098778",
            new BigDecimal("1427.00"),
            "Third transfer",
            LocalDateTime.now().minusMonths(1)
    );

    private static final Payment A_THREE_MONTH_AND_ONE_DAY_AGO_PAYMENT = new Payment(
            "4",
            SECOND_ACCOUNT_ID,
            FIRST_ACCOUNT_ID,
            "First",
            "56780987789009877890098778",
            new BigDecimal("1427.00"),
            "Fourth transfer",
            LocalDateTime.now().minusMonths(3).minusDays(1)
    );

    @Before
    public void insertDataToTests() throws ExecuteStatementException {
        insertPayment(FIRST_PAYMENT);
        insertPayment(SECOND_PAYMENT);
        insertPayment(THIRD_PAYMENT);
        insertPayment(A_THREE_MONTH_AND_ONE_DAY_AGO_PAYMENT);
    }

    private void insertPayment(Payment payment) throws ExecuteStatementException {
        String updateStatement = "INSERT INTO payment " +
                "(PAYMENT_ID, SENDER_ACCOUNT_ID, RECIPIENT_ACCOUNT_ID, RECIPIENT_NAME, " +
                "RECIPIENT_NUMBER, AMOUNT, TITLE, CREATE_TIME) " +
                "VALUES " +
                "(?, ?, ?, ?, ?, ?, ?, ?)";

        List<String> dataToUpdate = Arrays.asList(
                payment.getPaymentId(),
                payment.getSenderAccountId(),
                payment.getRecipientAccountId(),
                payment.getRecipientName(),
                payment.getRecipientNumber(),
                payment.getAmount().toString(),
                payment.getTitle(),
                payment.getDate().toString()
        );

        DBManager.dbExecuteUpdate(updateStatement, dataToUpdate);
    }

    @After
    public void clearTable() throws ExecuteStatementException {
        DBManager.dbExecuteUpdate(DISABLE_FOREIGN_KEY_CHECK, null);

        String updateStatement = "TRUNCATE TABLE payment";
        DBManager.dbExecuteUpdate(updateStatement, null);
    }

    @Test
    public void shouldReturnCorrectTransferInsideData() {
        BigDecimal recipientNewValue = new BigDecimal("2500.00").add(SECOND_PAYMENT.getAmount());
        BigDecimal senderNewValue = new BigDecimal("1500.00").subtract(SECOND_PAYMENT.getAmount());

        Map<List<String>, String> transferInsideData = paymentDAO.getTransferInsideData(
                SECOND_PAYMENT.getRecipientAccountId(),
                SECOND_PAYMENT.getSenderAccountId(),
                recipientNewValue,
                senderNewValue
        );

        final int expectedSize = 2;
        assertEquals(expectedSize, transferInsideData.size());

        transferInsideData.forEach((data, updateStatement) -> {
            final int expectedDataSize = 2;
            assertEquals(expectedDataSize, data.size());

            if (Objects.equals(recipientNewValue.toString(), data.get(0))) {
                assertEquals(SECOND_PAYMENT.getRecipientAccountId(), data.get(1));
            } else {
                assertEquals(senderNewValue.toString(), data.get(0));
                assertEquals(SECOND_PAYMENT.getSenderAccountId(), data.get(1));
            }
        });
    }

    @Test
    public void shouldReturnCorrectTransferOutsideData() {
        BigDecimal senderNewValue = new BigDecimal("4000").subtract(THIRD_PAYMENT.getAmount());

        Map<List<String>, String> transferOutsideData = paymentDAO.getTransferOutsideData(
                THIRD_PAYMENT.getSenderAccountId(),
                senderNewValue
        );

        final int expectedSize = 1;
        assertEquals(expectedSize, transferOutsideData.size());

        List<String> data = transferOutsideData.entrySet().iterator().next().getKey();

        final int expectedDataSize = 2;
        assertEquals(expectedDataSize, data.size());

        assertEquals(senderNewValue.toString(), data.get(0));
        assertEquals(THIRD_PAYMENT.getSenderAccountId(), data.get(1));
    }

    @Test
    public void shouldReturnCorrectInsertPaymentData() {
        Map<List<String>, String> insertPaymentData =
                paymentDAO.getInsertPaymentData(
                        FIRST_PAYMENT.getSenderAccountId(),
                        FIRST_PAYMENT.getRecipientAccountId(),
                        FIRST_PAYMENT.getRecipientName(),
                        FIRST_PAYMENT.getRecipientNumber(),
                        FIRST_PAYMENT.getAmount(),
                        FIRST_PAYMENT.getTitle(),
                        FIRST_PAYMENT.getDate()
                );

        final int expectedSize = 1;
        assertEquals(expectedSize, insertPaymentData.size());

        List<String> expectedData = Arrays.asList(
                FIRST_PAYMENT.getSenderAccountId(),
                FIRST_PAYMENT.getRecipientAccountId(),
                FIRST_PAYMENT.getRecipientName(),
                FIRST_PAYMENT.getRecipientNumber(),
                FIRST_PAYMENT.getAmount().toString(),
                FIRST_PAYMENT.getTitle(),
                FIRST_PAYMENT.getDate().toString()
        );
        assertEquals(expectedData, insertPaymentData.entrySet().iterator().next().getKey());
    }

    @Test
    public void getPaymentsHistoryTest() {
        List<Payment> allPayments = paymentDAO.findAll(FIRST_ACCOUNT_ID);
        assertNotNull(allPayments);

        /*
          expected payments:
                   FIRST_PAYMENT,
                   SECOND_PAYMENT,
                   THIRD_PAYMENT,
                   A_THREE_MONTH_AND_ONE_DAY_AGO_PAYMENT
         */
        final int expectedSize = 4;
        assertEquals(expectedSize, allPayments.size());

        allPayments.forEach(payment -> {
            if (payment.getPaymentId().equals(FIRST_PAYMENT.getPaymentId())) {
                assertEquals(FIRST_PAYMENT, payment);
            } else if (payment.getPaymentId().equals(SECOND_PAYMENT.getPaymentId())) {
                assertEquals(SECOND_PAYMENT, payment);
            } else if (payment.getPaymentId().equals(THIRD_PAYMENT.getPaymentId())) {
                assertEquals(THIRD_PAYMENT, payment);
            } else {
                assertEquals(A_THREE_MONTH_AND_ONE_DAY_AGO_PAYMENT, payment);
            }
        });
    }

    @Test
    public void getLastThreeMonthPaymentHistoryTest() {
        List<Payment> lastThreeMonthPayment = paymentDAO.findAllFromLastThreeMonth(SECOND_ACCOUNT_ID);
        assertNotNull(lastThreeMonthPayment);

        /*
          expected payments:
                   FIRST_PAYMENT,
                   THIRD_PAYMENT
         */
        final int expectedSize = 2;
        assertEquals(expectedSize, lastThreeMonthPayment.size());

        lastThreeMonthPayment.forEach(payment -> {
            if (payment.getPaymentId().equals(FIRST_PAYMENT.getPaymentId())) {
                assertEquals(FIRST_PAYMENT, payment);
            } else {
                assertEquals(THIRD_PAYMENT, payment);
            }
        });
    }

    @Test
    public void shouldNotFindPaymentForBadId() {
        String badAccountId = "-30";
        final int expectedSize = 0;

        List<Payment> allPayment = paymentDAO.findAll(badAccountId);
        assertEquals(expectedSize, allPayment.size());

        List<Payment> lastThreeMonthPayment = paymentDAO.findAllFromLastThreeMonth(badAccountId);
        assertEquals(expectedSize, lastThreeMonthPayment.size());
    }
}