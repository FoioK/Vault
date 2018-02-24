package com.wojo.Vault.Service;

import com.wojo.Vault.Database.Model.Account;

public interface AccountService {
    Account addNewAccount(Integer idPerson, String contryCode, int length);

    <T> void deleteAccount(T value);

    String getFormatAccountNumber();
}
