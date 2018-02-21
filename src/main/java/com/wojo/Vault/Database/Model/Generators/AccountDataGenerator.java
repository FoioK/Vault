package com.wojo.Vault.Database.Model.Generators;

import com.wojo.Vault.Database.DAO.AccountDAO;
import com.wojo.Vault.Database.DAO.Impl.AccountDAOImpl;

import java.util.Random;

public class AccountDataGenerator {

    private AccountDAO accountDAO = new AccountDAOImpl();

    public String generateIBAN(String countryCode, int length) {
        return countryCode.length() == 2 ?
                length > 0 ?
                        isCountryCodeCorrect(countryCode) ?
                                countryCode + generateNumberProcess(length):
                                "" :
                        "" :
                "";
    }

    private boolean isCountryCodeCorrect(String countryCode) {
        return countryCode.chars()
                .mapToObj(item -> (char) item)
                .filter(i -> (i < '[' && i > '@'))
                .count() == countryCode.length();
    }

    private String generateNumberProcess(int length) {
        String number = generateRandomNumber(new Random(), length);
        return accountDAO.searchAccountByNumber(number) == 0 ? number : generateNumberProcess(length);
    }

    private String generateRandomNumber(Random random, int length) {
        return random.ints('0', '9')
                .mapToObj(i -> (char) i).limit(length)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }
}
