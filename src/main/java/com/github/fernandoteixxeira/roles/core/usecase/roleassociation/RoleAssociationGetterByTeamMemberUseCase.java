package com.github.fernandoteixxeira.roles.core.usecase.roleassociation;

import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;
import lombok.val;

@Named
@RequiredArgsConstructor
public class RoleAssociationGetterByRoleTeamMemberUseCase {
    private final RoleAssociationGetterTeamMember roleAssociationGetterByTeamMember;
    public ListOfRoleAssociations getByTeamMember(final TeamMember teamMember) {
        val roleAssociations = roleAssociationGetterByTeamMember.getByTeamMember(teamMember);
        return ListOfRoleAssociations.builder()
                .roleAssociations(roleAssociations)
                .build();
    }
}
