package com.management.api.repository;


import com.management.api.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;


public interface UserRepository  extends JpaRepository<Users, Long>, JpaSpecificationExecutor<Users> {

    Users findByUsername(String username);
}
