package com.issoft.cinemaapplication.controller.user;

import com.issoft.cinemaapplication.config.Role;
import com.issoft.cinemaapplication.dto.HallDto;
import com.issoft.cinemaapplication.dto.SessionDto;
import com.issoft.cinemaapplication.dto.SessionOutDto;
import com.issoft.cinemaapplication.exception.EntityNotFoundException;
import com.issoft.cinemaapplication.mapper.SessionMapper;
import com.issoft.cinemaapplication.model.Movie;
import com.issoft.cinemaapplication.service.MovieService;
import com.issoft.cinemaapplication.service.SessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class SessionController {
    private final SessionMapper sessionMapper;
    private final SessionService sessionService;
    private final MovieService movieService;

    @Operation(summary = "Get all sessions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all sessions",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = SessionDto.class)))
                    })
    })
    @Secured({Role.Values.ROLE_USER, Role.Values.ROLE_ADMIN})
    @GetMapping("/sessions")
    public List<SessionDto> getAll() {
        return this.sessionMapper.toDto(this.sessionService.findAll());
    }

    @Operation(summary = "Get all sessions by hall id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all sessions by hall id",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = HallDto.class)))
                    })
    })
    @Secured({Role.Values.ROLE_USER, Role.Values.ROLE_ADMIN})
    @GetMapping("sessions/{hallId}")
    public List<SessionOutDto> getAllSessionsByHallId(@PathVariable final Long hallId) {
        final List<SessionDto> sessionsDto = this.sessionMapper.toDto(this.sessionService.findSessionByHallId(hallId));
        final List<SessionOutDto> sessionOutDtos = new ArrayList<>();
        for (SessionDto sessionDto : sessionsDto) {
            final Movie movie = this.movieService.findById(sessionDto.getMovieId()).orElseThrow(() -> new EntityNotFoundException(sessionDto.getId()));
            final SessionOutDto sessionOutDto = new SessionOutDto(sessionDto.getId(), sessionDto.getHallId(), movie.getName(), sessionDto.getStartDateTime(), sessionDto.getEndDateTime());
            sessionOutDtos.add(sessionOutDto);
        }
        return sessionOutDtos;
    }
}
