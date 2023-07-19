package com.github.fernandoteixxeira.roles.entrypoint.restapi.roleassociation;

import lombok.Builder;
import lombok.Getter;

import java.util.Collection;

@Builder
@Getter
public class ListOfRoleAssociationResponse {
    private Collection<RoleAssociationResponse> associations;
}
