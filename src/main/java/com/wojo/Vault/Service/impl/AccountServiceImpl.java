package com.wojo.Vault.Service.impl;

import com.wojo.Vault.Database.DAO.AccountDAO;
import com.wojo.Vault.Database.DAO.Impl.AccountDAOImpl;
import com.wojo.Vault.Database.Model.Account;
import com.wojo.Vault.Database.Model.Generators.AccountDataGenerator;
import com.wojo.Vault.Database.Model.Person;
import com.wojo.Vault.Service.AccountService;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class
AccountServiceImpl implements AccountService {

    private AccountDAO accountDAO = new AccountDAOImpl();

    @Override
    public boolean createAccount(Account account) {
        String number = new AccountDataGenerator().generateIBAN(Account.NUMBER_LENGTH);

        if (account != null && !number.equals("")) {
            account.setNumber(number);

            return accountDAO.insertAccount(account);
        }

        return false;
    }

    @Override
    public boolean addValue(BigDecimal value, String number) {
        if (number == null || value == null) {
            return false;
        }

        BigDecimal currentValue = accountDAO.getValueByNumber(number);
        if (currentValue.compareTo(BigDecimal.ZERO) < 0) {
            return false;
        }

        BigDecimal newValue = currentValue.add(value).setScale(Account.ROUND_SCALE, BigDecimal.ROUND_HALF_EVEN);

        return accountDAO.setValue(number, newValue);
    }

    @Override
    public String getFormatAccountNumber(Account account) {
        final int firstPartLength = 2;
        List<String> formatNumberInParts = Arrays.asList(account
                .getNumber()
                .substring(firstPartLength)
                .split(String.format("(?<=\\G.{%1$d})", 4)));

        StringBuilder formatNumber = new StringBuilder();
        formatNumber.append(account.getNumber(), 0, firstPartLength).append(" ");
        formatNumberInParts.forEach(part -> {
            part += " ";
            formatNumber.append(part);
        });

        return formatNumber.toString();
    }

    @Override
    public boolean setAccounts(Person person) {
        if (person == null) {
            return false;
        }

        person.setAccountList(accountDAO.findAllByPersonId(person.getPersonId()));

        return true;
    }
}
