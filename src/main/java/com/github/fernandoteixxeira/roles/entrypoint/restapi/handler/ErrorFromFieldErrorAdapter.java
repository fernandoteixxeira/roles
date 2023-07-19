package com.github.fernandoteixxeira.roles.entrypoint.restapi.handler;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.FieldError;

import java.util.Objects;

import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PRIVATE)
public class ErrorFromFieldErrorAdapter {
    private final FieldError fieldError;

    public static ErrorFromFieldErrorAdapter of(final FieldError fieldError) {
        Objects.requireNonNull(fieldError);
        return new ErrorFromFieldErrorAdapter(fieldError);
    }

    public Error adapt() {
        return Error.builder()
                .scope("attribute")
                .field(fieldError.getField())
                .value(String.valueOf(fieldError.getRejectedValue()))
                .message(fieldError.getDefaultMessage())
                .build();
    }
}
