package com.issoft.cinemaapplication.controller.admin;

import com.issoft.cinemaapplication.audit.Audit;
import com.issoft.cinemaapplication.config.Role;
import com.issoft.cinemaapplication.dto.SessionDto;
import com.issoft.cinemaapplication.exception.EntityNotFoundException;
import com.issoft.cinemaapplication.exception.handler.ErrorResponse;
import com.issoft.cinemaapplication.mapper.SessionMapper;
import com.issoft.cinemaapplication.model.Hall;
import com.issoft.cinemaapplication.model.Movie;
import com.issoft.cinemaapplication.model.Session;
import com.issoft.cinemaapplication.service.HallService;
import com.issoft.cinemaapplication.service.MovieService;
import com.issoft.cinemaapplication.service.SessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class SessionAdminController {
    private final SessionMapper sessionMapper;
    private final SessionService sessionService;

    private final HallService hallService;
    private final MovieService movieService;

    @Operation(summary = "Update session by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Session updated successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SessionDto.class))}),
            @ApiResponse(responseCode = "400", description = "Data entered invalid",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Session not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})})
    @Secured(Role.Values.ROLE_ADMIN)
    @PutMapping("/admin/sessions/{id}")
    public SessionDto updateSession(@PathVariable final Long id, @Valid @RequestBody final SessionDto sessionDto) {
        Session session = this.sessionService.findById(id).orElseThrow(() -> new EntityNotFoundException(id));

        final Movie movie = movieService.findById(sessionDto.getMovieId()).orElseThrow(() -> new EntityNotFoundException(sessionDto.getMovieId()));
        final Hall hall = hallService.findById(sessionDto.getHallId()).orElseThrow(() -> new EntityNotFoundException(sessionDto.getHallId()));

        session = this.sessionMapper.fillFrom(session, sessionDto, movie, hall);

        return this.sessionMapper.toDto(this.sessionService.save(session));
    }

    @Operation(summary = "Add new session")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Added new session",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SessionDto.class))}),
            @ApiResponse(responseCode = "400", description = "Data entered invalid",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})})
    @Audit
    @Secured(Role.Values.ROLE_ADMIN)
    @PostMapping("/admin/sessions")
    public SessionDto addSession(@Valid @RequestBody final SessionDto sessionDto) {
        return this.sessionMapper.toDto(this.sessionService.save(this.sessionMapper.toEntity(sessionDto)));
    }

    @Operation(summary = "Delete session by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted session",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Session not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})})
    @Audit
    @Secured(Role.Values.ROLE_ADMIN)
    @DeleteMapping("/admin/sessions/{id}")
    public void deleteSession(@PathVariable final Long id) {
        this.sessionService.deleteById(id);
    }
}
