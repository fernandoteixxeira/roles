package com.github.fernandoteixxeira.roles.core.usecase.roleassociation;

import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
public class RoleAssociationSaverUseCase {
    private final RoleAssociationSaver roleAssociationSaver;
    public RoleAssociation save(final RoleAssociation roleAssociation) {
        return roleAssociationSaver.save(roleAssociation);
    }
}
