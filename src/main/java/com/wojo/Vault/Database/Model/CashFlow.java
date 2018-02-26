package com.wojo.Vault.Database.Model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class CashFlow {

    private Integer year;
    private Month month;
    private List<Payment> paymentList = new ArrayList<>();

    public CashFlow(LocalDate date) {
        month = date.getMonth();
        year = date.getYear();
    }

    public Integer getYear() {
        return year;
    }

    public Month getMonth() {
        return month;
    }

    public void addPayment(Payment payment) {
        paymentList.add(payment);
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