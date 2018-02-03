package com.wojo.Vault.Database.Model;

import com.wojo.Vault.Database.Model.Generators.AccountDataGenerator;

import java.util.List;

public class Account {

    private Integer idAccount;
    private final String IBAN_NUMBER;
    private String value;
    private List<Payment> paymentList;

    public Account(String countryCode, int length) {
        IBAN_NUMBER = AccountDataGenerator.generateIBAN(countryCode, length);
        value = "0";
    }

    public Account(Integer idAccount, String IBAN_NUMBER) {
        this.idAccount = idAccount;
        this.IBAN_NUMBER = IBAN_NUMBER;
        value = "0";
    }

    public Account() {
        this.IBAN_NUMBER = null;
        value = "0";
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
