package com.issoft.cinemaapplication.controller.user;

import com.issoft.cinemaapplication.config.Role;
import com.issoft.cinemaapplication.dto.CinemaDto;
import com.issoft.cinemaapplication.exception.EntityNotFoundException;
import com.issoft.cinemaapplication.exception.handler.ErrorResponse;
import com.issoft.cinemaapplication.mapper.CinemaMapper;
import com.issoft.cinemaapplication.service.CinemaService;
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

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CinemaController {
    private final CinemaService cinemaService;
    private final CinemaMapper cinemaMapper;

    @Operation(summary = "Get all cinemas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all cinemas",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = CinemaDto.class)))
                    })
    })
    @Secured({Role.Values.ROLE_USER, Role.Values.ROLE_ADMIN})
    @GetMapping("/cinemas")
    public List<CinemaDto> getAll() {
        return this.cinemaMapper.toDto(this.cinemaService.findAll());
    }

    @Operation(summary = "Get cinema by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the cinema",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CinemaDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Cinema not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})})
    @Secured({Role.Values.ROLE_USER, Role.Values.ROLE_ADMIN})
    @GetMapping("/cinemas/{id}")
    public CinemaDto getCinemaById(@PathVariable final Long id) {
        return this.cinemaMapper.toDto(this.cinemaService.findById(id).orElseThrow(() -> new EntityNotFoundException(id)));
    }
}
