package com.wojo.Vault.Database.Model;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Person {

    public static final Integer LOGIN_LENGTH = 9;

    private String personId;
    private String firstName;
    private String lastName;
    private String telephoneNumber;
    private String login;
    private char[] password;
    private LocalDateTime createTime;
    private List<Address> addressList;
    private List<Account> accountList;

    public Person(String personId) {
        this.personId = personId;
    }

    public Person(String firstName, String lastName, String telephoneNumber, String login, char[] password,
                  LocalDateTime createTime) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.telephoneNumber = telephoneNumber;
        this.login = login;
        this.password = password;
        this.createTime = createTime;
    }

    public Person(String personId, String firstName, String lastName, String telephoneNumber, String login,
                  char[] password, LocalDateTime createTime) {
        this.personId = personId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.telephoneNumber = telephoneNumber;
        this.login = login;
        this.password = password;
        this.createTime = createTime;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public List<Address> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }

    public List<Account> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<Account> accountList) {
        this.accountList = accountList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;

        return Objects.equals(personId, person.personId) &&
                Objects.equals(firstName, person.firstName) &&
                Objects.equals(lastName, person.lastName) &&
                Objects.equals(telephoneNumber, person.telephoneNumber) &&
                Objects.equals(login, person.login) &&
                Arrays.equals(password, person.password) &&
                Objects.equals(createTime.toLocalDate(), person.createTime.toLocalDate());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(personId, firstName, lastName, telephoneNumber, login, createTime.toLocalDate());
        result = 31 * result + Arrays.hashCode(password);

        return result;
    }
}
