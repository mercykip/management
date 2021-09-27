package com.management.api.global;

import com.management.api.service.UserService;
import com.management.api.service.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class GlobalService {
    public static UserService userService;

    public static RoleService roleService;

    public static BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public  void setRoleService(RoleService roleService) {
        GlobalService.roleService = roleService;
    }

    @Autowired
    public  void setUserService(UserService userService) {
        GlobalService.userService = userService;
    }

    @Autowired
    public  void setPasswordEncoder() {
        GlobalService.passwordEncoder = new BCryptPasswordEncoder(8);
    }
}
