package com.movieinventory.auth;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException() {
        super("Invalid x-api-key");
    }
}
