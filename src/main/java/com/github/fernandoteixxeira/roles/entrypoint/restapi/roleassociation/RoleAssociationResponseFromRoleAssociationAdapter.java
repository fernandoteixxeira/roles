package com.github.fernandoteixxeira.roles.entrypoint.restapi.roleassociation;

import com.github.fernandoteixxeira.roles.core.usecase.roleassociation.RoleAssociation;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

import static java.time.LocalDateTime.now;
import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PRIVATE)
public class RoleAssociationResponseFromRoleAssociationAdapter {
    private final RoleAssociation roleAssociationRequest;

    public static RoleAssociationResponseFromRoleAssociationAdapter of(final RoleAssociation roleAssociationRequest) {
        Objects.requireNonNull(roleAssociationRequest);
        return new RoleAssociationResponseFromRoleAssociationAdapter(roleAssociationRequest);
    }

    public RoleAssociationResponse adapt() {
        return RoleAssociationResponse.builder()
                .teamId(roleAssociationRequest.getTeamId())
                .userId(roleAssociationRequest.getUserId())
                .roleId(roleAssociationRequest.getRoleId())
                .createdAt(now())
                .build();
    }
}
