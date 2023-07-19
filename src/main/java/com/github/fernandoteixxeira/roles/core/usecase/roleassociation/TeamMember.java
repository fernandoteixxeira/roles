package com.github.fernandoteixxeira.roles.core.usecase.roleassociation;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TeamMember {
    private String teamId;
    private String userId;
}
