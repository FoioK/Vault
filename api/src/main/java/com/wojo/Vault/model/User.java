package com.wojo.Vault.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 128)
    private byte[] login;

    @Column(nullable = false, length = 128)
    private byte[] password;

    @Column(nullable = false)
    private LocalDateTime createTime;

    @OneToMany(mappedBy = "user")
    private Set<UserRole> roles;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
    private Person person;

    @OneToMany(mappedBy = "createdBy")
    private Set<AccountType> accountTypes;

    @OneToMany(mappedBy = "createdBy")
    private Set<DepositType> depositTypes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getLogin() {
        return login;
    }

    public void setLogin(byte[] login) {
        this.login = login;
    }

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Set<AccountType> getAccountTypes() {
        return accountTypes;
    }

    public void setAccountTypes(Set<AccountType> accountTypes) {
        this.accountTypes = accountTypes;
    }

    public Set<DepositType> getDepositTypes() {
        return depositTypes;
    }

    public void setDepositTypes(Set<DepositType> depositTypes) {
        this.depositTypes = depositTypes;
    }
}
