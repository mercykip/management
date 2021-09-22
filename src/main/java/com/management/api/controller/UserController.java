package com.management.api.controller;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.management.api.domain.Role;
import com.management.api.domain.RoleType;
import com.management.api.domain.Users;
import com.management.api.module.UserModel;
import com.management.api.module.UserProfile;
import com.management.api.specification.UserPredicate;
import com.management.security.filter.TokenHelper;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;


import static com.management.api.global.GlobalRepository.roleRepository;
import static com.management.api.global.GlobalService.passwordEncoder;
import static com.management.api.global.GlobalService.userService;
import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin
@RestController
@RequestMapping("/api")
@Slf4j
public class UserController {



    @PostMapping("/registration")
    public ResponseEntity<?> registerUser(HttpServletRequest  request,@Validated @RequestBody UserModel userModel){
       // Users user = new Users();
     //   Role role=roleRepository.findByName("USER");
       // log.info("role",role);
       // user.addRole(role);
       // userService.saveRole(new Role(null, "ROLE_USER"));
     Users users =  userService.saveUser(
                new Users(
                        userModel.getFirst_name(),
                        userModel.getLast_name(),
                        userModel.getUsername(),
                        userModel.getPassword()


                ));

       //userService.addRoleToUser(userModel.getUsername(), "ROLE_USER");


//        if(user ==null){
//             return new ResponseEntity<>("Details not saved", HttpStatus.BAD_REQUEST);
//         }

//        Map responce = TokenHelper.getToken(request,user);
//         if(responce ==null){
//             return new ResponseEntity<>(responce, HttpStatus.BAD_REQUEST);
//         }
//        responce.put("user",user);
//        log.info(responce.toString());
      //  return new ResponseEntity<>(user, HttpStatus.CREATED);
        return new ResponseEntity<>("user", HttpStatus.CREATED);
    }
    @PostMapping("/reg")
    public ResponseEntity<?> register(
            HttpServletRequest  request ,
            HttpServletResponse response,
            @Validated @RequestBody UserModel userModel,
            Users users) throws IOException {

       Users user= new Users(
                userModel.getFirst_name(),
                userModel.getLast_name(),
                userModel.getUsername(),
                userModel.getPassword()

        );
        Role role=roleRepository.findByName("ROLE_SUPER_ADMIN");
        user.getRoles().add(role);
        userService.saveUser(user);

        TokenHelper.accessToken(request,response,user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
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

        List<Users> usersList =userService.getAllUsers(new UserPredicate(null,id));

        Users users = !usersList.isEmpty() ? usersList.get(0) : null;

        if(users == null){
            return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);
        } else {
            Users savedUser=new Users(userModel.getFirst_name(),userModel.getLast_name(),userModel.getUsername(),userModel.getPassword());
            savedUser.setId(id);
            Users user=userService.saveUser(savedUser);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
    }

    @PostMapping("/role/add")
     public ResponseEntity<Role> saveRole(@RequestBody Role role){

        Role userRole=userService.saveRole(role);
        return new ResponseEntity<>(userRole, HttpStatus.CREATED);
     }

    @PostMapping("/role/addUserRole")
    public ResponseEntity<Role> addRoleUser(@RequestBody RoleToUserForm roleToUserForm){
        userService.addRoleToUser(roleToUserForm.getUsername(),roleToUserForm.getRoleName());

        return ResponseEntity.ok().build();
    }

    @GetMapping(value = {"/token/refresh"})
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        TokenHelper.refreshToken(request, response);
    }

    private static void readFromInputStream(InputStream inputStream) throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
                System.out.println(resultStringBuilder);
            }
        }

        //String result = resultStringBuilder.toString();

        //System.out.println("body "+result);
    }
}
@Getter
@Setter
class RoleToUserForm{
    private String username;
    private String roleName;
}