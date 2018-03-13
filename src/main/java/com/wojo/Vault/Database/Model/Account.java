package com.wojo.Vault.Database.Model;

import com.wojo.Vault.Database.Model.Generators.AccountDataGenerator;

import java.math.BigDecimal;

public class Account {

    private Integer idAccount;
    private String IBAN_NUMBER;
    private BigDecimal value;

    public Account(String countryCode, int length, BigDecimal value) {
        AccountDataGenerator accountDataGenerator = new AccountDataGenerator();
        IBAN_NUMBER = accountDataGenerator.generateIBAN(countryCode, length);
        this.value = value;
    }

    public Account(Integer idAccount, String IBAN_NUMBER, BigDecimal value) {
        this.idAccount = idAccount;
        this.IBAN_NUMBER = IBAN_NUMBER;
        this.value = value;
    }

    public Account() {
        this(0, "", new BigDecimal("0"));
    }

    public Integer getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(Integer idAccount) {
        this.idAccount = idAccount;
    }

    public String getIBAN_NUMBER() {
        return IBAN_NUMBER;
    }

    public void setIBAN_NUMBER(String IBAN_NUMBER) {
        this.IBAN_NUMBER = IBAN_NUMBER;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
