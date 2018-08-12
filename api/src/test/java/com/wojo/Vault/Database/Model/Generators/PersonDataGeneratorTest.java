package com.wojo.Vault.Database.Model.Generators;

import com.wojo.Vault.Database.DBManager;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class PersonDataGeneratorTest {

    private static final int NUMBER_OF_TESTS = 100;
    private static final int LOGIN_LENGTH = 9;

    @BeforeClass
    public static void connectionToTestDatabase() {
        DBManager.setTestConnectionPath();
        DBManager.dbConnection();
    }

    @AfterClass
    public static void clearDatabaseAndDisconnect() {
        DBManager.dbDisconnect();
    }

    private PersonDataGenerator personDataGenerator = new PersonDataGenerator();

    @Test
    public void shouldGenerateLoginWith9Char() {
        assertEquals(IntStream.range(0, NUMBER_OF_TESTS)
                .mapToObj(value -> personDataGenerator.generateLogin(LOGIN_LENGTH))
                .filter(value -> (value.length() == LOGIN_LENGTH))
                .count(), NUMBER_OF_TESTS);
    }

    @Test
    public void shouldGenerateOnlyUpperCase() {
        assertEquals(IntStream.range(0, NUMBER_OF_TESTS)
                .mapToObj(value -> personDataGenerator.generateLogin(LOGIN_LENGTH))
                .filter(value -> value.equals(value.toUpperCase()))
                .count(), NUMBER_OF_TESTS);
    }

    @Test
    public void shouldReturnEmptyString() {
        int initValue = Integer.MIN_VALUE;
        int stepValue = 25;
        while (initValue < 0) {
            assertEquals("", personDataGenerator.generateLogin(initValue));
            initValue += stepValue;
        }
    }

}