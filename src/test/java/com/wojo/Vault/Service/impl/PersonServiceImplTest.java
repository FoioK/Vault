package com.wojo.Vault.Service.impl;

import com.wojo.Vault.Database.DBManager;
import com.wojo.Vault.Database.Model.Person;
import com.wojo.Vault.Service.PersonService;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class PersonServiceImplTest {

    private static final String LOGIN = "XBCX67GH2";
    private static final String PASSWORD = "TestXXX";

    private static final String BAD_PASSWORD = "badPassword";

    private PersonService personService = new PersonServiceImpl();

    @BeforeClass
    public static void connectionToTestDatabase() throws IOException, SQLException {
        DBManager.setTestConnectionPath();
        DBManager.dbConnection();

        String updateStatement = "INSERT INTO person " +
                "(FIRST_NAME, LAST_NAME, PERSON_ID, ADDRESS, TELEPHONE_NUMBER, EMAIL, LOGIN, PASSWORD) " +
                "VALUES " +
                "(?, ?, ?, ?, ?, ?, ?, ?)";
        assertEquals(1, DBManager.dbExecuteUpdate(updateStatement,
                Arrays.asList("", "", "", "",
                        "", "", LOGIN, PASSWORD)));
    }

    @AfterClass
    public static void clearDatabaseAndDisconnect() throws SQLException {
        String updateStatement = "TRUNCATE TABLE person";
        DBManager.dbExecuteUpdate(updateStatement, null);
        DBManager.dbDisconnect();
    }

    @Test
    public void loginProcessShouldReturnTrueToValidData() {
        Person.setLogin(LOGIN);
        assertTrue(personService.loginStep2Process(PASSWORD));
    }

    @Test
    public void loginProcessShouldReturnFalseToInvalidData() {
        Person.setLogin(LOGIN);
        assertFalse(personService.loginStep2Process(BAD_PASSWORD));
    }
}