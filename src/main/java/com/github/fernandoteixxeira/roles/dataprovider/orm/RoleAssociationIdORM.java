package com.github.fernandoteixxeira.roles.dataprovider.orm;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor
@SuperBuilder
public class RoleAssociationIdORM implements Serializable {
    private String teamId;
    private String userId;
    private String roleId;
}
