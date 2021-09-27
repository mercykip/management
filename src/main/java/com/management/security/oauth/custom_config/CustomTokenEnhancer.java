package com.management.security.oauth.custom_config;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

public class CustomTokenEnhancer  implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        final Map<String, Object> additionalInfo = new HashMap<>();
        if(oAuth2Authentication.getOAuth2Request().getGrantType().equals("password")) {
            additionalInfo.put("user", oAuth2Authentication.getPrincipal());
        }

        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(additionalInfo);


        oAuth2Authentication.getDetails();
        return oAuth2AccessToken;
    }

}
