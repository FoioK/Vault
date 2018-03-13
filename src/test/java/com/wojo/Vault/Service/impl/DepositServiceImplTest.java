package com.wojo.Vault.Service.impl;

import com.wojo.Vault.Database.DBManager;
import com.wojo.Vault.Database.Model.Account;
import com.wojo.Vault.Database.Model.Deposit;
import com.wojo.Vault.Database.Model.DepositsModel.MiddleDeposit;
import com.wojo.Vault.Database.Model.DepositsModel.ShortDeposit;
import com.wojo.Vault.Database.Model.Person;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DepositServiceImplTest {

    private DepositServiceImpl depositService = new DepositServiceImpl();

    @BeforeClass
    public static void connectionToTestDatabase() throws IOException, SQLException {
        DBManager.setTestConnectionPath();
        DBManager.dbConnection();
    }

    @AfterClass
    public static void clearDatabaseAndDisconnect() throws SQLException {
        String updateStatement = "TRUNCATE TABLE deposit";
        DBManager.dbExecuteUpdate(updateStatement, null);
        DBManager.dbDisconnect();
    }

    @Test
    public void shouldCreateDeposit() {
        Account account = new Account();
        account.setIdAccount(0);
        account.setValue(BigDecimal.valueOf(2100));
        Person.setAccounts(Collections.singletonList(account));
        BigDecimal depositAmount = BigDecimal.valueOf(700);

        assertTrue(depositService.createDeposit(depositAmount, Deposit.DepositType.Short));
        assertTrue(depositService.createDeposit(depositAmount, Deposit.DepositType.Middle));
        assertTrue(depositService.createDeposit(depositAmount, Deposit.DepositType.Long));
    }

    @Test
    public void shouldReturnCorrectActiveDepositsList() {
        Account account = new Account();
        account.setIdAccount(777);
        Person.setAccounts(Collections.singletonList(account));

        BigDecimal depositAmount = BigDecimal.valueOf(6000);

        ShortDeposit shortDeposit = new ShortDeposit(account.getIdAccount(), depositAmount, LocalDateTime.now());
        List<Deposit> deposits = Arrays.asList(shortDeposit, shortDeposit, shortDeposit, shortDeposit);
        insertDepositsToDatabase(deposits);

        assertEquals(deposits.size(), depositService.getActiveDeposits().size());

        ShortDeposit endDeposit =
                new ShortDeposit(account.getIdAccount(), depositAmount, LocalDateTime.now().minusDays(1));
        List<Deposit> endDeposits = Arrays.asList(endDeposit, endDeposit);
        insertDepositsToDatabase(endDeposits);

        assertEquals(deposits.size(), depositService.getActiveDeposits().size());
    }

    @Test
    public void shouldSubtractAccountValueAfterInsert() {
        BigDecimal accountValue = BigDecimal.valueOf(7000.00);
        BigDecimal depositAmount = BigDecimal.valueOf(5000.00);

        Account account = new Account();
        account.setIdAccount(2);
        account.setValue(accountValue);
        Person.setAccounts(Collections.singletonList(account));

        if (depositService.createDeposit(depositAmount, Deposit.DepositType.Short)) {
            assertEquals(accountValue.subtract(depositAmount).setScale(2, RoundingMode.CEILING),
                    account.getValue().setScale(2, RoundingMode.CEILING));
        }
    }

    private static void insertDepositsToDatabase(List<Deposit> depositsToInsert) {
        String updateStatement = "INSERT INTO deposit " +
                "(ID_ACCOUNT, AMOUNT, DATE_START, DATE_END, TYPE, IS_ACTIVE)" +
                "VALUES " +
                "(?, ?, ?, ?, ?, ?)";
        depositsToInsert.forEach(deposit -> {
            try {
                DBManager.dbExecuteUpdate(updateStatement,
                        Arrays.asList(String.valueOf(deposit.getIdAccount()),
                                deposit.getDepositAmount().toString(),
                                deposit.getStartDate().toString(),
                                deposit.getEndDate().toString(),
                                deposit.getDepositType().toString(),
                                1 + ""));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    public void shouldReturnCorrectEndDepositAmount() {
        Integer idAccount = 6;
        BigDecimal depositAmount = BigDecimal.valueOf(100000);
        MiddleDeposit middleDeposit = new MiddleDeposit(idAccount, depositAmount, LocalDateTime.now());

        Double depositProfit = depositAmount.doubleValue() * middleDeposit.getPercent() /
                100 * middleDeposit.getNumberOfDays() / 365;
        BigDecimal expectedReturn = depositAmount.add(
                BigDecimal.valueOf(depositProfit).setScale(2, RoundingMode.CEILING));

        assertEquals(expectedReturn, depositService.getEndDepositAmount(middleDeposit));
    }
}