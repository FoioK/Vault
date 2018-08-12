package com.wojo.Vault.Service;

import com.wojo.Vault.Database.Model.Account;
import com.wojo.Vault.Database.Model.Person;

import java.math.BigDecimal;

public interface AccountService {

    boolean createAccount(Account account);

    boolean addValue(BigDecimal value, String number);

    String getFormatAccountNumber(Account account);

    boolean setAccounts(Person person);
}
