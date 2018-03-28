package com.wojo.Vault.Database.Model.Generators;

import com.wojo.Vault.Database.DAO.AccountDAO;
import com.wojo.Vault.Database.DAO.Impl.AccountDAOImpl;

import java.util.Random;

public class AccountDataGenerator {

    private AccountDAO accountDAO = new AccountDAOImpl();

    public String generateIBAN(int length) {
        return length > 0 ? generateNumberProcess(length) : "";
    }

    private String generateNumberProcess(int length) {
        String number = generateRandomNumber(new Random(), length);
        
        return accountDAO.isNumberExist(number) ?
                generateNumberProcess(length) : generateRandomNumber(new Random(), length);
    }

    private String generateRandomNumber(Random random, int length) {
        return random.ints('0', '9')
                .mapToObj(i -> (char) i).limit(length)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }
}
