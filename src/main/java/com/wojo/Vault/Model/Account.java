package com.wojo.Vault.Model;

import java.util.Random;

public class Account {

//    private static final int ID_PERSON = Person.getIdPersonInDatabase();
    private final String IBAN_NUMBER;
    private int value;

    public Account(String countryCode, int length) {
        IBAN_NUMBER = generateIBAN(countryCode, length);
    }

    public Account(String IBAN_NUMBER) {
        this.IBAN_NUMBER = IBAN_NUMBER;
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

    public String getIBAN_NUMBER() {
        return IBAN_NUMBER;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

}
