package com.wojo.Vault.Database.DAO.Impl;

import com.wojo.Vault.Database.DAO.AddressDAO;
import com.wojo.Vault.Database.DBManager;
import com.wojo.Vault.Database.Model.Address;
import com.wojo.Vault.Exception.ExecuteStatementException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AddressDAOImpl implements AddressDAO {

    @Override
    public List<Address> findAll(String personId) {
        String queryStatement = "SELECT address.ADDRESS_ID, address.CITY, address.STREET, address.APARTMENT_NUMBER " +
                "FROM address " +
                "JOIN person_has_address p ON address.ADDRESS_ID = p.ADDRESS_ID " +
                "WHERE p.PERSON_ID LIKE ?";

        ResultSet resultSet;
        try {
            resultSet = DBManager.dbExecuteQuery(queryStatement, Collections.singletonList(personId));

            return resultSet.next() ? getAddressList(resultSet) : new ArrayList<>();
        } catch (ExecuteStatementException e) {
            System.out.println(e.errorCode() + ": Find all address");
        } catch (SQLException e) {
            System.out.println("ResultSet error: Find all address");
        }

        return new ArrayList<>();
    }

    private List<Address> getAddressList(ResultSet resultSet) throws SQLException {
        List<Address> addressList = new ArrayList<>();

        do {
            addressList.add(new Address(
                    resultSet.getString("ADDRESS_ID"),
                    resultSet.getString("CITY"),
                    resultSet.getString("STREET"),
                    resultSet.getString("APARTMENT_NUMBER")
            ));
        } while (resultSet.next());

        return addressList;
    }
}
