package com.wojo.vault.security.oauth2server.repository;


import com.wojo.vault.security.oauth2server.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository
        extends PermissionRepository, JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByLogin(String login);
}
