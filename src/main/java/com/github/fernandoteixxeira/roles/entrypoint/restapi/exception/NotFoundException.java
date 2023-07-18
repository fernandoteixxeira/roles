package com.github.fernandoteixxeira.roles.entrypoint.restapi.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}