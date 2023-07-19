package com.github.fernandoteixxeira.roles.core.usecase.roleassociation;

import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.util.List;

@Named
@RequiredArgsConstructor
public class RoleAssociationGetterByRoleIdUseCase {
    private final RoleAssociationGetterByRoleId roleAssociationGetterByRoleId;
    public ListOfRoleAssociations getByRoleId(final String roleId) {
        val roleAssociations = roleAssociationGetterByRoleId.getByRoleId(roleId);
        return ListOfRoleAssociations.builder()
                .roleAssociations(roleAssociations)
                .build();
    }
}
