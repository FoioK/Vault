package com.wojo.Vault.DAO;

import com.wojo.Vault.Model.Account;
import com.wojo.Vault.Model.Person;
import com.wojo.Vault.Util.DBUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDAO {

    public static void addNewAccount(int idPerson, String countryCode, int length) {
        Account account = new Account(idPerson, countryCode, length);
        insertAccountToDB(account);
    }

    public static boolean insertAccountToDB(Account account) {
        String updateStmt = "INSERT INTO `bankdate`.`accounts`" +
                "   (`idPerson`, `number`, `value`)" +
                "VALUES" +
                "   ('" + account.getIdPerson() + "', '"
                + account.getIbanNumber() + "', '"
                + account.getValue() + "');";
        try {
            DBUtil.dbExecuteUpdate(updateStmt);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static void insertAccountDate(int idPerson) {
        String queryStatement = "SELECT * FROM accounts" +
                " WHERE " +
                "   idPerson = '" + idPerson + "';";
        ResultSet resultSet = null;
        try {
            resultSet = DBUtil.dbExecuteQuery(queryStatement);
            resultSet.next();
        } catch (SQLException e) {
//            e.printStackTrace();
        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
        }
        try {
            Account account = new Account();
            account.setIdAccount(resultSet.getInt(1));
            account.setIdPerson(resultSet.getInt(2));
            account.setIbanNumber(resultSet.getString(3));
            account.setValue(resultSet.getInt(4));
            Person.addAccount(account);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
