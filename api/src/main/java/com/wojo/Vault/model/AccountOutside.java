package com.wojo.Vault.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class AccountOutside {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 28)
    private String number;

    @Column(nullable = false, length = 128)
    private String recipientName;

    @OneToMany(mappedBy = "outsideTo", fetch = FetchType.LAZY)
    private Set<Payment> payments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public Set<Payment> getPayments() {
        return payments;
    }

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }
}
