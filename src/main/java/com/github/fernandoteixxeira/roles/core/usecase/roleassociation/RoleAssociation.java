package com.github.fernandoteixxeira.roles.core.usecase.roleassociation;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class RoleAssociation {
    private String teamId;
    private String userId;
    private String roleId;
    private LocalDateTime createdAt;
}
