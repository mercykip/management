package com.management.api.repository;

import com.management.api.domain.Role;

import com.management.api.domain.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository  extends JpaRepository<Role,Long> {
    Role findByName(String name);

}
