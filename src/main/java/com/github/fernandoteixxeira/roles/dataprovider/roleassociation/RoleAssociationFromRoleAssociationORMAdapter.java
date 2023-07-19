package com.github.fernandoteixxeira.roles.dataprovider.roleassociation;

import com.github.fernandoteixxeira.roles.core.usecase.roleassociation.RoleAssociation;
import com.github.fernandoteixxeira.roles.dataprovider.orm.RoleAssociationORM;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.util.Objects;

import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PRIVATE)
public class RoleAssociationFromRoleAssociationORMAdapter {
    private final RoleAssociationORM roleAssociationORM;

    public static RoleAssociationFromRoleAssociationORMAdapter of(final RoleAssociationORM roleAssociationORM) {
        Objects.requireNonNull(roleAssociationORM);
        return new RoleAssociationFromRoleAssociationORMAdapter(roleAssociationORM);
    }

    public RoleAssociation adapt() {
        return RoleAssociation.builder()
                .teamId(roleAssociationORM.getId().getTeamId())
                .userId(roleAssociationORM.getId().getUserId())
                .roleId(roleAssociationORM.getId().getRoleId())
                .createdAt(roleAssociationORM.getCreatedAt())
                .build();
    }
}
