package com.issoft.cinemaapplication.controller.admin;

import com.issoft.cinemaapplication.dto.user.*;
import com.issoft.cinemaapplication.dto.weather.WeatherDto;
import com.issoft.cinemaapplication.exception.EntityNotFoundException;
import com.issoft.cinemaapplication.mapper.UserMapper;
import com.issoft.cinemaapplication.model.SystemRole;
import com.issoft.cinemaapplication.model.User;
import com.issoft.cinemaapplication.repository.SystemRoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.*;

public class UserControllerTest extends BaseIntegrationAdminTest {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SystemRoleRepository systemRoleRepository;

    @Test
    public void getUserByValidIdTest() {
        final ResponseEntity<UserPageDto> response = super.getRestTemplate().exchange("/users",
                HttpMethod.GET, this.buildRequest(null), new ParameterizedTypeReference<>() {
                });

        final WeatherDto weatherDto = response.getBody().getWeatherDto();


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(super.getUserOutDto(), response.getBody().getUserOutDto());

        assertNotNull(weatherDto.getName());
        assertNotNull(weatherDto.getDateTime());
        assertNotNull(weatherDto.getMain().getTemp());
        assertNotNull(weatherDto.getWind().getSpeed());
    }

    @Test
    public void getUserByInvalidIdTest() {
        final ResponseEntity<UserOutDto> response = super.getRestTemplate().exchange("/users/-1", HttpMethod.GET,
                this.buildRequest(null), new ParameterizedTypeReference<>() {
                });

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


    @Test
    public void addValidUserTest() {
        final SystemRole systemRoleUser = this.systemRoleRepository.findById(2L).orElseThrow(() -> new EntityNotFoundException(2L));

        final User validUserEntity = new User("Petr", "Petrov", "petrov2001",
                "qwerty", "petrov@email.com", "+375291211010", 0, systemRoleUser);
        final UserInDto validUserInDto = new UserInDto(2L, "Petr", "Petrov", "petrov2001",
                "qwerty", "petrov@email.com", "+375291211010", 0, 2L);
        final UserOutDto validUserOutDto = this.userMapper.toDto(validUserEntity);

        final ResponseEntity<UserOutDto> response = super.getRestTemplate()
                .postForEntity("/register", this.buildRequest(validUserInDto), UserOutDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(validUserOutDto, response.getBody());

        super.getUserRepository().deleteById(response.getBody().getId());
    }

    @Test
    public void addInvalidUserWithEmptyNameTest() {
        final UserInDto invalidUserInDto = new UserInDto(1L, "", "Petrov", "petrov2001",
                "qwerty", "petrov@email.com", "+375291211010", 0, 2L);

        final ResponseEntity<UserOutDto> response = super.getRestTemplate()
                .postForEntity("/register", this.buildRequest(invalidUserInDto), UserOutDto.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void addInvalidUserWithEmptySurnameTest() {
        final UserInDto invalidUserInDto = new UserInDto(1L, "", "Petrov", "petrov2001",
                "qwerty", "petrov@email.com", "+375291211010", 0, 2L);

        final ResponseEntity<UserOutDto> response = super.getRestTemplate()
                .postForEntity("/register", this.buildRequest(invalidUserInDto), UserOutDto.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void addInvalidUserWithEmptyLoginTest() {
        final UserInDto invalidUserInDto = new UserInDto(1L, "", "Petrov", "petrov2001",
                "qwerty", "petrov@email.com", "+375291211010", 0, 2L);

        final ResponseEntity<UserOutDto> response = super.getRestTemplate()
                .postForEntity("/register", this.buildRequest(invalidUserInDto), UserOutDto.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void addInvalidUserWithShortPasswordTest() {
        final UserInDto invalidUserInDto = new UserInDto(1L, "", "Petrov", "petrov2001",
                "qwerty", "petrov@email.com", "+375291211010", 0, 2L);

        final ResponseEntity<UserOutDto> response = super.getRestTemplate()
                .postForEntity("/register", this.buildRequest(invalidUserInDto), UserOutDto.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void addInvalidUserWithLongPasswordTest() {
        final UserInDto invalidUserInDto = new UserInDto(1L, "", "Petrov", "petrov2001",
                "qwerty", "petrov@email.com", "+375291211010", 0, 2L);

        final ResponseEntity<UserOutDto> response = super.getRestTemplate()
                .postForEntity("/register", this.buildRequest(invalidUserInDto), UserOutDto.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void addInvalidUserWithInvalidEmailFormatTest() {
        final UserInDto invalidUserInDto = new UserInDto(1L, "", "Petrov", "petrov2001",
                "qwerty", "petrov@email.com", "+375291211010", 0, 2L);

        final ResponseEntity<UserOutDto> response = super.getRestTemplate()
                .postForEntity("/register", this.buildRequest(invalidUserInDto), UserOutDto.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void addInvalidUserWithEmptyEmailTest() {
        final UserInDto invalidUserInDto = new UserInDto(1L, "", "Petrov", "petrov2001",
                "qwerty", "petrov@email.com", "+375291211010", 0, 2L);

        final ResponseEntity<UserOutDto> response = super.getRestTemplate()
                .postForEntity("/register", this.buildRequest(invalidUserInDto), UserOutDto.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void addInvalidUserWithEmptyPhoneTest() {
        final UserInDto invalidUserInDto = new UserInDto(1L, "", "Petrov", "petrov2001",
                "qwerty", "petrov@email.com", "+375291211010", 0, 2L);

        final ResponseEntity<UserOutDto> response = super.getRestTemplate()
                .postForEntity("/register", this.buildRequest(invalidUserInDto), UserOutDto.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void addInvalidUserWithSmallDiscountTest() {
        final UserInDto invalidUserInDto = new UserInDto(1L, "", "Petrov", "petrov2001",
                "qwerty", "petrov@email.com", "+375291211010", 0, 2L);

        final ResponseEntity<UserOutDto> response = super.getRestTemplate()
                .postForEntity("/register", this.buildRequest(invalidUserInDto), UserOutDto.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void addInvalidUserWithGreatDiscountTest() {
        final UserInDto invalidUserInDto = new UserInDto(1L, "", "Petrov", "petrov2001",
                "qwerty", "petrov@email.com", "+375291211010", 0, 2L);

        final ResponseEntity<UserOutDto> response = super.getRestTemplate()
                .postForEntity("/register", this.buildRequest(invalidUserInDto), UserOutDto.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }


    @Test
    public void updateValidUserTest() {
        final UserInDto updatedUserInDto = new UserInDto(1L, "Petr", "Petrov", "petrov2001",
                "qwerty", "petrov@email.com", "+375291211010", 0, 2L);
        final UserOutDto updatedUserOutDto = new UserOutDto(1L, "Petr", "Petrov", "petrov2001",
                "petrov@email.com", "+375291211010", 0, 2L);

        final ResponseEntity<UserOutDto> response = super.getRestTemplate()
                .exchange("/users", HttpMethod.PUT, this.buildRequest(updatedUserInDto), UserOutDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedUserOutDto, response.getBody());
    }

    @Test
    public void updateInvalidUserTest() {
        final UserInDto invalidUserInDto = new UserInDto(1L, "", "Petrov", "petrov2001",
                "qwerty", "petrov@email.com", "+375291211010", 0, 2L);

        final ResponseEntity<UserOutDto> response = super.getRestTemplate()
                .exchange("/users", HttpMethod.PUT, this.buildRequest(invalidUserInDto), UserOutDto.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void deleteExistUserTest() {
        final ResponseEntity<UserOutDto> response = super.getRestTemplate()
                .exchange("/users", HttpMethod.DELETE, this.buildRequest(null),
                        new ParameterizedTypeReference<>() {
                        });

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(super.getUserRepository().findById(super.getUserOutDto().getId()).isEmpty());

        this.before();
    }

    @Test
    public void deleteNotExistUserTest() {
        final ResponseEntity<UserOutDto> response = super.getRestTemplate()
                .exchange("/users/-1", HttpMethod.DELETE, this.buildRequest(null),
                        new ParameterizedTypeReference<>() {
                        });

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void loginUserTest() {
        final String login = "testLogin";
        final String password = "testPassword";
        final String encodedPassword = "$2a$12$X6OIQGUzbRTLuymeJxfwyOr42WDjjF0iECt5scmyWnefGCdT2iVKy";

        final SystemRole systemRoleUser = this.systemRoleRepository.findById(2L).orElseThrow(() -> new EntityNotFoundException(2L));

        final User user = new User("TestName", "TestSurname", login, encodedPassword,
                "testEmail@mail.ru", "+375290001100", 5, systemRoleUser);

        super.getUserRepository().save(user);
        final UserOutDto userOutDto = this.userMapper.toDto(user);

        final ResponseEntity<UserResponseDto> responseLogin = super.getRestTemplate()
                .postForEntity("/login", new UserLoginDto(login, password), UserResponseDto.class);

        assertEquals(userOutDto.getLogin(), responseLogin.getBody().getLogin());
        assertNotNull(responseLogin.getBody().getJwtToken());

        super.getUserRepository().deleteById(userOutDto.getId());
    }

    private HttpEntity<UserInDto> buildRequest(final UserInDto userInDto) {
        final HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", super.getToken());

        return new HttpEntity<>(userInDto, headers);
    }
}
