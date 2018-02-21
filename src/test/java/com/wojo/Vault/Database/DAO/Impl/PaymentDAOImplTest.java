package com.wojo.Vault.Database.DAO.Impl;

import com.wojo.Vault.Database.DAO.PaymentDAO;
import com.wojo.Vault.Database.DBManager;
import com.wojo.Vault.Database.Model.Payment;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.Assert.*;

public class PaymentDAOImplTest {

    private PaymentDAO paymentDAO = new PaymentDAOImpl();

    @BeforeClass
    public static void setConnectionTestPath() {
        DBManager.setTestConnectionPath();
    }

    @Test
    public void shouldReturnCorrectTransferInsideData() {
        String recipientIdAccount = 1 + "";
        String senderIdAccount = 2 + "";
        BigDecimal recipientNewValue = BigDecimal.valueOf(1000);
        BigDecimal senderNewValue = BigDecimal.valueOf(2000);

        Integer valueIndexOnList = 0;
        Integer idIndexOnList = 1;

        Map<List<Object>, String> transferInsideData =
                paymentDAO.getTransferInsideData(recipientIdAccount, senderIdAccount, recipientNewValue, senderNewValue);

        int expectedDataSize = 2;
        assertEquals(expectedDataSize, transferInsideData.size());
        transferInsideData.forEach((data, updateStatement) -> {
            if (Objects.equals(data.get(idIndexOnList), recipientIdAccount)) {
                assertEquals(recipientNewValue, data.get(valueIndexOnList));
            } else {
                assertEquals(senderIdAccount, data.get(idIndexOnList));
                assertEquals(senderNewValue, data.get(valueIndexOnList));
            }

            assertFalse(updateStatement == null);
            assertTrue(updateStatement.length() != 0);
        });
    }

    @Test
    public void shouldReturnCorrectTransferOutsideData() {
        String senderIdAccount = 2 + "";
        BigDecimal senderNewValue = BigDecimal.valueOf(2000);

        Integer valueIndexOnList = 0;
        Integer idIndexOnList = 1;

        Map<List<Object>, String> transferOutsideData =
                paymentDAO.getTransferOutsideData(senderIdAccount, senderNewValue);

        int expectedDataSize = 1;
        assertEquals(expectedDataSize, transferOutsideData.size());
        transferOutsideData.forEach((data, updateStatement) -> {
            assertEquals(senderNewValue, data.get(valueIndexOnList));
            assertEquals(senderIdAccount, data.get(idIndexOnList));

            assertFalse(updateStatement == null);
            assertTrue(updateStatement.length() != 0);
        });
    }

    @Test
    public void shouldReturnCorrectInsertPaymentData() {
        String senderIdAccount = 1 + "";
        String recipientIdAccount = 2 + "";
        String recipient = "John";
        String sender = "Andrej";
        String title = "Test";
        BigDecimal value = BigDecimal.valueOf(700);

        Integer senderIdAccountIndexOnList = 0;
        Integer recipientIdAccountIndexOnList = 1;
        Integer recipientIndexOnList = 2;
        Integer senderIndexOnList = 3;
        Integer titleIndexOnList = 4;
        Integer valueIndexOnList = 5;
        Integer dataIndexOnList = 6;

        Map<List<Object>, String> insertPaymentData =
                paymentDAO.getInsertPaymentData(senderIdAccount, recipientIdAccount, recipient, sender, title, value);

        int expectedDataSize = 1;
        assertEquals(expectedDataSize, insertPaymentData.size());
        insertPaymentData.forEach((data, updateStatement) -> {
            assertEquals(senderIdAccount, data.get(senderIdAccountIndexOnList));
            assertEquals(recipientIdAccount, data.get(recipientIdAccountIndexOnList));
            assertEquals(recipient, data.get(recipientIndexOnList));
            assertEquals(sender, data.get(senderIndexOnList));
            assertEquals(title, data.get(titleIndexOnList));
            assertEquals(value, data.get(valueIndexOnList));
            assertTrue(Math.abs(new Date().getTime() - ((Date) data.get(dataIndexOnList)).getTime()) < 500);

            assertFalse(updateStatement == null);
            assertTrue(updateStatement.length() != 0);
        });
    }

