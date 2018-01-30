package com.wojo.Vault.Database.Model.Generators;

import java.util.Random;

public class PersonDataGenerator {

    public static String generateLogin(int length) {
        return length > 0 ? generateRandomString(new Random(), length) : "";
    }

    private static String generateRandomString(Random random, int length) {
        return random.ints('0', 'Z')
                .filter(i -> (i < ':' || i > '@'))
                .mapToObj(i -> (char) i)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::append,
                        StringBuilder::append)
                .toString();
    }
}
