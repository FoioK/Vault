package com.vault.models.entity;

import javax.persistence.*;

@Entity
public class TelephoneNumber {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Person person;

    @Column(nullable = false, length = 12)
    private String number;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
