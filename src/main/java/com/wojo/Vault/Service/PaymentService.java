package com.wojo.Vault.Service;

import com.wojo.Vault.Database.Model.Account;
import com.wojo.Vault.Database.Model.Payment;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface PaymentService {

    boolean sendTransfer(String recipient, String recipientNumber, String title, BigDecimal value);

    Map<List<Object>, String> getTransferInsideData(Account senderAccount, String recipientIdAccount
            , BigDecimal value);

    Map<List<Object>, String> getTransferOutsideData(Account senderAccount, BigDecimal value);

    Map<List<Object>, String> getInsertPaymentData(Account senderAccount, String recipientIdAccount
            , String recipient, String title, BigDecimal value);

    String getFormatAccountNumber();

    List<Payment> getAllPayment();
}
