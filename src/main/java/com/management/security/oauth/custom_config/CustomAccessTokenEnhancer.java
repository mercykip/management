package com.management.security.oauth.custom_config;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;


// token enhancerStrategy for enhancing an access token before it is stored by an
// AuthorizationServerTokenServices implementation.

// Provides an opportunity for customization of an access token (e.g. through its
// additional information map) during the process of creating a new token for use
//  by a client.
@Component
public class CustomAccessTokenEnhancer  implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        /// Create an access token from the value provided.
        DefaultOAuth2AccessToken result = new DefaultOAuth2AccessToken(oAuth2AccessToken);
        Map<String, Object> info = new LinkedHashMap(oAuth2AccessToken.getAdditionalInformation());
        if (info.containsKey("")) {
            info.remove("");
        }
        if (info.containsKey("")) {
            info.remove("");
        }
        result.setScope(null);
        result.setAdditionalInformation(info);
        return result;
    }

}
