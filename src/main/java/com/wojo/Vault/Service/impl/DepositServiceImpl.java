package com.wojo.Vault.Service.impl;

import com.wojo.Vault.Database.DAO.DepositDAO;
import com.wojo.Vault.Database.DAO.Impl.DepositDAOImpl;
import com.wojo.Vault.Database.Model.Account;
import com.wojo.Vault.Database.Model.Deposits;
import com.wojo.Vault.Database.Model.DepositsModel.LongDeposit;
import com.wojo.Vault.Database.Model.DepositsModel.MiddleDeposit;
import com.wojo.Vault.Database.Model.DepositsModel.ShortDeposit;
import com.wojo.Vault.Database.Model.Person;
import com.wojo.Vault.Service.DepositService;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class DepositServiceImpl implements DepositService {

    private DepositDAO depositDAO = new DepositDAOImpl();

    @Override
    public boolean createDeposit(BigDecimal amount, Deposits.DepositType type) {
        Integer activeAccountId = 0;
        Account account = Person.getAccounts().get(activeAccountId);
        Deposits deposits;
        if (amount.compareTo(account.getValue()) > 0) {
            return false;
        }
        if (type == Deposits.DepositType.Short) {
            deposits = new ShortDeposit(account.getIdAccount(), amount, LocalDateTime.now());
        } else if (type == Deposits.DepositType.Middle) {
            deposits = new MiddleDeposit(account.getIdAccount(), amount, LocalDateTime.now());
        } else if (type == Deposits.DepositType.Long) {
            deposits = new LongDeposit(account.getIdAccount(), amount, LocalDateTime.now());
        } else {
            return false;
        }
        return depositDAO.insertDepositToDB(deposits) == 1;
    }
}
