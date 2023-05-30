package com.issoft.cinemaapplication.exception;

public class TicketCannotUpdateException extends RuntimeException {
    public TicketCannotUpdateException(final Long id) {
        super(String.format("Cannot update ticket with id %d because it has already been purchased.", id));
    }
}
