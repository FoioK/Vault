package com.wojo.Vault.Database.Model;

import java.time.LocalDateTime;
import java.util.List;

public class Person {

    private static String personId;
    private static String firstName;
    private static String lastName;
    private static String telephoneNumber;
    private static String login;
    private static char[] password;
    private static LocalDateTime createTime;
    private List<Address> addressList;

    public static String getPersonId() {
        return personId;
    }

    public static void setPersonId(String personId) {
        Person.personId = personId;
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

    public static String getTelephoneNumber() {
        return telephoneNumber;
    }

    public static void setTelephoneNumber(String telephoneNumber) {
        Person.telephoneNumber = telephoneNumber;
    }

    public static String getLogin() {
        return login;
    }

    public static void setLogin(String login) {
        Person.login = login;
    }

    public static char[] getPassword() {
        return password;
    }

    public static void setPassword(char[] password) {
        Person.password = password;
    }

    public static LocalDateTime getCreateTime() {
        return createTime;
    }

    public static void setCreateTime(LocalDateTime createTime) {
        Person.createTime = createTime;
    }

    public List<Address> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }
}
