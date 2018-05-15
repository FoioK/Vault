package com.wojo.Vault.Database.Model;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
                        .filter(payment -> payment.getAmount()
                                .compareTo(BigDecimal.ZERO) > 0)
                        .mapToDouble(i -> Double.valueOf(i.getAmount().toString()))
                        .sum()).setScale(2, RoundingMode.HALF_EVEN);
    }

    public BigDecimal getExpenses() {
        return BigDecimal.valueOf(
                paymentList.stream()
                        .filter(payment -> payment.getAmount()
                                .compareTo(BigDecimal.ZERO) < 0)
                        .mapToDouble(i -> Double.valueOf(i.getAmount().toString()))
                        .sum()).setScale(2, RoundingMode.HALF_EVEN);
    }

    public BigDecimal getBalance() {
        return BigDecimal.valueOf(
                paymentList.stream()
                        .mapToDouble(i -> Double.valueOf(i.getAmount().toString()))
                        .sum()).setScale(2, RoundingMode.HALF_EVEN);
    }
}