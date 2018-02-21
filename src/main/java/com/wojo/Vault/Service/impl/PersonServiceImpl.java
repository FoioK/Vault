package com.wojo.Vault.Service.impl;

import com.wojo.Vault.Database.DAO.Impl.PersonDAOImpl;
import com.wojo.Vault.Database.DAO.PersonDAO;
import com.wojo.Vault.Database.Model.Generators.PersonDataGenerator;
import com.wojo.Vault.Database.Model.Person;
import com.wojo.Vault.Service.PersonService;

import java.util.List;

public class PersonServiceImpl implements PersonService {

    private PersonDAO personDAO = new PersonDAOImpl();

    @Override
    public String generateLogin(int length) {
        return new PersonDataGenerator().generateLogin(length);
    }

    @Override
    public boolean searchPersonLogin(String login) {
        return personDAO.searchPersonLogin(login);
    }

    @Override
    public void setPeronLogin(String login) {
        Person.setLogin(login);
    }

    @Override
    public boolean loginStep2Process(String password) {
        String[] idPersonAndPassword = personDAO.getIdPersonAndPassword(Person.getLogin());
        if (idPersonAndPassword == null || idPersonAndPassword.length != 2) {
            return false;
        }
        if (idPersonAndPassword[1].equals(password)) {
            personDAO.insertPersonData(Integer.valueOf(idPersonAndPassword[0]));
            return true;
        }
        return false;
    }

    @Override
    public int insertPersonToDB(List<String> accountDate) {
        return personDAO.insertPersonToDB(accountDate);
    }

    @Override
    public <T> boolean deletePerson(T value) {
        return personDAO.deletePerson(value);
    }

}
