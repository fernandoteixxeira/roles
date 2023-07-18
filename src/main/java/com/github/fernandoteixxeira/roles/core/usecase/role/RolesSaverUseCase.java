package com.github.fernandoteixxeira.roles.core.usecase.role;

import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Named
public class RolesSaverUseCase {
    private final RoleSaver roleSaver;

    public Role save(final Role role) {
        return roleSaver.save(role);
    }
}
