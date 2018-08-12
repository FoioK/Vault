package com.wojo.Vault.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class Account {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "account")
    private Set<PersonAccount> persons;

    @Column(nullable = false, length = 28)
    private String number;

    @Column(nullable = false, scale = 2)
    private BigDecimal value;

    @ManyToOne
    private AccountType type;

    @Column(nullable = false)
    private LocalDateTime createTime;

    @OneToMany(mappedBy = "from")
    private Set<Payment> sendPayments;

    @OneToMany(mappedBy = "insideTo")
    private Set<Payment> receivedPayments;

    @OneToMany(mappedBy = "account")
    private Set<Deposit> deposits;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<PersonAccount> getPersons() {
        return persons;
    }

    public void setPersons(Set<PersonAccount> persons) {
        this.persons = persons;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Set<Payment> getSendPayments() {
        return sendPayments;
    }

    public void setSendPayments(Set<Payment> sendPayments) {
        this.sendPayments = sendPayments;
    }

    public Set<Payment> getReceivedPayments() {
        return receivedPayments;
    }

    public void setReceivedPayments(Set<Payment> receivedPayments) {
        this.receivedPayments = receivedPayments;
    }

    public Set<Deposit> getDeposits() {
        return deposits;
    }

    public void setDeposits(Set<Deposit> deposits) {
        this.deposits = deposits;
    }
}
