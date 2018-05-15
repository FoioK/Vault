package com.wojo.Vault.Database.DAO.Impl;

import com.wojo.Vault.Database.DAO.AddressDAO;
import com.wojo.Vault.Database.DBManager;
import com.wojo.Vault.Database.Model.Address;
import com.wojo.Vault.Exception.ExecuteStatementException;
import org.junit.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class AddressDAOImplTest {

    private static final String PERSON_ID = "11";

    private static final Address FIRST_ADDRESS = new Address(
            "10",
            "City1",
            "Street1",
            "1/10"
    );

    private static final Address SECOND_ADDRESS = new Address(
            "20",
            "City2",
            "Street2",
            "2/20"
    );

    private static final Address THIRD_ADDRESS = new Address(
            "30",
            "City3",
            "Street3",
            "3/30"
    );

    private static final String DISABLE_FOREIGN_KEY_CHECK = "SET FOREIGN_KEY_CHECKS = 0";
    private static final String ENABLE_FOREIGN_KEY_CHECK = "SET FOREIGN_KEY_CHECKS = 1";

    private AddressDAO addressDAO = new AddressDAOImpl();

    @BeforeClass
    public static void connectionToTestDatabase() throws ExecuteStatementException {
        DBManager.setTestConnectionPath();
        DBManager.dbConnection();

        DBManager.dbExecuteUpdate(DISABLE_FOREIGN_KEY_CHECK, null);

        String updateStatementAddress = "TRUNCATE TABLE address";
        DBManager.dbExecuteUpdate(updateStatementAddress, null);

        String updateStatementPersonHasAddress = "TRUNCATE TABLE person_has_address";
        DBManager.dbExecuteUpdate(updateStatementPersonHasAddress, null);
    }

    @AfterClass
    public static void clearDatabaseAndDisconnect() throws ExecuteStatementException {
        String updateStatementAddress = "TRUNCATE TABLE address";
        DBManager.dbExecuteUpdate(updateStatementAddress, null);

        String updateStatementPersonHasAddress = "TRUNCATE TABLE person_has_address";
        DBManager.dbExecuteUpdate(updateStatementPersonHasAddress, null);

        DBManager.dbExecuteUpdate(ENABLE_FOREIGN_KEY_CHECK, null);

        DBManager.dbDisconnect();
    }

    @Before
    public void insertDataToTest() throws ExecuteStatementException {
        String updateStatementAddress = "INSERT INTO address " +
                "(ADDRESS_ID, CITY, STREET, APARTMENT_NUMBER) " +
                "VALUES " +
                "(?, ?, ?, ?)";

        String updateStatementPersonHasAddress = "INSERT INTO person_has_address " +
                "(PERSON_ID, ADDRESS_ID) " +
                "VALUES " +
                "(?, ?)";

        List<String> firstAddressData = Arrays.asList(
                FIRST_ADDRESS.getAddressId(),
                FIRST_ADDRESS.getCity(),
                FIRST_ADDRESS.getStreet(),
                FIRST_ADDRESS.getApartmentNumber()
        );
        List<String> firstPersonHasAddressData = Arrays.asList(
               PERSON_ID,
               FIRST_ADDRESS.getAddressId()
        );

        Map<List<String>, String> firstDataToUpdate = new HashMap<>();
        firstDataToUpdate.put(firstAddressData, updateStatementAddress);
        firstDataToUpdate.put(firstPersonHasAddressData, updateStatementPersonHasAddress);
        DBManager.dbExecuteTransactionUpdate(firstDataToUpdate);

        List<String> secondAddressData = Arrays.asList(
                SECOND_ADDRESS.getAddressId(),
                SECOND_ADDRESS.getCity(),
                SECOND_ADDRESS.getStreet(),
                SECOND_ADDRESS.getApartmentNumber()
        );
        List<String> secondPersonHasAddressData = Arrays.asList(
                PERSON_ID,
                SECOND_ADDRESS.getAddressId()
        );

        Map<List<String>, String> secondDataToUpdate = new HashMap<>();
        secondDataToUpdate.put(secondAddressData, updateStatementAddress);
        secondDataToUpdate.put(secondPersonHasAddressData, updateStatementPersonHasAddress);
        DBManager.dbExecuteTransactionUpdate(secondDataToUpdate);

        List<String> thirdAddressData = Arrays.asList(
                THIRD_ADDRESS.getAddressId(),
                THIRD_ADDRESS.getCity(),
                THIRD_ADDRESS.getStreet(),
                THIRD_ADDRESS.getApartmentNumber()
        );
        List<String> thirdPersonHasAddressData = Arrays.asList(
                PERSON_ID,
                THIRD_ADDRESS.getAddressId()
        );

        Map<List<String>, String> thirdDataToUpdate = new HashMap<>();
        thirdDataToUpdate.put(thirdAddressData, updateStatementAddress);
        thirdDataToUpdate.put(thirdPersonHasAddressData, updateStatementPersonHasAddress);
        DBManager.dbExecuteTransactionUpdate(thirdDataToUpdate);
    }

    @After
    public void clearTable() throws ExecuteStatementException {
        DBManager.dbExecuteUpdate(DISABLE_FOREIGN_KEY_CHECK, null);

        String updateStatement = "TRUNCATE TABLE person";
        DBManager.dbExecuteUpdate(updateStatement, null);

        String updateStatementPersonHasAddress = "TRUNCATE TABLE person_has_address";
        DBManager.dbExecuteUpdate(updateStatementPersonHasAddress, null);
    }

    @Test
    public void shouldFindAllAddresses() {
        List<Address> addressList = addressDAO.findAll(PERSON_ID);

        int expectedSize = 3;
        assertEquals(expectedSize, addressList.size());

        Address expectedFirstAddress = null;
        Address expectedSecondAddress = null;
        Address expectedThirdAddress = null;

        for (Address address : addressList) {
            String addressId = address.getAddressId();

            if (addressId.equals(FIRST_ADDRESS.getAddressId())) {
                expectedFirstAddress = address;
            } else if (addressId.equals(SECOND_ADDRESS.getAddressId())) {
                expectedSecondAddress = address;
            } else {
                expectedThirdAddress = address;
            }
        }

        assertTrue(expectedFirstAddress != null);
        assertTrue(expectedSecondAddress != null);
        assertTrue(expectedThirdAddress != null);

        assertTrue(expectedFirstAddress.equals(FIRST_ADDRESS));
        assertTrue(expectedSecondAddress.equals(SECOND_ADDRESS));
        assertTrue(expectedThirdAddress.equals(THIRD_ADDRESS));
    }

    @Test
    public void shouldNotFindAddresses() {
        String badPersonId = "-20";
        int expectedSize = 0;

        assertEquals(expectedSize, addressDAO.findAll(badPersonId).size());
    }
}