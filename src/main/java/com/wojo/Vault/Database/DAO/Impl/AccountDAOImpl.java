package com.wojo.Vault.Database.DAO.Impl;

import com.wojo.Vault.Database.DAO.AccountDAO;
import com.wojo.Vault.Database.DBManager;
import com.wojo.Vault.Database.Model.Account;
import com.wojo.Vault.Database.Model.Person;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;

public class AccountDAOImpl implements AccountDAO {

    public Account addNewAccount(Integer idPerson, String countryCode, int length) {
        Account account = new Account(countryCode, length, new BigDecimal("0"));
        return account.getIBAN_NUMBER().equals("") ?
                null :
                insertAccountToDB(account, idPerson) ?
                        account :
                        null;
    }

    public boolean insertAccountToDB(Account account, Integer idPerson) {
        if (account == null) {
            return false;
        }
        String updateStatement = "INSERT INTO accounts " +
                "(idPerson, number, value) " +
                "VALUES " +
                "(?, ?, ?)";
        try {
            DBManager.dbExecuteUpdate(updateStatement,
                    Arrays.asList(String.valueOf(idPerson),
                            String.valueOf(account.getIBAN_NUMBER()),
                            String.valueOf(account.getValue())));
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void insertAccountData(Integer idPerson) {
        String queryStatement = "SELECT * FROM accounts WHERE idPerson = ?";
        ResultSet resultSet;
        try {
            resultSet = DBManager.dbExecuteQuery(queryStatement,
                    Collections.singletonList(String.valueOf(idPerson)));
            if (resultSet.next()) {
                Account account = new Account(resultSet.getInt("idAccount"),
                        resultSet.getString("number"), new BigDecimal("0"));
                account.setValue(new BigDecimal(resultSet.getString("value")));
                Person.addAccount(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public <T> Integer deleteAccount(T value) {
        String updateStatement = "DELETE FROM accounts WHERE idPerson = ?";
        try {
            return DBManager.dbExecuteUpdate(updateStatement, Collections.singletonList(String.valueOf(value)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Integer searchAccountByNumber(String accountNumber) {
        String queryStatement = "SELECT idAccount FROM accounts " +
                "WHERE SUBSTRING(number, 3, 26) = ?";
        ResultSet resultSet;
        try {
            resultSet = DBManager.dbExecuteQuery(queryStatement, Collections.singletonList(accountNumber));
            return resultSet.next() ? resultSet.getInt("idAccount") : 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public BigDecimal getAccountValue(String idAccount) {
        String queryStatement = "SELECT value FROM accounts " +
                "WHERE idAccount = ?";
        ResultSet resultSet;
        try {
            resultSet = DBManager.dbExecuteQuery(queryStatement, Collections.singletonList(idAccount));
            return resultSet.next() ?
                    new BigDecimal(resultSet.getString("value")) : BigDecimal.ZERO;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return BigDecimal.ZERO;
    }

    @Override
    public Integer addAccountValue(String idAccount, BigDecimal value) {
        BigDecimal currentValue = this.getAccountValue(idAccount);

        String updateStatement = "UPDATE accounts " +
                "SET value = ? " +
                "WHERE idAccount = ?";

        try {
            return DBManager.dbExecuteUpdate(updateStatement,
                    Arrays.asList(currentValue.add(value).toString(), idAccount));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
