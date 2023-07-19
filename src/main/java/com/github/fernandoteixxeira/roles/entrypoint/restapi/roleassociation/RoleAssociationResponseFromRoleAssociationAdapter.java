package com.github.fernandoteixxeira.roles.entrypoint.restapi.roleassociation;

import com.github.fernandoteixxeira.roles.core.usecase.roleassociation.RoleAssociation;
import com.github.fernandoteixxeira.roles.entrypoint.restapi.hateaos.LinkResponse;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import static java.time.LocalDateTime.now;
import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PRIVATE)
public class RoleAssociationResponseFromRoleAssociationAdapter {
    private final RoleAssociation roleAssociationRequest;
    private final List<Function<UserIdGetter, LinkResponse>> linkCreator;

    public static RoleAssociationResponseFromRoleAssociationAdapter of(
            final RoleAssociation roleAssociationRequest,
            final List<Function<UserIdGetter, LinkResponse>> linkCreator
    ) {
        Objects.requireNonNull(roleAssociationRequest);
        return new RoleAssociationResponseFromRoleAssociationAdapter(roleAssociationRequest, linkCreator);
    }

    public RoleAssociationResponse adapt() {
        val roleAssociationResponse = RoleAssociationResponse.builder()
                .teamId(roleAssociationRequest.getTeamId())
                .userId(roleAssociationRequest.getUserId())
                .roleId(roleAssociationRequest.getRoleId())
                .createdAt(now())
                .build();

        val links = linkCreator.stream()
                .map(function -> function.apply(roleAssociationResponse))
                .toList();

        return roleAssociationResponse.toBuilder()
                .links(links)
                .build();
    }
}
