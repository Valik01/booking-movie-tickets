package com.issoft.cinemaapplication.controller.user;

import com.issoft.cinemaapplication.config.Role;
import com.issoft.cinemaapplication.dto.HallDto;
import com.issoft.cinemaapplication.exception.EntityNotFoundException;
import com.issoft.cinemaapplication.exception.handler.ErrorResponse;
import com.issoft.cinemaapplication.mapper.HallMapper;
import com.issoft.cinemaapplication.service.HallService;
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
public class HallController {
    private final HallService hallService;
    private final HallMapper hallMapper;

    @Operation(summary = "Get hall by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the hall",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = HallDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Hall not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})})
    @Secured({Role.Values.ROLE_USER, Role.Values.ROLE_ADMIN})
    @GetMapping("/halls/{id}")
    public HallDto getHallById(@PathVariable final Long id) {
        return this.hallMapper.toDto(this.hallService.findById(id).orElseThrow(() -> new EntityNotFoundException(id)));
    }

    @Operation(summary = "Get all halls by cinema id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all halls by cinema id",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = HallDto.class)))
                    })
    })
    @Secured({Role.Values.ROLE_USER, Role.Values.ROLE_ADMIN})
    @GetMapping("cinemas/{id}/halls")
    public List<HallDto> getAllHallsByCinemaId(@PathVariable final Long id) {
        return this.hallMapper.toDto(this.hallService.findAllByCinemaId(id));
    }
}
