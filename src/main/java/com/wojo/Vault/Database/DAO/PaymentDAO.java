package com.wojo.Vault.Database.DAO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface PaymentDAO {

    Map<List<Object>, String> getTransferInsideData(String recipientIdAccount
            , String senderIdAccount, BigDecimal recipientNewValue, BigDecimal senderNewValue);

    Map<List<Object>, String> getTransferOutsideData(String senderIdAccount
            , BigDecimal senderNewValue);

    Map<List<Object>, String> getInsertPaymentData(String senderIdAccount, String recipientIdAccount
            , String recipient, String sender, String title, BigDecimal value);

    boolean sendTransfer(Map<List<Object>, String> dataToUpdate);
}
