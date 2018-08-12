package com.wojo.Vault.Service.impl;

import com.wojo.Vault.Database.DBManager;
import com.wojo.Vault.Database.Model.Account;
import com.wojo.Vault.Database.Model.Address;
import com.wojo.Vault.Database.Model.Generators.AccountDataGenerator;
import com.wojo.Vault.Database.Model.Person;
import com.wojo.Vault.Exception.ExecuteStatementException;
import com.wojo.Vault.Exception.LoginException;
import org.junit.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.Assert.*;

public class PersonServiceImplTest {

    private static final String PERSON_TABLE_NAME = "person";
    private static final String ACCOUNT_TABLE_NAME = "account";
    private static final String PERSON_HAS_ADDRESS_TABLE_NAME = "person_has_address";

    @BeforeClass
    public static void setup() throws ExecuteStatementException {
        Configuration.connectionToTestDatabase();
        Configuration.disableForeignKeyCheck();
        Configuration.truncateTable(PERSON_TABLE_NAME);
    }

    @AfterClass
    public static void clearDatabaseAndDisconnect() throws ExecuteStatementException {
        Configuration.disableForeignKeyCheck();
        Configuration.truncateTable(PERSON_TABLE_NAME);
        Configuration.truncateTable(ACCOUNT_TABLE_NAME);
        Configuration.truncateTable(PERSON_HAS_ADDRESS_TABLE_NAME);
        Configuration.enableForeignKeyCheck();
        DBManager.dbDisconnect();
    }

    private static final Person FIRST_PERSON = new Person(
            "16",
            "First",
            "Yul",
            "987123456",
            "ZXCVBNMLP",
            "Pass".toCharArray(),
            LocalDateTime.now()
    );

    private static final Person SECOND_PERSON = new Person(
            "17",
            "Second",
            "Blg",
            "321123678",
            "QWERTYUIO",
            "Pass".toCharArray(),
            LocalDateTime.now()
    );

    @Before
    public void insertDataToTest() throws ExecuteStatementException {
        String updateStatement = "INSERT INTO person " +
                "(PERSON_ID, FIRST_NAME, LAST_NAME, TELEPHONE_NUMBER, LOGIN, PASSWORD, CREATE_TIME) " +
                "VALUES " +
                "(?, ?, ?, ?, ?, ?, ?)";

        DBManager.dbExecuteUpdate(updateStatement, Arrays.asList(
                String.valueOf(FIRST_PERSON.getPersonId()),
                FIRST_PERSON.getFirstName(),
                FIRST_PERSON.getLastName(),
                FIRST_PERSON.getTelephoneNumber(),
                FIRST_PERSON.getLogin(),
                String.valueOf(FIRST_PERSON.getPassword()),
                FIRST_PERSON.getCreateTime().toString()
        ));

        DBManager.dbExecuteUpdate(updateStatement, Arrays.asList(
                String.valueOf(SECOND_PERSON.getPersonId()),
                SECOND_PERSON.getFirstName(),
                SECOND_PERSON.getLastName(),
                SECOND_PERSON.getTelephoneNumber(),
                SECOND_PERSON.getLogin(),
                String.valueOf(SECOND_PERSON.getPassword()),
                SECOND_PERSON.getCreateTime().toString()
        ));
    }

    @After
    public void clearTables() throws ExecuteStatementException {
        Configuration.disableForeignKeyCheck();
        Configuration.truncateTable(PERSON_TABLE_NAME);
        Configuration.truncateTable(ACCOUNT_TABLE_NAME);
        Configuration.truncateTable(PERSON_HAS_ADDRESS_TABLE_NAME);
    }

    private PersonServiceImpl personService = new PersonServiceImpl();

