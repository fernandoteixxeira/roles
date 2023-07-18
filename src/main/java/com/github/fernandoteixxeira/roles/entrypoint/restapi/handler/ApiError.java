package com.github.fernandoteixxeira.roles.entrypoint.restapi.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * API Error representation.
 */
@Getter
@Builder
public class ApiError {
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Error> errors;
}