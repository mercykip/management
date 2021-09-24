package com.management.api.service.role;

import com.management.api.domain.Role;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.management.api.global.GlobalRepository.roleRepository;

@Service
@Transactional
public class RoleServiceImplementation implements RoleService {
    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

}
