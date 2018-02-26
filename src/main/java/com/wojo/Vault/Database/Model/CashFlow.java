package com.wojo.Vault.Database.Model;

import java.math.BigDecimal;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class CashFlow {

    private Month month;
    private List<Payment> paymentList = new ArrayList<>();

    public CashFlow(Month month) {
        this.month = month;
    }

    public Month getMonth() {
        return month;
    }

    public boolean addPayment(Payment payment) {
        return paymentList.add(payment);
    }

    public BigDecimal getIncomes() {
        return BigDecimal.valueOf(
                paymentList.stream()
                        .filter(payment -> payment.getPaymentValue()
                                .compareTo(BigDecimal.ZERO) > 0)
                        .mapToDouble(i -> Double.valueOf(i.getPaymentValue().toString()))
                        .sum());
    }

    public BigDecimal getExpenses() {
        return BigDecimal.valueOf(
                paymentList.stream()
                        .filter(payment -> payment.getPaymentValue()
                                .compareTo(BigDecimal.ZERO) < 0)
                        .mapToDouble(i -> Double.valueOf(i.getPaymentValue().toString()))
                        .sum());
    }

    public BigDecimal getBalance() {
        return BigDecimal.valueOf(
                paymentList.stream()
                        .mapToDouble(i -> Double.valueOf(i.getPaymentValue().toString()))
                        .sum());
    }
}