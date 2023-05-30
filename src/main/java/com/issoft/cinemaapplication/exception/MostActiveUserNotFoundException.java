package com.issoft.cinemaapplication.exception;

public class MostActiveUserNotFoundException extends RuntimeException {
    public MostActiveUserNotFoundException(final String message) {
        super(message);
    }
}
