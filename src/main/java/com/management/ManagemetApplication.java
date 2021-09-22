package com.management;

import com.management.api.domain.Role;
import com.management.api.domain.Users;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.HashSet;

import static com.management.api.global.GlobalService.userService;

@EnableJpaRepositories
@SpringBootApplication
public class ManagemetApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManagemetApplication.class, args);
    }

    @Bean
    PasswordEncoder  PasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    CommandLineRunner run () {
//        return args -> {
//            //roles
//            userService.saveRole(new Role(null, "ROLE_USER"));
//            userService.saveRole(new Role(null, "ROLE_ADMIN"));
//            userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));
//        };
//    }
//    @Bean
//     CommandLineRunner run () {
//		return args -> {
//			//roles
//            userService.saveRole(new Role(null, "ROLE_USER"));
//            userService.saveRole(new Role(null,"ROLE_ADMIN"));
//            userService.saveRole(new Role(null,"ROLE_SUPER_ADMIN"));
//			//Users
//            userService.saveUser(new Users("Superman", "Kal-El", "Clark@kanzas.com", "Lois", new ArrayList<>()));
//            userService.saveUser(new Users("Batman", "Wayne", "richboi@gotham.com", "Robin", new ArrayList<>()));
//            userService.saveUser(new Users("Luther", "Lex", "lex@dictator.com", "Me", new ArrayList<>()));
//			//RolesToUser
//            userService.addRoleToUser("Clark@kanzas.com", "ROLE_SUPER_ADMIN");
//            userService.addRoleToUser("richboi@gotham.com", "ROLE_ADMIN");
//            userService.addRoleToUser("lex@dictator.com", "ROLE_USER");
//            userService.addRoleToUser("lex@dictator.com", "ROLE_ADMIN");
////
//		};
//	}
}
