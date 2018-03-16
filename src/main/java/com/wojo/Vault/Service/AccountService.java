package com.wojo.Vault.Service;

import com.wojo.Vault.Database.Model.Account;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {
    Account addNewAccount(Integer idPerson, String countryCode, int length);

    boolean createAccount(String login, List<String> accountDataList, String countryCode, int length);

    String getFormatAccountNumber();

    BigDecimal getAccountValue();

    boolean addValueToAccount(BigDecimal value, String number);
}
