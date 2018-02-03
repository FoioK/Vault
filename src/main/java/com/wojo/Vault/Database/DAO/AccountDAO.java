package com.wojo.Vault.Database.DAO;

import com.wojo.Vault.Database.Model.Account;

import java.math.BigDecimal;

public interface AccountDAO {

    Account addNewAccount(Integer idPerson, String countryCode, int length);

    boolean insertAccountToDB(Account account, Integer idPerson);

    void insertAccountData(Integer idPerson);

    <T> void deleteAccount(T value);

    Integer searchAccount(String accountNumber);
    
    BigDecimal getAccountValue(String idAccount);
}
