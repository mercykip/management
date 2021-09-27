package com.management.api.controller;



import com.management.api.domain.Role;
import com.management.api.domain.Users;
import com.management.api.module.UserModel;
import com.management.api.specification.UserPredicate;

import com.management.utils.ApiCode;
import com.management.utils.JsonResponse;
import com.management.utils.JsonSetErrorResponse;
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
            Users name = userRepository.findByUsername( userModel.getUsername());

            /// check if user exists
            if (name != null) {
                return new ResponseEntity<>("Registration failed, username taken", HttpStatus.BAD_REQUEST);
            }
            /// get user details
            try{
            Users user = new Users(userModel.getFirst_name(), userModel.getLast_name(), userModel.getOther_name(), userModel.getPhone_number(), userModel.getUsername(), userModel.getPassword());

            /// Add role and save user data
            roles.add(roleRepository.findByRoleName("USER"));
            user.setRoles(roles);
            userService.saveUser(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }
        catch (Exception e){
                JsonResponse errorResponse = JsonSetErrorResponse.setResponse(ApiCode.FAILED.getCode(), ApiCode.FAILED.getDescription(), null);
                return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
        try {
            if (registeredUser == null) {
                return new ResponseEntity<>("Login failed, Email not found", HttpStatus.BAD_REQUEST);
            }

            /// validate password
            String dbPassword = registeredUser.getPassword();
            if (!passwordEncoder.matches(password, dbPassword)) {
                return new ResponseEntity<>("Login failed incorrect password", HttpStatus.BAD_REQUEST);
            }

            // TokenHelper.accessToken(request,response,registeredUser);
            return new ResponseEntity<>(registeredUser, HttpStatus.OK);
        }catch (Exception e){
            JsonResponse errorResponse = JsonSetErrorResponse.setResponse(ApiCode.FAILED.getCode(), ApiCode.FAILED.getDescription(), null);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
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

     Users savedUser= new Users();
        try {
           // List<Users> dBUser = userService.getAllUsers(new UserPredicate(null, id));
           Users dBUser=userRepository.getById(id);

            if(dBUser==null){
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }

            if(dBUser.getFirst_name() !=null
                    && dBUser.getLast_name() !=null
                    && dBUser.getOther_name() !=null
                    && dBUser.getPhone_number() !=null
                    && dBUser.getUsername() !=null
                    && dBUser.getPassword() !=null

            ){
                dBUser.setFirst_name(userModel.getFirst_name());
                dBUser.setLast_name(userModel.getLast_name());
                dBUser.setOther_name(userModel.getOther_name());
                dBUser.setPhone_number(userModel.getPhone_number());
                dBUser.setUsername(userModel.getUsername());
                dBUser.setPassword(userModel.getPassword());
            }

            Users user = userService.saveUser(dBUser);

            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        catch (Exception e){
            e.getMessage();
            return new ResponseEntity<>("Update failed", HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }



//    @GetMapping(value = {"/token/refresh"})
//    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        TokenHelper.refreshToken(request, response);
//    }


//try {
//        // List<Users> dBUser = userService.getAllUsers(new UserPredicate(null, id));
//        Users dBUser=userRepository.getById(id);
//
//        if(dBUser==null){
//            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
//        }
//
//        if(dBUser.getFirst_name() !=null){
//            dBUser.setFirst_name(userModel.getFirst_name());
//            System.out.println("first name" +dBUser.getFirst_name());
//        }
//        if(dBUser.getLast_name() !=null){
//            dBUser.setLast_name(userModel.getLast_name());
//            System.out.println("first name" +dBUser.getLast_name());
//        }
//        if(dBUser.getOther_name() !=null){
//            dBUser.setOther_name(userModel.getOther_name());
//            System.out.println("first name" +dBUser.getOther_name());
//        }
//        if(dBUser.getPhone_number() !=null){
//            dBUser.setPhone_number(userModel.getPhone_number());
//            System.out.println("first name" +dBUser.getPhone_number());
//        }
//        if(dBUser.getUsername() !=null){
//            dBUser.setUsername(userModel.getUsername());
//            System.out.println("first name" +dBUser.getUsername());
//        }
//        if(dBUser.getPassword() !=null){
//            dBUser.setPassword(userModel.getPassword());
//            System.out.println("first name" +dBUser.getPassword());
//        }
//
//        Users user = userService.saveUser(dBUser);
}
