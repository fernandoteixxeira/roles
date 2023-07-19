package com.github.fernandoteixxeira.roles.entrypoint.restapi.role;

import com.github.fernandoteixxeira.roles.core.usecase.role.Role;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PRIVATE)

public class RoleResponseFromRoleAdapter {
    private final Role role;

    public static RoleResponseFromRoleAdapter of(final Role role) {
        Objects.requireNonNull(role);
        return new RoleResponseFromRoleAdapter(role);
    }

    public RoleResponse adapt() {
        return RoleResponse.builder()
                .id(role.getId())
                .description(role.getDescription())
                .createdAt(role.getCreatedAt())
                .build();
    }
}
