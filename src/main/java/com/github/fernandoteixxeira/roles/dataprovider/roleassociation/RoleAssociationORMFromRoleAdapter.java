package com.github.fernandoteixxeira.roles.dataprovider.roleassociation;

import com.github.fernandoteixxeira.roles.core.usecase.roleassociation.RoleAssociation;
import com.github.fernandoteixxeira.roles.dataprovider.orm.RoleAssociationIdORM;
import com.github.fernandoteixxeira.roles.dataprovider.orm.RoleAssociationORM;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.util.Objects;

import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PRIVATE)
public class RoleAssociationORMFromRoleAdapter {
    private final RoleAssociation roleAssociation;

    public static RoleAssociationORMFromRoleAdapter of(final RoleAssociation roleAssociation) {
        Objects.requireNonNull(roleAssociation);
        return new RoleAssociationORMFromRoleAdapter(roleAssociation);
    }

    public RoleAssociationORM adapt() {
        val id = RoleAssociationIdORM.builder()
                .teamId(roleAssociation.getTeamId())
                .userId(roleAssociation.getUserId())
                .roleId(roleAssociation.getRoleId())
                .build();
        return RoleAssociationORM.builder()
                .id(id)
                .createdAt(roleAssociation.getCreatedAt())
                .build();
    }
}
