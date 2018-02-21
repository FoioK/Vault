package com.wojo.Vault.Service.impl;

import com.wojo.Vault.Database.DAO.AccountDAO;
import com.wojo.Vault.Database.DAO.Impl.AccountDAOImpl;
import com.wojo.Vault.Database.DAO.Impl.PaymentDAOImpl;
import com.wojo.Vault.Database.DAO.PaymentDAO;
import com.wojo.Vault.Database.Model.Account;
import com.wojo.Vault.Database.Model.Payment;
import com.wojo.Vault.Database.Model.Person;
import com.wojo.Vault.Service.PaymentService;

import java.math.BigDecimal;
import java.util.*;

public class PaymentServiceImpl implements PaymentService {

    private AccountDAO accountDAO = new AccountDAOImpl();
    private PaymentDAO paymentDAO = new PaymentDAOImpl();

    //TODO getId active account
    private static Integer activeAccountId = 0;

    @Override
    public boolean sendTransfer(String recipient, String recipientNumber, String title,
                                BigDecimal value) {
        Map<List<Object>, String> dataToUpdate = new HashMap<>();
        Account senderAccount = Person.getAccounts().get(activeAccountId);
        Integer recipientIdAccount = accountDAO.searchAccountByNumber(recipientNumber);
        try {
            if (recipientIdAccount != 0) {
                dataToUpdate.putAll(getTransferInsideData(senderAccount, String.valueOf(recipientIdAccount), value));
            } else {
                dataToUpdate.putAll(getTransferOutsideData(senderAccount, value));
            }
            dataToUpdate.putAll(getInsertPaymentData(senderAccount, String.valueOf(recipientIdAccount)
                    , recipient, title, value));
        } catch (NullPointerException e) {
            return false;
        }
        return dataToUpdate.size() != 0 && paymentDAO.sendTransfer(dataToUpdate);
    }

    @Override
    public Map<List<Object>, String> getTransferInsideData(Account senderAccount, String recipientIdAccount
            , BigDecimal value) {
        if (senderAccount == null) {
            return null;
        }
        String senderIdAccount = String.valueOf(senderAccount.getIdAccount());
        BigDecimal recipientValue = accountDAO.getAccountValue(recipientIdAccount);
        BigDecimal senderValue = accountDAO.getAccountValue(senderIdAccount);

        return senderValue.subtract(value).compareTo(BigDecimal.ZERO) < 0 ? null :
                paymentDAO.getTransferInsideData(recipientIdAccount, senderIdAccount
                        , recipientValue.add(value), senderValue.subtract(value));
    }

    @Override
    public Map<List<Object>, String> getTransferOutsideData(Account senderAccount, BigDecimal value) {
        if (senderAccount == null) {
            return null;
        }
        String senderIdAccount = String.valueOf(senderAccount.getIdAccount());
        BigDecimal senderValue = accountDAO.getAccountValue(senderIdAccount);

        return senderValue.subtract(value).compareTo(BigDecimal.ZERO) < 0 ? null :
                paymentDAO.getTransferOutsideData(senderIdAccount, senderValue.subtract(value));
    }

    @Override
    public Map<List<Object>, String> getInsertPaymentData(Account senderAccount, String recipientIdAccount
            , String recipient, String title, BigDecimal value) {
        if (senderAccount == null) {
            return null;
        }
        String senderIdAccount = String.valueOf(senderAccount.getIdAccount());
        String sender = Person.getFirstName() + " " + Person.getLastName();

        return paymentDAO.getInsertPaymentData(senderIdAccount, recipientIdAccount, recipient
                , sender, title, value);
    }

    @Override
    public String getFormatAccountNumber() {
        List<String> formatNumberInParts = Arrays.asList(Person.getAccounts()
                .get(activeAccountId)
                .getIBAN_NUMBER()
                .split(String.format("(?<=\\G.{%1$d})", 4)));
        StringBuilder formatNumber = new StringBuilder();
        formatNumberInParts.forEach(part -> {
            part += " ";
            formatNumber.append(part);
        });
        return formatNumber.toString().substring(2, formatNumber.toString().length() - 1);
    }

    @Override
    public List<Payment> getAllPayment() {
        Integer activeAccountId = 0;
        Integer idAccount = Person.getAccounts().get(activeAccountId).getIdAccount();
        List<Payment> allPayments = paymentDAO.getAllPayment(idAccount);
        if (allPayments == null || allPayments.size() == 0) {
            return new ArrayList<>();
        }
        allPayments.sort(Comparator.comparing(Payment::getDate));
        Collections.reverse(allPayments);
        return allPayments;
    }
}
