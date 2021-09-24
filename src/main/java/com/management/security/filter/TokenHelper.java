package com.management.security.filter;

import static com.management.api.global.GlobalService.userService;
import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.management.api.domain.Role;
import com.management.api.domain.Users;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
@Slf4j
public class TokenHelper {
    public static void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            try{
                String refresh_token =authorizationHeader.substring("Bearer ".length());
                Algorithm alg=Algorithm.HMAC256("secret".getBytes());
                JWTVerifier jWTVerifier= JWT.require(alg).build();
                DecodedJWT decodedJWT= jWTVerifier.verify(refresh_token);
                String username= decodedJWT.getSubject();
                Users user= userService.getAUser(username);
                String access_token = JWT.create().
                        withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis()+10*60*1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles().stream().map(Role::getRoleName)
                                .collect(Collectors.toList())).sign(alg);


                Map<String,String> tokens =new HashMap<>();
                tokens.put("access_token",access_token);
                tokens.put("refresh_token",refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(),tokens);


            } catch (Exception e) {
                e.printStackTrace();
                log.error("Error logging in : " + e.getMessage());
                response.setHeader("error", e.getMessage());
                response.setStatus(FORBIDDEN.value());


            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }
    public static String accessToken(HttpServletRequest request, HttpServletResponse response,Users user) throws IOException {


            try{
                Algorithm alg=Algorithm.HMAC256("secret".getBytes());

                String access_token = JWT.create().
                        withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis()+10*60*1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles().stream().map(Role::getRoleName)
                                .collect(Collectors.toList())).sign(alg);


                Map<String,String> tokens =new HashMap<>();
                tokens.put("access_token",access_token);
                //tokens.put("refresh_token",refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
        return access_token;
            } catch (Exception e) {
                e.printStackTrace();
                log.error("Error logging in : " + e.getMessage());
                response.setHeader("error", e.getMessage());
                response.setStatus(FORBIDDEN.value());

        return null;
            }
    }

    public static Map<String,String> getToken(HttpServletRequest request, Users user) {
        Map<String,String> tokens = new HashMap<>();

        Algorithm alg=Algorithm.HMAC256("secret".getBytes());

        String access_token = JWT.create().
                withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+10*60*1000))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", user.getRoles().stream().map(Role::getRoleName)
                        .collect(Collectors.toList())).sign(alg);

        String refresh_token = JWT.create().
                withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+10*60*2000))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", user.getRoles().stream().map(Role::getRoleName)
                        .collect(Collectors.toList())).sign(alg);

        tokens.put("access_token",access_token);
        tokens.put("refresh_token",refresh_token);

        return tokens;
    }



}
