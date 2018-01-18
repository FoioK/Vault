package com.wojo.Vault.DAO;

import com.wojo.Vault.Model.Account;
import com.wojo.Vault.Model.Person;
import org.junit.AfterClass;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class AccountDAOTest {

    /**
     * Test Accounts on database
     */
    private final int testIdPersonInDB = 1;
    private final String testIBNNumber = "PL99999999999999999999999999";
    private final String testValue = "999";

    private static final String PL_COUNTRY_CODE = "PL";
    private static final int NUMBER_LENGTH = 26;
    private static final int NUMBER_PLUS_COUNTRY_CODE_LENGTH = 28;
    private static final int testIdPersonToDelete = -1;

    @AfterClass
    public static void deleteTestAccounts() throws SQLException {
        AccountDAO.deleteAccount(testIdPersonToDelete);
    }

    @Test
    public void shouldCorrectCreateAccount() throws SQLException {
        Account account = AccountDAO
                .addNewAccount(testIdPersonToDelete, PL_COUNTRY_CODE, NUMBER_LENGTH);
        assertEquals("0", account.getValue());
        assertEquals(NUMBER_PLUS_COUNTRY_CODE_LENGTH, account.getIBAN_NUMBER().length());
    }

    @Test(expected = NullPointerException.class)
    public void shouldntCreateObjectWithBadCountryCode() throws SQLException {
        String badCountryCode = "PL1";
        AccountDAO
                .addNewAccount(testIdPersonToDelete, badCountryCode, NUMBER_LENGTH)
                .toString();
    }

    @Test(expected = NullPointerException.class)
    public void shouldntCreateObjectWithBadLength() throws SQLException {
        int badNumberLength = 0;
        AccountDAO
                .addNewAccount(testIdPersonToDelete, PL_COUNTRY_CODE, badNumberLength)
                .toString();
    }

    @Test
    public void shouldCorrectInsertAccountToDB() throws SQLException {
        assertTrue(
                AccountDAO.insertAccountToDB(
                        new Account(PL_COUNTRY_CODE, NUMBER_LENGTH),
                        testIdPersonToDelete)
        );
    }

    @Test
    public void shouldntInsertAccountToDB() throws SQLException {
        assertFalse(AccountDAO.insertAccountToDB(null, testIdPersonToDelete));
    }

    @Test
    public void shouldCorrectInsertAccountDateToClass() throws SQLException {
        AccountDAO.insertAccountDate(testIdPersonInDB);
        assertEquals(testIBNNumber, Person.getAccounts().get(0).getIBAN_NUMBER());
        assertEquals(testValue, Person.getAccounts().get(0).getValue());
    }

    @Test
    public void shouldntInsertAccountDateToClass() throws SQLException {
        Person.setAccounts(new ArrayList<>());
        AccountDAO.insertAccountDate(Integer.MIN_VALUE);
        assertEquals(0, Person.getAccounts().size());
    }

    @Test
    public void shouldDeleteAccounts() throws SQLException {
        AccountDAO.deleteAccount(Integer.valueOf(testIdPersonToDelete));
    }
}