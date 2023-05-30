package com.issoft.cinemaapplication.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(final Long id) {
        super(String.format("Entity with id %d not found.", id));
    }

    public EntityNotFoundException(final String login) {
        super(String.format("User with login %s doesn't exists.", login));
    }
}
