package com.wojo.Vault.Database;

import org.junit.*;

import java.io.IOException;
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
    private static final String FIRST_NAME = "FirstName";
    private static final String LAST_NAME = "LastName";
    private static final String PERSON_ID = "00000000000";
    private static final String ADDRESS = "address";
    private static final String TELEPHONE_NUMBER = "123456789";
    private static final String EMAIL = "email";
    private static final String LOGIN = "ABCDEFGHI";
    private static final String PASSWORD = "Test";

    private static final String INSERT_FIRST_NAME = "Jan";
    private static final String INSERT_LAST_NAME = "Kowalski";
    private static final String INSERT_PERSON_ID = "70010100123";
    private static final String INSERT_ADDRESS = "nowa 17";
    private static final String INSERT_TELEPHONE_NUMBER = "700500600";
    private static final String INSERT_EMAIL = "jan.kowalski@email.com";
    private static final String INSERT_LOGIN = "ZXCVBNMPO";
    private static final String INSERT_PASSWORD = "Janek";

    @BeforeClass
    public static void connectionToTestDatabase() throws IOException, SQLException {
        DBManager.setTestConnectionPath();
        DBManager.dbConnection();
    }

    @AfterClass
    public static void clearDatabaseAndDisconnect() throws SQLException {
        String truncatePerson = "TRUNCATE TABLE person";
        DBManager.dbExecuteUpdate(truncatePerson, null);
        String truncateAccounts = "TRUNCATE TABLE accounts";
        DBManager.dbExecuteUpdate(truncateAccounts, null);
        String truncatePayments = "TRUNCATE TABLE payments";
        DBManager.dbExecuteUpdate(truncatePayments, null);
        DBManager.dbDisconnect();
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
        String updateStatement = "INSERT INTO person " +
                "(idPerson, FIRST_NAME, LAST_NAME, PERSON_ID, ADDRESS, TELEPHONE_NUMBER, EMAIL, LOGIN, PASSWORD) " +
                "VALUES " +
                "(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Integer uniqueIdPerson = 148;
        String uniquePassword = "Passwordddd";
        String uniqueTelephoneNumber = "765233111";
        assertEquals(1, DBManager.dbExecuteUpdate(updateStatement,
                Arrays.asList(String.valueOf(uniqueIdPerson), FIRST_NAME, LAST_NAME, PERSON_ID, ADDRESS,
                        uniqueTelephoneNumber, EMAIL, LOGIN, uniquePassword)));

        String queryStatement = "SELECT * FROM person WHERE idPerson = ?;";
        ResultSet resultSet = DBManager.dbExecuteQuery(
                queryStatement, Collections.singletonList(String.valueOf(uniqueIdPerson)));
        Assert.assertTrue(resultSet.next());
        if (resultSet.next()) {
            assertEquals(String.valueOf(uniqueIdPerson), resultSet.getString("idPerson"));
            assertEquals(FIRST_NAME, resultSet.getString("FIRST_NAME"));
            assertEquals(LAST_NAME, resultSet.getString("LAST_NAME"));
            assertEquals(PERSON_ID, resultSet.getString("PERSON_ID"));
            assertEquals(ADDRESS, resultSet.getString("ADDRESS"));
            assertEquals(uniqueTelephoneNumber, resultSet.getString("TELEPHONE_NUMBER"));
            assertEquals(EMAIL, resultSet.getString("EMAIL"));
            assertEquals(LOGIN, resultSet.getString("LOGIN"));
            assertEquals(uniquePassword, resultSet.getString("PASSWORD"));
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
        String updateStatement = "INSERT INTO person " +
                "(idPerson, FIRST_NAME, LAST_NAME, PERSON_ID, ADDRESS, TELEPHONE_NUMBER, EMAIL, LOGIN, PASSWORD) " +
                "VALUES " +
                "(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Integer uniqueIdPerson = 258;
        assertEquals(1, DBManager.dbExecuteUpdate(updateStatement,
                Arrays.asList(String.valueOf(uniqueIdPerson), FIRST_NAME, LAST_NAME, PERSON_ID, ADDRESS,
                        TELEPHONE_NUMBER, EMAIL, LOGIN, PASSWORD)));

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
        updateDate.add(String.valueOf(uniqueIdPerson));
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
        String updateStatement = "INSERT INTO accounts (idAccount, idPerson, number, value) VALUES (?, ?, ?, ?)";

        Integer firstIdAccount = 3478;
        Integer firstIdPerson = 1;
        String firstNumber = "PL47345678901266567899123456";

        Integer secondIdAccount = 4478;
        Integer secondIdPerson = 2;
        String secondNumber = "PL57345678901266567899123456";

        assertEquals(1, DBManager.dbExecuteUpdate(updateStatement,
                Arrays.asList(String.valueOf(firstIdAccount),
                        String.valueOf(firstIdPerson), firstNumber, BigDecimal.valueOf(1000.0).toString())));
        assertEquals(1, DBManager.dbExecuteUpdate(updateStatement,
                Arrays.asList(String.valueOf(secondIdAccount),
                        String.valueOf(secondIdPerson), secondNumber, BigDecimal.valueOf(1000.0).toString())));

        String updateStatementFirstAndSecond = "UPDATE accounts " +
                "SET value = ? " +
                "WHERE idAccount = ?";
        List<Object> firstData = Arrays.asList(BigDecimal.valueOf(900.0), firstIdAccount);
        List<Object> secondData = Arrays.asList(BigDecimal.valueOf(1100.0), secondIdAccount);

        String updateStatementThird = "INSERT INTO payments " +
                "(idAccount, recipientIdAccount, recipientName, senderName, title, paymentValue, date) " +
                "VALUES " +
                "(?, ?, ?, ?, ?, ?, ?)";
        List<Object> thirdData = Arrays.asList(firstIdAccount, secondIdAccount, "Recipient", "Sender", "Title"
                , BigDecimal.valueOf(100.0), new Date());

        Map<List<Object>, String> dataToUpdate = new HashMap<>(3);
        dataToUpdate.put(firstData, updateStatementFirstAndSecond);
        dataToUpdate.put(secondData, updateStatementFirstAndSecond);
        dataToUpdate.put(thirdData, updateStatementThird);

        assertTrue(DBManager.dbExecuteTransactionUpdate(dataToUpdate));
    }
}
