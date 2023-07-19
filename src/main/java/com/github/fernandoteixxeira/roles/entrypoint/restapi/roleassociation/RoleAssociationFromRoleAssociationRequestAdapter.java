package com.github.fernandoteixxeira.roles.entrypoint.restapi.roleassociation;

import com.github.fernandoteixxeira.roles.core.usecase.roleassociation.RoleAssociation;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

import static java.time.LocalDateTime.now;
import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PRIVATE)
public class RoleAssociationFromRoleAssociationRequestAdapter {
    private final String roleId;
    private final RoleAssociationRequest roleAssociationRequest;

    public static RoleAssociationFromRoleAssociationRequestAdapter of(final String roleId, final RoleAssociationRequest roleAssociationRequest) {
        Objects.requireNonNull(roleId);
        Objects.requireNonNull(roleAssociationRequest);
        return new RoleAssociationFromRoleAssociationRequestAdapter(roleId, roleAssociationRequest);
    }

    public RoleAssociation adapt() {
        return RoleAssociation.builder()
                .teamId(roleAssociationRequest.getTeamId())
                .userId(roleAssociationRequest.getUserId())
                .roleId(roleId)
                .createdAt(now())
                .build();
    }
}
