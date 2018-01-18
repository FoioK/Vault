package com.wojo.Vault.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Person {

    private static int idPersonInDatabase;
    private static String firstName;
    private static String lastName;
    private static String personId;
    private static String address;
    private static String telephoneNumber;
    private static String email;
    private static String login;
    private static String password;

    private static List<Account> accounts = new ArrayList<>();

    public static String generateLogin(int length) {
        return length > 0 ? generateRandomString(new Random(), length) : "";
    }

    private static String generateRandomString(Random random, int length) {
        return random.ints('0', 'Z')
                .filter(i -> (i < ':' || i > '@'))
                .mapToObj(i -> (char) i)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::append,
                        StringBuilder::append)
                .toString();
    }

    public static int getIdPersonInDatabase() {
        return idPersonInDatabase;
    }

    public static void setIdPersonInDatabase(int idPersonInDatabase) {
        Person.idPersonInDatabase = idPersonInDatabase;
    }

    public static String getFirstName() {
        return firstName;
    }

    public static void setFirstName(String firstName) {
        Person.firstName = firstName;
    }

    public static String getLastName() {
        return lastName;
    }

    public static void setLastName(String lastName) {
        Person.lastName = lastName;
    }

    public static String getPersonId() {
        return personId;
    }

    public static void setPersonId(String personId) {
        Person.personId = personId;
    }

    public static String getAddress() {
        return address;
    }

    public static void setAddress(String address) {
        Person.address = address;
    }

    public static String getTelephoneNumber() {
        return telephoneNumber;
    }

    public static void setTelephoneNumber(String telephoneNumber) {
        Person.telephoneNumber = telephoneNumber;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        Person.email = email;
    }

    public static String getLogin() {
        return login;
    }

    public static void setLogin(String login) {
        Person.login = login;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        Person.password = password;
    }

    public static void addAccount(Account account) {
        accounts.add(account);
    }

    public static List<Account> getAccounts() {
        return accounts;
    }

    public static void setAccounts(List<Account> accounts) {
        Person.accounts = accounts;
    }
}
