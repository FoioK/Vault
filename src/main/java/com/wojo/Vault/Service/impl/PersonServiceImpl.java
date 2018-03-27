package com.wojo.Vault.Service.impl;

import com.wojo.Vault.Database.DAO.AccountDAO;
import com.wojo.Vault.Database.DAO.AddressDAO;
import com.wojo.Vault.Database.DAO.Impl.AccountDAOImpl;
import com.wojo.Vault.Database.DAO.Impl.AddressDAOImpl;
import com.wojo.Vault.Database.DAO.Impl.PersonDAOImpl;
import com.wojo.Vault.Database.DAO.PersonDAO;
import com.wojo.Vault.Database.Model.Account;
import com.wojo.Vault.Database.Model.Address;
import com.wojo.Vault.Database.Model.Generators.PersonDataGenerator;
import com.wojo.Vault.Database.Model.Person;
import com.wojo.Vault.Exception.ErrorCode;
import com.wojo.Vault.Exception.LoginException;
import com.wojo.Vault.Service.AccountService;
import com.wojo.Vault.Service.PersonService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class PersonServiceImpl implements PersonService {

    private PersonDAO personDAO = new PersonDAOImpl();

    private AccountService accountService = new AccountServiceImpl();
    private AccountDAO accountDAO = new AccountDAOImpl();

    private AddressDAO addressDAO = new AddressDAOImpl();

    @Override
    public Person findPersonByLogin(String login) {
        Person person = personDAO.findByLogin(Optional.ofNullable(login).get());

        return person != null && !person.getPersonId().equals("-1") ? person : null;
    }

    @Override
    public String findPersonIdByLogin(String login) {
        return login != null && !login.equals("") ? personDAO.findIdByLogin(login) : "";
    }

    @Override
    public boolean setPersonData(Person person) {
        try {
            return setAccounts(person) && setAddresses(person);
        } catch (LoginException e) {
            System.out.println(e.errorCode() + ": Set person data");
        }

        return false;
    }

    private boolean setAccounts(Person person) throws LoginException {
        List<Account> accountList = accountDAO.findAllByPersonId(person.getPersonId());

        if (accountList.size() == 0) {
            accountService.createAccount(new Account(person.getPersonId(), "", BigDecimal.ZERO));
            accountList = accountDAO.findAllByPersonId(person.getPersonId());

            if (accountList.size() == 0) {
                throw new LoginException("Create account error", ErrorCode.NO_ACCOUNT_FOR_PERSON);
            }
        }

        person.setAccountList(accountList);
        return true;
    }

    private boolean setAddresses(Person person) throws LoginException {
        List<Address> addressList = addressDAO.findAll(person.getPersonId());

        if (addressList.size() == 0) {
            //TODO ADD ADDRESS
            throw new LoginException("Find addresses error", ErrorCode.NO_ADDRESS);
        }

        person.setAddressList(addressList);
        return true;
    }

    @Override
    public String createPerson(Person person, Address address) {
        String login = new PersonDataGenerator().generateLogin(Person.LOGIN_LENGTH);

        if (person != null && !login.equals("")) {
            person.setLogin(login);
            return personDAO.insertPersonAndAddress(person, address) ? login : "";
        }

        return "";
    }
}
