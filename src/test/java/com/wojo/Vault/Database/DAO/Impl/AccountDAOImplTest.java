package com.wojo.Vault.Database.DAO.Impl;

import com.wojo.Vault.Database.DAO.AccountDAO;
import com.wojo.Vault.Database.DBManager;
import com.wojo.Vault.Database.Model.Account;
import com.wojo.Vault.Database.Model.Person;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class AccountDAOImplTest {

    /**
     * Test Accounts on database
     */
    private static final Integer idAccount = 3;
    private static final Integer idPerson = 7;
    private static final String NUMBER = "12345678901234567890123456";
    private static final String IBAN_NUMBER = "PL12345678901234567890123456";
    private static final BigDecimal VALUE = new BigDecimal("10000");

    private static final String PL_COUNTRY_CODE = "PL";
    private static final int NUMBER_LENGTH = 26;
    private static final int NUMBER_PLUS_COUNTRY_CODE_LENGTH = 28;

    private AccountDAO accountDAO = new AccountDAOImpl();

    @BeforeClass
    public static void setConnectionTestPath() {
        DBManager.setTestConnectionPath();
    }

    @Test
    public void shouldCorrectCreateAccount() {
        Account account = accountDAO
                .addNewAccount(idPerson, PL_COUNTRY_CODE, NUMBER_LENGTH);
        assertEquals(new BigDecimal("0"), account.getValue());
        assertEquals(NUMBER_PLUS_COUNTRY_CODE_LENGTH, account.getIBAN_NUMBER().length());
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test(expected = NullPointerException.class)
    public void shouldNotCreateObjectWithBadCountryCode() {
        String badCountryCode = "PL1";
        accountDAO
                .addNewAccount(idPerson, badCountryCode, NUMBER_LENGTH)
                .toString();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test(expected = NullPointerException.class)
    public void shouldNotCreateObjectWithBadLength() {
        int badNumberLength = 0;
        accountDAO
                .addNewAccount(idPerson, PL_COUNTRY_CODE, badNumberLength)
                .toString();
    }

    @Test
    public void shouldCorrectInsertAccountToDB() {
        assertTrue(
                accountDAO.insertAccountToDB(
                        new Account(PL_COUNTRY_CODE, NUMBER_LENGTH, new BigDecimal("0")),
                        idPerson)
        );
    }

    @Test
    public void shouldNotInsertAccountToDB() {
        assertFalse(accountDAO.insertAccountToDB(null, idPerson));
    }

    @Test
    public void shouldCorrectInsertAccountDateToClass() {
        accountDAO.insertAccountData(idPerson);
        Integer activeAccountIndex = Person.getAccounts().size() - 1;
        assertEquals(IBAN_NUMBER, Person.getAccounts().get(activeAccountIndex).getIBAN_NUMBER());
        assertEquals(VALUE, Person.getAccounts().get(activeAccountIndex).getValue());
    }

    @Test
    public void shouldNotInsertAccountDateToClass() {
        Person.setAccounts(new ArrayList<>());
        accountDAO.insertAccountData(Integer.MIN_VALUE);
        assertEquals(0, Person.getAccounts().size());
    }

    @Test
    public void shouldTryDeleteAccounts() {
        assertEquals((Integer) 0, accountDAO.deleteAccount(0));
    }

    @Test
    public void shouldFindAccountByNumber() {
        assertEquals(idAccount, accountDAO.searchAccountByNumber(NUMBER));
    }

    @Test
    public void searchAccountByBadParameter() {
        assertEquals((Integer) 0, accountDAO.searchAccountByNumber(""));
    }

    @Test
    public void shouldReturnCorrectAccountValue() {
        assertEquals(VALUE, accountDAO.getAccountValue(String.valueOf(idAccount)));
    }

    @Test
    public void shouldNotReturnValue() {
        assertEquals(new BigDecimal("0"), accountDAO.getAccountValue(""));
    }
}