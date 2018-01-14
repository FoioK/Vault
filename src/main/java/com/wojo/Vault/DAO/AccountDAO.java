package com.wojo.Vault.DAO;

import com.wojo.Vault.Model.Account;
import com.wojo.Vault.Model.Person;
import com.wojo.Vault.Util.DBUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class AccountDAO {

    public static void addNewAccount(int idPerson, String countryCode, int length)
            throws SQLException {
        Account account = new Account(countryCode, length);
        insertAccountToDB(account, idPerson);
    }

    public static boolean insertAccountToDB(Account account, int idPerson) throws SQLException {
        String updateStatement = "INSERT INTO accounts " +
                "(idPerson, number, value) " +
                "VALUES " +
                "(?, ?, ?)";
        DBUtil.dbExecuteUpdated(updateStatement,
                Arrays.asList(String.valueOf(idPerson),
                        String.valueOf(account.getIBAN_NUMBER()),
                        String.valueOf(account.getValue())));
        return true;
    }

    public static void insertAccountDate(int idPerson) throws SQLException {
        String queryStatement = "SELECT * FROM accounts WHERE idPerson = ?";
        ResultSet resultSet = DBUtil.dbExecuteQuery(queryStatement,
                Arrays.asList(String.valueOf(idPerson)));
        if (resultSet.next()) {
            Account account = new Account(resultSet.getString("number"));
            account.setValue(resultSet.getInt("value"));
            Person.addAccount(account);
        }
    }

    public static <T> void deleteAccount(T value) throws SQLException {
        String updateStatement = "DELETE FROM accounts WHERE idPerson = ?";
        DBUtil.dbExecuteUpdated(updateStatement, Arrays.asList(String.valueOf(value)));
    }
}
