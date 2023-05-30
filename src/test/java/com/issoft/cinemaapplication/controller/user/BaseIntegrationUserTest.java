package com.issoft.cinemaapplication.controller.user;

import com.issoft.cinemaapplication.dto.user.UserInDto;
import com.issoft.cinemaapplication.dto.user.UserLoginDto;
import com.issoft.cinemaapplication.dto.user.UserOutDto;
import com.issoft.cinemaapplication.dto.user.UserResponseDto;
import com.issoft.cinemaapplication.mock.MockBeanTestExecutionListener;
import com.issoft.cinemaapplication.repository.UserRepository;
import lombok.Getter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;

import java.util.Objects;

@Getter
@TestExecutionListeners(
        value = {MockBeanTestExecutionListener.class},
        mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class BaseIntegrationUserTest {
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private UserRepository userRepository;

    private UserOutDto userOutDto;
    private String token;

    @BeforeEach
    public void before() {
        final String login = "test";
        final String password = "test_test";

        final ResponseEntity<UserOutDto> responseRegister = this.restTemplate.postForEntity("/register",
                new UserInDto(10L, "TestName", "TestSurname", login, password,
                        "testEmail@mail.ru", "+375290001100", 5, 2L), UserOutDto.class);
        this.userOutDto = responseRegister.getBody();

        final ResponseEntity<UserResponseDto> responseLogin = this.restTemplate.postForEntity(
                "/login", new UserLoginDto(login, password), UserResponseDto.class);
        this.token = Objects.requireNonNull(responseLogin.getBody()).getJwtToken();
    }

    @AfterEach
    public void after() {
        this.userRepository.deleteById(this.userOutDto.getId());
    }
}


