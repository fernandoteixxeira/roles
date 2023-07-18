package com.github.fernandoteixxeira.roles.entrypoint.restapi.handler;

import com.github.fernandoteixxeira.roles.entrypoint.restapi.exception.NotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.val;
import org.apache.commons.collections4.ListUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {NotFoundException.class})
    protected ResponseEntity<ApiError> notFound(final RuntimeException exception) {
        val apiError = ApiError.builder()
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }


    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            final MissingServletRequestParameterException exception,
            final HttpHeaders headers,
            final HttpStatusCode status,
            final WebRequest request
    ) {
        val error = ErrorFromMissingServletRequestParameterExceptionAdapter.of(exception).adapt();
        val apiError = ApiError.builder()
                .message("some fields contain errors")
                .errors(List.of(error))
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    protected ResponseEntity<ApiError> constraintViolationException(final ConstraintViolationException exception) {
        val errors = exception.getConstraintViolations().stream()
                .map(error -> ErrorFromConstraintViolationAdapter.of(error).adapt())
                .collect(Collectors.toList());
        val apiError = ApiError.builder()
                .message("some fields contain errors")
                .errors(errors)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            final MethodArgumentNotValidException exception,
            final HttpHeaders headers,
            final HttpStatusCode status,
            final WebRequest request
    ) {
        val attributeErrors = exception.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> ErrorFromFieldErrorAdapter.of(error).adapt())
                .collect(Collectors.toList());

        val validationErrors = exception.getBindingResult().getGlobalErrors()
                .stream()
                .map(error -> ErrorFromObjectErrorAdapter.of(error).adapt())
                .collect(Collectors.toList());

        val apiError = ApiError.builder()
                .message("some fields contain errors")
                .errors(ListUtils.union(attributeErrors, validationErrors))
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            final HttpMessageNotReadableException exception,
            final HttpHeaders headers,
            final HttpStatusCode status,
            final WebRequest request
    ) {
        val apiError = ApiError.builder()
                .message(exception.getMostSpecificCause().getMessage())
                .build();
        return ResponseEntity.status(status).body(apiError);
    }

}