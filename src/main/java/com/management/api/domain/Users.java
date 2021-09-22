package com.management.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.AUTO;

@Entity
@Getter
@Setter
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name="users")

public class Users {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    @JsonProperty(value="first_name")
    private String first_name;
    @JsonProperty(value="last_name")
    private String last_name;
    @JsonProperty(value="username")
    @Column(unique = true)
    private String username;
    @JsonProperty(value="password")
    private String password;
   /// load all roles whenever the user is fetched
    //@ManyToMany(fetch =EAGER)
//    @JoinTable(name="user_roles",
//            joinColumns = @JoinColumn(name="id"),
//            inverseJoinColumns = @JoinColumn(name="role_id"))
//    private Set<Role> roles = new HashSet<>();

//    @ManyToMany(fetch = FetchType.EAGER) //anytime load user, load a role
//    private Collection<Role> roles = new ArrayList<>();
//@JoinTable(
//        name = "users_roles",
//        joinColumns = @JoinColumn(
//                name = "id", referencedColumnName = "id"),
//        inverseJoinColumns = @JoinColumn(
//                name = "role_id", referencedColumnName = "id"))
//private Collection<Role> roles;

    @ManyToMany(fetch = EAGER)
//    @JoinTable(	name = "user_roles",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles = new HashSet<>();

    public Users() {
    }

    public Users(Long id) {
        this.id = id;
    }

    public Users(String first_name, String last_name, String username, String password) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.username = username;
        this.password = password;

    }


}


