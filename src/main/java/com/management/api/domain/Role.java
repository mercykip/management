package com.management.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.security.Permission;
import java.util.Collection;

import static javax.persistence.GenerationType.AUTO;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "role")
public class Role {

    @Id  @GeneratedValue(strategy = IDENTITY)
    Long role_id;
//    @Enumerated(EnumType.STRING)
//    @Column(length = 20)
    private String name;

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }
//
//    @ManyToMany
//    @JoinTable(
//            name = "role_permissions",
//            joinColumns = @JoinColumn(
//                    name = "role_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(
//                    name = "permission_id", referencedColumnName = "id"))
//    private Collection<Permission> permissions;
}
