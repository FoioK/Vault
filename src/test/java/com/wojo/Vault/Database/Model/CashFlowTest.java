package com.wojo.Vault.Database.Model;

import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

import static junit.framework.Assert.assertEquals;

public class CashFlowTest {

    private static CashFlow cashFlowWithPayment;
    private static CashFlow cashFlowWithoutPayment = new CashFlow(LocalDate.now());

    private static final BigDecimal VALUE_OF_500 = BigDecimal.valueOf(500.00);
    private static final BigDecimal VALUE_OF_1000 = BigDecimal.valueOf(1000.00);
    private static final BigDecimal VALUE_OF_1500 = BigDecimal.valueOf(1500.00);

    @BeforeClass
    public static void setDataToTests() {
        cashFlowWithPayment = new CashFlow(LocalDate.now());

        Payment depositTransferOf500 = new Payment(0, 0, "", "",
                "", VALUE_OF_500, new Date());
        Payment depositTransferOf1000 = new Payment(0, 0, "", "",
                "", VALUE_OF_1000, new Date());
        Payment depositTransferOf1500 = new Payment(0, 0, "", "",
                "", VALUE_OF_1500, new Date());
        cashFlowWithPayment.addPayment(depositTransferOf500);
        cashFlowWithPayment.addPayment(depositTransferOf1000);
        cashFlowWithPayment.addPayment(depositTransferOf1500);

        Payment debitTransferOf500 = new Payment(0, 0, "", "",
                "", VALUE_OF_500.negate(), new Date());
        Payment debitTransferOf1000 = new Payment(0, 0, "", "",
                "", VALUE_OF_1000.negate(), new Date());
        cashFlowWithPayment.addPayment(debitTransferOf500);
        cashFlowWithPayment.addPayment(debitTransferOf1000);
    }

    @Test
    public void shouldReturnCorrectIncomes() {
        assertEquals(BigDecimal.valueOf(0.0), cashFlowWithoutPayment.getIncomes());
        assertEquals(VALUE_OF_500.add(VALUE_OF_1000).add(VALUE_OF_1500), cashFlowWithPayment.getIncomes());
    }

    @Test
    public void shouldReturnCorrectExpenses() {
        assertEquals(BigDecimal.valueOf(0.0), cashFlowWithoutPayment.getExpenses());
        assertEquals(VALUE_OF_500.add(VALUE_OF_1000).negate(), cashFlowWithPayment.getExpenses());
    }

    @Test
    public void shouldReturnCorrectBalance() {
        assertEquals(BigDecimal.valueOf(0.0), cashFlowWithoutPayment.getBalance());

        BigDecimal expectedBalance = BigDecimal.valueOf(1500.0);
        assertEquals(expectedBalance, cashFlowWithPayment.getBalance());
    }
}