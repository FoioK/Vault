package com.wojo.Vault.DAO;

import com.wojo.Vault.Model.Person;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PersonDAOTest {
    /**
     * Test Person on database
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

    private static final String TEST_VALUE = "ToDelete";

    @Test
    public void shouldInsertAccountToDB() throws SQLException {
        List<String> accountDate = new ArrayList<>();
        int numberOfDate = 8;
        for (int i = 0; i < numberOfDate; i++) {
            accountDate.add(TEST_VALUE);
        }
        if (PersonDAO.insertPersonToDB(accountDate) < 0) {
            fail("null idPerson");
        }
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowException() throws SQLException {
        PersonDAO.insertPersonToDB(null);
    }

    @Test
    public void shouldntInsertPersonToDB() throws SQLException {
        List<String> accountDate = new ArrayList<>();
        int badNumberOfDate = 7;
        for (int i = 0; i < badNumberOfDate; i++) {
            accountDate.add(TEST_VALUE);
        }
        assertEquals(-1, PersonDAO.insertPersonToDB(accountDate));
    }

    @Test
    public void searchLoginTest() throws SQLException {
        assertTrue(PersonDAO.searchPersonLogin(LOGIN));
    }

    @Test
    public void shouldReturnFalse() throws SQLException {
        assertFalse(PersonDAO.searchPersonLogin(null));
    }

    @Test
    public void shouldInsertAccountDateToClass() throws SQLException {
        PersonDAO.insertPersonData(idPerson);

        assertEquals((int) idPerson, Person.getIdPersonInDatabase());
        assertEquals(FIRST_NAME, Person.getFirstName());
        assertEquals(LAST_NAME, Person.getLastName());
        assertEquals(PERSON_ID, Person.getPersonId());
        assertEquals(ADDRESS, Person.getAddress());
        assertEquals(TELEPHONE_NUMBER, Person.getTelephoneNumber());
        assertEquals(EMAIL, Person.getEmail());
        assertEquals(LOGIN, Person.getLogin());
        assertEquals(PASSWORD, Person.getPassword());
    }

    @Test
    public void shouldDeleteTestAccount() throws SQLException {
        String testFirstAndLastName = TEST_VALUE;
        assertTrue(PersonDAO.deletePerson(testFirstAndLastName));
    }

    @Test
    public void shouldReturnGoodIdPersonAndPassword() throws SQLException {
        String[] idPersonAndPassword = PersonDAO.getIdPersonAndPassword(LOGIN);
        assertEquals(String.valueOf(idPerson), idPersonAndPassword[0]);
        assertEquals(PASSWORD, idPersonAndPassword[1]);
    }
}
