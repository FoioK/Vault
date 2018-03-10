package com.wojo.Vault.Database.DAO.Impl;

import com.wojo.Vault.Database.DAO.DepositDAO;
import com.wojo.Vault.Database.DBManager;
import com.wojo.Vault.Database.Model.Deposits;
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

        Integer expectedReturn = 1;

        Deposits shortDeposit = new ShortDeposit(idAccount, depositAmount, startDate);
        assertEquals(expectedReturn, depositDAO.insertDepositToDB(shortDeposit));

        Deposits middleDeposit = new MiddleDeposit(idAccount, depositAmount, startDate);
        assertEquals(expectedReturn, depositDAO.insertDepositToDB(middleDeposit));

        Deposits longDeposit = new LongDeposit(idAccount, depositAmount, startDate);
        assertEquals(expectedReturn, depositDAO.insertDepositToDB(longDeposit));
    }

    @Test
    public void shouldReturnAllDeposits() throws SQLException {
        String updateStatement = "INSERT INTO deposit " +
                "(ID_ACCOUNT, AMOUNT, DATE_START, DATE_END, TYPE) " +
                "VALUES " +
                "(?, ?, ?, ?, ?)";

        Integer uniqueIdAccount = 987;

        BigDecimal shortDepositAmount = BigDecimal.valueOf(4000.00);
        BigDecimal middleDepositAmount = BigDecimal.valueOf(3000.00);
        BigDecimal longDepositAmount = BigDecimal.valueOf(2000.00);

        LocalDateTime startDate = LocalDateTime.now();

        Deposits shortDeposit = new ShortDeposit(uniqueIdAccount, shortDepositAmount, startDate);
        DBManager.dbExecuteUpdate(updateStatement, getDateToInsertDeposit(shortDeposit));

        MiddleDeposit middleDeposit = new MiddleDeposit(uniqueIdAccount, middleDepositAmount, startDate);
        DBManager.dbExecuteUpdate(updateStatement, getDateToInsertDeposit(middleDeposit));

        LongDeposit longDeposit = new LongDeposit(uniqueIdAccount, longDepositAmount, startDate);
        DBManager.dbExecuteUpdate(updateStatement, getDateToInsertDeposit(longDeposit));

        List<Deposits> allDeposits = depositDAO.getAllDeposits(uniqueIdAccount);

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

    private static List<String> getDateToInsertDeposit(Deposits deposit) {
        return Arrays.asList(
                String.valueOf(deposit.getIdAccount()),
                deposit.getDepositAmount().toString(),
                deposit.getStartDate().toString(),
                deposit.getEndDate().toString(),
                deposit.getDepositType().toString()
        );
    }
}