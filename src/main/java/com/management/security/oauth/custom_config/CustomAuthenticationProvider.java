package com.management.security.oauth.custom_config;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

import static com.management.api.global.GlobalService.passwordEncoder;
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        UserDetails user = null;
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();

        if(name == null || name.isEmpty()){
            throw  new RuntimeException("Invalid username");
        }

        if(password == null || password.isEmpty()){
            throw  new RuntimeException("Invalid user credential");
        }

        if(passwordEncoder.matches(password,user.getPassword())){
           ///SecurityContextHolder, Associates a given SecurityContext with the current execution thread.
            Authentication authCredentials= SecurityContextHolder.getContext().getAuthentication();
            Authentication auth = new UsernamePasswordAuthenticationToken(user, password,user.getAuthorities() );
            return auth;
        }else {
            throw  new UnapprovedClientAuthenticationException("Invalid credentials");
        }


//        return new UsernamePasswordAuthenticationToken(
//                name, password, new ArrayList<>());

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
