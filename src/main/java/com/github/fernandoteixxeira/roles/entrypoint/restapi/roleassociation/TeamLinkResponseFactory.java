package com.github.fernandoteixxeira.roles.entrypoint.restapi.roleassociation;

import com.github.fernandoteixxeira.roles.entrypoint.restapi.hateaos.LinkResponse;
import com.github.fernandoteixxeira.roles.entrypoint.restapi.hateaos.LinkResponse.HttpVerb;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@RequiredArgsConstructor
@Component
public class TeamLinkResponseFactory {
    @Value("${hateaos.team.type}")
    private final HttpVerb type;
    @Value("${hateaos.team.rel}")
    private final String rel;
    @Value("${hateaos.team.uri}")
    private final String uri;

    protected LinkResponse create(final IdGetter idGetter) {
        if (!(idGetter instanceof TeamIdGetter teamIdGetter)) {
            return null;
        }
        val uri = MessageFormat.format(this.uri, teamIdGetter.getTeamId());
        return LinkResponse.builder()
                .type(type)
                .rel(rel)
                .uri(uri)
                .build();
    }
}
