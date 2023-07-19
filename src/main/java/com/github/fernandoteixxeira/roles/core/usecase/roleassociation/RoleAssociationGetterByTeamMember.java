package com.github.fernandoteixxeira.roles.core.usecase.roleassociation;

import java.util.List;

public interface RoleAssociationGetterTeamMember {
    List<RoleAssociation> getByTeamMember(TeamMember teamMember);
}
