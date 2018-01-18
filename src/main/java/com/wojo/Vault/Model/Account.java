package com.wojo.Vault.Model;

import java.util.Random;

public class Account {

    private final String IBAN_NUMBER;
    private String value;

    public Account(String countryCode, int length) {
        IBAN_NUMBER = generateIBAN(countryCode, length);
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

    public String generateIBAN(String countryCode, int length) {
        return countryCode.length() == 2 ?
                length > 0 ?
                        isCountryCodeCorrect(countryCode) ?
                                countryCode + generateRandomNumber(new Random(), length) :
                                "" :
                        "" :
                "";
    }

    private boolean isCountryCodeCorrect(String countryCode) {
        return countryCode.chars()
                .mapToObj(item -> (char) item)
                .filter(i -> (i < '[' && i > '@'))
                .count() == countryCode.length();
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
