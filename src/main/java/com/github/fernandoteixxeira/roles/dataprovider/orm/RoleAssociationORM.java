package com.github.fernandoteixxeira.roles.dataprovider.orm;


import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Table(name = "role_user_team_association")
@Getter
@NoArgsConstructor
@SuperBuilder
public class RoleAssociationORM {
    @EmbeddedId
    private RoleAssociationIdORM id;
    private LocalDateTime createdAt;
}
