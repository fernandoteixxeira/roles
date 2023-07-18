package com.github.fernandoteixxeira.roles.entrypoint.restapi.role;

import lombok.Builder;
import lombok.Getter;

import java.util.Collection;

@Builder
@Getter
public class ListOfRolesResponse {
    private Collection<RoleResponse> roles;
}
