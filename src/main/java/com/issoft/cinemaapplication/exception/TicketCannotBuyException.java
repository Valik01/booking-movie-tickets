package com.issoft.cinemaapplication.exception;

public class TicketCannotBuyException extends RuntimeException {
    public TicketCannotBuyException(final Long id) {
        super(String.format("Ticket with id %s cannot be bought, because it has already been bought.", id));
    }
}
