package com.malex.test_app_backend.security.exception;

public class ClientAuthorizationException extends RuntimeException {

    public ClientAuthorizationException(String message) {
        super(message);
    }
}
