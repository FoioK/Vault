package com.wojo.Vault.Database.Model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public abstract class Deposit {

    private Integer percent;
    private BigDecimal minimalAmount;
    private Integer numberOfDays;
    private Integer idDeposit;
    private Integer idAccount;
    private BigDecimal depositAmount;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private DepositType depositType;

    public enum DepositType {
        Short,
        Middle,
        Long
    }

    public Deposit(Integer idDeposit, Integer idAccount, BigDecimal depositAmount, LocalDateTime startDate) {
        this.idDeposit = idDeposit;
        this.idAccount = idAccount;
        this.depositAmount = depositAmount;
        this.startDate = startDate;
    }

    public Deposit(Integer idAccount, BigDecimal depositAmount, LocalDateTime startDate) {
        this.idAccount = idAccount;
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

    public Integer getIdDeposit() {
        return idDeposit;
    }

    public void setIdDeposit(Integer idDeposit) {
        this.idDeposit = idDeposit;
    }

    public Integer getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(Integer idAccount) {
        this.idAccount = idAccount;
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
}
