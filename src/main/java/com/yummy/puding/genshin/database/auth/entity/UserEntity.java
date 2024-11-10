package com.yummy.puding.genshin.database.auth.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.Set;

@Entity
@Table(name = "USERS")
public class UserEntity {
    @Id
    @UuidGenerator
    @JsonIgnore
    private String id;

    @Column(name = "username", nullable = false)
    private String username;

    @JsonIgnore
    @Column(name = "password", nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
        name = "USER_ROLES",
        joinColumns = {@JoinColumn(name = "USER_UUID")},
        inverseJoinColumns = {@JoinColumn(name = "ROLE_ID")}
    )
    private Set<RoleEntity> roles;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Set<RoleEntity> getRoles() {
        return roles;
    }
}
