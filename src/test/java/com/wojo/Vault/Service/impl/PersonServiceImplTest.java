package com.wojo.Vault.Service.impl;

import com.wojo.Vault.Database.DBManager;
import com.wojo.Vault.Database.Model.Person;
import com.wojo.Vault.Service.PersonService;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class PersonServiceImplTest {

    private static final String LOGIN = "ABCDEFGHI";
    private static final String PASSWORD = "Test";

    private static final String BAD_PASSWORD = "badPassword";

    private PersonService personService = new PersonServiceImpl();

    @BeforeClass
    public static void setConnectionTestPath() {
        DBManager.setTestConnectionPath();
    }

    @Test
    public void loginProcessShouldReturnTrueToValidData() {
        Person.setLogin(LOGIN);
        assertTrue(personService.loginStep2Process(PASSWORD));
    }

    @Test
    public void loginProcessShouldReturnFalseToInvalidData() {
        Person.setLogin(LOGIN);
        assertFalse(personService.loginStep2Process(BAD_PASSWORD));
    }
}