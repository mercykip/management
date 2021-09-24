package com.management.api.controller;



import com.management.api.domain.Role;
import com.management.api.domain.Users;
import com.management.api.module.UserModel;
import com.management.api.specification.UserPredicate;
import com.management.security.filter.TokenHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;


import static com.management.api.global.GlobalRepository.roleRepository;
import static com.management.api.global.GlobalRepository.userRepository;
import static com.management.api.global.GlobalService.passwordEncoder;
import static com.management.api.global.GlobalService.userService;


@CrossOrigin
@RestController
@RequestMapping("/api")
@Slf4j
public class UserController {

    @PostMapping("/reg")
    public ResponseEntity<?> register(HttpServletRequest  request , HttpServletResponse response, @Validated @RequestBody UserModel userModel) throws IOException {

        Set<Role> roles=new HashSet<>();
        Map<String ,Object> myData = new HashMap<>();

        /// check if user exists
        Users name = userRepository.findByUsername( userModel.getUsername());
        if(name!= null){
            return new ResponseEntity<>("Registration failed, username taken", HttpStatus.BAD_REQUEST);
        }

        /// get user details
       Users user= new Users(userModel.getFirst_name(), userModel.getLast_name(), userModel.getOther_name(),userModel.getPhone_number(),userModel.getUsername(), userModel.getPassword());

        /// Add role and save user data
        roles.add(roleRepository.findByRoleName("USER"));
        user.setRoles(roles);
        userService.saveUser(user);

        /// generate access token from user details
        String token = TokenHelper.accessToken(request,response,user);
        myData.put("access_token",token);
        myData.put("user",user);

        return new ResponseEntity<>(myData, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity login(
            HttpServletRequest  request,
            HttpServletResponse response,
            @Validated @RequestBody Users userModel) throws ServletException, IOException {

       /// user input
        String username = userModel.getUsername();
        String password = userModel.getPassword();

        Users registeredUser= userService.getAUser(username);
        if(registeredUser == null){
            return new ResponseEntity<>("Login failed, Email not found", HttpStatus.BAD_REQUEST);
        }

        /// validate password
        String dbPassword = registeredUser.getPassword();
        if(!passwordEncoder.matches(password,dbPassword)){
            return new ResponseEntity<>("Login failed incorrect password", HttpStatus.BAD_REQUEST);
        }

     TokenHelper.accessToken(request,response,registeredUser);
         return new ResponseEntity<>(registeredUser, HttpStatus.OK);
    }


    @GetMapping(value="/getUsers")
    public ResponseEntity getAllUsers(@Param(value = "id") Long id, @Param(value = "username") String username) {
       List<Users> users= userService.getAllUsers(new UserPredicate(new Users(id)));

       if(users == null){
           return new ResponseEntity<>("No user in the Database", HttpStatus.BAD_REQUEST);
       }

         return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping(value="/updateUser")
    public  ResponseEntity<Object> updateUsers(@Param(value = "id") Long id,@Validated @RequestBody UserModel userModel){


        try {
            List<Users> dBUser = userService.getAllUsers(new UserPredicate(null, id));

            if(dBUser.isEmpty()){
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }
            
            Users savedUser = dBUser.get(0);
            savedUser.setUsername(userModel.getUsername());
            savedUser.setPassword(userModel.getPassword());
            Users user = userService.saveUser(savedUser);

            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        catch (Exception e){
            e.getMessage();
            return new ResponseEntity<>("Update failed", HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }



    @GetMapping(value = {"/token/refresh"})
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        TokenHelper.refreshToken(request, response);
    }



}
