package com.management.api.controller;

import com.management.api.domain.Role;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import static com.management.api.global.GlobalService.roleService;


@RestController
public class RoleController {



    @PostMapping({"/createNewRole"})
    public Role createNewRole(@RequestBody Role role){
        return roleService.saveRole(role);

    }

}
