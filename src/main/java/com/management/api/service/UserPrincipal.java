//package com.management.api.service;
//
//import com.management.api.domain.Role;
//import com.management.api.domain.Users;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.Setter;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//import java.util.List;
//import java.util.Objects;
//import java.util.stream.Collectors;
//@Getter
//@Setter
//@Builder
//public class UserPrincipal implements UserDetails {
//    private Long id;
//    private String password;
//    private String username;
//    private Collection<? extends GrantedAuthority> roles;
//    private Collection<? extends GrantedAuthority> permissions;
//
//    public UserPrincipal(String password, String username, Collection<? extends GrantedAuthority> roles, Collection<? extends GrantedAuthority> permissions) {
//        this.password = password;
//        this.username = username;
//        this.roles = roles;
//        this.permissions = permissions;
//    }
////
////    public static UserPrincipal createUserPrincipal(Users user) {
////
////        if (user != null) {
////            List<GrantedAuthority> roles= user.getRoles().stream().filter(Objects::nonNull)
////                    .map(role -> new SimpleGrantedAuthority(role.getName()))
////                    .collect(Collectors.toList());
////
////            List<GrantedAuthority> permissions = user.getRoles().stream().filter(Objects::nonNull)
////                    .map(Role::getPermissions).flatMap(Collection::stream)
////                    .map(permission-> new SimpleGrantedAuthority(permission.getName()))
////                    .collect(Collectors.toList());
////
////            return UserPrincipal.builder()
////                    .id(user.getId())
////                    .password(user.getPassword())
////                    .username(user.getUsername())
////                    .roles(roles)
////                    .permissions(permissions)
////                    .build();
////        }
////        return null;
////    }
//
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return null;
//    }
//
//    @Override
//    public String getPassword() {
//        return null;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return false;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return false;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return false;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return false;
//    }
//}
