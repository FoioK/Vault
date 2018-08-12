package com.wojo.Vault.Database.DAO;

import com.wojo.Vault.Database.Model.Payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface PaymentDAO {

    Map<List<String>, String> getTransferInsideData(String recipientAccountId
            , String senderAccountId, BigDecimal recipientNewValue, BigDecimal senderNewValue);

    Map<List<String>, String> getTransferOutsideData(String senderAccountId
            , BigDecimal senderNewValue);

    Map<List<String>, String> getInsertPaymentData(String senderAccountId, String recipientAccountId
            , String recipientName, String recipientNumber, BigDecimal amount, String title, LocalDateTime date);

    <T> boolean sendTransfer(Map<List<T>, String> dataToUpdate);

    List<Payment> findAll(String accountId);

    List<Payment> findAllFromLastThreeMonth(String accountId);
}
