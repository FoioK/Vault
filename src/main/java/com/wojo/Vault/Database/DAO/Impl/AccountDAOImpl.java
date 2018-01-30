package com.wojo.Vault.Database.DAO.Impl;

import com.wojo.Vault.Database.DAO.AccountDAO;
import com.wojo.Vault.Database.DBManager;
import com.wojo.Vault.Database.Model.Account;
import com.wojo.Vault.Database.Model.Person;

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
                Account account = new Account(resultSet.getString("number"));
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
}
