package com.issoft.cinemaapplication.service;

import com.issoft.cinemaapplication.model.Ticket;
import com.issoft.cinemaapplication.model.User;

import java.util.List;

public interface TicketService extends BaseService<Ticket> {
    Ticket buy(final Long id, final User user);

    Ticket cancel(final Long id);

    List<Ticket> findAllFreeBySessionId(final Long sessionId);
}
