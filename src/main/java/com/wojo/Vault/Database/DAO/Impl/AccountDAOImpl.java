package com.wojo.Vault.Database.DAO.Impl;

import com.wojo.Vault.Database.DAO.AccountDAO;
import com.wojo.Vault.Database.DBManager;
import com.wojo.Vault.Database.Model.Account;
import com.wojo.Vault.Database.Model.Person;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class AccountDAOImpl implements AccountDAO {

    public Account addNewAccount(Integer idPerson, String countryCode, int length) {
        Account account = new Account(countryCode, length);
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
        ResultSet resultSet = null;
        try {
            resultSet = DBManager.dbExecuteQuery(queryStatement,
                    Arrays.asList(String.valueOf(idPerson)));
            if (resultSet.next()) {
                Account account = new Account(resultSet.getInt("idAccount"),
                        resultSet.getString("number"));
                account.setValue(resultSet.getString("value"));
                Person.addAccount(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public <T> void deleteAccount(T value) {
        String updateStatement = "DELETE FROM accounts WHERE idPerson = ?";
        try {
            DBManager.dbExecuteUpdate(updateStatement, Arrays.asList(String.valueOf(value)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Integer searchAccount(String accountNumber) {
        String queryStatement = "SELECT idAccount FROM accounts " +
                "WHERE SUBSTRING(number, 3, 26) = ?";
        ResultSet resultSet;
        try {
            resultSet = DBManager.dbExecuteQuery(queryStatement, Arrays.asList(accountNumber));
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
            resultSet = DBManager.dbExecuteQuery(queryStatement, Arrays.asList(idAccount));
            return resultSet.next() ?
                    new BigDecimal(resultSet.getString("value")) : null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
