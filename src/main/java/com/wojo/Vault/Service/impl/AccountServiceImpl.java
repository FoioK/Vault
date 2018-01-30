package com.wojo.Vault.Service.impl;

import com.wojo.Vault.Database.DAO.AccountDAO;
import com.wojo.Vault.Database.DAO.Impl.AccountDAOImpl;
import com.wojo.Vault.Database.Model.Account;
import com.wojo.Vault.Service.AccountService;

public class AccountServiceImpl implements AccountService {

    private AccountDAO accountDAO = new AccountDAOImpl();

    @Override
    public Account addNewAccount(Integer idPerson, String contryCode, int length) {
        return accountDAO.addNewAccount(idPerson, contryCode, length);
    }

    @Override
    public <T> void deleteAccount(T value) {
        accountDAO.deleteAccount(value);
    }
}
