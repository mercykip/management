package com.management.api.global;

import com.management.api.repository.RoleRepository;
import com.management.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GlobalRepository {
    public static UserRepository userRepository;
    public static RoleRepository roleRepository;

   @Autowired
    public  void setUserRepository(UserRepository userRepository) {
        GlobalRepository.userRepository = userRepository;
    }

    @Autowired
    public  void setRoleRepository(RoleRepository roleRepository) {
        GlobalRepository.roleRepository = roleRepository;
    }

}
