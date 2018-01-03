package com.wojo.Vault.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import com.wojo.Vault.Model.Person;
import com.wojo.Vault.Util.DBUtil;

public class PersonDAO {

    public static String generateLogin(int length)
            throws IllegalArgumentException {
        return length > 0 ? generateRandomString(new Random(), length) : "";
    }

    private static String generateRandomString(Random random, int length) {
        return random.ints('0', 'Z').filter(i -> (i < ':' || i > '@'))
                .mapToObj(i -> (char) i).limit(length)
                .collect(StringBuilder::new, StringBuilder::append,
                        StringBuilder::append)
                .toString();
    }

    public static int insertPersonToDB(List<String> accountDate)
            throws ClassNotFoundException, SQLException {
        if (accountDate.size() < 8) {
            return -1;
        }

        String updateStmt = "INSERT INTO `bankdate`.`person`\r\n"
                + "(`FIRST_NAME`,\r\n" + "`LAST_NAME`,\r\n" + "`PERSON_ID`,\r\n"
                + "`ADDRESS`,\r\n" + "`TELEPHONE_NUMBER`,\r\n" + "`EMAIL`,\r\n"
                + "`LOGIN`,\r\n" + "`PASSWORD`)\r\n" + "VALUES\r\n" + "('"
                + accountDate.get(0) + "',\r\n" + " '" + accountDate.get(1)
                + "',\r\n" + " '" + accountDate.get(2) + "', \r\n" + " '"
                + accountDate.get(3) + "',\r\n" + " '" + accountDate.get(4)
                + "',\r\n" + " '" + accountDate.get(5) + "',\r\n" + " '"
                + accountDate.get(6) + "',\r\n" + " '" + accountDate.get(7)
                + "');";
        DBUtil.dbExecuteUpdate(updateStmt);
        ResultSet resultSet = DBUtil.dbExecuteQuery("SELECT idPerson from person" +
                " WHERE" +
                "   login = '"+ accountDate.get(6) +"';");
        resultSet.next();
        return resultSet.getInt("idPerson");
    }

    public static boolean searchPersonLogin(String login)
            throws ClassNotFoundException, SQLException {
        String queryStatement = "SELECT COUNT(LOGIN) FROM person WHERE LOGIN = '"
                + login + "';";
        ResultSet resulSet = DBUtil.dbExecuteQuery(queryStatement);
        resulSet.next();

        return resulSet.getInt(1) != 0;
    }

    public static void insertPersonDate(int idPerson) {
        String queryStatement = "SELECT * FROM person WHERE idPerson = '"
                + idPerson + "'";
        ResultSet resultSet = null;
        try {
            resultSet = DBUtil.dbExecuteQuery(queryStatement);
            resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            Person.setIdPersonInDatabase(resultSet.getInt(1));
            Person.setFirstName(resultSet.getString(2));
            Person.setLastName(resultSet.getString(3));
            Person.setPersonId(resultSet.getString(4));
            Person.setAddress(resultSet.getString(5));
            Person.setTelephoneNumber(resultSet.getString(6));
            Person.setEmail(resultSet.getString(7));
            Person.setLogin(resultSet.getString(8));
            Person.setPassword(resultSet.getString(9));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        AccountDAO.insertAccountDate(idPerson);
    }

    public static <T> boolean deletePerson(T value) {
        String queryStatement = null;
        if (value instanceof Integer) {
            queryStatement = "DELETE FROM person WHERE idPerson = '" + value + "';";
        } else if (value instanceof String) {
            queryStatement = "DELETE FROM person WHERE LOGIN = '" + value +"' " +
                    "OR (FIRST_NAME = '" + value + "' AND LAST_NAME = '" + value + "');";
        } else {
            return false;
        }

        try {
            DBUtil.dbExecuteUpdate(queryStatement);
        } catch (SQLException e) {
            return false;
        } catch (ClassNotFoundException e) {
            return false;
        }

        return true;
    }
}
