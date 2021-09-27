package com.management.security.oauth.config;

import com.management.security.oauth.custom_config.CustomAccessTokenEnhancer;
import com.management.security.oauth.custom_config.CustomTokenEnhancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import javax.sql.DataSource;
import java.util.Arrays;
import static com.management.api.global.GlobalService.passwordEncoder;

@Configuration
@EnableAuthorizationServer
public class AuthServerConfig   extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserDetailsService userDetailsService;

    ///Helper that translates between JWT encoded token values and OAuth authentication
    // information (in both directions). Also acts as a TokenEnhancer when tokens are
    // granted.
    /// implements TokenEnhancer, AccessTokenConverter, InitializingBean
    @Autowired
    @Qualifier("jwtAccessTokenConverter") ///we can eliminate the issue of which bean needs to be injected.
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired
    @Qualifier("customAccessTokenEnhancer") ///we can eliminate the issue of which bean needs to be injected.
    private CustomAccessTokenEnhancer customAccessTokenEnhancer;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endPoint) throws Exception {
        final TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        endPoint.pathMapping("/oauth/token", "/authenticate");
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), jwtAccessTokenConverter, customAccessTokenEnhancer));
        endPoint.tokenStore(tokenStore()).tokenEnhancer(tokenEnhancerChain).authenticationManager(authenticationManager);

    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("hasAuthority('USER')")
                .passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(
            ClientDetailsServiceConfigurer clients)
            throws Exception {
        clients.jdbc(dataSource);
    }

    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }


    @Bean
    public TokenEnhancer tokenEnhancer(){
        return new CustomTokenEnhancer();
    }

    ///DefaultTokenServices is the Base implementation for token services using random UUID values for the access token and refresh token values.
    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setTokenStore(tokenStore());
        return tokenServices;
    }

}
