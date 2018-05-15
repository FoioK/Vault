package com.wojo.Vault.Service.impl;

import com.wojo.Vault.Database.DBManager;
import com.wojo.Vault.Database.Model.Account;
import com.wojo.Vault.Database.Model.Deposit;
import com.wojo.Vault.Database.Model.DepositsModel.LongDeposit;
import com.wojo.Vault.Database.Model.DepositsModel.MiddleDeposit;
import com.wojo.Vault.Database.Model.DepositsModel.ShortDeposit;
import com.wojo.Vault.Exception.ExecuteStatementException;
import org.junit.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class DepositServiceImplTest {

    private DepositServiceImpl depositService = new DepositServiceImpl();

    private static final String DEPOSIT_TABLE_NAME = "Deposit";

    @BeforeClass
    public static void setup() throws ExecuteStatementException {
        Configuration.connectionToTestDatabase();
        Configuration.disableForeignKeyCheck();
        Configuration.truncateTable(DEPOSIT_TABLE_NAME);
    }

    @AfterClass
    public static void clearDatabaseAndDisconnect() throws ExecuteStatementException {
        Configuration.disableForeignKeyCheck();
        Configuration.truncateTable(DEPOSIT_TABLE_NAME);
        Configuration.enableForeignKeyCheck();
        DBManager.dbDisconnect();
    }

    private static final Account ACCOUNT = new Account(
            "11",
            "44",
            "33351234123409874567789033",
            new BigDecimal("8000.00")
    );

    private static final Deposit SHORT_DEPOSIT = new ShortDeposit(
            "1",
            ACCOUNT.getAccountId(),
            new BigDecimal("1000.00"),
            LocalDateTime.now()
    );

    private static final Deposit MIDDLE_DEPOSIT = new MiddleDeposit(
            "2",
            ACCOUNT.getAccountId(),
            new BigDecimal("2000.00"),
            LocalDateTime.now()
    );

    private static final Deposit LONG_DEPOSIT = new LongDeposit(
            "3",
            ACCOUNT.getAccountId(),
            new BigDecimal("3000.00"),
            LocalDateTime.now()
    );

    @Before
    public void insertDataToTest() throws ExecuteStatementException {
        assertTrue(insertDeposit(SHORT_DEPOSIT));
        assertTrue(insertDeposit(MIDDLE_DEPOSIT));
        assertTrue(insertDeposit(LONG_DEPOSIT));
    }

    private boolean insertDeposit(Deposit deposit) throws ExecuteStatementException {
        String updateStatement = "INSERT INTO deposit " +
                "(ACCOUNT_ID, AMOUNT, START_DATE, END_DATE, TYPE, IS_ACTIVE) " +
                "VALUES " +
                "(?, ?, ?, ?, ?, ?)";

        return DBManager.dbExecuteUpdate(updateStatement, Arrays.asList(
                deposit.getAccountId(),
                deposit.getDepositAmount().toString(),
                deposit.getStartDate().toString(),
                deposit.getEndDate().toString(),
                deposit.getDepositType().toString(),
                "1"
        )) == 1;
    }

    @After
    public void clearTable() throws ExecuteStatementException {
        Configuration.disableForeignKeyCheck();
        Configuration.truncateTable(DEPOSIT_TABLE_NAME);
    }

    @Test
    public void shouldCreateDeposit() {
        Account account = new Account(
                "100",
                "2",
                "98771234123409874567789033",
                new BigDecimal("5000.00")
        );
        BigDecimal depositAmount = new BigDecimal("1000.00");

        assertTrue(depositService.createDeposit(account, depositAmount, Deposit.DepositType.Short));
        assertTrue(depositService.createDeposit(account, depositAmount, Deposit.DepositType.Middle));
        assertTrue(depositService.createDeposit(account, depositAmount, Deposit.DepositType.Long));

        assertEquals(account.getValue(), depositAmount.multiply(new BigDecimal("3")));
    }

    @Test
    public void shouldNotCreateDepositWhenDepositAmountIsGraterThanAccountValue() {
        Account account = new Account(
                "200",
                "2",
                "98751234123409874567789033",
                new BigDecimal("500.00")
        );
        BigDecimal depositAmount = new BigDecimal("505.00");

        assertFalse(depositService.createDeposit(account, depositAmount, Deposit.DepositType.Short));
        assertFalse(depositService.createDeposit(account, depositAmount, Deposit.DepositType.Middle));
        assertFalse(depositService.createDeposit(account, depositAmount, Deposit.DepositType.Long));
    }

    @Test
    public void shouldReturnCorrectActiveDepositsList() {
        List<Deposit> activeDeposits = depositService.getActiveDeposits(ACCOUNT);

        final int expectedSize = 3;
        assertEquals(expectedSize, activeDeposits.size());

        activeDeposits.forEach(deposit -> {
            if (deposit instanceof ShortDeposit) {
                assertEquals(deposit, SHORT_DEPOSIT);
            } else if (deposit instanceof MiddleDeposit) {
                assertEquals(deposit, MIDDLE_DEPOSIT);
            } else {
                assertEquals(deposit, LONG_DEPOSIT);
            }
        });
    }
}