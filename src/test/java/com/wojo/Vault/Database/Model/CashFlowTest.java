package com.wojo.Vault.Database.Model;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class CashFlowTest {

    private Payment paymentOf500 = new Payment();
    private Payment paymentOf1000 = new Payment();
    private Payment paymentOf2000 = new Payment();

    private Payment paymentOfMinus500 = new Payment();
    private Payment paymentOfMinus1000 = new Payment();
    private Payment paymentOfMinus2000 = new Payment();

    @Before
    public void initDataToTest() {
        paymentOf500.setAmount(new BigDecimal("500.00"));
        paymentOf1000.setAmount(new BigDecimal("1000.00"));
        paymentOf2000.setAmount(new BigDecimal("2000.00"));

        paymentOfMinus500.setAmount(new BigDecimal("-500.00"));
        paymentOfMinus1000.setAmount(new BigDecimal("-1000.00"));
        paymentOfMinus2000.setAmount(new BigDecimal("-2000.00"));
    }

    @Test
    public void shouldReturnCorrectIncomes() {
        CashFlow cashFlow = new CashFlow(LocalDate.now());
        cashFlow.addPayment(paymentOf2000);
        cashFlow.addPayment(paymentOf1000);
        cashFlow.addPayment(paymentOfMinus1000);
        cashFlow.addPayment(paymentOfMinus500);

        BigDecimal expected = new BigDecimal("3000.00");
        assertEquals(expected, cashFlow.getIncomes());
    }

    @Test
    public void shouldReturnCorrectExpenses() {
        CashFlow cashFlow = new CashFlow(LocalDate.now());
        cashFlow.addPayment(paymentOf2000);
        cashFlow.addPayment(paymentOf1000);
        cashFlow.addPayment(paymentOfMinus1000);
        cashFlow.addPayment(paymentOfMinus500);

        BigDecimal expected = new BigDecimal("-1500.00");
        assertEquals(expected, cashFlow.getExpenses());
    }

    @Test
    public void shouldReturnCorrectBalance() {
        CashFlow cashFlow = new CashFlow(LocalDate.now());
        cashFlow.addPayment(paymentOf2000);
        cashFlow.addPayment(paymentOf1000);
        cashFlow.addPayment(paymentOfMinus1000);
        cashFlow.addPayment(paymentOfMinus500);
        cashFlow.addPayment(paymentOf500);
        cashFlow.addPayment(paymentOfMinus1000);


        BigDecimal expected = new BigDecimal("1000.00");
        assertEquals(expected, cashFlow.getBalance());
    }
}