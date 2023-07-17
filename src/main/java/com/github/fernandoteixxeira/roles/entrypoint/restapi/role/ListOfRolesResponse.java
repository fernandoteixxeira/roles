package com.github.fernandoteixxeira.roles.entrypoint.restapi.role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Collection;

@SuperBuilder
@Getter
@NoArgsConstructor
public class ListOfRolesResponse {
    private Collection<RoleResponse> roles;
}
