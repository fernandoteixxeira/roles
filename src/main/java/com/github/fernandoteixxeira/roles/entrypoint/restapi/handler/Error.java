package com.github.fernandoteixxeira.roles.entrypoint.restapi.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * Error representation.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@SuperBuilder
public final class Error {
    private String scope;
    private String field;
    private String value;
    private String message;
}