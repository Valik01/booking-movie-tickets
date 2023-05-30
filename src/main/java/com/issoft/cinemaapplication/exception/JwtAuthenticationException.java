package com.issoft.cinemaapplication.exception;

public class JwtAuthenticationException extends RuntimeException {
    public JwtAuthenticationException(final String message) {
        super(message);
    }
}
