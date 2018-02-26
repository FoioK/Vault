package com.wojo.Vault.Service.impl;

import com.wojo.Vault.Database.DAO.AccountDAO;
import com.wojo.Vault.Database.DAO.Impl.AccountDAOImpl;
import com.wojo.Vault.Database.Model.Account;
import com.wojo.Vault.Database.Model.Person;
import com.wojo.Vault.Service.AccountService;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class AccountServiceImpl implements AccountService {

    private AccountDAO accountDAO = new AccountDAOImpl();

    //TODO getId active account
    private static Integer activeAccountId = 0;

    @Override
    public Account addNewAccount(Integer idPerson, String countryCode, int length) {
        return accountDAO.addNewAccount(idPerson, countryCode, length);
    }

//    @Override
//    public <T> void deleteAccount(T value) {
//        accountDAO.deleteAccount(value);
//    }

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
    public BigDecimal getAccountValue() {
        String idAccount = Person.getAccounts().get(activeAccountId).getIdAccount() + "";
        return accountDAO.getAccountValue(idAccount);
    }
}
