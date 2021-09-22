package com.management.api.service;


import com.management.api.domain.Role;
import com.management.api.domain.RoleType;
import com.management.api.domain.Users;
import org.springframework.data.jpa.domain.Specification;
import java.util.List;

public interface UserService {
    /// User
    Users saveUser(Users user);
    List<Users> getAllUsers(Specification specification);
    Users getAUser(String username);

    /// Role
    Role saveRole(Role userRoles);
    void addRoleToUser(String username, String roleName);




}
