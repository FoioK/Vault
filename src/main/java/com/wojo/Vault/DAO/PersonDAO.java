package com.wojo.Vault.DAO;

import com.wojo.Vault.Model.Person;
import com.wojo.Vault.Util.DBUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class PersonDAO {

    public static int insertPersonToDB(List<String> accountDate)
            throws SQLException {
        if (accountDate.size() < 8) {
            return -1;
        }

        String updateStatement = "INSERT INTO person " +
                "(FIRST_NAME, LAST_NAME, PERSON_ID, " +
                "ADDRESS, TELEPHONE_NUMBER, EMAIL, " +
                "LOGIN, PASSWORD) " +
                "VALUES " +
                "(?, ?, ?," +
                "?, ?, ?, " +
                "?, ?)";
        DBUtil.dbExecuteUpdated(updateStatement, accountDate);
        return getIdPerson(accountDate.get(6));
    }

    private static int getIdPerson(String login) throws SQLException {
        String updateStatement = "SELECT idPerson FROM person " +
                "WHERE LOGIN LIKE ?";
        ResultSet resultSet = DBUtil.dbExecuteQuery(updateStatement, Arrays.asList(login));
        return resultSet.next() ? resultSet.getInt("idPerson") : 0;
    }

    public static boolean searchPersonLogin(String login)
            throws SQLException {
        String queryStatement = "SELECT COUNT(LOGIN) FROM person WHERE LOGIN LIKE ?";
        ResultSet resultSet = DBUtil.dbExecuteQuery(queryStatement, Arrays.asList(login));
        return resultSet.next() ? resultSet.getInt(1) != 0 : false;
    }

    public static void insertPersonDate(int idPerson) throws SQLException {
        String quertyStatement = "SELECT * FROM person WHERE idPerson = ?";
        ResultSet resultSet = DBUtil.dbExecuteQuery(quertyStatement,
                Arrays.asList(String.valueOf(idPerson)));
        if (resultSet.next()) {
            Person.setIdPersonInDatabase(resultSet.getInt("idPerson"));
            Person.setFirstName(resultSet.getString("FIRST_NAME"));
            Person.setLastName(resultSet.getString("LAST_NAME"));
            Person.setPersonId(resultSet.getString("PERSON_ID"));
            Person.setAddress(resultSet.getString("ADDRESS"));
            Person.setTelephoneNumber(resultSet.getString("TELEPHONE_NUMBER"));
            Person.setEmail(resultSet.getString("EMAIL"));
            Person.setLogin(resultSet.getString("LOGIN"));
            Person.setPassword(resultSet.getString("PASSWORD"));
        }
        AccountDAO.insertAccountDate(idPerson);
    }

    public static <T> boolean deletePerson(T value) throws SQLException {
        String updateStatement;
        if (value instanceof Integer) {
            updateStatement = "DELETE FROM person WHERE idPerson = ?";
            DBUtil.dbExecuteQuery(updateStatement, Arrays.asList(String.valueOf(value)));
            AccountDAO.deleteAccount(value);
        } else if (value instanceof String) {
            updateStatement = "DELETE FROM person WHERE LOGIN LIKE ? OR " +
                    "(FIRST_NAME LIKE ? AND LAST_NAME LIKE ?)";
            DBUtil.dbExecuteUpdated(updateStatement,
                    Arrays.asList(String.valueOf(value), String.valueOf(value),
                            String.valueOf(value)));
            Integer idPerson = PersonDAO.getIdPerson(String.valueOf(value));
            if(idPerson != 0) {
                AccountDAO.deleteAccount(idPerson);
            }
        } else {
            return false;
        }
        return true;
    }

    public static String[] getIdPersonAndPassword(String login) throws SQLException {
        String queryStatement = "SELECT idPerson, PASSWORD FROM person " +
                "WHERE LOGIN LIKE ?";
        ResultSet resultSet = DBUtil.dbExecuteQuery(queryStatement, Arrays.asList(login));
        String[] idPersonAndPassword = new String[2];
        if (resultSet.next()) {
            idPersonAndPassword[0] = resultSet.getString("idPerson");
            idPersonAndPassword[1] = resultSet.getString("PASSWORD");
            return idPersonAndPassword;
        }
        else {
            return null;
        }
    }
}
