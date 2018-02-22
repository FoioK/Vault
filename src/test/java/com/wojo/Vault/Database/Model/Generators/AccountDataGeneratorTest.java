package com.wojo.Vault.Database.Model.Generators;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AccountDataGeneratorTest {
    private static final String PL_COUNTRY_CODE = "PL";
    private static final int NUMBER_OF_ATTEMPTS = 100;
    private static final int NUMBER_LENGTH = 26;

    private AccountDataGenerator accountDataGenerator = new AccountDataGenerator();

    @Test
    public void shouldGenerateIBANWith26Char() {
        for (int i = 0; i < NUMBER_OF_ATTEMPTS; i++) {
            assertEquals(NUMBER_LENGTH,
                    accountDataGenerator
                            .generateIBAN(PL_COUNTRY_CODE, NUMBER_LENGTH)
                            .substring(2)
                            .length());
        }
    }

    @Test
    public void shouldGenerateOnlyNumber() {
        for (int i = 0; i < NUMBER_OF_ATTEMPTS; i++) {
            assertEquals(NUMBER_LENGTH,
                    accountDataGenerator.generateIBAN(PL_COUNTRY_CODE, NUMBER_LENGTH)
                            .substring(2)
                            .chars()
                            .mapToObj(item -> (char) item)
                            .filter(j -> (j < ':' && j > '/'))
                            .count());
        }
    }

    @Test
    public void shouldGenerateCorrectCountryCode() {
        for (int i = 0; i < NUMBER_OF_ATTEMPTS; i++) {
            assertEquals(PL_COUNTRY_CODE,
                    accountDataGenerator.generateIBAN(PL_COUNTRY_CODE, NUMBER_LENGTH)
                            .substring(0, 2));
        }
    }

    @Test
    public void shouldReturnEmptyString() {
        int initValue = Integer.MIN_VALUE;
        while (initValue < 0) {
            assertEquals("", accountDataGenerator.generateIBAN(PL_COUNTRY_CODE, initValue));
            initValue += 25;
        }

        String badCountryCode = "37";
        String badCountryCode2 = "ABC";
        String badCountryCode3 = "2L";
        String badCountryCode4 = "";
        assertEquals("", accountDataGenerator
                .generateIBAN(badCountryCode, NUMBER_LENGTH));
        assertEquals("", accountDataGenerator
                .generateIBAN(badCountryCode2, NUMBER_LENGTH));
        assertEquals("", accountDataGenerator
                .generateIBAN(badCountryCode3, NUMBER_LENGTH));
        assertEquals("", accountDataGenerator
                .generateIBAN(badCountryCode4, NUMBER_LENGTH));
    }
}