    private static final Integer FIRST_ID_ACCOUNT = 3;
    private static final String FIRST_NAME = "First Name";

    private static final Integer SECOND_ID_ACCOUNT = 4;
    private static final String SECOND_NAME = "Second Name";

    private static final String TITLE = "Test title";
    private static final BigDecimal ACCOUNT_VALUE = BigDecimal.valueOf(50000);
    private static final BigDecimal PAYMENT_VALUE = BigDecimal.valueOf(300);

    @Test
    public void shouldSendPaymentFromFirstToSecondAccount() {
        Map<List<Object>, String> dataToUpdate = new HashMap<>(3);

        String updateAccountsStatement = "UPDATE accounts " +
                "SET value = ? " +
                "WHERE idAccount = ?";
        dataToUpdate.put(Arrays.asList(ACCOUNT_VALUE.subtract(PAYMENT_VALUE), FIRST_ID_ACCOUNT)
                , updateAccountsStatement);
        dataToUpdate.put(Arrays.asList(ACCOUNT_VALUE.add(PAYMENT_VALUE), SECOND_ID_ACCOUNT)
                , updateAccountsStatement);

        String updatePaymentsStatement = "INSERT INTO payments " +
                "(idAccount, recipientIdAccount, recipientName, senderName, title, paymentValue, date) " +
                "VALUES " +
                "(?, ?, ?, ?, ?, ?, ?)";
        dataToUpdate.put(Arrays.asList(FIRST_ID_ACCOUNT, SECOND_ID_ACCOUNT, SECOND_NAME, FIRST_NAME
                , TITLE, PAYMENT_VALUE, new Date()), updatePaymentsStatement);

        assertTrue(paymentDAO.sendTransfer(dataToUpdate));
    }

    @Test
    public void shouldSendPaymentFromSecondToFirstAccount() {
        Map<List<Object>, String> dataToUpdate = new HashMap<>(3);

        String updateAccountsStatement = "UPDATE accounts " +
                "SET value = ? " +
                "WHERE idAccount = ?";
        dataToUpdate.put(Arrays.asList(ACCOUNT_VALUE.add(PAYMENT_VALUE), FIRST_ID_ACCOUNT)
                , updateAccountsStatement);
        dataToUpdate.put(Arrays.asList(ACCOUNT_VALUE.subtract(PAYMENT_VALUE), SECOND_ID_ACCOUNT)
                , updateAccountsStatement);

        String updatePaymentsStatement = "INSERT INTO payments " +
                "(idAccount, recipientIdAccount, recipientName, senderName, title, paymentValue, date) " +
                "VALUES " +
                "(?, ?, ?, ?, ?, ?, ?)";
        dataToUpdate.put(Arrays.asList(SECOND_ID_ACCOUNT, FIRST_ID_ACCOUNT, FIRST_NAME, SECOND_NAME
                , TITLE, PAYMENT_VALUE, new Date()), updatePaymentsStatement);

        assertTrue(paymentDAO.sendTransfer(dataToUpdate));
    }

    @Test
    public void shouldGetPaymentsHistoryForFirstAccount() {
        List<Payment> firstAccountPayments = paymentDAO.getAllPayment(FIRST_ID_ACCOUNT);

        firstAccountPayments.forEach(payment -> {
               if (payment.getIdAccount().equals(FIRST_ID_ACCOUNT)) {
                   assertEquals(SECOND_NAME ,payment.getRecipientName());
                   assertEquals(FIRST_NAME ,payment.getSenderName());
                    assertEquals(PAYMENT_VALUE.negate(), payment.getPaymentValue());
               }
               else {
                   assertEquals(FIRST_NAME, payment.getRecipientName());
                   assertEquals(SECOND_NAME ,payment.getSenderName());
                   assertEquals(PAYMENT_VALUE, payment.getPaymentValue());
               }
               assertEquals(TITLE, payment.getTitle());
        });
    }
}