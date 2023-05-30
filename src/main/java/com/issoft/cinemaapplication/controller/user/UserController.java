package com.issoft.cinemaapplication.controller.user;

import com.issoft.cinemaapplication.audit.Audit;
import com.issoft.cinemaapplication.config.Role;
import com.issoft.cinemaapplication.dto.user.UserInDto;
import com.issoft.cinemaapplication.dto.user.UserOutDto;
import com.issoft.cinemaapplication.dto.user.UserPageDto;
import com.issoft.cinemaapplication.dto.weather.WeatherDto;
import com.issoft.cinemaapplication.exception.EntityNotFoundException;
import com.issoft.cinemaapplication.exception.handler.ErrorResponse;
import com.issoft.cinemaapplication.mapper.UserMapper;
import com.issoft.cinemaapplication.mapper.WeatherMapper;
import com.issoft.cinemaapplication.model.User;
import com.issoft.cinemaapplication.service.UserService;
import com.issoft.cinemaapplication.service.WeatherService;
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
public class UserController {
    private final UserService userService;
    private final WeatherService weatherService;
    private final UserMapper userMapper;
    private final WeatherMapper weatherMapper;

    @Operation(summary = "Get user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the user",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserOutDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})})
    @Secured({Role.Values.ROLE_USER, Role.Values.ROLE_ADMIN})
    @GetMapping("/users")
    public UserPageDto getUserById(final Principal principal) {
        final UserOutDto userOutDto = this.userMapper.toDto(this.userService.findByLogin(principal.getName()).orElseThrow(() -> new EntityNotFoundException(String.format("User with login %s not found.", principal.getName()))));
        final WeatherDto weatherDto = this.weatherMapper.toDto(this.weatherService.getLast());

        return new UserPageDto(userOutDto, weatherDto);
    }

    @Operation(summary = "Update user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserOutDto.class))}),
            @ApiResponse(responseCode = "400", description = "Data entered invalid",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})})
    @Secured({Role.Values.ROLE_USER, Role.Values.ROLE_ADMIN})
    @PutMapping("/users")
    public UserOutDto updateUser(@Valid @RequestBody final UserInDto userInDto, final Principal principal) {
        User user = this.userService.findByLogin(principal.getName()).orElseThrow(() -> new EntityNotFoundException(String.format("User with login %s not found.", principal.getName())));

        user = this.userMapper.fillFrom(user, userInDto);

        return this.userMapper.toDto(this.userService.save(user));
    }

    @Operation(summary = "Add new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Added new user",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserOutDto.class))}),
            @ApiResponse(responseCode = "400", description = "Data entered invalid",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})})
    @Audit
    @PostMapping("/register")
    public UserOutDto addUser(@Valid @RequestBody final UserInDto userInDto) {
        return this.userMapper.toDto(this.userService.save(this.userMapper.toEntity(userInDto)));
    }

    @Operation(summary = "Delete user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted user",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})})
    @Audit
    @Secured({Role.Values.ROLE_USER, Role.Values.ROLE_ADMIN})
    @DeleteMapping("/users")
    public void deleteUser(final Principal principal) {
        final User user = this.userService.findByLogin(principal.getName()).orElseThrow(() -> new EntityNotFoundException(String.format("User with login %s not found.", principal.getName())));
        this.userService.deleteById(user.getId());
    }
}
