package com.wojo.Vault.Database.DAO.Impl;

import com.wojo.Vault.Database.DAO.PaymentDAO;
import com.wojo.Vault.Database.DBManager;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
        transferInsideData.entrySet().forEach(index -> {
            List<Object> data = index.getKey();
            if (Objects.equals(data.get(idIndexOnList), recipientIdAccount)) {
                assertEquals(recipientNewValue, data.get(valueIndexOnList));
            } else {
                assertEquals(senderIdAccount, data.get(idIndexOnList));
                assertEquals(senderNewValue, data.get(valueIndexOnList));
            }

            String updateStatement = index.getValue();
            assertFalse(updateStatement.equals(null));
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
        transferOutsideData.entrySet().forEach(index -> {
            List<Object> data = index.getKey();
            assertEquals(senderNewValue, data.get(valueIndexOnList));
            assertEquals(senderIdAccount, data.get(idIndexOnList));

            String updateStatement = index.getValue();
            assertFalse(updateStatement.equals(null));
            assertTrue(updateStatement.length() != 0);
        });
    }

    @Test
    public void shouldReturnCorrectInsertPaymentData() {
        String senderIdAccount = 1 + "";
        String recipientIdAccount = 2 + "";
        String recipient = "Jan";
        String sender = "Andrzej";
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
        insertPaymentData.entrySet().forEach(index -> {
            List<Object> data = index.getKey();
            assertEquals(senderIdAccount, data.get(senderIdAccountIndexOnList));
            assertEquals(recipientIdAccount, data.get(recipientIdAccountIndexOnList));
            assertEquals(recipient, data.get(recipientIndexOnList));
            assertEquals(sender, data.get(senderIndexOnList));
            assertEquals(title, data.get(titleIndexOnList));
            assertEquals(value, data.get(valueIndexOnList));
            assertTrue(Math.abs(new Date().getTime() - ((Date) data.get(dataIndexOnList)).getTime()) < 500);

            String updateStatement = index.getValue();
            assertFalse(updateStatement.equals(null));
            assertTrue(updateStatement.length() != 0);
        });
    }
}