package com.wojo.Vault.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Role {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 64)
    private String name;

    @OneToMany(mappedBy = "role")
    private Set<UserRole> users;

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

    public Set<UserRole> getUsers() {
        return users;
    }

    public void setUsers(Set<UserRole> users) {
        this.users = users;
    }
}
