package com.wojo.Vault.DAO;

import com.wojo.Vault.Model.Account;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AccountDAOTest {

    @Test
    public void shouldGenerateLoginWith9Char() {
        for (int i = 0; i < 100; i++) {
            assertEquals(9, AccountDAO.generateLogin(9).length());
        }
    }

    @Test
    public void shouldGenerateOnlyUpperCase() {
        String generatedSring = "";
        for (int i = 0; i < 100; i++) {
            generatedSring = AccountDAO.generateLogin(9);
            assertEquals(generatedSring.toUpperCase(), generatedSring);
        }
    }

    @Test
    public void shouldReturnEmptyString() {
        int initValue = Integer.MIN_VALUE;
        while (initValue < 0) {
            assertEquals("", AccountDAO.generateLogin(initValue));
            initValue += 50;
        }
    }

    @Test
    public void shouldInsertAccountToDB() throws SQLException, ClassNotFoundException {
        List<String> accountDate = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            accountDate.add("ToDelete");
        }
        assertTrue(AccountDAO.insertAccountToDB(accountDate));
    }

    @Test
    public void searchLoginTest() throws SQLException, ClassNotFoundException {
        String testLoginInDB = "ABCDEFGHI";
        assertTrue(AccountDAO.searchPersonLogin(testLoginInDB));
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

        AccountDAO.insertPersonDate(testIdPersonInDB);

        assertEquals(testIdPersonInDB, Account.getIdPersonInDatabase());
        assertEquals(testFirstName, Account.getFirstName());
        assertEquals(testLastName, Account.getLastName());
        assertEquals(testPersonId, Account.getPersonId());
        assertEquals(testAddress, Account.getAdress());
        assertEquals(testTelephoneNumber, Account.getTelephonNumber());
        assertEquals(testEmail, Account.getEmail());
        assertEquals(testLogin, Account.getLogin());
        assertEquals(testPassword, Account.getPassword());
    }

    @Test
    public void shouldDeleteTestAccount() {
        String testFirstAndLastName = "ToDelete";
        assertTrue(AccountDAO.deletePerson(testFirstAndLastName));
    }
}
