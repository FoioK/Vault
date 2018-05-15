package com.wojo.Vault.Database.DAO;

import com.wojo.Vault.Database.Model.Address;
import com.wojo.Vault.Database.Model.Person;

public interface PersonDAO {

    boolean insertPersonAndAddress(Person person, Address address);

    Person findByLogin(String login);

    String findIdByLogin(String login);
    
    boolean isLoginExist(String login);
}
