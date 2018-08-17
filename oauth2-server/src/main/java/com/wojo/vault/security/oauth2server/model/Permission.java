package com.wojo.vault.security.oauth2server.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Permission {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 64)
    private String name;

    @OneToMany(mappedBy = "permission")
    private Set<RolePermission> roles;

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

    public Set<RolePermission> getRoles() {
        return roles;
    }

    public void setRoles(Set<RolePermission> roles) {
        this.roles = roles;
    }
}
