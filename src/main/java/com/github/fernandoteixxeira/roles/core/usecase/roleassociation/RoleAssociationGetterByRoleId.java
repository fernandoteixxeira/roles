package com.github.fernandoteixxeira.roles.core.usecase.roleassociation;

import java.util.List;

public interface RoleAssociationGetterByRoleId {
    List<RoleAssociation> getByRoleId(String roleId);
}
