package com.wojo.Vault.Database.DAO;

import com.wojo.Vault.Database.Model.Account;

import java.math.BigDecimal;
import java.util.List;

public interface AccountDAO {

    List<Account> findAllByPersonId(String personId);
    
    String findIdByNumber(String number);

    boolean isNumberExist(String accountNumber);

    boolean insertAccount(Account account);

    boolean setValue(String number, BigDecimal value);

    BigDecimal getValueByNumber(String number);
}
