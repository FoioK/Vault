package com.wojo.Vault.Database.DAO.Impl;

import com.wojo.Vault.Database.DAO.DepositDAO;
import com.wojo.Vault.Database.DBManager;
import com.wojo.Vault.Database.Model.Account;
import com.wojo.Vault.Database.Model.Deposit;
import com.wojo.Vault.Database.Model.DepositsModel.LongDeposit;
import com.wojo.Vault.Database.Model.DepositsModel.MiddleDeposit;
import com.wojo.Vault.Database.Model.DepositsModel.ShortDeposit;
import com.wojo.Vault.Exception.ExecuteStatementException;
import org.junit.*;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DepositDAOImplTest {

    private DepositDAO depositDAO = new DepositDAOImpl();

    private static final String DISABLE_FOREIGN_KEY_CHECK = "SET FOREIGN_KEY_CHECKS = 0";
    private static final String ENABLE_FOREIGN_KEY_CHECK = "SET FOREIGN_KEY_CHECKS = 1";

    @BeforeClass
    public static void connectToTestDatabase() throws ExecuteStatementException {
        DBManager.setTestConnectionPath();
        DBManager.dbConnection();

        DBManager.dbExecuteUpdate(DISABLE_FOREIGN_KEY_CHECK, null);

        String updateStatementDeposit = "TRUNCATE TABLE deposit";
        DBManager.dbExecuteUpdate(updateStatementDeposit, null);

        String updateStatementAccount = "TRUNCATE TABLE account";
        DBManager.dbExecuteUpdate(updateStatementAccount, null);
    }

    @AfterClass
    public static void clearDatabaseAndDisconnect() throws SQLException {
        String updateStatement = "TRUNCATE TABLE deposit";
        DBManager.dbExecuteUpdate(updateStatement, null);

        DBManager.dbExecuteUpdate(ENABLE_FOREIGN_KEY_CHECK, null);

        DBManager.dbDisconnect();
    }

    private static final Account FIRST_ACCOUNT = new Account(
            "41",
            "1",
            "12345678912345678921345678",
            new BigDecimal("100000.00")
    );

    private static final ShortDeposit SHORT_DEPOSIT = new ShortDeposit(
            "1",
            FIRST_ACCOUNT.getAccountId(),
            new BigDecimal("4270.00"),
            LocalDateTime.now()
    );

    private static final MiddleDeposit MIDDLE_DEPOSIT = new MiddleDeposit(
            "2",
            FIRST_ACCOUNT.getAccountId(),
            new BigDecimal("1000.00"),
            LocalDateTime.now().minusDays(3)
    );

    private static final LongDeposit LONG_DEPOSIT = new LongDeposit(
            "4",
            FIRST_ACCOUNT.getAccountId(),
            new BigDecimal("78000.00"),
            LocalDateTime.now().minusDays(10)
    );

    @Before
    public void insertDataToTests() throws ExecuteStatementException {
        insertAccount(FIRST_ACCOUNT);

        insertDeposit(SHORT_DEPOSIT);
        insertDeposit(MIDDLE_DEPOSIT);
        insertDeposit(LONG_DEPOSIT);
    }

    private void insertAccount(Account account) throws ExecuteStatementException {
        String updateStatement = "INSERT INTO account " +
                "(ACCOUNT_ID, PERSON_ID, NUMBER, VALUE) " +
                "VALUES " +
                "(?, ?, ?, ?)";

        List<String> dataToUpdate = Arrays.asList(
                account.getAccountId(),
                account.getPersonId(),
                account.getNumber(),
                account.getValue().toString()
        );

        DBManager.dbExecuteUpdate(updateStatement, dataToUpdate);
    }

    private <T extends Deposit> void insertDeposit(T deposit) throws ExecuteStatementException {
        String updateStatement = "INSERT INTO deposit " +
                "(DEPOSIT_ID, ACCOUNT_ID, AMOUNT, START_DATE, END_DATE, TYPE, IS_ACTIVE) " +
                "VALUES " +
                "(?, ?, ?, ?, ?, ?, ?)";

        List<String> dataToUpdate = Arrays.asList(
                deposit.getDepositId(),
                deposit.getAccountId(),
                deposit.getDepositAmount().toString(),
                deposit.getStartDate().toString(),
                deposit.getEndDate().toString(),
                deposit.getDepositType().name(),
                "1"
        );

        DBManager.dbExecuteUpdate(updateStatement, dataToUpdate);
    }

    @After
    public void clearTable() throws ExecuteStatementException {
        DBManager.dbExecuteUpdate(DISABLE_FOREIGN_KEY_CHECK, null);

        String updateStatementDeposit = "TRUNCATE TABLE deposit";
        DBManager.dbExecuteUpdate(updateStatementDeposit, null);

        String updateStatementAccount = "TRUNCATE TABLE account";
        DBManager.dbExecuteUpdate(updateStatementAccount, null);
    }

    @Test
    public void shouldCorrectInsertDepositToDatabase() {
        BigDecimal depositAmount = new BigDecimal("1250.00");

        ShortDeposit shortDeposit = new ShortDeposit(
                FIRST_ACCOUNT.getAccountId(),
                depositAmount,
                LocalDateTime.now()
        );
        assertTrue(depositDAO.insertDepositToDB(shortDeposit, FIRST_ACCOUNT.getValue().subtract(depositAmount)));

        MiddleDeposit middleDeposit = new MiddleDeposit(
                FIRST_ACCOUNT.getAccountId(),
                depositAmount,
                LocalDateTime.now()
        );
        assertTrue(depositDAO.insertDepositToDB(middleDeposit, FIRST_ACCOUNT.getValue().subtract(depositAmount)));

        LongDeposit longDeposit = new LongDeposit(
                FIRST_ACCOUNT.getAccountId(),
                depositAmount,
                LocalDateTime.now()
        );
        assertTrue(depositDAO.insertDepositToDB(longDeposit, FIRST_ACCOUNT.getValue().subtract(depositAmount)));
    }

    @Test
    public void findAllTest() {
        List<Deposit> allDeposit = depositDAO.findAll(FIRST_ACCOUNT.getAccountId());

        /*
          expected deposits:
                   SHORT_DEPOSIT,
                   MIDDLE_DEPOSIT,
                   LONG_DEPOSIT
         */
        final int expectedSize = 3;
        assertEquals(expectedSize, allDeposit.size());

        allDeposit.forEach(deposit -> {
            if (deposit.getDepositId().equals(SHORT_DEPOSIT.getDepositId())) {
                assertTrue(deposit.equals(SHORT_DEPOSIT));
            } else if (deposit.getDepositId().equals(MIDDLE_DEPOSIT.getDepositId())) {
                assertTrue(deposit.equals(MIDDLE_DEPOSIT));
            } else {
                assertTrue(deposit.equals(LONG_DEPOSIT));
            }
        });
    }

    @Test
    public void shouldArchiveDeposit() {
        BigDecimal depositAmount = new BigDecimal("3500.00");

        assertTrue(depositDAO.archiveDeposit(SHORT_DEPOSIT, depositAmount));
        assertTrue(depositDAO.archiveDeposit(MIDDLE_DEPOSIT, depositAmount));
        assertTrue(depositDAO.archiveDeposit(LONG_DEPOSIT, depositAmount));
    }
}