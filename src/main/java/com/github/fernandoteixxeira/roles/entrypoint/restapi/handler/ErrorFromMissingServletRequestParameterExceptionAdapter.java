package com.github.fernandoteixxeira.roles.entrypoint.restapi.handler;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.web.bind.MissingServletRequestParameterException;

import java.text.MessageFormat;
import java.util.Objects;

import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PRIVATE)
public class ErrorFromMissingServletRequestParameterExceptionAdapter {
    private final MissingServletRequestParameterException requestParameterException;

    public static ErrorFromMissingServletRequestParameterExceptionAdapter of(final MissingServletRequestParameterException requestParameterException) {
        Objects.requireNonNull(requestParameterException);
        return new ErrorFromMissingServletRequestParameterExceptionAdapter(requestParameterException);
    }

    public Error adapt() {
        val errorMessage = MessageFormat.format("{0} parameter is missing", requestParameterException.getParameterName());
        return Error.builder()
                .scope("path")
                .field(requestParameterException.getParameterName())
                .message(errorMessage)
                .build();
    }
}
