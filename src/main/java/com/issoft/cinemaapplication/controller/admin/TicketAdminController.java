package com.issoft.cinemaapplication.controller.admin;

import com.issoft.cinemaapplication.audit.Audit;
import com.issoft.cinemaapplication.config.Role;
import com.issoft.cinemaapplication.dto.TicketDto;
import com.issoft.cinemaapplication.exception.EntityNotFoundException;
import com.issoft.cinemaapplication.exception.TicketCannotUpdateException;
import com.issoft.cinemaapplication.exception.handler.ErrorResponse;
import com.issoft.cinemaapplication.mapper.TicketMapper;
import com.issoft.cinemaapplication.model.Session;
import com.issoft.cinemaapplication.model.Ticket;
import com.issoft.cinemaapplication.service.SessionService;
import com.issoft.cinemaapplication.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TicketAdminController {
    private final TicketService ticketService;
    private final TicketMapper ticketMapper;

    private final SessionService sessionService;

    @Operation(summary = "Get all tickets")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all tickets",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = TicketDto.class)))
                    })
    })
    @Secured(Role.Values.ROLE_ADMIN)
    @GetMapping("/admin/tickets")
    public List<TicketDto> getAll() {
        return this.ticketMapper.toDto(this.ticketService.findAll());
    }

    @Operation(summary = "Update ticket by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket updated successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TicketDto.class))}),
            @ApiResponse(responseCode = "400", description = "Data entered invalid",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Ticket not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})})
    @Secured(Role.Values.ROLE_ADMIN)
    @PutMapping("/admin/tickets/{id}")
    public TicketDto updateTicket(@PathVariable final Long id, @Valid @RequestBody final TicketDto ticketDto) {
        final Session session = sessionService.findById(ticketDto.getSessionId()).orElseThrow(() -> new EntityNotFoundException(id));
        final Ticket ticket = this.ticketService.findById(id).orElseThrow(() -> new EntityNotFoundException(id));

        if (ticket.getUser() != null) {
            throw new TicketCannotUpdateException(ticketDto.getId());
        } else {
            return this.ticketMapper.toDto(this.ticketService.save(this.ticketMapper.fillFrom(ticket, ticketDto, session)));
        }
    }

    @Operation(summary = "Add new ticket")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Added new ticket",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TicketDto.class))}),
            @ApiResponse(responseCode = "400", description = "Data entered invalid",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})})
    @Audit
    @Secured(Role.Values.ROLE_ADMIN)
    @PostMapping("/admin/tickets")
    public TicketDto addTicket(@Valid @RequestBody final TicketDto ticketDto) {
        ticketDto.setUserId(null);
        return this.ticketMapper.toDto(this.ticketService.save(this.ticketMapper.toEntity(ticketDto)));
    }

    @Operation(summary = "Delete ticket by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted ticket",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Ticket not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})})
    @Audit
    @Secured(Role.Values.ROLE_ADMIN)
    @DeleteMapping("/admin/tickets/{id}")
    public void deleteTicket(@PathVariable final Long id) {
        this.ticketService.deleteById(id);
    }
}
