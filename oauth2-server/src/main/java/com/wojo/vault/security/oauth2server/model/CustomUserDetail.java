package com.wojo.vault.security.oauth2server.model;

import org.springframework.security.core.userdetails.User;

import java.time.LocalDateTime;

public class CustomUserDetail extends User {

    private Long id;
    private String email;
    private LocalDateTime createTime;
    private String userType;

    public CustomUserDetail(UserEntity user) {
        super(user.getLogin(), user.getPassword(), user.getGrantedAuthorityList());
        this.id = user.getId();
        this.email = user.getEmail();
        this.createTime = user.getCreateTime();
        this.userType = user.getUserType();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
