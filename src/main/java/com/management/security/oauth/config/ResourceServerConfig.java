package com.management.security.oauth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;


//         This class indicates to the resource server adapter which endpoints
//        should be secured by the bearer token authentication. The class below
//        configures Resource Server to secure the web application endpoints that begin with /rest

@Configuration
@EnableResourceServer
public class ResourceServerConfig   extends ResourceServerConfigurerAdapter {

    private static final String resource_id = "my_rest_api";

    private static final String[] AUTH_WHITELIST = {
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v2/api-docs",
            "/webjars/**",
            "/api/**",
            "/tokens/**",
            "/token/**",
            "/oauth/token",
            "/authenticate",
    };

    /// he definition of resource identification in order to match the client’s access defined in the previous class.
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        ///super.configure(resources);
        resources.resourceId(resource_id ).stateless(false);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers(AUTH_WHITELIST).permitAll()
                .anyRequest().authenticated()
                .and().authorizeRequests();

               // .and().exceptionHandling().accessDeniedHandler(new oauth2accessdeniedhandler());

    }



}
