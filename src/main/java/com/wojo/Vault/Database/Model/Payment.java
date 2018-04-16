package com.wojo.Vault.Database.Model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Payment {

    private String paymentId;
    private String senderAccountId;
    private String recipientAccountId;
    private String recipientName;
    private String recipientNumber;
    private BigDecimal amount;
    private String title;
    private LocalDateTime data;

    public Payment(String paymentId, String senderAccountId, String recipientAccountId, String recipientName,
                   String recipientNumber, BigDecimal amount, String title, LocalDateTime data) {
        this.paymentId = paymentId;
        this.senderAccountId = senderAccountId;
        this.recipientAccountId = recipientAccountId;
        this.recipientName = recipientName;
        this.recipientNumber = recipientNumber;
        this.amount = amount;
        this.title = title;
        this.data = data;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getSenderAccountId() {
        return senderAccountId;
    }

    public void setSenderAccountId(String senderAccountId) {
        this.senderAccountId = senderAccountId;
    }

    public String getRecipientAccountId() {
        return recipientAccountId;
    }

    public void setRecipientAccountId(String recipientAccountId) {
        this.recipientAccountId = recipientAccountId;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getRecipientNumber() {
        return recipientNumber;
    }

    public void setRecipientNumber(String recipientNumber) {
        this.recipientNumber = recipientNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Payment)) return false;
        Payment payment = (Payment) o;
        return Objects.equals(paymentId, payment.paymentId) &&
                Objects.equals(senderAccountId, payment.senderAccountId) &&
                Objects.equals(recipientAccountId, payment.recipientAccountId) &&
                Objects.equals(recipientName, payment.recipientName) &&
                Objects.equals(recipientNumber, payment.recipientNumber) &&
                Objects.equals(amount.abs(), payment.amount.abs()) &&
                Objects.equals(title, payment.title) &&
                Objects.equals(data.toLocalDate(), payment.data.toLocalDate());
    }

    @Override
    public int hashCode() {

        return Objects.hash(paymentId,
                senderAccountId,
                recipientAccountId,
                recipientName,
                recipientNumber,
                amount,
                title,
                data);
    }
}
