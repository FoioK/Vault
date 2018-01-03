package com.wojo.Vault.Model;

import java.util.Random;

public class Account {

    private int idPerson;
    private int idAccount;
    private String ibanNumber;
    private int value;

    public Account(int idPerson, String countryCode, int length) {
        this.idPerson = idPerson;
        ibanNumber = generateIBAN(countryCode, length);
    }

    public Account() {

    }

    public void setIdAccount(int idAccount) {
        this.idAccount = idAccount;
    }

    public void setIdPerson(int idPerson) {
        this.idPerson = idPerson;
    }

    public void setIbanNumber(String ibanNumber) {
        this.ibanNumber = ibanNumber;
    }

    public int getIdAccount() {
        return idAccount;
    }

    public int getIdPerson() {
        return idPerson;
    }

    public String getIbanNumber() {
        return ibanNumber;
    }
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String generateIBAN(String countryCode, int length) {
        return length > 0 ? countryCode + generateRandomNumber(new Random(), length) : "";
    }

    private String generateRandomNumber(Random random, int length) {
        return random.ints('0', '9')
                .mapToObj(i -> (char) i).limit(length)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }

}
