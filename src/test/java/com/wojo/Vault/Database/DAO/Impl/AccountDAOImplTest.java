package com.wojo.Vault.Database.DAO.Impl;

import com.wojo.Vault.Database.DAO.AccountDAO;
import com.wojo.Vault.Database.DBManager;
import com.wojo.Vault.Database.Model.Account;
import com.wojo.Vault.Exception.ExecuteStatementException;
import org.junit.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AccountDAOImplTest {

    private static final String FIRST_PERSON_ID = "10";
    private static final String SECOND_PERSON_ID = "11";

    private static final Account FIRST_ACCOUNT = new Account(
            "1",
            FIRST_PERSON_ID,
            "12345678900987654321456789",
            BigDecimal.valueOf(5000.15)
    );

    private static final Account SECOND_ACCOUNT = new Account(
            "2",
            FIRST_PERSON_ID,
            "32145678900987654321456987",
            BigDecimal.valueOf(1230.00)
    );

    private static final Account THIRD_ACCOUNT = new Account(
            "3",
            SECOND_PERSON_ID,
            "55345678955987654321456785",
            BigDecimal.valueOf(500.50)
    );

    private static final String DISABLE_FOREIGN_KEY_CHECK = "SET FOREIGN_KEY_CHECKS = 0";
    private static final String ENABLE_FOREIGN_KEY_CHECK = "SET FOREIGN_KEY_CHECKS = 1";

    private static final AccountDAO accountDAO = new AccountDAOImpl();

    @BeforeClass
    public static void connectionToTestDatabase() throws ExecuteStatementException {
        DBManager.setTestConnectionPath();
        DBManager.dbConnection();


        DBManager.dbExecuteUpdate(DISABLE_FOREIGN_KEY_CHECK, null);
        String updateStatement = "TRUNCATE TABLE account";
        DBManager.dbExecuteUpdate(updateStatement, null);
    }

    @AfterClass
    public static void clearDatabaseAndDisconnect() throws ExecuteStatementException {
        String updateStatement = "TRUNCATE TABLE account";
        DBManager.dbExecuteUpdate(updateStatement, null);

        DBManager.dbExecuteUpdate(ENABLE_FOREIGN_KEY_CHECK, null);

        DBManager.dbDisconnect();
    }

    @Before
    public void insertDataToTests() throws ExecuteStatementException {
        String updateStatement = "INSERT INTO account " +
                "(ACCOUNT_ID, PERSON_ID, NUMBER, VALUE) " +
                "VALUES " +
                "(?, ?, ?, ?)";

        DBManager.dbExecuteUpdate(updateStatement, Arrays.asList(
                FIRST_ACCOUNT.getAccountId(),
                FIRST_ACCOUNT.getPersonId(),
                FIRST_ACCOUNT.getNumber(),
                FIRST_ACCOUNT.getValue().toString()
        ));

        DBManager.dbExecuteUpdate(updateStatement, Arrays.asList(
                SECOND_ACCOUNT.getAccountId(),
                SECOND_ACCOUNT.getPersonId(),
                SECOND_ACCOUNT.getNumber(),
                SECOND_ACCOUNT.getValue().toString()
        ));

        DBManager.dbExecuteUpdate(updateStatement, Arrays.asList(
                THIRD_ACCOUNT.getAccountId(),
                THIRD_ACCOUNT.getPersonId(),
                THIRD_ACCOUNT.getNumber(),
                THIRD_ACCOUNT.getValue().toString()
        ));
    }

    @After
    public void clearTable() throws ExecuteStatementException {
        DBManager.dbExecuteUpdate(DISABLE_FOREIGN_KEY_CHECK, null);

        String updateStatement = "TRUNCATE TABLE account";
        DBManager.dbExecuteUpdate(updateStatement, null);
    }

    @Test
    public void shouldFindAllByPersonId() {
        List<Account> accountList = accountDAO.findAllByPersonId(FIRST_PERSON_ID);

        int expectedSize = 2;
        assertEquals(expectedSize, accountList.size());

        Account firstExpectedAccount = null;
        Account secondExpectedAccount = null;

        for (Account account : accountList) {
            String accountId = account.getAccountId();
            if (accountId.equals(FIRST_ACCOUNT.getAccountId())) {
                firstExpectedAccount = account;
            } else {
                secondExpectedAccount = account;
            }
        }

        assertTrue(firstExpectedAccount != null);
        assertTrue(secondExpectedAccount != null);

        assertTrue(firstExpectedAccount.equals(FIRST_ACCOUNT));
        assertTrue(secondExpectedAccount.equals(SECOND_ACCOUNT));

        assertEquals(firstExpectedAccount.hashCode(), FIRST_ACCOUNT.hashCode());
        assertEquals(secondExpectedAccount.hashCode(), SECOND_ACCOUNT.hashCode());
    }

    @Test
    public void shouldNotFindAccountForBadId() {
        String badPersonId = "-8";

        List<Account> accountList = accountDAO.findAllByPersonId(badPersonId);

        assertEquals(0, accountList.size());
    }

    @Test
    public void shouldFindNumber() {
        assertTrue(accountDAO.isNumberExist(FIRST_ACCOUNT.getNumber()));
        assertTrue(accountDAO.isNumberExist(SECOND_ACCOUNT.getNumber()));
        assertTrue(accountDAO.isNumberExist(THIRD_ACCOUNT.getNumber()));
    }

    @Test
    public void shouldNotFindNumber() {
        String badNumber = "000";

        assertFalse(accountDAO.isNumberExist(badNumber));
    }

    @Test
    public void shouldInsertAccount() {
        String uniquePersonID = "985";
        String uniqueNumber = "33387690411123456789033455";

        Account accountToInsert = new Account(
                uniquePersonID,
                uniqueNumber,
                BigDecimal.TEN
        );

        assertTrue(accountDAO.insertAccount(accountToInsert));
    }

    @Test
    public void shouldSetAccountValue() {
        BigDecimal newValue = BigDecimal.TEN;

        assertTrue(accountDAO.setValue(FIRST_ACCOUNT.getNumber(), newValue));
    }

    @Test
    public void shouldNotSetAccountValue() {
        String badAccountNumber = "000";
        BigDecimal newValue = BigDecimal.ONE;

        assertFalse(accountDAO.setValue(badAccountNumber, newValue));
    }

    @Test
    public void shouldReturnCorrectValue() {
        assertEquals(FIRST_ACCOUNT.getValue(), accountDAO.getValueByNumber(FIRST_ACCOUNT.getNumber()));

        assertEquals(THIRD_ACCOUNT.getValue(), accountDAO.getValueByNumber(THIRD_ACCOUNT.getNumber()));
    }

    @Test
    public void shouldReturnCorrectValueForBadNumber() {
        String badNumber = "00000";

        BigDecimal expectedValue = new BigDecimal("-100");
        assertEquals(expectedValue, accountDAO.getValueByNumber(badNumber));
    }
}
