package com.wojo.Vault.Database.Model.Generators;

import java.util.Random;

public class AccountDataGenerator {

    public static String generateIBAN(String countryCode, int length) {
        return countryCode.length() == 2 ?
                length > 0 ?
                        isCountryCodeCorrect(countryCode) ?
                                countryCode + generateRandomNumber(new Random(), length) :
                                "" :
                        "" :
                "";
    }

    private static boolean isCountryCodeCorrect(String countryCode) {
        return countryCode.chars()
                .mapToObj(item -> (char) item)
                .filter(i -> (i < '[' && i > '@'))
                .count() == countryCode.length();
    }

    private static String generateRandomNumber(Random random, int length) {
        return random.ints('0', '9')
                .mapToObj(i -> (char) i).limit(length)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }
}
