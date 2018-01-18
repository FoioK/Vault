package com.wojo.Vault.Model;

import org.junit.Test;

import static org.junit.Assert.*;

public class AccountTest {
    private final Account accountToTest = new Account();
    private static final String PL_COUNTRY_CODE = "PL";
    private static final int NUMBER_OF_ATTEMPTS = 100;
    private static final int NUMBER_LENGTH = 26;

    @Test
    public void shouldGenerateIBANWith26Char() {
        for (int i = 0; i < NUMBER_OF_ATTEMPTS; i++) {
            assertEquals(NUMBER_LENGTH,
                    accountToTest
                            .generateIBAN(PL_COUNTRY_CODE, NUMBER_LENGTH)
                            .substring(2)
                            .length());
        }
    }

    @Test
    public void shouldGenerateOnlyNumber() {
        for (int i = 0; i < NUMBER_OF_ATTEMPTS; i++) {
            assertEquals(NUMBER_LENGTH,
                    accountToTest.generateIBAN(PL_COUNTRY_CODE, NUMBER_LENGTH)
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
                    accountToTest.generateIBAN(PL_COUNTRY_CODE, NUMBER_LENGTH)
                            .substring(0, 2));
        }
    }

    @Test
    public void shouldReturnEmptyString() {
        int initValue = Integer.MIN_VALUE;
        while (initValue < 0) {
            assertEquals("", accountToTest.generateIBAN(PL_COUNTRY_CODE, initValue));
            initValue += 25;
        }

        String badCountryCode = "37";
        String badCountryCode2 = "ABC";
        String badCountryCode3 = "2L";
        String badCountryCode4 = "";
        assertEquals("", accountToTest.generateIBAN(badCountryCode, NUMBER_LENGTH));
        assertEquals("", accountToTest.generateIBAN(badCountryCode2, NUMBER_LENGTH));
        assertEquals("", accountToTest.generateIBAN(badCountryCode3, NUMBER_LENGTH));
        assertEquals("", accountToTest.generateIBAN(badCountryCode4, NUMBER_LENGTH));
    }
}