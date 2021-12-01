package com.project.paymybuddy.Exception;

public class InvalidJwtAuthenticationException extends RuntimeException {
    public InvalidJwtAuthenticationException(String expired_or_invalid_jwt_token, RuntimeException e) {

    }
}
