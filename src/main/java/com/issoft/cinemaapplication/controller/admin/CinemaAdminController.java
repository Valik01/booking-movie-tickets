package com.issoft.cinemaapplication.controller.admin;

import com.issoft.cinemaapplication.audit.Audit;
import com.issoft.cinemaapplication.config.Role;
import com.issoft.cinemaapplication.dto.CinemaDto;
import com.issoft.cinemaapplication.exception.EntityNotFoundException;
import com.issoft.cinemaapplication.exception.ForbiddenException;
import com.issoft.cinemaapplication.exception.handler.ErrorResponse;
import com.issoft.cinemaapplication.mapper.CinemaMapper;
import com.issoft.cinemaapplication.model.Cinema;
import com.issoft.cinemaapplication.service.CinemaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class CinemaAdminController {
    private final CinemaService cinemaService;
    private final CinemaMapper cinemaMapper;

    @Operation(summary = "Add new cinema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Added new cinema",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CinemaDto.class))}),
            @ApiResponse(responseCode = "400", description = "Data entered invalid",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})})
    @Audit
    @Secured(Role.Values.ROLE_ADMIN)
    @PostMapping("/admin/cinemas")
    public CinemaDto addCinema(@Valid @RequestBody final CinemaDto cinemaDto) {
        return this.cinemaMapper.toDto(this.cinemaService.save(this.cinemaMapper.toEntity(cinemaDto)));
    }

    @Operation(summary = "Update cinema by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cinema updated successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CinemaDto.class))}),
            @ApiResponse(responseCode = "400", description = "Data entered invalid",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Cinema not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})})
    @Secured(Role.Values.ROLE_ADMIN)
    @PutMapping("/admin/cinemas/{id}")
    public CinemaDto updateCinema(@PathVariable final Long id, @Valid @RequestBody final CinemaDto cinemaDto, final Principal principal) {
        Cinema cinema = this.cinemaService.findById(id).orElseThrow(() -> new EntityNotFoundException(id));

        if (!cinema.getAdmin().getLogin().equals(principal.getName())) {
            throw new ForbiddenException("Only the administrator of this cinema can update information about the cinema.");
        }

        cinema = this.cinemaMapper.fillFrom(cinema, cinemaDto);

        return this.cinemaMapper.toDto(this.cinemaService.save(cinema));
    }

    @Operation(summary = "Delete cinema by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted cinema",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Cinema not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})})
    @Audit
    @Secured(Role.Values.ROLE_ADMIN)
    @DeleteMapping("/admin/cinemas/{id}")
    public void deleteCinema(@PathVariable final Long id) {
        this.cinemaService.deleteById(id);
    }

}
