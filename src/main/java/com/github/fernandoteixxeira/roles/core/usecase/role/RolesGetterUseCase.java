package com.github.fernandoteixxeira.roles.core.usecase.role;

import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;
import lombok.val;

@Named
@RequiredArgsConstructor
public class RolesGetterUseCase {

    private final RolesGetter rolesGetter;

    public ListOfRoles getAllRoles() {
        val roles = rolesGetter.getAll();
        return ListOfRoles.builder()
                .roles(roles)
                .build();
    }
}
