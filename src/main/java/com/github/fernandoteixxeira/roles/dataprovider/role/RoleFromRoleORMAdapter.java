package com.github.fernandoteixxeira.roles.dataprovider.role;

import com.github.fernandoteixxeira.roles.core.usecase.role.Role;
import com.github.fernandoteixxeira.roles.dataprovider.orm.RoleORM;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PRIVATE)
public class RoleFromRoleORMAdapter {
    private final RoleORM roleORM;

    public static RoleFromRoleORMAdapter of(final RoleORM roleORM) {
        Objects.requireNonNull(roleORM);
        return new RoleFromRoleORMAdapter(roleORM);
    }

    public Role adapt() {
        return Role.builder()
                .id(roleORM.getId())
                .description(roleORM.getDescription())
                .createdAt(roleORM.getCreatedAt())
                .build();
    }
}
