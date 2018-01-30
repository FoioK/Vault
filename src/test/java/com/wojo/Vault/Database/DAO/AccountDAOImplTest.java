package com.wojo.Vault.Database.DAO;

import com.wojo.Vault.Database.DAO.Impl.AccountDAOImpl;
import com.wojo.Vault.Database.Model.Account;
import com.wojo.Vault.Database.Model.Person;
import org.junit.AfterClass;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class AccountDAOImplTest {

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

    private AccountDAO accountDAO = new AccountDAOImpl();

    @AfterClass
    public static void deleteTestAccounts() throws SQLException {
        AccountDAO accountCleaner = new AccountDAOImpl();
        accountCleaner.deleteAccount(testIdPersonToDelete);
    }

    @Test
    public void shouldCorrectCreateAccount() throws SQLException {
        Account account = accountDAO
                .addNewAccount(testIdPersonToDelete, PL_COUNTRY_CODE, NUMBER_LENGTH);
        assertEquals("0", account.getValue());
        assertEquals(NUMBER_PLUS_COUNTRY_CODE_LENGTH, account.getIBAN_NUMBER().length());
    }

    @Test(expected = NullPointerException.class)
    public void shouldntCreateObjectWithBadCountryCode() throws SQLException {
        String badCountryCode = "PL1";
        accountDAO
                .addNewAccount(testIdPersonToDelete, badCountryCode, NUMBER_LENGTH)
                .toString();
    }

    @Test(expected = NullPointerException.class)
    public void shouldntCreateObjectWithBadLength() throws SQLException {
        int badNumberLength = 0;
        accountDAO
                .addNewAccount(testIdPersonToDelete, PL_COUNTRY_CODE, badNumberLength)
                .toString();
    }

    @Test
    public void shouldCorrectInsertAccountToDB() throws SQLException {
        assertTrue(
                accountDAO.insertAccountToDB(
                        new Account(PL_COUNTRY_CODE, NUMBER_LENGTH),
                        testIdPersonToDelete)
        );
    }

    @Test
    public void shouldntInsertAccountToDB() throws SQLException {
        assertFalse(accountDAO.insertAccountToDB(null, testIdPersonToDelete));
    }

    @Test
    public void shouldCorrectInsertAccountDateToClass() throws SQLException {
        accountDAO.insertAccountData(testIdPersonInDB);
        assertEquals(testIBNNumber, Person.getAccounts().get(0).getIBAN_NUMBER());
        assertEquals(testValue, Person.getAccounts().get(0).getValue());
    }

    @Test
    public void shouldntInsertAccountDateToClass() throws SQLException {
        Person.setAccounts(new ArrayList<>());
        accountDAO.insertAccountData(Integer.MIN_VALUE);
        assertEquals(0, Person.getAccounts().size());
    }

    @Test
    public void shouldDeleteAccounts() throws SQLException {
        accountDAO.deleteAccount(Integer.valueOf(testIdPersonToDelete));
    }
}