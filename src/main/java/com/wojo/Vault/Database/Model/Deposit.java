package com.wojo.Vault.Database.Model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public abstract class Deposit {

    private Integer percent;
    private BigDecimal minimalAmount;
    private Integer numberOfDays;
    private String depositId;
    private String accountId;
    private BigDecimal depositAmount;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private DepositType depositType;

    public enum DepositType {
        Short,
        Middle,
        Long
    }

    public Deposit(String depositId, String accountId, BigDecimal depositAmount, LocalDateTime startDate) {
        this.depositId = depositId;
        this.accountId = accountId;
        this.depositAmount = depositAmount;
        this.startDate = startDate;
    }

    public Deposit(String accountId, BigDecimal depositAmount, LocalDateTime startDate) {
        this.accountId = accountId;
        this.depositAmount = depositAmount;
        this.startDate = startDate;
    }

    public Integer getPercent() {
        return percent;
    }

    public void setPercent(Integer percent) {
        this.percent = percent;
    }

    public BigDecimal getMinimalAmount() {
        return minimalAmount;
    }

    public void setMinimalAmount(BigDecimal minimalAmount) {
        this.minimalAmount = minimalAmount;
    }

    public Integer getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(Integer numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public String getDepositId() {
        return depositId;
    }

    public void setDepositId(String depositId) {
        this.depositId = depositId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(BigDecimal depositAmount) {
        this.depositAmount = depositAmount;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public DepositType getDepositType() {
        return depositType;
    }

    public void setDepositType(DepositType depositType) {
        this.depositType = depositType;
    }

    public long getHoursToEnd() {
        return ChronoUnit.HOURS.between(LocalDateTime.now(), this.endDate);
    }

    public BigDecimal getProfit() {
        return BigDecimal.valueOf(this.getDepositAmount().doubleValue() *
                this.getPercent() / 100 * this.getNumberOfDays() / 365).setScale(2, RoundingMode.CEILING);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Deposit)) return false;
        Deposit deposit = (Deposit) o;
        return Objects.equals(percent, deposit.percent) &&
                Objects.equals(minimalAmount, deposit.minimalAmount) &&
                Objects.equals(numberOfDays, deposit.numberOfDays) &&
                Objects.equals(depositId, deposit.depositId) &&
                Objects.equals(accountId, deposit.accountId) &&
                Objects.equals(depositAmount, deposit.depositAmount) &&
                Objects.equals(startDate.toLocalDate(), deposit.startDate.toLocalDate()) &&
                Objects.equals(endDate.toLocalDate(), deposit.endDate.toLocalDate()) &&
                depositType == deposit.depositType;
    }

    @Override
    public int hashCode() {

        return Objects.hash(percent, minimalAmount, numberOfDays,
                depositId, accountId, depositAmount, startDate, endDate, depositType);
    }
}
