package com.wojo.Vault.Service.impl;

import com.wojo.Vault.Database.DAO.AccountDAO;
import com.wojo.Vault.Database.DAO.Impl.AccountDAOImpl;
import com.wojo.Vault.Database.DAO.Impl.PaymentDAOImpl;
import com.wojo.Vault.Database.DAO.PaymentDAO;
import com.wojo.Vault.Database.Model.Account;
import com.wojo.Vault.Database.Model.Person;
import com.wojo.Vault.Service.PaymentService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaymentServiceImpl implements PaymentService {

    AccountDAO accountDAO = new AccountDAOImpl();
    PaymentDAO paymentDAO = new PaymentDAOImpl();

    @Override
    public boolean sendTransfer(String recipient, String recipientNumber, String title,
                                BigDecimal value) {
        Map<List<Object>, String> dataToUpdate = new HashMap<>();
        //TODO getId active account
        Integer activeAccountId = 0;
        Account senderAccount = Person.getAccounts().get(activeAccountId);
        Integer recipientIdAccount = accountDAO.searchAccount(recipientNumber);
        if (recipientIdAccount != 0) {
            dataToUpdate.putAll(getTransferInsideData(senderAccount, String.valueOf(recipientIdAccount), value));
        } else {
            dataToUpdate.putAll(getTransferOutsideData(senderAccount, value));
        }
        dataToUpdate.putAll(getInsertPaymentData(senderAccount, String.valueOf(recipientIdAccount)
                , recipient, title, value));

        return dataToUpdate.size() != 0 && paymentDAO.sendTransfer(dataToUpdate);
    }

    private Map<List<Object>, String> getTransferInsideData(Account senderAccount, String recipientIdAccount
            , BigDecimal value) {
        String senderIdAccount = String.valueOf(senderAccount.getIdAccount());
        BigDecimal recipientValue = accountDAO.getAccountValue(recipientIdAccount);
        BigDecimal senderValue = accountDAO.getAccountValue(senderIdAccount);

        return paymentDAO.getTransferInsideData(recipientIdAccount, senderIdAccount
                , recipientValue.add(value), senderValue.subtract(value));
    }

    private Map<List<Object>, String> getTransferOutsideData(Account senderAccount, BigDecimal value) {
        String senderIdAccount = String.valueOf(senderAccount.getIdAccount());
        BigDecimal senderValue = accountDAO.getAccountValue(senderIdAccount);

        return paymentDAO.getTransferOutsideData(senderIdAccount, senderValue.subtract(value));
    }

    private Map<List<Object>, String> getInsertPaymentData(Account senderAccount, String recipientIdAccount
            , String recipient, String title, BigDecimal value) {
        String senderIdAccount = String.valueOf(senderAccount.getIdAccount());
        String sender = Person.getFirstName() + " " + Person.getLastName();

        return paymentDAO.getInsertPaymentData(senderIdAccount, recipientIdAccount, recipient
                , sender, title, value);
    }
}
