package com.wojo.Vault.Database;

import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DBManagerTest {

    /**
     * TestAccount in database
     * idPeron:1
     * FIRST_NAME:TestAccount
     * LAST_NAME:TestAccount
     * PERSON_ID:00000000000
     * ADDRESS:TestAccount
     * TELEPHONE_NUMBER:123456789
     * EMAIL:TestAccount
     * LOGIN:ABCDEFGHI
     * PASSWORD:Test
     */
    private static final Integer idPerson = 1;
    private static final String FIRST_NAME = "TestAccount";
    private static final String LAST_NAME = "TestAccount";
    private static final String PERSON_ID = "00000000000";
    private static final String ADDRESS = "TestAccount";
    private static final String TELEPHONE_NUMBER = "123456789";
    private static final String EMAIL = "TestAccount";
    private static final String LOGIN = "ABCDEFGHI";
    private static final String PASSWORD = "Test";


    @Test(expected = SQLException.class)
    public void shouldntExecuteQuery() throws SQLException {
        DBManager.dbExecuteQuery(null, null);
    }

    @Test(expected = SQLException.class)
    public void shouldntExecuteUpdate() throws SQLException {
        DBManager.dbExecuteUpdate(null, null);
    }

    @Test
    public void executeUpdateTest() throws SQLException {
        String queryStatementSetNewValue = "UPDATE person " +
                "SET " +
                "FIRST_NAME = ?, " +
                "LAST_NAME = ?, " +
                "PERSON_ID = ?, " +
                "ADDRESS = ?, " +
                "TELEPHONE_NUMBER = ?, " +
                "EMAIL = ?, " +
                "LOGIN = ?, " +
                "PASSWORD = ? " +
                "WHERE idPerson = ?";
        List<String> updateDate = new ArrayList<>();
        String newValue = "NewValue";
        int listDateSize = 8;
        for (int i = 0; i < listDateSize; i++) {
            updateDate.add(newValue);
        }
        updateDate.add(String.valueOf(idPerson));
        DBManager.dbExecuteUpdate(queryStatementSetNewValue, updateDate);

        String queryStatementSetOldValue = "UPDATE person " +
                "SET " +
                "FIRST_NAME = ?, " +
                "LAST_NAME = ?, " +
                "PERSON_ID = ?, " +
                "ADDRESS = ?, " +
                "TELEPHONE_NUMBER = ?, " +
                "EMAIL = ?, " +
                "LOGIN = ?, " +
                "PASSWORD = ? " +
                "WHERE idPerson = ?";
        updateDate = Arrays.asList(
                FIRST_NAME,
                LAST_NAME,
                PERSON_ID,
                ADDRESS,
                TELEPHONE_NUMBER,
                EMAIL,
                LOGIN,
                PASSWORD,
                String.valueOf(idPerson)
        );
        DBManager.dbExecuteUpdate(queryStatementSetOldValue, updateDate);
    }

    @Test
    public void shouldReturnCorrectDate() throws SQLException {
        String queryStatement = " SELECT * FROM person WHERE idPerson = ?;";
        ResultSet resultSet = DBManager.dbExecuteQuery(
                queryStatement, Arrays.asList(String.valueOf(idPerson)));
        if (resultSet.next()) {
            assertEquals(String.valueOf(idPerson), resultSet.getString("idPerson"));
            assertEquals(FIRST_NAME, resultSet.getString("FIRST_NAME"));
            assertEquals(LAST_NAME, resultSet.getString("LAST_NAME"));
            assertEquals(PERSON_ID, resultSet.getString("PERSON_ID"));
            assertEquals(ADDRESS, resultSet.getString("ADDRESS"));
            assertEquals(TELEPHONE_NUMBER, resultSet.getString("TELEPHONE_NUMBER"));
            assertEquals(EMAIL, resultSet.getString("EMAIL"));
            assertEquals(LOGIN, resultSet.getString("LOGIN"));
            assertEquals(PASSWORD, resultSet.getString("PASSWORD"));
        }
    }
}
