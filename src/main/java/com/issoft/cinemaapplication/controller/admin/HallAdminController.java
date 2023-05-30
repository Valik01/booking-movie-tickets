package com.issoft.cinemaapplication.controller.admin;

import com.issoft.cinemaapplication.audit.Audit;
import com.issoft.cinemaapplication.config.Role;
import com.issoft.cinemaapplication.dto.HallDto;
import com.issoft.cinemaapplication.exception.EntityNotFoundException;
import com.issoft.cinemaapplication.exception.handler.ErrorResponse;
import com.issoft.cinemaapplication.mapper.HallMapper;
import com.issoft.cinemaapplication.model.Hall;
import com.issoft.cinemaapplication.service.HallService;
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
public class HallAdminController {
    private final HallService hallService;
    private final HallMapper hallMapper;

    @Operation(summary = "Get all halls")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all halls",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = HallDto.class)))
                    })
    })
    @Secured(Role.Values.ROLE_ADMIN)
    @GetMapping("/admin/halls")
    public List<HallDto> getAll() {
        return this.hallMapper.toDto(this.hallService.findAll());
    }

    @Operation(summary = "Add new hall")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Added new hall",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = HallDto.class))}),
            @ApiResponse(responseCode = "400", description = "Data entered invalid",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})})
    @Audit
    @Secured(Role.Values.ROLE_ADMIN)
    @PostMapping("/admin/halls")
    public HallDto addHall(@Valid @RequestBody final HallDto hallDto) {
        return this.hallMapper.toDto(this.hallService.save(this.hallMapper.toEntity(hallDto)));
    }

    @Operation(summary = "Update hall by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hall updated successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = HallDto.class))}),
            @ApiResponse(responseCode = "400", description = "Data entered invalid",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Hall not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})})
    @Secured(Role.Values.ROLE_ADMIN)
    @PutMapping("/admin/halls/{id}")
    public HallDto updateHall(@PathVariable final Long id, @Valid @RequestBody final HallDto hallDto) {
        Hall hall = this.hallService.findById(id).orElseThrow(() -> new EntityNotFoundException(id));

        hall = this.hallMapper.fillFrom(hall, hallDto);

        return this.hallMapper.toDto(this.hallService.save(hall));
    }

    @Operation(summary = "Delete hall by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted hall",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Hall not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})})
    @Audit
    @Secured(Role.Values.ROLE_ADMIN)
    @DeleteMapping("/admin/halls/{id}")
    public void deleteHall(@PathVariable final Long id) {
        this.hallService.deleteById(id);
    }
}
