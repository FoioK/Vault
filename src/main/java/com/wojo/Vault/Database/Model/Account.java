package com.wojo.Vault.Database.Model;

import com.wojo.Vault.Database.Model.Generators.AccountDataGenerator;

public class Account {

    private final String IBAN_NUMBER;
    private String value;

    public Account(String countryCode, int length) {
        IBAN_NUMBER = AccountDataGenerator.generateIBAN(countryCode, length);
        value = "0";
    }

    public Account(String IBAN_NUMBER) {
        this.IBAN_NUMBER = IBAN_NUMBER;
        value = "0";
    }

    public Account() {
        this.IBAN_NUMBER = null;
        value = "0";
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
