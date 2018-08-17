package com.wojo.vault.security.oauth2server.repository;

import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PermissionRepository {

    String query = "SELECT p.name FROM user u " +
            "JOIN user_role ur ON u.id = ur.user_id " +
            "JOIN role r ON ur.role_id = r.id " +
            "JOIN role_permission rp ON r.id = rp.role_id " +
            "JOIN permission p ON rp.permission_id = p.id " +
            "WHERE u.login like ?1";

    @Query(value = query, nativeQuery = true)
    List<String> getPermissions(String login);
}
