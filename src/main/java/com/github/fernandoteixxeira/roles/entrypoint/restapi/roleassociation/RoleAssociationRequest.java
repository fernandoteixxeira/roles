package com.github.fernandoteixxeira.roles.entrypoint.restapi.roleassociation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class RoleAssociationRequest {
    @NotBlank
    @Size(max = 50)
    private String teamId;
    @NotBlank
    @Size(max = 50)
    private String userId;
}
