package com.github.fernandoteixxeira.roles.dataprovider.role;

import com.github.fernandoteixxeira.roles.core.usecase.role.Role;
import com.github.fernandoteixxeira.roles.dataprovider.orm.RoleORM;

import java.util.Objects;

public class RoleAdapter {

    private final RoleORM roleORM;

    private RoleAdapter(final RoleORM roleORM) {
        this.roleORM = roleORM;
    }

    public static RoleAdapter of(final RoleORM roleORM) {
        Objects.requireNonNull(roleORM);
        return new RoleAdapter(roleORM);
    }

    public Role adapt() {
        return Role.builder()
                .id(roleORM.getId())
                .description(roleORM.getDescription())
                .createdAt(roleORM.getCreatedAt())
                .build();
    }
}
