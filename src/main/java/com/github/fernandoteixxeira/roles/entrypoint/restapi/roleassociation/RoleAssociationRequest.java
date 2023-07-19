package com.github.fernandoteixxeira.roles.entrypoint.restapi.roleassociation;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class RoleAssociationRequest {
    @NotBlank
    private String teamId;
    @NotBlank
    private String userId;
}
