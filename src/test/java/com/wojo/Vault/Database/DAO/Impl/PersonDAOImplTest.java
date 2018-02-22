package com.wojo.Vault.Database.DAO.Impl;

import com.wojo.Vault.Database.DAO.PersonDAO;
import com.wojo.Vault.Database.DBManager;
import com.wojo.Vault.Database.Model.Person;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PersonDAOImplTest {
    /**
     * Test Person on database
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

    private static final String TEST_VALUE = "ToDelete";

    private PersonDAO personDAO = new PersonDAOImpl();

    @BeforeClass
    public static void setConnectionTstPath() {
        DBManager.setTestConnectionPath();
    }

    @Test
    public void searchLoginWithBadParameterValue() {
        assertFalse(personDAO.searchPersonLogin(null));
    }

    @Test
    public void shouldFindLogin() {
        assertTrue(personDAO.searchPersonLogin(LOGIN));
    }

    @Test
    public void shouldNotFindLogin() {
        assertFalse(personDAO.searchPersonLogin(""));
    }

    @Test
    public void shouldReturnGoodIdPersonAndPassword() {
        String[] idPersonAndPassword = personDAO.getIdPersonAndPassword(LOGIN);
        assertEquals(String.valueOf(idPerson), idPersonAndPassword[0]);
        assertEquals(PASSWORD, idPersonAndPassword[1]);
    }

    @Test(expected = NullPointerException.class)
    public void shouldReturnNull() {
        String[] idPersonAndPassword = personDAO.getIdPersonAndPassword("");
        assertEquals(String.valueOf(idPerson), idPersonAndPassword[0]);
        assertEquals(PASSWORD, idPersonAndPassword[1]);
    }

    @Test
    public void shouldNotTryInsertDataForBadId() {
        Integer badIdPerson = 0;
        assertFalse(personDAO.insertPersonData(badIdPerson));
    }

    @Test
    public void shouldTryInsertData() {
        assertTrue(personDAO.insertPersonData(idPerson));
    }

    @Test
    public void shouldInsertAccountDateToClass() {
        personDAO.insertPersonData(idPerson);

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
    public void shouldInsertAccountToDB() {
        List<String> accountDate = new ArrayList<>();
        int numberOfDate = 8;
        for (int i = 0; i < numberOfDate; i++) {
            accountDate.add(TEST_VALUE);
        }
        if (personDAO.insertPersonToDB(accountDate) < 0) {
            fail("null idPerson");
        }
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowException() {
        personDAO.insertPersonToDB(null);
    }

    @Test
    public void shouldNotInsertPersonToDB() {
        List<String> accountDate = new ArrayList<>();
        int badNumberOfDate = 7;
        for (int i = 0; i < badNumberOfDate; i++) {
            accountDate.add(TEST_VALUE);
        }
        assertEquals(-1, personDAO.insertPersonToDB(accountDate));
    }

    @Test
    public void shouldDeleteByFirstAndLastName() {
        assertTrue(personDAO.deletePerson(TEST_VALUE));
    }

    @Test
    public void shouldDeleteByIdPerson() {
        Integer testIdPerson = 0;
        assertTrue(personDAO.deletePerson(testIdPerson));
    }

    @Test
    public void shouldNotDelete() {
        String badParameterValue = "";
        assertFalse(personDAO.deletePerson(badParameterValue));
    }
}
