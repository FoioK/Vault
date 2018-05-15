package com.wojo.Vault.Database.DAO;

import com.wojo.Vault.Database.Model.Address;

import java.util.List;

public interface AddressDAO {

    List<Address> findAll(String personId);
}
