package com.github.fernandoteixxeira.roles.entrypoint.restapi.handler;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.ObjectError;

import java.util.Objects;

import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PRIVATE)
public class ErrorFromObjectErrorAdapter {
    private final ObjectError objectError;

    public static ErrorFromObjectErrorAdapter of(final ObjectError objectError) {
        Objects.requireNonNull(objectError);
        return new ErrorFromObjectErrorAdapter(objectError);
    }

    public Error adapt() {
        return Error.builder()
                .scope("validation")
                .message(objectError.getDefaultMessage())
                .build();
    }
}
