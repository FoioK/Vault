package com.wojo.Vault.Service;

import com.wojo.Vault.Database.Model.Account;

import java.math.BigDecimal;

public interface AccountService {
    Account addNewAccount(Integer idPerson, String contryCode, int length);

    <T> void deleteAccount(T value);

    String getFormatAccountNumber();

    BigDecimal getAccountValue();
}
