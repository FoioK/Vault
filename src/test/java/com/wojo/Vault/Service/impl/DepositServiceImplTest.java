package com.wojo.Vault.Service.impl;

import com.wojo.Vault.Database.DBManager;
import com.wojo.Vault.Database.Model.Account;
import com.wojo.Vault.Database.Model.Deposits;
import com.wojo.Vault.Database.Model.Person;
import com.wojo.Vault.Service.DepositService;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Collections;

import static org.junit.Assert.*;

public class DepositServiceImplTest {

    private DepositService depositService = new DepositServiceImpl();

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
        account.setValue(BigDecimal.valueOf(700));
        Person.setAccounts(Collections.singletonList(account));
        BigDecimal depositAmount = BigDecimal.valueOf(700);

        assertTrue(depositService.createDeposit(depositAmount, Deposits.DepositType.Short));
        assertTrue(depositService.createDeposit(depositAmount, Deposits.DepositType.Middle));
        assertTrue(depositService.createDeposit(depositAmount, Deposits.DepositType.Long));
    }
}