package com.wojo.Vault.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class AccountType {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 128)
    private String name;

    @ManyToOne
    private UserEntity createdBy;

    @OneToMany(mappedBy = "type", fetch = FetchType.LAZY)
    private Set<Account> accounts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserEntity getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserEntity createdBy) {
        this.createdBy = createdBy;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }
}
