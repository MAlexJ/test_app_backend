package com.malex.test_app_backend.security.exception;

public class ApplicationAuthorizationException extends RuntimeException {

  public ApplicationAuthorizationException(String message) {
    super(message);
  }
}
