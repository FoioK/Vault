package com.wojo.vault.security.oauth2server.configuration;

import com.wojo.vault.security.oauth2server.model.CustomUserDetail;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.LinkedHashMap;
import java.util.Map;

public class CustomTokenEnhancer extends JwtAccessTokenConverter {

    @Override
    public OAuth2AccessToken enhance(
            OAuth2AccessToken accessToken,
            OAuth2Authentication authentication) {

        CustomUserDetail user = (CustomUserDetail) authentication.getPrincipal();
        Map<String, Object> info = new LinkedHashMap<>(accessToken.getAdditionalInformation());

        if (user.getId() != null) {
            info.put("id", user.getId());
        }
        if (user.getEmail() != null) {
            info.put("email", user.getEmail());
        }
        if (user.getCreateTime() != null) {
            info.put("createTime", user.getCreateTime());
        }
        if (user.getUserType() != null) {
            info.put("user_type", user.getUserType());
        }
        DefaultOAuth2AccessToken customAccesToken = new DefaultOAuth2AccessToken(accessToken);
        customAccesToken.setAdditionalInformation(info);

        return super.enhance(customAccesToken, authentication);
    }
}
