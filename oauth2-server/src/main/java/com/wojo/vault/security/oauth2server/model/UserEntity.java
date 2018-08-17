package com.wojo.vault.security.oauth2server.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "User")
public class UserEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 10)
    private String login;

    @Column(nullable = false, length = 64)
    private String password;

    @Column(nullable = false, length = 64)
    private String email;

    @Column(nullable = false)
    private LocalDateTime createTime;

    @Column(length = 64)
    private String userType;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<UserRole> roles;

    @Transient
    private Collection<GrantedAuthority> grantedAuthorityList = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }

    public Collection<GrantedAuthority> getGrantedAuthorityList() {
        return grantedAuthorityList;
    }

    public void setGrantedAuthorityList(Collection<GrantedAuthority> grantedAuthorityList) {
        this.grantedAuthorityList = grantedAuthorityList;
    }
}
