package com.wojo.Vault.Database.Model;

import java.math.BigDecimal;
import java.util.Objects;

public class Account {

    public static final Integer NUMBER_LENGTH = 26;
    public static final Integer ROUND_SCALE = 2;

    private String accountId;
    private String personId;
    private String number;
    private BigDecimal value;

    public Account(String personId, String number, BigDecimal value) {
        this.personId = personId;
        this.number = number;
        this.value = value;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return Objects.equals(accountId, account.accountId) &&
                Objects.equals(personId, account.personId) &&
                Objects.equals(number, account.number) &&
                Objects.equals(value, account.value);
    }

    @Override
    public int hashCode() {

        return Objects.hash(accountId, personId, number, value);
    }
}
