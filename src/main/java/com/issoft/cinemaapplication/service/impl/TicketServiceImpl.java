package com.issoft.cinemaapplication.service.impl;

import com.issoft.cinemaapplication.exception.EntityNotFoundException;
import com.issoft.cinemaapplication.exception.TicketCannotBuyException;
import com.issoft.cinemaapplication.model.Ticket;
import com.issoft.cinemaapplication.model.User;
import com.issoft.cinemaapplication.repository.TicketRepository;
import com.issoft.cinemaapplication.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl extends BaseServiceImpl<Ticket> implements TicketService {
    private final TicketRepository ticketRepository;

    @Override
    public Ticket buy(final Long id, final User user) {
        this.checkExistEntity(id);
        this.ticketIsFree(id);
        final Ticket ticket = this.findById(id).orElseThrow(() -> new EntityNotFoundException(id));
        ticket.setUser(user);
        ticket.setDateTimePurchase(LocalDateTime.now());

        return super.save(ticket);
    }

    @Override
    public Ticket cancel(final Long id) {
        final Ticket ticket = this.findById(id).orElseThrow(() -> new EntityNotFoundException(id));
        ticket.setUser(null);
        ticket.setDateTimePurchase(null);
        return super.save(ticket);
    }

    @Override
    public List<Ticket> findAllFreeBySessionId(final Long sessionId) {
        return ticketRepository.findAllBySessionIdAndUserIsNull(sessionId);
    }

    private void ticketIsFree(final Long id) {
        if (this.findById(id).orElseThrow(() -> new EntityNotFoundException(id)).getUser() == null) {
            return;
        } else {
            throw new TicketCannotBuyException(id);
        }
    }
}
