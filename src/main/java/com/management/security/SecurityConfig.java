package com.management.security;
import com.management.security.filter.CustomAuthenticationFilter;
import com.management.security.filter.CustomeAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.management.api.global.GlobalService.passwordEncoder;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

/// manage users

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter{
    @Autowired
    private  final UserDetailsService userDetailsService;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);

        return authProvider;
    }




    public String[] WHITELIST = new String[]{
            "/api/login",
            "/api/reg",
        //   "/api/getUsers"
    };

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        ///tell spring to check for users
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
     //disable crossite request
        CustomAuthenticationFilter customAuthenticationFilter =new CustomAuthenticationFilter(authenticationManager());
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        http.addFilterBefore(new CustomeAuthorizationFilter(),UsernamePasswordAuthenticationFilter.class);
        http.addFilter(customAuthenticationFilter);
        http.authorizeRequests().antMatchers(WHITELIST).permitAll();
//        http.authorizeRequests()
//                .antMatchers("/").hasAnyAuthority("USER", "CREATOR", "EDITOR", "ADMIN")
//                .antMatchers("/new").hasAnyAuthority("ADMIN", "CREATOR")
//                .antMatchers("/edit/**").hasAnyAuthority("ADMIN", "EDITOR")
//                .antMatchers("/delete/**").hasAuthority("ADMIN");
    ///    http.authorizeRequests().antMatchers("/api/getUsers").hasRole("USER");
        http.authorizeRequests();
//        http.authorizeRequests().anyRequest().authenticated();

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
