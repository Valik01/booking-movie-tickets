package com.issoft.cinemaapplication.exception;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException(final String message) {
        super(message);
    }
}
