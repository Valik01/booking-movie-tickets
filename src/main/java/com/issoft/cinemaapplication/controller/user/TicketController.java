package com.issoft.cinemaapplication.controller.user;

import com.issoft.cinemaapplication.audit.Audit;
import com.issoft.cinemaapplication.config.Role;
import com.issoft.cinemaapplication.dto.TicketDto;
import com.issoft.cinemaapplication.exception.EntityNotFoundException;
import com.issoft.cinemaapplication.exception.ForbiddenException;
import com.issoft.cinemaapplication.exception.handler.ErrorResponse;
import com.issoft.cinemaapplication.mapper.TicketMapper;
import com.issoft.cinemaapplication.model.Cinema;
import com.issoft.cinemaapplication.model.Hall;
import com.issoft.cinemaapplication.model.Ticket;
import com.issoft.cinemaapplication.model.User;
import com.issoft.cinemaapplication.service.CinemaService;
import com.issoft.cinemaapplication.service.HallService;
import com.issoft.cinemaapplication.service.TicketService;
import com.issoft.cinemaapplication.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;
    private final TicketMapper ticketMapper;
    private final HallService hallService;
    private final CinemaService cinemaService;

    private final UserService userService;

    @Operation(summary = "Get all free tickets by session id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all tickets by session id",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = TicketDto.class)))
                    })
    })
    @Secured({Role.Values.ROLE_USER, Role.Values.ROLE_ADMIN})
    @GetMapping("/tickets/{sessionId}")
    public List<TicketDto> getAllFreeTicketsBySessionId(@PathVariable final Long sessionId) {
        return this.ticketMapper.toDto(this.ticketService.findAllFreeBySessionId(sessionId));
    }

    @Operation(summary = "Buy ticket by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket purchased successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TicketDto.class))}),
            @ApiResponse(responseCode = "400", description = "Data entered invalid",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Ticket not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})})
    @Audit
    @Secured({Role.Values.ROLE_USER, Role.Values.ROLE_ADMIN})
    @PutMapping("/tickets/{id}")
    public TicketDto buyTicket(@PathVariable final Long id, final Principal principal) {
        final User user = this.userService.findByLogin(principal.getName()).orElseThrow(() -> new EntityNotFoundException(id));

        return this.ticketMapper.toDto(this.ticketService.buy(id, user));
    }

    @Operation(summary = "Cancel ticket by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket canceled",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TicketDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Ticket not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})})
    @Audit
    @Secured({Role.Values.ROLE_USER, Role.Values.ROLE_ADMIN})
    @PostMapping("/tickets/{id}")
    public TicketDto cancelTicket(@PathVariable final Long id, final Principal principal) {
        final Ticket ticket = this.ticketService.findById(id).orElseThrow(() -> new EntityNotFoundException(id));

        final User cinemaAdmin = this.findCinemaAdminByTicket(ticket);

        if (ticket.getUser() != null && (ticket.getUser().getLogin().equals(principal.getName()) || cinemaAdmin.getLogin().equals(principal.getName()))) {
            return this.ticketMapper.toDto(this.ticketService.cancel(id));
        }

        throw new ForbiddenException("User can only return their own tickets.");
    }

    private User findCinemaAdminByTicket(final Ticket ticket) {
        final Long hallId = ticket.getSession().getHall().getId();
        final Hall hallFromTicket = this.hallService.findById(hallId).orElseThrow(() -> new EntityNotFoundException(hallId));
        final Cinema cinema = this.cinemaService.findById(hallFromTicket.getCinema().getId()).orElseThrow(() -> new EntityNotFoundException(hallFromTicket.getCinema().getId()));
        return cinema.getAdmin();
    }
}
