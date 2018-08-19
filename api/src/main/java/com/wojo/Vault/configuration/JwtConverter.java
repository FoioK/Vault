package com.wojo.Vault.configuration;

import org.springframework.boot.autoconfigure.security.oauth2.resource.JwtAccessTokenConverterConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class JwtConverter extends DefaultAccessTokenConverter
        implements JwtAccessTokenConverterConfigurer {

    @Override
    public void configure(JwtAccessTokenConverter jwtAccessTokenConverter) {
        jwtAccessTokenConverter.setAccessTokenConverter(this);
    }

    @Override
    public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
        OAuth2Authentication auth = super.extractAuthentication(map);
        AccessTokenMapper details = new AccessTokenMapper();

        if (map.get("id") != null) {
            details.setId(Long.valueOf((Integer) map.get("id")));
        }
        if (map.get("email") != null) {
            details.setEmail((String) map.get("email"));
        }
        if (map.get("createTime") != null) {
            details.setCreateTime(LocalDateTime.parse((String) map.get("createTime")));
        }
        if (map.get("user_type") != null) {
            details.setUserType((String) map.get("user_type"));
        }
        List<String> authorities = new ArrayList<>();
        auth.getAuthorities()
                .forEach(a -> authorities.add(a.getAuthority()));
        details.setAuthorities(authorities);

        return auth;
    }
}
