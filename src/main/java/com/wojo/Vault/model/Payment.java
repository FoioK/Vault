package com.wojo.Vault.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "PAYMENT")
@SecondaryTable(name = "PAYMENT_DETAILS")
public class Payment {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Account from;

    @ManyToOne
    private Account insideTo;

    @ManyToOne
    private AccountOutside outsideTo;

    @Column(nullable = false, length = 128, table = "PAYMENT_DETAILS")
    private String recipientName;

    @Column(nullable = false, table = "PAYMENT_DETAILS")
    private String title;

    @Column(nullable = false, table = "PAYMENT_DETAILS")
    private BigDecimal amount;

    @Column(nullable = false, table = "PAYMENT_DETAILS")
    private LocalDateTime date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getFrom() {
        return from;
    }

    public void setFrom(Account from) {
        this.from = from;
    }

    public Account getInsideTo() {
        return insideTo;
    }

    public void setInsideTo(Account insideTo) {
        this.insideTo = insideTo;
    }

    public AccountOutside getOutsideTo() {
        return outsideTo;
    }

    public void setOutsideTo(AccountOutside outsideTo) {
        this.outsideTo = outsideTo;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
