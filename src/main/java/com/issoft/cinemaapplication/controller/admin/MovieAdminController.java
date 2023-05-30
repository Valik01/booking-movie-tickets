package com.issoft.cinemaapplication.controller.admin;

import com.issoft.cinemaapplication.audit.Audit;
import com.issoft.cinemaapplication.config.Role;
import com.issoft.cinemaapplication.dto.MovieDto;
import com.issoft.cinemaapplication.exception.EntityNotFoundException;
import com.issoft.cinemaapplication.exception.handler.ErrorResponse;
import com.issoft.cinemaapplication.mapper.MovieMapper;
import com.issoft.cinemaapplication.model.Movie;
import com.issoft.cinemaapplication.service.MovieService;
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
public class MovieAdminController {
    private final MovieService movieService;
    private final MovieMapper movieMapper;

    @Operation(summary = "Update movie by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie updated successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MovieDto.class))}),
            @ApiResponse(responseCode = "400", description = "Data entered invalid",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Movie not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})})
    @Secured(Role.Values.ROLE_ADMIN)
    @PutMapping("/admin/movies/{id}")
    public MovieDto updateMovie(@PathVariable final Long id, @Valid @RequestBody final MovieDto movieDto) {
        Movie movie = this.movieService.findById(id).orElseThrow(() -> new EntityNotFoundException(id));

        movie = this.movieMapper.fillFrom(movie, movieDto);

        return this.movieMapper.toDto(this.movieService.save(movie));
    }

    @Operation(summary = "Add new movie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Added new movie",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MovieDto.class))}),
            @ApiResponse(responseCode = "400", description = "Data entered invalid",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})})
    @Audit
    @Secured(Role.Values.ROLE_ADMIN)
    @PostMapping("/admin/movies")
    public MovieDto addMovie(@Valid @RequestBody final MovieDto movieDto) {
        return this.movieMapper.toDto(this.movieService.save(this.movieMapper.toEntity(movieDto)));
    }

    @Operation(summary = "Delete movie by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted movie",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Movie not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})})
    @Audit
    @Secured(Role.Values.ROLE_ADMIN)
    @DeleteMapping("/admin/movies/{id}")
    public void deleteMovie(@PathVariable final Long id) {
        this.movieService.deleteById(id);
    }
}
