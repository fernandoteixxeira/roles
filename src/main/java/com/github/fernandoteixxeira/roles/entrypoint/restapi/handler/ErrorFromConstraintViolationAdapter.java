package com.github.fernandoteixxeira.roles.entrypoint.restapi.handler;

import jakarta.validation.ConstraintViolation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PRIVATE)
public class ErrorFromConstraintViolationAdapter {
    private final ConstraintViolation constraintViolation;

    public static ErrorFromConstraintViolationAdapter of(final ConstraintViolation constraintViolation) {
        Objects.requireNonNull(constraintViolation);
        return new ErrorFromConstraintViolationAdapter(constraintViolation);
    }

    public Error adapt() {
        return Error.builder()
                .scope("path")
                .field(constraintViolation.getPropertyPath().toString())
                .value(constraintViolation.getInvalidValue().toString())
                .message(constraintViolation.getMessage())
                .build();
    }
}
