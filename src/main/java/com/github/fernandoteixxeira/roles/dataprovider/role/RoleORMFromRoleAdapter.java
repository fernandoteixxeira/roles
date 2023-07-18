package com.github.fernandoteixxeira.roles.dataprovider.role;

import com.github.fernandoteixxeira.roles.core.usecase.role.Role;
import com.github.fernandoteixxeira.roles.dataprovider.orm.RoleORM;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class RoleORMFromRoleAdapter {
    private final Role role;

    public static RoleORMFromRoleAdapter of(final Role role) {
        Objects.requireNonNull(role);
        return new RoleORMFromRoleAdapter(role);
    }

    public RoleORM adapt() {
        return RoleORM.builder()
                .id(role.getId())
                .description(role.getDescription())
                .createdAt(role.getCreatedAt())
                .build();
    }
}
