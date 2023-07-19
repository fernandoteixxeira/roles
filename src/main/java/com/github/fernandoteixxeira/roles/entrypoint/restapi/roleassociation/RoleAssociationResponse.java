package com.github.fernandoteixxeira.roles.entrypoint.restapi.roleassociation;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class RoleAssociationResponse {
    private String teamId;
    private String userId;
    private String roleId;
    private LocalDateTime createdAt;
}
