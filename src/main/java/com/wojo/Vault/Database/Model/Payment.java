package com.wojo.Vault.Database.Model;

import java.math.BigDecimal;
import java.util.Date;

public class Payment {

    private Integer idPayment;
    private Integer idAccount;
    private String recipientName;
    private String senderName;
    private String title;
    private BigDecimal paymentValue;
    private Date date;

    public Payment(Integer idPayment, Integer idAccount, String recipientName,
                   String senderName, String title, BigDecimal paymentValue, Date date) {
        this.idPayment = idPayment;
        this.idAccount = idAccount;
        this.recipientName = recipientName;
        this.senderName = senderName;
        this.title = title;
        this.paymentValue = paymentValue;
        this.date = date;
    }

    @SuppressWarnings("unused")
    public Integer getIdPayment() {
        return idPayment;
    }

    public Integer getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(Integer idAccount) {
        this.idAccount = idAccount;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPaymentValue() {
        return paymentValue;
    }

    public Date getDate() {
        return date;
    }

}
