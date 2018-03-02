package com.wojo.Vault.Service.impl;

import com.wojo.Vault.Database.DBManager;
import com.wojo.Vault.Database.Model.Account;
import com.wojo.Vault.Database.Model.CashFlow;
import com.wojo.Vault.Database.Model.Person;
import com.wojo.Vault.Service.CashFlowService;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static org.junit.Assert.assertEquals;

public class CashFlowServiceImplTest {

    private CashFlowService cashFlowService = new CashFlowServiceImpl();

    private final static Integer ID_ACCOUNT = -7;
    private final static Integer RECIPIENT_ID_ACCOUNT = -6;
    private final static String NAME = "Test";
    private final static String TITLE = "Title";
    private final static BigDecimal VALUE_OF_500 = BigDecimal.valueOf(500.0);
    private final static BigDecimal VALUE_OF_1000 = BigDecimal.valueOf(1000.0);
    private final static BigDecimal VALUE_OF_1500 = BigDecimal.valueOf(1500.0);

    @BeforeClass
    public static void setRecordToPayment() throws SQLException, IOException {
        DBManager.setTestConnectionPath();
        DBManager.dbConnection();

        String updateStatement = "INSERT INTO payments " +
                "(idAccount, recipientIdAccount, recipientName, senderName, title, paymentValue, date) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        final Date THIS_MONTH = new Date();
        final Date A_MONTH_AGO =
                Date.from(LocalDateTime.now().minusMonths(1).atZone(ZoneId.systemDefault()).toInstant());
        final Date A_TWO_MONTH_AGO =
                Date.from(LocalDateTime.now().minusMonths(2).atZone(ZoneId.systemDefault()).toInstant());

        List<Object> transferToRecipient500ThisMonth =
                Arrays.asList(ID_ACCOUNT, RECIPIENT_ID_ACCOUNT, NAME, NAME, TITLE, VALUE_OF_500, THIS_MONTH);
        List<Object> transferFromRecipient1000ThisMonth =
                Arrays.asList(RECIPIENT_ID_ACCOUNT, ID_ACCOUNT, NAME, NAME, TITLE, VALUE_OF_1000, THIS_MONTH);

        List<Object> transferToRecipient500AMonthAgo =
                Arrays.asList(ID_ACCOUNT, RECIPIENT_ID_ACCOUNT, NAME, NAME, TITLE, VALUE_OF_500, A_MONTH_AGO);
        List<Object> transferToRecipient1500AMonthAgo =
                Arrays.asList(ID_ACCOUNT, RECIPIENT_ID_ACCOUNT, NAME, NAME, TITLE, VALUE_OF_1500, A_MONTH_AGO);
        List<Object> transferFromRecipient1000AMonthAgo =
                Arrays.asList(RECIPIENT_ID_ACCOUNT, ID_ACCOUNT, NAME, NAME, TITLE, VALUE_OF_1000, A_MONTH_AGO);

        List<Object> transferToRecipient500ATwoMonthAgo =
                Arrays.asList(ID_ACCOUNT, RECIPIENT_ID_ACCOUNT, NAME, NAME, TITLE, VALUE_OF_500, A_TWO_MONTH_AGO);
        List<Object> transferFromRecipient1000ATwoMonthAgo =
                Arrays.asList(RECIPIENT_ID_ACCOUNT, ID_ACCOUNT, NAME, NAME, TITLE, VALUE_OF_1000, A_TWO_MONTH_AGO);
        List<Object> transferFromRecipient1500ATwoMonthAgo =
                Arrays.asList(RECIPIENT_ID_ACCOUNT, ID_ACCOUNT, NAME, NAME, TITLE, VALUE_OF_1500, A_TWO_MONTH_AGO);


        Map<List<Object>, String> dataToUpdate = new HashMap<>();
        dataToUpdate.put(transferToRecipient500ThisMonth, updateStatement);
        dataToUpdate.put(transferFromRecipient1000ThisMonth, updateStatement);

        dataToUpdate.put(transferToRecipient500AMonthAgo, updateStatement);
        dataToUpdate.put(transferToRecipient1500AMonthAgo, updateStatement);
        dataToUpdate.put(transferFromRecipient1000AMonthAgo, updateStatement);

        dataToUpdate.put(transferToRecipient500ATwoMonthAgo, updateStatement);
        dataToUpdate.put(transferFromRecipient1000ATwoMonthAgo, updateStatement);
        dataToUpdate.put(transferFromRecipient1500ATwoMonthAgo, updateStatement);

        Assert.assertTrue(DBManager.dbExecuteTransactionUpdate(dataToUpdate));

        Account account = new Account();
        account.setIdAccount(ID_ACCOUNT);
        Person.setAccounts(Collections.singletonList(account));
    }

    @AfterClass
    public static void clearDatabaseAndDisconnect() throws SQLException {
        String updateStatement = "TRUNCATE TABLE payments";
        DBManager.dbExecuteUpdate(updateStatement, null);
        DBManager.dbDisconnect();
    }

    @Test
    public void getLastThreeMonthCashFlowTest() {
        List<CashFlow> paymentList = cashFlowService.getLastThreeMonthCashFlow();

        assertEquals(3, paymentList.size());

        final Integer THIS_MONTH_INDEX = 0;
        final Integer A_MONTH_AGO_INDEX = 1;
        final Integer A_TWO_MONTH_AGO_INDEX = 2;

        CashFlow thisMonth = paymentList.get(THIS_MONTH_INDEX);
        assertEquals(VALUE_OF_500.negate(), thisMonth.getExpenses());
        assertEquals(VALUE_OF_1000, thisMonth.getIncomes());
        assertEquals(VALUE_OF_1000.subtract(VALUE_OF_500), thisMonth.getBalance());

        CashFlow aMonthAgo = paymentList.get(A_MONTH_AGO_INDEX);
        assertEquals(VALUE_OF_500.add(VALUE_OF_1500).negate(), aMonthAgo.getExpenses());
        assertEquals(VALUE_OF_1000, aMonthAgo.getIncomes());
        assertEquals(VALUE_OF_1000.subtract(VALUE_OF_500.add(VALUE_OF_1500)), aMonthAgo.getBalance());

        CashFlow aTwoMonthAgo = paymentList.get(A_TWO_MONTH_AGO_INDEX);
        assertEquals(VALUE_OF_500.negate(), aTwoMonthAgo.getExpenses());
        assertEquals(VALUE_OF_1000.add(VALUE_OF_1500), aTwoMonthAgo.getIncomes());
        assertEquals(VALUE_OF_1000.add(VALUE_OF_1500).subtract(VALUE_OF_500), aTwoMonthAgo.getBalance());
    }

    @Test
    public void getLastMonthCashFlowTest() {
        CashFlow lastMonthCashFlow = cashFlowService.getLastMothFlow();
        assertEquals(VALUE_OF_500.add(VALUE_OF_1500).negate(), lastMonthCashFlow.getExpenses());
        assertEquals(VALUE_OF_1000, lastMonthCashFlow.getIncomes());
        assertEquals(VALUE_OF_1000.subtract(VALUE_OF_500.add(VALUE_OF_1500)), lastMonthCashFlow.getBalance());
    }
}