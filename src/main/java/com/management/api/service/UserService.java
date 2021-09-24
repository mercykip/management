package com.management.api.service;

import com.management.api.domain.Users;
import org.springframework.data.jpa.domain.Specification;
import java.util.List;

public interface UserService {
    /// User
    Users saveUser(Users user);
    List<Users> getAllUsers(Specification specification);
    Users getAUser(String username);





}
