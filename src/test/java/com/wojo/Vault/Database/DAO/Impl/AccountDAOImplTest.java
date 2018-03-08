package com.wojo.Vault.Database.DAO.Impl;

import com.wojo.Vault.Database.DAO.AccountDAO;
import com.wojo.Vault.Database.DBManager;
import com.wojo.Vault.Database.Model.Account;
import com.wojo.Vault.Database.Model.Person;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class AccountDAOImplTest {

    /**
     * Test Accounts on database
     */
    private static final Integer idPerson = 7;
    private static final String IBAN_NUMBER = "PL12345678901234567890123456";
    private static final BigDecimal VALUE = BigDecimal.valueOf(10000.0);

    private static final String PL_COUNTRY_CODE = "PL";
    private static final int NUMBER_LENGTH = 26;
    private static final int NUMBER_PLUS_COUNTRY_CODE_LENGTH = 28;

    private AccountDAO accountDAO = new AccountDAOImpl();

    @BeforeClass
    public static void connectionToTestDatabase() throws IOException, SQLException {
        DBManager.setTestConnectionPath();
        DBManager.dbConnection();
    }

    @AfterClass
    public static void clearDatabaseAndDisconnect() throws SQLException {
        String updateStatement = "TRUNCATE TABLE accounts";
        DBManager.dbExecuteUpdate(updateStatement, null);
        DBManager.dbDisconnect();
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
    public void shouldCorrectInsertAccountDateToClass() throws SQLException {
        String updateStatement = "INSERT INTO accounts (idPerson, number, value) VALUES (?, ?, ?)";
        Integer uniqueIdPerson = 647;
        assertEquals(1, DBManager.dbExecuteUpdate(updateStatement,
                Arrays.asList(String.valueOf(uniqueIdPerson), IBAN_NUMBER, VALUE.toString())));

        Person.setAccounts(new ArrayList<>());
        accountDAO.insertAccountData(uniqueIdPerson);
        Integer activeAccountIndex = 0;
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
    public void shouldFindAccountByNumber() throws SQLException {
        String updateStatement = "INSERT INTO accounts (idAccount, idPerson, number, value) VALUES (?, ?, ?, ?)";
        Integer uniqueIdAccount = 4789;
        String uniqueFullNumber = "PL97436678987234567890432156";
        String uniqueNumber = "97436678987234567890432156";
        assertEquals(1, DBManager.dbExecuteUpdate(updateStatement,
                Arrays.asList(String.valueOf(uniqueIdAccount),
                        String.valueOf(idPerson), uniqueFullNumber, VALUE.toString())));

        assertEquals(uniqueIdAccount, accountDAO.searchAccountByNumber(uniqueNumber));
    }

    @Test
    public void searchAccountByBadParameter() {
        assertEquals((Integer) 0, accountDAO.searchAccountByNumber(""));
    }

    @Test
    public void shouldReturnCorrectAccountValue() throws SQLException {
        String updateStatement = "INSERT INTO accounts (idAccount, idPerson, number, value) VALUES (?, ?, ?, ?)";
        Integer uniqueIdAccount = 5790;
        assertEquals(1, DBManager.dbExecuteUpdate(updateStatement,
                Arrays.asList(String.valueOf(uniqueIdAccount), String.valueOf(idPerson),
                        IBAN_NUMBER, VALUE.toString())));

        assertEquals(VALUE, accountDAO.getAccountValue(String.valueOf(uniqueIdAccount)));
    }

    @Test
    public void shouldNotReturnValue() {
        assertEquals(new BigDecimal("0"), accountDAO.getAccountValue(""));
    }
}