package com.github.fernandoteixxeira.roles.dataprovider.orm;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@SuperBuilder
@NoArgsConstructor
@Getter
@Entity
@Table(name = "role")
public class RoleORM {
    @Id
    private String id;
    private String description;
    private LocalDateTime createdAt;
}
