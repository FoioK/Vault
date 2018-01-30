package com.wojo.Vault.Database.DAO;

import com.wojo.Vault.Database.Model.Account;

public interface AccountDAO {

    Account addNewAccount(Integer idPerson, String countryCode, int length);

    boolean insertAccountToDB(Account account, Integer idPerson);

    void insertAccountData(Integer idPerson);

    <T> void deleteAccount(T value);
}
