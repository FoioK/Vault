package com.wojo.Vault.Service.impl;

import com.wojo.Vault.Database.DAO.AccountDAO;
import com.wojo.Vault.Database.DAO.Impl.AccountDAOImpl;
import com.wojo.Vault.Database.DAO.Impl.PaymentDAOImpl;
import com.wojo.Vault.Database.DAO.PaymentDAO;
import com.wojo.Vault.Database.Model.Account;
import com.wojo.Vault.Database.Model.Payment;
import com.wojo.Vault.Service.PaymentService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

public class PaymentServiceImpl implements PaymentService {

    private AccountDAO accountDAO = new AccountDAOImpl();
    private PaymentDAO paymentDAO = new PaymentDAOImpl();

    @Override
    public boolean sendTransfer(Account senderAccount,
                                String recipientName,
                                String recipientNumber,
                                String title,
                                BigDecimal amount) {
        if (!validateData(senderAccount, recipientName, recipientNumber, title, amount)) {
            return false;
        }

        Map<List<String>, String> dataToUpdate = new HashMap<>();
        String recipientAccountId = accountDAO.findIdByNumber(recipientNumber);

        String senderAccountId = senderAccount.getAccountId();
        try {
            if (!recipientAccountId.equals("")) {
                dataToUpdate.putAll(getTransferInsideData(senderAccountId, recipientAccountId, recipientNumber, amount));
            } else {
                dataToUpdate.putAll(getTransferOutsideData(senderAccountId, senderAccount.getNumber(), amount));
            }
            dataToUpdate.putAll(getInsertPaymentData(senderAccountId, recipientAccountId, recipientNumber,
                    recipientName, title, amount));
        } catch (NullPointerException e) {
            return false;
        }

        return dataToUpdate.size() != 0 && paymentDAO.sendTransfer(dataToUpdate);
    }

    private boolean validateData(Account senderAccount,
                                 String recipientName,
                                 String recipientNumber,
                                 String title,
                                 BigDecimal amount) {
        return senderAccount != null && recipientName != null && recipientNumber != null &&
                title != null && amount != null;
    }

    @Override
    public Map<List<String>, String> getTransferInsideData(String senderAccountId,
                                                           String recipientAccountId,
                                                           String recipientNumber,
                                                           BigDecimal amount) {
        BigDecimal recipientValue = accountDAO.getValueByNumber(recipientNumber);
        BigDecimal senderValue = accountDAO.getValueByNumber(recipientNumber);

        return senderValue.subtract(amount).compareTo(BigDecimal.ZERO) < 0 ? null :
                paymentDAO.getTransferInsideData(recipientAccountId, senderAccountId,
                        recipientValue.add(amount), senderValue.subtract(amount));
    }

    @Override
    public Map<List<String>, String> getTransferOutsideData(String senderAccountId,
                                                            String senderNumber,
                                                            BigDecimal amount) {
        BigDecimal senderValue = accountDAO.getValueByNumber(senderNumber);

        return senderValue.subtract(amount).compareTo(BigDecimal.ZERO) < 0 ? null :
                paymentDAO.getTransferOutsideData(senderAccountId, senderValue.subtract(amount));
    }

    @Override
    public Map<List<String>, String> getInsertPaymentData(String senderAccountId,
                                                          String recipientAccountId,
                                                          String recipientNumber,
                                                          String recipientName,
                                                          String title,
                                                          BigDecimal amount) {
        return paymentDAO.getInsertPaymentData(senderAccountId, recipientAccountId, recipientName,
                recipientNumber, amount, title, LocalDateTime.now());
    }

    @Override
    public List<Payment> findAll(String accountId) {
        List<Payment> allPayments = paymentDAO.findAll(accountId);

        if (allPayments == null || allPayments.size() == 0) {
            return new ArrayList<>();
        }

        allPayments.sort(Comparator.comparing(Payment::getDate));
        Collections.reverse(allPayments);

        return allPayments;
    }

    @Override
    public List<Payment> getLastThreePayment(String accountId) {
        List<Payment> allPayments = this.findAll(accountId);

        return allPayments.size() > 3 ? allPayments.subList(0, 3) : allPayments;
    }

    @Override
    public Payment getRecentDeposit(String accountId) {
        return this.findAll(accountId)
                .stream()
                .filter(payment -> payment.getAmount()
                        .compareTo(BigDecimal.ZERO) > 0)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Payment getRecentDebit(String accountId) {
        return this.findAll(accountId)
                .stream()
                .filter(payment -> payment.getAmount()
                        .compareTo(BigDecimal.ZERO) < 0)
                .findFirst()
                .orElse(null);
    }
}
