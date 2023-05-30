package com.issoft.cinemaapplication.mapper;

import com.issoft.cinemaapplication.dto.TicketDto;
import com.issoft.cinemaapplication.model.Session;
import com.issoft.cinemaapplication.model.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TicketMapper {

    @Mappings({
            @Mapping(target = "sessionId", source = "session.id"),
            @Mapping(target = "userId", source = "user.id")
    })
    TicketDto toDto(final Ticket ticket);

    List<TicketDto> toDto(final List<Ticket> tickets);

    @Mappings({
            @Mapping(target = "session.id", source = "sessionId"),
            @Mapping(target = "user", expression = "java(null)"),
    })
    Ticket toEntity(final TicketDto ticketDto);

    @Mappings({
            @Mapping(target = "id", source = "ticket.id"),
            @Mapping(target = "raw", source = "ticketDto.raw"),
            @Mapping(target = "seat", source = "ticketDto.seat"),
            @Mapping(target = "price", source = "ticketDto.price"),
            @Mapping(target = "dateTimePurchase", source = "ticketDto.dateTimePurchase"),
            @Mapping(target = "session", source = "session")
    })
    Ticket fillFrom(final Ticket ticket, final TicketDto ticketDto, final Session session);
}
