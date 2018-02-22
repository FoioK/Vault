package com.wojo.Vault.Database;

import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DBManagerTest {

    /**
     * Test person data
     */
    private static final Integer idPerson = 1;
    private static final String FIRST_NAME = "FirstName";
    private static final String LAST_NAME = "LastName";
    private static final String PERSON_ID = "00000000000";
    private static final String ADDRESS = "address";
    private static final String TELEPHONE_NUMBER = "123456789";
    private static final String EMAIL = "email";
    private static final String LOGIN = "ABCDEFGHI";
    private static final String PASSWORD = "Test";

    private static final Integer idPerson_TO_SET = 2;

    private static final String INSERT_FIRST_NAME = "Jan";
    private static final String INSERT_LAST_NAME = "Kowalski";
    private static final String INSERT_PERSON_ID = "70010100123";
    private static final String INSERT_ADDRESS = "nowa 17";
    private static final String INSERT_TELEPHONE_NUMBER = "700500600";
    private static final String INSERT_EMAIL = "jan.kowalski@email.com";
    private static final String INSERT_LOGIN = "ZXCVBNMPO";
    private static final String INSERT_PASSWORD = "Janek";

    @BeforeClass
    public static void setConnectionTestPath() {
        DBManager.setTestConnectionPath();
    }

    @Test(expected = SQLException.class)
    public void shouldNotExecuteQuery() throws SQLException {
        DBManager.dbExecuteQuery(null, null);
    }

    @Test(expected = SQLException.class)
    public void queryWithWrongSizeOfTheDataList() throws SQLException {
        String queryStatement = "SELECT idPerson FROM bankdata.person " +
                "WHERE LOGIN = ? AND PASSWORD = ?";
        DBManager.dbExecuteQuery(queryStatement, Collections.singletonList("Login"));
    }

    @Test
    public void shouldReturnCorrectDate() throws SQLException {
        String queryStatement = "SELECT * FROM person WHERE idPerson = ?;";
        ResultSet resultSet = DBManager.dbExecuteQuery(
                queryStatement, Collections.singletonList(String.valueOf(idPerson)));
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

    @Test(expected = SQLException.class)
    public void shouldNotExecuteUpdate() throws SQLException {
        DBManager.dbExecuteUpdate(null, null);
    }

    @Test(expected = SQLException.class)
    public void updateWithWrongSizeOfTheDataList() throws SQLException {
        String queryStatement = "UPDATE person SET EMAIL = ? WHERE idPerson = ?";
        assertEquals(1, DBManager.dbExecuteUpdate(queryStatement, Collections.singletonList("Email")));
    }

    @Test
    public void executeUpdateTest() throws SQLException {
        String queryStatementSetNewValue = "UPDATE person SET " +
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
        String newValue = "SET_TEST";
        int listDateSize = 8;
        for (int i = 0; i < listDateSize; i++) {
            updateDate.add(newValue);
        }
        updateDate.add(String.valueOf(idPerson_TO_SET));
        assertEquals(1, DBManager.dbExecuteUpdate(queryStatementSetNewValue, updateDate));
    }

    @Test
    public void shouldInsertNewRow() throws SQLException {
        String updateStatement = "INSERT INTO person " +
                "(FIRST_NAME, LAST_NAME, PERSON_ID" +
                ", ADDRESS, TELEPHONE_NUMBER, EMAIL" +
                ", LOGIN, PASSWORD) " +
                "VALUES " +
                "(?, ?, ?, ?, ?, ?, ?, ?)";
        List<String> updateData =
                Arrays.asList(INSERT_FIRST_NAME, INSERT_LAST_NAME, INSERT_PERSON_ID
                        , INSERT_ADDRESS, INSERT_TELEPHONE_NUMBER, INSERT_EMAIL
                        , INSERT_LOGIN, INSERT_PASSWORD);
        assertEquals(1, DBManager.dbExecuteUpdate(updateStatement, updateData));
    }

    @Test
    public void OneWrongStatementInTheTransaction() throws SQLException {
        String updateStatementFirstAndSecond = "UPDATE accounts " +
                "SET value = ? " +
                "WHERE idAccount = ?";
        List<Object> firstData = Arrays.asList(new BigDecimal("50000"), "1");
        List<Object> secondData = Arrays.asList(new BigDecimal("50000"), "2");

        String updateStatementThird = "INSERT INTO payments " +
                "(idAccount, recipientIdAccount, recipientName, senderName, title, paymentValue, date) " +
                "VALUES " +
                "(?, ?, ?, ?, ?, ?, ?)";
        List<Object> thirdData = Arrays.asList("1", "2", "Recipient", "Sender", "Title"
                , new BigDecimal("1"), new Date());

        Map<List<Object>, String> dataToUpdate = new HashMap<>(3);
        dataToUpdate.put(firstData, updateStatementFirstAndSecond);
        dataToUpdate.put(secondData, updateStatementFirstAndSecond);
        dataToUpdate.put(thirdData, updateStatementThird);

        assertTrue(DBManager.dbExecuteTransactionUpdate(dataToUpdate));
    }
}
