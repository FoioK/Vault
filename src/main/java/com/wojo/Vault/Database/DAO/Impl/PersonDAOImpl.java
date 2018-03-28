package com.wojo.Vault.Database.DAO.Impl;

import com.wojo.Vault.Database.DAO.PersonDAO;
import com.wojo.Vault.Database.DBManager;
import com.wojo.Vault.Database.Model.Address;
import com.wojo.Vault.Database.Model.Person;
import com.wojo.Vault.Exception.ExecuteStatementException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

public class PersonDAOImpl implements PersonDAO {

    public boolean insertPersonAndAddress(Person person, Address address) {
        Integer personId;
        Integer addressId;
        try {
            personId = getLastPersonId() + 1;
            addressId = getLastAddressId() + 1;
        } catch (SQLException e) {
            System.out.println("Find max personId and addressId error");
            return false;
        }

        if (personId < 0 || addressId < 0) {
            return false;
        }

        String disableForeignKeyCheck = "SET FOREIGN_KEY_CHECKS = 0";
        String enableForeignKeyCheck = "SET FOREIGN_KEY_CHECKS = 1";

        String updatePersonStatement = "INSERT INTO person " +
                "(PERSON_ID, FIRST_NAME, LAST_NAME, TELEPHONE_NUMBER, LOGIN, PASSWORD, CREATE_TIME) " +
                "VALUES " +
                "(?, ?, ?, ?, ?, ?, ?)";
        List<String> dataToUpdatePerson = Arrays.asList(
                String.valueOf(personId),
                person.getFirstName(),
                person.getLastName(),
                person.getTelephoneNumber(),
                person.getLogin(),
                String.valueOf(person.getPassword()),
                person.getCreateTime().toString()
        );

        String updateAddressStatement = "INSERT INTO address (ADDRESS_ID, CITY, STREET, APARTMENT_NUMBER) VALUES (?, ?, ?, ?)";
        List<String> dataToUpdateAddress = Arrays.asList(
                String.valueOf(addressId),
                address.getCity(),
                address.getStreet(),
                address.getApartmentNumber()
        );

        String updatePersonHasAddressStatement =
                "INSERT INTO person_has_address (PERSON_ID, ADDRESS_ID) VALUES (?, ?)";
        List<String> dataToUpdatePersonHasAddress = Arrays.asList(
                String.valueOf(personId),
                String.valueOf(addressId)
        );

        Map<List<String>, String> dataToUpdate = new HashMap<>(3);
        dataToUpdate.put(dataToUpdatePerson, updatePersonStatement);
        dataToUpdate.put(dataToUpdateAddress, updateAddressStatement);
        dataToUpdate.put(dataToUpdatePersonHasAddress, updatePersonHasAddressStatement);

        try {
            return DBManager.dbExecuteUpdate(disableForeignKeyCheck, null) == 0 &&
                    DBManager.dbExecuteTransactionUpdate(dataToUpdate) &&
                    DBManager.dbExecuteUpdate(enableForeignKeyCheck, null) == 0;
        } catch (ExecuteStatementException e) {
            System.out.println(e.errorCode() + ": Insert person");
        }

        return false;
    }

    private Integer getLastPersonId() throws SQLException {
        String queryStatement = "SELECT MAX(PERSON_ID) FROM person";
        ResultSet resultSet = DBManager.dbExecuteQuery(queryStatement, null);

        return resultSet.next() ? resultSet.getInt(1) : -1;
    }

    private Integer getLastAddressId() throws SQLException {
        String queryStatement = "SELECT MAX(ENTRY_ID) FROM person_has_address";
        ResultSet resultSet = DBManager.dbExecuteQuery(queryStatement, null);

        return resultSet.next() ? resultSet.getInt(1) : -1;
    }

    /**
     *
     * @return return Person object, when don't find person return object with id "-1"
     * or null if the case of an exception.
     */
    public Person findByLogin(String login) {
        String queryStatement = "SELECT * FROM person WHERE LOGIN = ?";

        ResultSet resultSet;
        try {
            resultSet = DBManager.dbExecuteQuery(queryStatement, Collections.singletonList(login));
            return resultSet.next() ? getPersonObject(resultSet) : new Person("-1");
        } catch (ExecuteStatementException e) {
            System.out.println("find person by login: " + e.errorCode());
        } catch (SQLException e) {
            System.out.println("ResultSet error: Find login");
        }

        return null;
    }

    @Override
    public String findIdByLogin(String login) {
        String queryStatement = "SELECT PERSON_ID FROM person WHERE LOGIN LIKE ?";


        ResultSet resultSet;
        try {
            resultSet = DBManager.dbExecuteQuery(queryStatement, Collections.singletonList(login));
            return resultSet.next() ? resultSet.getString("PERSON_ID") : "";
        } catch (ExecuteStatementException e) {
            System.out.println("find person id by login: " + e.errorCode());
        } catch (SQLException e) {
            System.out.println("ResultSet error: Find person id by login");
        }

        return "";
    }

    private Person getPersonObject(ResultSet resultSet) throws SQLException {
        return new Person(
                resultSet.getString("PERSON_ID"),
                resultSet.getString("FIRST_NAME"),
                resultSet.getString("LAST_NAME"),
                resultSet.getString("TELEPHONE_NUMBER"),
                resultSet.getString("LOGIN"),
                resultSet.getString("PASSWORD").toCharArray(),
                LocalDateTime.ofInstant(
                        Instant.ofEpochMilli(resultSet.getTimestamp("CREATE_TIME").getTime()),
                        TimeZone.getDefault().toZoneId())
        );
    }

    @Override
    public boolean isLoginExist(String login) {
        String queryStatement = "SELECT COUNT(LOGIN) FROM person WHERE LOGIN LIKE ?";

        ResultSet resultSet;
        try {
            resultSet = DBManager.dbExecuteQuery(queryStatement, Collections.singletonList(login));
            return resultSet.next() && resultSet.getInt(1) > 0;
        } catch (ExecuteStatementException e) {
            System.out.println("check login: " + e.errorCode());
        } catch (SQLException e) {
            System.out.println("ResultSet error: check login");
        }

        return false;
    }
}
