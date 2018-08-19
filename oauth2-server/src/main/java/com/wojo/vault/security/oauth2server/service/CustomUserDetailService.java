package com.wojo.vault.security.oauth2server.service;

import com.wojo.vault.security.oauth2server.model.CustomUserDetail;
import com.wojo.vault.security.oauth2server.model.UserEntity;
import com.wojo.vault.security.oauth2server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("User login not found!"));

        if (Objects.equals(userEntity.getUserType(), "super_admin")) {
            GrantedAuthority grantedAuthority =
                    new SimpleGrantedAuthority("ROLE_super_admin");
            userEntity.setGrantedAuthorityList(Collections.singletonList(grantedAuthority));
        } else {
            Collection<GrantedAuthority> permissions = new ArrayList<>();
            userRepository.getPermissions(login)
                    .stream()
                    .map(SimpleGrantedAuthority::new)
                    .forEach(p -> permissions.add(new SimpleGrantedAuthority("ROLE_" + p)));

            userEntity.setGrantedAuthorityList(permissions);
        }

        return new CustomUserDetail(userEntity);
    }
}
