package com.issoft.cinemaapplication.repository;

import com.issoft.cinemaapplication.model.Ticket;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends BaseRepository<Ticket> {
    List<Ticket> findAllBySessionIdAndUserIsNull(final Long sessionId);
}
