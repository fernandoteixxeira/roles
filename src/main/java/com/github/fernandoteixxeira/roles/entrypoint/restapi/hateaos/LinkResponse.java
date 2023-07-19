package com.github.fernandoteixxeira.roles.entrypoint.restapi.hateaos;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LinkResponse {
    public enum HttpVerb {
        GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS
    }

    private HttpVerb type;
    private String rel;
    private String uri;
}
