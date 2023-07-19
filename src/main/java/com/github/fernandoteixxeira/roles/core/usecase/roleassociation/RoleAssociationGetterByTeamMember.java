package com.github.fernandoteixxeira.roles.core.usecase.roleassociation;

import java.util.List;

public interface RoleAssociationGetterByTeamMember {
    List<RoleAssociation> getByTeamMember(TeamMember teamMember);
}
