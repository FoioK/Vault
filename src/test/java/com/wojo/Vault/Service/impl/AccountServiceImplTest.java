package com.wojo.Vault.Service.impl;

import com.wojo.Vault.Database.Model.Account;
import com.wojo.Vault.Database.Model.Person;
import com.wojo.Vault.Service.AccountService;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class AccountServiceImplTest {

    private AccountService accountService = new AccountServiceImpl();

    @Test
    public void shouldReturnFormattedNumber() {
        String accountNumber = "PL12345678901234567890123496";
        String formattedAccountNumber = "12 3456 7890 1234 5678 9012 3496";

        Account account = new Account();
        account.setIBAN_NUMBER(accountNumber);
        Person.setAccounts(Collections.singletonList(account));

        assertEquals(formattedAccountNumber, accountService.getFormatAccountNumber());
    }

    @Test
    public void formattedNumberShouldHaveLength32() {
        Person.setAccounts(Collections.singletonList(new Account("PL", 26, BigDecimal.ZERO)));
        assertEquals(32, accountService.getFormatAccountNumber().length());
    }

}