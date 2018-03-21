package com.wojo.Vault.Database.Model;

import java.math.BigDecimal;

public class Account {

    private String accountId;
    private String personId;
    private String number;
    private BigDecimal value;

    public Account(String accountId, String personId, String number, BigDecimal value) {
        this.accountId = accountId;
        this.personId = personId;
        this.number = number;
        this.value = value;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
