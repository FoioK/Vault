package com.wojo.Vault.Service;

import com.wojo.Vault.Database.Model.Address;
import com.wojo.Vault.Database.Model.Person;

public interface PersonService {
    Person findPersonByLogin(String login);

    String findPersonIdByLogin(String login);

    boolean setPersonData(Person person);

    String createPerson(Person person, Address address);
}
