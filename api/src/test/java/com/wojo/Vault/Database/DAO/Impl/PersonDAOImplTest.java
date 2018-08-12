package com.wojo.Vault.Database.DAO.Impl;

import com.wojo.Vault.Database.DAO.PersonDAO;
import com.wojo.Vault.Database.DBManager;
import com.wojo.Vault.Database.Model.Address;
import com.wojo.Vault.Database.Model.Person;
import com.wojo.Vault.Exception.ExecuteStatementException;
import org.junit.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class PersonDAOImplTest {
    /**
     * Test Person on database
     */
    private static final String PERSON_ID = "17";
    private static final String FIRST_NAME = "FirstName";
    private static final String LAST_NAME = "LastName";
    private static final String TELEPHONE_NUMBER = "123456789";
    private static final String LOGIN = "ABCDEFGHI";
    private static final String PASSWORD = "Test";
    private static final LocalDateTime CREATE_TIME = LocalDateTime.now();

    private static final String DISABLE_FOREIGN_KEY_CHECK = "SET FOREIGN_KEY_CHECKS = 0";
    private static final String ENABLE_FOREIGN_KEY_CHECK = "SET FOREIGN_KEY_CHECKS = 1";

    private PersonDAO personDAO = new PersonDAOImpl();

    @BeforeClass
    public static void connectionToTestDatabase() throws ExecuteStatementException {
        DBManager.setTestConnectionPath();
        DBManager.dbConnection();

        DBManager.dbExecuteUpdate(DISABLE_FOREIGN_KEY_CHECK, null);
        String updateStatement = "TRUNCATE TABLE person";
        DBManager.dbExecuteUpdate(updateStatement, null);
        DBManager.dbExecuteUpdate(ENABLE_FOREIGN_KEY_CHECK, null);
    }

    @AfterClass
    public static void clearDatabaseAndDisconnect() throws ExecuteStatementException {
        DBManager.dbExecuteUpdate(DISABLE_FOREIGN_KEY_CHECK, null);
        String updateStatement = "TRUNCATE TABLE person";
        DBManager.dbExecuteUpdate(updateStatement, null);
        DBManager.dbExecuteUpdate(ENABLE_FOREIGN_KEY_CHECK, null);

        DBManager.dbDisconnect();
    }

    @Before
    public void insertDataToTest() throws ExecuteStatementException {
        String updatePersonStatement = "INSERT INTO person " +
                "(PERSON_ID, FIRST_NAME, LAST_NAME, TELEPHONE_NUMBER, LOGIN, PASSWORD, CREATE_TIME) " +
                "VALUES " +
                "(?, ?, ?, ?, ?, ?, ?)";
        List<String> dataToUpdate = Arrays.asList(
                PERSON_ID,
                FIRST_NAME,
                LAST_NAME,
                TELEPHONE_NUMBER,
                LOGIN,
                PASSWORD,
                CREATE_TIME.toString()
        );
        DBManager.dbExecuteUpdate(updatePersonStatement, dataToUpdate);
    }

    @After
    public void clearTable() throws ExecuteStatementException {
        DBManager.dbExecuteUpdate(DISABLE_FOREIGN_KEY_CHECK, null);
        String updateStatement = "TRUNCATE TABLE person";
        DBManager.dbExecuteUpdate(updateStatement, null);
        DBManager.dbExecuteUpdate(ENABLE_FOREIGN_KEY_CHECK, null);
    }


    @Test
    public void shouldInsertPersonAndAddress() {
        Person person = new Person(
                FIRST_NAME,
                LAST_NAME,
                TELEPHONE_NUMBER,
                LOGIN,
                PASSWORD.toCharArray(),
                CREATE_TIME
        );

        final String city = "CityName";
        final String street = "StreetName";
        final String apartmentNumber = "12/4";

        Address address = new Address(
                city,
                street,
                apartmentNumber
        );

        assertTrue(personDAO.insertPersonAndAddress(person, address));
    }

    @Test
    public void shouldFindPersonByLogin() {
        Person expectedObject = new Person(
                PERSON_ID,
                FIRST_NAME,
                LAST_NAME,
                TELEPHONE_NUMBER,
                LOGIN,
                PASSWORD.toCharArray(),
                CREATE_TIME
        );

        Person foundPerson = personDAO.findByLogin(LOGIN);

        assertTrue(expectedObject.equals(foundPerson));
        assertEquals(expectedObject.hashCode(), foundPerson.hashCode());
    }

    @Test
    public void shouldNotFindPersonByLogin() {
        String badLogin = "";
        String expectedPersonId = "-1";

        assertEquals(expectedPersonId, personDAO.findByLogin(badLogin).getPersonId());
    }

    @Test
    public void shouldFindPersonIdByLogin() {
        assertEquals(PERSON_ID, personDAO.findIdByLogin(LOGIN));
    }

    @Test
    public void shouldNotFindPersonIdByLogin() {
        String badPersonId = "-33";

        assertEquals("", personDAO.findIdByLogin(badPersonId));
    }

    @Test
    public void isLoginExistTest() {
        assertTrue(personDAO.isLoginExist(LOGIN));

        String badLogin = "-98";
        assertFalse(personDAO.isLoginExist(badLogin));
    }

    @Test
    public void searchLoginWithBadParameterValue() {
        assertFalse(personDAO.isLoginExist(null));
    }
}
