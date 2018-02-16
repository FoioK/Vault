package com.wojo.Vault.Database.DAO.Impl;

import com.wojo.Vault.Database.DAO.AccountDAO;
import com.wojo.Vault.Database.DAO.PersonDAO;
import com.wojo.Vault.Database.Model.Person;
import com.wojo.Vault.Database.DBManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class PersonDAOImpl implements PersonDAO {

    private AccountDAO accountDAO = new AccountDAOImpl();

    public boolean searchPersonLogin(String login) {
        if (login == null) {
            return false;
        }
        String queryStatement = "SELECT COUNT(LOGIN) FROM person WHERE LOGIN LIKE ?";
        ResultSet resultSet;
        try {
            resultSet = DBManager.dbExecuteQuery(queryStatement, Arrays.asList(login));
            return resultSet.next() && resultSet.getInt(1) != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String[] getIdPersonAndPassword(String login) {
        //TODO zapezpieczyc NullPointerException w miejscach użycia metody
        String queryStatement = "SELECT idPerson, PASSWORD FROM person " +
                "WHERE LOGIN LIKE ?";
        ResultSet resultSet;
        String[] idPersonAndPassword = new String[2];
        try {
            resultSet = DBManager.dbExecuteQuery(queryStatement, Arrays.asList(login));
            if (resultSet.next()) {
                idPersonAndPassword[0] = resultSet.getString("idPerson");
                idPersonAndPassword[1] = resultSet.getString("PASSWORD");
                return idPersonAndPassword;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insertPersonData(Integer idPerson) {
        String queryStatement = "SELECT * FROM person WHERE idPerson = ?";
        ResultSet resultSet;
        try {
            resultSet = DBManager.dbExecuteQuery(queryStatement,
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
                accountDAO.insertAccountData(idPerson);
                return true;
            }
            else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int insertPersonToDB(List<String> accountData) {
        if (accountData.size() < 8) {
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
        try {
            DBManager.dbExecuteUpdate(updateStatement, accountData);
            return getIdPerson(accountData.get(6));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private int getIdPerson(String login) {
        String updateStatement = "SELECT idPerson FROM person " +
                "WHERE LOGIN LIKE ?";
        ResultSet resultSet;
        try {
            resultSet = DBManager.dbExecuteQuery(updateStatement, Arrays.asList(login));
            return resultSet.next() ? resultSet.getInt("idPerson") : 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public <T> boolean deletePerson(T value) {
        String updateStatement;
        if (value instanceof Integer) {
            updateStatement = "DELETE FROM person WHERE idPerson = ?";
            System.out.println(String.valueOf(value));
            try {
                DBManager.dbExecuteUpdate(updateStatement, Arrays.asList(String.valueOf(value)));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            accountDAO.deleteAccount(value);
            return true;
        } else if (value instanceof String && ((String) value).length() != 0) {
            updateStatement = "DELETE FROM person WHERE LOGIN LIKE ? OR " +
                    "(FIRST_NAME LIKE ? AND LAST_NAME LIKE ?)";
            Integer idPerson = 0;
            try {
                DBManager.dbExecuteUpdate(updateStatement,
                        Arrays.asList(String.valueOf(value), String.valueOf(value),
                                String.valueOf(value)));
                idPerson = getIdPerson(String.valueOf(value));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (idPerson != 0) {
                accountDAO.deleteAccount(idPerson);
            }
            return true;
        } else {
            return false;
        }
    }
}
