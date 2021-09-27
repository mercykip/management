package com.management.security.oauth.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

public class PermitErrorConfig extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .authorizeRequests()
                .antMatchers("/error").permitAll()
                .anyRequest().authenticated();
        // @formatter:on
    }
}
