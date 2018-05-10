package com.wojo.Vault.Service.impl;

import com.wojo.Vault.Database.DBManager;
import com.wojo.Vault.Database.Model.Account;
import com.wojo.Vault.Database.Model.Person;
import com.wojo.Vault.Exception.ExecuteStatementException;
import com.wojo.Vault.Service.AccountService;
import org.junit.*;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.Assert.*;

public class AccountServiceImplTest {

    private static final String ACCOUNT_TABLE_NAME = "Account";

    @BeforeClass
    public static void setup() throws ExecuteStatementException {
        Configuration.connectionToTestDatabase();
        Configuration.disableForeignKeyCheck();
        Configuration.truncateTable(ACCOUNT_TABLE_NAME);
    }

    @AfterClass
    public static void clearDatabaseAndDisconnect() throws ExecuteStatementException {
        Configuration.disableForeignKeyCheck();
        Configuration.truncateTable(ACCOUNT_TABLE_NAME);
        Configuration.enableForeignKeyCheck();
        DBManager.dbDisconnect();
    }


    private static final Account ACCOUNT_TO_TESTS = new Account(
            "11",
            "6",
            "43215678901234567890123456",
            new BigDecimal("9000.00")
    );

    @Before
    public void insertDataToTest() throws ExecuteStatementException {
        assertTrue(insertAccount());
    }

    private boolean insertAccount() throws ExecuteStatementException {
        String updateStatement = "INSERT INTO account (ACCOUNT_ID, PERSON_ID, NUMBER, VALUE) " +
                "VALUES (?, ?, ?, ?)";

        return DBManager.dbExecuteUpdate(updateStatement, Arrays.asList(
                AccountServiceImplTest.ACCOUNT_TO_TESTS.getAccountId(),
                AccountServiceImplTest.ACCOUNT_TO_TESTS.getPersonId(),
                AccountServiceImplTest.ACCOUNT_TO_TESTS.getNumber(),
                AccountServiceImplTest.ACCOUNT_TO_TESTS.getValue().toString()
        )) == 1;
    }

    @After
    public void clearTable() throws ExecuteStatementException {
        Configuration.disableForeignKeyCheck();
        Configuration.truncateTable(ACCOUNT_TABLE_NAME);
    }

    private AccountService accountService = new AccountServiceImpl();

    @Test
    public void shouldCreateAccount() {
        Account account = new Account(
                "10",
                "5",
                "12345678901234567890123456",
                new BigDecimal("9000.00")
        );

        assertTrue(accountService.createAccount(account));
    }

    @Test
    public void shouldNotCreateAccount() {
        assertFalse(accountService.createAccount(null));
    }

    @Test
    public void shouldAddValueToAccount() {
        BigDecimal valueToAdd = new BigDecimal("1000.00");

        assertTrue(accountService.addValue(valueToAdd, ACCOUNT_TO_TESTS.getNumber()));
    }

    @Test
    public void shouldReturnFormattedNumber() {
        assertEquals(ACCOUNT_TO_TESTS.getNumber(),
                accountService.getFormatAccountNumber(ACCOUNT_TO_TESTS)
                        .chars()
                        .filter(c -> c != 32)
                        .collect(StringBuilder::new,
                                StringBuilder::appendCodePoint,
                                StringBuilder::append)
                        .toString());
    }

    @Test
    public void formattedNumberShouldHave33CharacterLength() {
        final int expectedLength = 33;
        assertEquals(expectedLength, accountService.getFormatAccountNumber(ACCOUNT_TO_TESTS).length());
    }

    @Test
    public void shouldSetAccounts() {
        Person person = new Person(ACCOUNT_TO_TESTS.getPersonId());

        accountService.setAccounts(person);

        assertEquals(ACCOUNT_TO_TESTS,
                person.getAccountList()
                        .stream()
                        .filter(account -> account.getPersonId().equals(ACCOUNT_TO_TESTS.getPersonId()))
                        .findFirst().orElse(null));
    }
}