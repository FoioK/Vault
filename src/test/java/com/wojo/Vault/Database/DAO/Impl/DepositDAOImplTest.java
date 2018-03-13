package com.wojo.Vault.Database.DAO.Impl;

import com.wojo.Vault.Database.DAO.DepositDAO;
import com.wojo.Vault.Database.DBManager;
import com.wojo.Vault.Database.Model.Deposit;
import com.wojo.Vault.Database.Model.DepositsModel.LongDeposit;
import com.wojo.Vault.Database.Model.DepositsModel.MiddleDeposit;
import com.wojo.Vault.Database.Model.DepositsModel.ShortDeposit;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DepositDAOImplTest {

    private DepositDAO depositDAO = new DepositDAOImpl();

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
    public void shouldCorrectInsertDepositToDatabase() {
        final Integer idAccount = 0;
        final BigDecimal depositAmount = BigDecimal.valueOf(3000);
        final LocalDateTime startDate = LocalDateTime.now();

        Deposit shortDeposit = new ShortDeposit(idAccount, depositAmount, startDate);
        assertTrue(depositDAO.insertDepositToDB(shortDeposit, BigDecimal.valueOf(9000)));

        Deposit middleDeposit = new MiddleDeposit(idAccount, depositAmount, startDate);
        assertTrue(depositDAO.insertDepositToDB(middleDeposit, BigDecimal.valueOf(6000)));

        Deposit longDeposit = new LongDeposit(idAccount, depositAmount, startDate);
        assertTrue(depositDAO.insertDepositToDB(longDeposit, BigDecimal.valueOf(3000)));
    }

    @Test
    public void shouldReturnAllDeposits() throws SQLException {
        String updateStatement = "INSERT INTO deposit " +
                "(ID_ACCOUNT, AMOUNT, DATE_START, DATE_END, TYPE, IS_ACTIVE)" +
                "VALUES " +
                "(?, ?, ?, ?, ?, ?)";

        Integer uniqueIdAccount = 987;

        BigDecimal shortDepositAmount = BigDecimal.valueOf(4000.00);
        BigDecimal middleDepositAmount = BigDecimal.valueOf(3000.00);
        BigDecimal longDepositAmount = BigDecimal.valueOf(2000.00);

        LocalDateTime startDate = LocalDateTime.now();

        Deposit shortDeposit = new ShortDeposit(uniqueIdAccount, shortDepositAmount, startDate);
        DBManager.dbExecuteUpdate(updateStatement, getDateToInsertDeposit(shortDeposit, false));

        MiddleDeposit middleDeposit = new MiddleDeposit(uniqueIdAccount, middleDepositAmount, startDate);
        DBManager.dbExecuteUpdate(updateStatement, getDateToInsertDeposit(middleDeposit, false));

        LongDeposit longDeposit = new LongDeposit(uniqueIdAccount, longDepositAmount, startDate);
        DBManager.dbExecuteUpdate(updateStatement, getDateToInsertDeposit(longDeposit, false));

        List<Deposit> allDeposits = depositDAO.getAllDeposits(uniqueIdAccount);

        final int[] counter = {0};
        allDeposits.forEach(deposit -> {

            if (deposit instanceof ShortDeposit) {
                assertEquals(shortDepositAmount, deposit.getDepositAmount());
                counter[0] += 1;
            } else if (deposit instanceof MiddleDeposit) {
                assertEquals(middleDepositAmount, deposit.getDepositAmount());
                counter[0] += 1;
            } else if (deposit instanceof LongDeposit) {
                assertEquals(longDepositAmount, deposit.getDepositAmount());
                counter[0] += 1;
            }

            assertEquals(uniqueIdAccount, deposit.getIdAccount());
            assertEquals(startDate.toLocalDate(), deposit.getStartDate().toLocalDate());
        });
        assertEquals(3, counter[0]);
    }

    @Test
    public void shouldArchiveDeposit() throws SQLException {
        String updateStatement = "INSERT INTO deposit " +
                "(ID_DEPOSIT, ID_ACCOUNT, AMOUNT, DATE_START, DATE_END, TYPE, IS_ACTIVE)" +
                "VALUES " +
                "(?, ?, ?, ?, ?, ?, ?)";

        Integer idDeposit = 785;
        Integer idAccount = 98;

        MiddleDeposit middleDeposit = new MiddleDeposit(idDeposit, idAccount, BigDecimal.TEN, LocalDateTime.now());
        DBManager.dbExecuteUpdate(updateStatement, getDateToInsertDeposit(middleDeposit, true));

        assertTrue(depositDAO.archiveDeposit(middleDeposit, BigDecimal.valueOf(5000)));
    }

    private static List<String> getDateToInsertDeposit(Deposit deposit, boolean withIdDeposit) {
        return withIdDeposit ? Arrays.asList(
                String.valueOf(deposit.getIdDeposit()),
                String.valueOf(deposit.getIdAccount()),
                deposit.getDepositAmount().toString(),
                deposit.getStartDate().toString(),
                deposit.getEndDate().toString(),
                deposit.getDepositType().toString(),
                1 + ""
        ) : Arrays.asList(
                String.valueOf(deposit.getIdAccount()),
                deposit.getDepositAmount().toString(),
                deposit.getStartDate().toString(),
                deposit.getEndDate().toString(),
                deposit.getDepositType().toString(),
                1 + ""
        );
    }
}