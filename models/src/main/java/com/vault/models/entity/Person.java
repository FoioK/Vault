package com.vault.models.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Person {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private UserEntity user;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY)
    private Set<PersonAddress> addresses;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "person")
    private Set<TelephoneNumber> telephoneNumbers;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "person")
    private Set<PersonAccount> accounts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
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

    public Set<PersonAddress> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<PersonAddress> addresses) {
        this.addresses = addresses;
    }

    public Set<TelephoneNumber> getTelephoneNumbers() {
        return telephoneNumbers;
    }

    public void setTelephoneNumbers(Set<TelephoneNumber> telephoneNumbers) {
        this.telephoneNumbers = telephoneNumbers;
    }

    public Set<PersonAccount> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<PersonAccount> accounts) {
        this.accounts = accounts;
    }
}
