package com.wojo.Vault.Database.Model.Generators;

import com.wojo.Vault.Database.DAO.Impl.PersonDAOImpl;
import com.wojo.Vault.Database.DAO.PersonDAO;

import java.util.Random;

public class PersonDataGenerator {

    private PersonDAO personDAO = new PersonDAOImpl();

    public String generateLogin(int length) {
        return length > 0 ? generateLoginProcess(length) : "";
    }

    private String generateLoginProcess(int length) {
        String login = generateRandomString(new Random(), length);

        return personDAO.isLoginExist(login) ? generateLoginProcess(length) : login;
    }

    private String generateRandomString(Random random, int length) {
        return random.ints('0', 'Z')
                .filter(i -> (i < ':' || i > '@'))
                .mapToObj(i -> (char) i)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::append,
                        StringBuilder::append)
                .toString();
    }
}
