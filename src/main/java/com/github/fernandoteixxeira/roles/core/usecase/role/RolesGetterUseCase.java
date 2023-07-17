package com.github.fernandoteixxeira.roles.core.usecase.role;

import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;
import lombok.val;

@Named
@RequiredArgsConstructor
public class RolesGetterUseCase {

    private final RoleGetter roleGetter;

    public ListOfRoles getAllRoles() {
        val roles = roleGetter.getAll();
        return ListOfRoles.builder()
                .roles(roles)
                .build();
    }
}
