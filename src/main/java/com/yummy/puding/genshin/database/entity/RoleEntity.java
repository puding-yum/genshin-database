package com.yummy.puding.genshin.database.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "ROLES")
public class RoleEntity {
    @Id
    @UuidGenerator
    @JsonIgnore
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    public String getName() {
        return name;
    }
}
