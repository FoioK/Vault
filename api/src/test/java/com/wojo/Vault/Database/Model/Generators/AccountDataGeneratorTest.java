package com.wojo.Vault.Database.Model.Generators;

import com.wojo.Vault.Database.DBManager;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AccountDataGeneratorTest {
    private static final int NUMBER_OF_ATTEMPTS = 100;
    private static final int NUMBER_LENGTH = 26;

    @BeforeClass
    public static void connectionToTestDatabase() {
        DBManager.setTestConnectionPath();
        DBManager.dbConnection();
    }

    @AfterClass
    public static void clearDatabaseAndDisconnect() {
        DBManager.dbDisconnect();
    }

    private AccountDataGenerator accountDataGenerator = new AccountDataGenerator();

    @Test
    public void shouldGenerateIBANWith26Char() {
        for (int i = 0; i < NUMBER_OF_ATTEMPTS; i++) {
            assertEquals(NUMBER_LENGTH,
                    accountDataGenerator
                            .generateIBAN(NUMBER_LENGTH)
                            .length());
        }
    }

    @Test
    public void shouldGenerateOnlyNumber() {
        for (int i = 0; i < NUMBER_OF_ATTEMPTS; i++) {
            assertEquals(NUMBER_LENGTH,
                    accountDataGenerator.generateIBAN(NUMBER_LENGTH)
                            .chars()
                            .mapToObj(item -> (char) item)
                            .filter(j -> (j < ':' && j > '/'))
                            .count());
        }
    }

    @Test
    public void shouldReturnEmptyString() {
        int initValue = Integer.MIN_VALUE;
        while (initValue < 0) {
            assertEquals("", accountDataGenerator.generateIBAN(initValue));
            initValue += 25;
        }
    }
}