    @Test
    public void shouldFindPersonByLogin() {
        Person firstFoundedPerson = personService.findPersonByLogin(FIRST_PERSON.getLogin());
        assertEquals(FIRST_PERSON, firstFoundedPerson);

        Person secondFoundedPerson = personService.findPersonByLogin(SECOND_PERSON.getLogin());
        assertEquals(SECOND_PERSON, secondFoundedPerson);
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldNotFindPersonForNullParameter() {
        Person foundedPerson = personService.findPersonByLogin(null);
        @SuppressWarnings("unused") Integer personId = Integer.valueOf(foundedPerson.getPersonId());
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotFindPersonForBadParameter() {
        String badLogin = "XCV";
        Person foundedPerson = personService.findPersonByLogin(badLogin);
        @SuppressWarnings("unused") Integer personId = Integer.valueOf(foundedPerson.getPersonId());
    }

    @Test
    public void shouldFindPersonIdByLogin() {
        String firstFoundedPersonId = personService.findPersonIdByLogin(FIRST_PERSON.getLogin());
        assertEquals(firstFoundedPersonId, FIRST_PERSON.getPersonId());

        String secondFoundedPersonId = personService.findPersonIdByLogin(SECOND_PERSON.getLogin());
        assertEquals(secondFoundedPersonId, SECOND_PERSON.getPersonId());
    }

    @Test
    public void findPersonIdForNullParameterTest() {
        String expectedPersonId = "";
        String foundedPersonId = personService.findPersonIdByLogin(null);
        assertEquals(expectedPersonId, foundedPersonId);
    }

    @Test
    public void findPersonIdForBadParameterTest() {
        String expectedPersonId = "";

        String badLogin = "XBN";
        String foundedPersonId = personService.findPersonIdByLogin(badLogin);

        assertEquals(expectedPersonId, foundedPersonId);
    }

    @Test
    public void shouldSetPersonData() throws ExecuteStatementException {
        insertAddressToTest(FIRST_PERSON.getPersonId());
        assertTrue(personService.setPersonData(FIRST_PERSON));
    }

    private Account insertAccountToTest(String personId) throws ExecuteStatementException {
        String uniqueAccountId = "74";
        Integer numberLength = 26;
        String number = new AccountDataGenerator().generateIBAN(numberLength);

        Account account = new Account(uniqueAccountId, personId, number, new BigDecimal("0.00"));

        String updateStatement = "INSERT INTO account (ACCOUNT_ID, PERSON_ID, NUMBER, VALUE) VALUES (?, ?, ?, ?)";
        return DBManager.dbExecuteUpdate(
                updateStatement, Arrays.asList(
                        account.getAccountId(),
                        account.getPersonId(),
                        account.getNumber(),
                        account.getValue().toString())) == 1 ? account : null;
    }

    private static final Address ADDRESS = new Address(
            "32",
            "City",
            "Street",
            "37"
    );

    private Address insertAddressToTest(String personId) throws ExecuteStatementException {
        String updateStatementAddress = "INSERT INTO ADDRESS " +
                "(ADDRESS_ID, CITY, STREET, APARTMENT_NUMBER) " +
                "VALUES " +
                "(?, ?, ?, ?)";

        String updateStatementPersonHasAddress =
                "INSERT INTO person_has_address (PERSON_ID, ADDRESS_ID) VALUES (?, ?)";

        List<String> firstDataList = Arrays.asList(
                ADDRESS.getAddressId(),
                ADDRESS.getCity(),
                ADDRESS.getStreet(),
                ADDRESS.getApartmentNumber()
        );

        List<String> secondData = Arrays.asList(
                personId,
                ADDRESS.getAddressId()
        );

        Map<List<String>, String> dataToUpdate = new HashMap<>(2);
        dataToUpdate.put(firstDataList, updateStatementAddress);
        dataToUpdate.put(secondData, updateStatementPersonHasAddress);

        return DBManager.dbExecuteTransactionUpdate(dataToUpdate) ? ADDRESS : null;
    }

    @Test
    public void shouldSetAccounts() throws ExecuteStatementException {
        Account firstAccount = insertAccountToTest(FIRST_PERSON.getPersonId());
        assertNotNull(firstAccount);
        assertTrue(personService.setAccounts(FIRST_PERSON));
        assertEquals(1, FIRST_PERSON.getAccountList().size());
        assertEquals(firstAccount, FIRST_PERSON.getAccountList().get(0));
        assertEquals(firstAccount.hashCode(), FIRST_PERSON.getAccountList().get(0).hashCode());
    }

    @Test
    public void shouldCreateAndSetAccounts() {
        assertNull(FIRST_PERSON.getAccountList());
        assertTrue(personService.setAccounts(FIRST_PERSON));
        assertEquals(1, FIRST_PERSON.getAccountList().size());
    }

    @Test
    public void shouldSetAddresses() throws ExecuteStatementException {
        Address firstAddress = insertAddressToTest(FIRST_PERSON.getPersonId());
        assertNotNull(firstAddress);
        assertTrue(personService.setAddresses(FIRST_PERSON));
        assertEquals(1, FIRST_PERSON.getAddressList().size());
        assertTrue(Objects.equals(firstAddress, FIRST_PERSON.getAddressList().get(0)));
        assertEquals(firstAddress.hashCode(), FIRST_PERSON.getAddressList().get(0).hashCode());
    }

    @Test(expected = LoginException.class)
    public void shouldNotSetAddresses() {
        assertTrue(personService.setAddresses(FIRST_PERSON));
    }

    @Test
    public void shouldCreatePerson() {
        assertNotEquals("", personService.createPerson(FIRST_PERSON, ADDRESS));
        assertNotEquals("", personService.createPerson(SECOND_PERSON, ADDRESS));
    }

    @Test
    public void shouldNotCreatePersonForNullParameter() {
        assertEquals("", personService.createPerson(null, null));
    }
}