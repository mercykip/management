package com.management.api.service;


import com.management.api.domain.Role;
import com.management.api.domain.RoleType;
import com.management.api.domain.Users;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.management.api.global.GlobalRepository.roleRepository;
import static com.management.api.global.GlobalRepository.userRepository;
import static com.management.api.global.GlobalService.passwordEncoder;



@Service
@Transactional
@Slf4j
public class UserServiceImplementation implements UserService, UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       Users user=userRepository.findByUsername(username);
       if(user==null){
           log.error("user not found in the database");
           throw  new UsernameNotFoundException("User not found in database");
       }
       else{
           log.info("User found in the database: {}",username);
       }
        Set<GrantedAuthority> grantedAuthorities = new HashSet < > ();
        for (Role role: user.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" +role.getRoleName()));
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                grantedAuthorities);
    }

    @Override
    public Users saveUser(Users user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        //user.setRoles(new HashSet< >(roleRepository.findAll()));
       // user.setRoles(new HashSet< >(roleRepository.findAll()));
        return userRepository.save(user);
    }



    @Override
    public List<Users> getAllUsers(Specification specification) {
        return userRepository.findAll(specification);
    }

    @Override
    public Users getAUser(String username) {
        return userRepository.findByUsername(username);
    }


}
