package com.wojo.Vault.Service;

import com.wojo.Vault.Database.Model.Account;
import com.wojo.Vault.Database.Model.Payment;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface PaymentService {

    boolean sendTransfer(Account senderAccount,
                         String recipient,
                         String recipientNumber,
                         String title,
                         BigDecimal amount);

    Map<List<String>, String> getTransferInsideData(String senderAccountId,
                                                    String recipientAccountId,
                                                    String recipientNumber,
                                                    BigDecimal amount);

    Map<List<String>, String> getTransferOutsideData(String senderAccountId,
                                                     String senderNumber,
                                                     BigDecimal amount);

    Map<List<String>, String> getInsertPaymentData(String senderAccountId,
                                                   String recipientAccountId,
                                                   String recipientNumber,
                                                   String recipientName,
                                                   String title,
                                                   BigDecimal amount);

    List<Payment> findAll(String accountId);

    List<Payment> getLastThreePayment(String accountId);

    Payment getRecentDeposit(String accountId);

    Payment getRecentDebit(String accountId);
}
