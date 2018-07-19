package com.wojo.Vault.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Address {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 50)
    private String city;

    @Column(nullable = false, length = 50)
    private String street;

    @Column(length = 10)
    private String apartmentNumber;

    @OneToMany(mappedBy = "address")
    private Set<PersonAddress> persons;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public Set<PersonAddress> getPersons() {
        return persons;
    }

    public void setPersons(Set<PersonAddress> persons) {
        this.persons = persons;
    }
}
