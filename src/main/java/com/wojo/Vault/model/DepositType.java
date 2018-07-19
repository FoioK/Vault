package com.wojo.Vault.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
public class DepositType {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, scale = 2)
    private BigDecimal minValue;

    private Integer numberOfDays;

    @Column(nullable = false, scale = 2)
    private BigDecimal percent;

    @ManyToOne
    private User createdBy;

    @OneToMany(mappedBy = "type")
    private Set<Deposit> deposits;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getMinValue() {
        return minValue;
    }

    public void setMinValue(BigDecimal minValue) {
        this.minValue = minValue;
    }

    public Integer getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(Integer numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public BigDecimal getPercent() {
        return percent;
    }

    public void setPercent(BigDecimal percent) {
        this.percent = percent;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Set<Deposit> getDeposits() {
        return deposits;
    }

    public void setDeposits(Set<Deposit> deposits) {
        this.deposits = deposits;
    }
}
