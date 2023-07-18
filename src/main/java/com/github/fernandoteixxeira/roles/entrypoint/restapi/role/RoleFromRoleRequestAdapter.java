package com.github.fernandoteixxeira.roles.entrypoint.restapi.role;

import com.github.fernandoteixxeira.roles.core.usecase.role.Role;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

import static java.time.LocalDateTime.now;
import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PRIVATE)
public class RoleFromRoleRequestAdapter {
    private final RoleRequest roleRequest;

    public static RoleFromRoleRequestAdapter of(final RoleRequest roleRequest) {
        Objects.requireNonNull(roleRequest);
        return new RoleFromRoleRequestAdapter(roleRequest);
    }

    public Role adapt() {
        return Role.builder()
                .id(roleRequest.getId())
                .description(roleRequest.getDescription())
                .createdAt(now())
                .build();
    }
}
