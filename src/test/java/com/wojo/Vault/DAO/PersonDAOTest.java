package com.wojo.Vault.DAO;

import com.wojo.Vault.Model.Person;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class PersonDAOTest {

    @Test
    public void shouldGenerateLoginWith9Char() {
        for (int i = 0; i < 100; i++) {
            assertEquals(9, PersonDAO.generateLogin(9).length());
        }
    }

    @Test
    public void shouldGenerateOnlyUpperCase() {
        String generatedSring = "";
        for (int i = 0; i < 100; i++) {
            generatedSring = PersonDAO.generateLogin(9);
            assertEquals(generatedSring.toUpperCase(), generatedSring);
        }
    }

    @Test
    public void shouldReturnEmptyString() {
        int initValue = Integer.MIN_VALUE;
        while (initValue < 0) {
            assertEquals("", PersonDAO.generateLogin(initValue));
            initValue += 50;
        }
    }

    @Test
    public void shouldInsertAccountToDB() throws SQLException, ClassNotFoundException {
        List<String> accountDate = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            accountDate.add("ToDelete");
        }
        if(PersonDAO.insertPersonToDB(accountDate) < 0) {
            fail("null idPerson");
        }
    }

    @Test
    public void searchLoginTest() throws SQLException, ClassNotFoundException {
        String testLoginInDB = "ABCDEFGHI";
        assertTrue(PersonDAO.searchPersonLogin(testLoginInDB));
    }

    @Test
    public void shouldInsertAccountDateToClass() {
        int testIdPersonInDB = 1;
        String testFirstName = "TestAccount";
        String testLastName = "TestAccount";
        String testPersonId = "00000000000";
        String testAddress = "TestAccount";
        String testTelephoneNumber = "123456789";
        String testEmail = "TestAccount";
        String testLogin = "ABCDEFGHI";
        String testPassword = "Test";

        PersonDAO.insertPersonDate(testIdPersonInDB);

        assertEquals(testIdPersonInDB, Person.getIdPersonInDatabase());
        assertEquals(testFirstName, Person.getFirstName());
        assertEquals(testLastName, Person.getLastName());
        assertEquals(testPersonId, Person.getPersonId());
        assertEquals(testAddress, Person.getAddress());
        assertEquals(testTelephoneNumber, Person.getTelephoneNumber());
        assertEquals(testEmail, Person.getEmail());
        assertEquals(testLogin, Person.getLogin());
        assertEquals(testPassword, Person.getPassword());
    }

    @Test
    public void shouldDeleteTestAccount() {
        String testFirstAndLastName = "ToDelete";
        assertTrue(PersonDAO.deletePerson(testFirstAndLastName));
    }
}
