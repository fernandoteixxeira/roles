package com.github.fernandoteixxeira.roles.entrypoint.restapi.role;

import com.github.fernandoteixxeira.roles.core.usecase.role.Role;

import java.util.Objects;

public class RoleResponseAdapter {
    private final Role role;

    public static RoleResponseAdapter of(final Role role) {
        Objects.requireNonNull(role);
        return new RoleResponseAdapter(role);
    }

    private RoleResponseAdapter(final Role role) {
        this.role = role;
    }

    public RoleResponse adapt() {
        return RoleResponse.builder()
                .id(role.getId())
                .description(role.getDescription())
                .createdAt(role.getCreatedAt())
                .build();
    }
}
