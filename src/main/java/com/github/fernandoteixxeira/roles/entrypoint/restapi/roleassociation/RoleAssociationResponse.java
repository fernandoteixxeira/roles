package com.github.fernandoteixxeira.roles.entrypoint.restapi.roleassociation;

import com.github.fernandoteixxeira.roles.entrypoint.restapi.hateaos.LinkResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@SuperBuilder(toBuilder = true)
@Getter
@NoArgsConstructor
public class RoleAssociationResponse implements UserIdGetter, TeamIdGetter {
    private String teamId;
    private String userId;
    private String roleId;
    private LocalDateTime createdAt;
    private List<LinkResponse> links;
}
