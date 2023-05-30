package com.issoft.cinemaapplication.controller.admin;

import com.issoft.cinemaapplication.dto.SessionDto;
import com.issoft.cinemaapplication.exception.EntityNotFoundException;
import com.issoft.cinemaapplication.mapper.SessionMapper;
import com.issoft.cinemaapplication.model.*;
import com.issoft.cinemaapplication.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SessionAdminControllerTest extends BaseIntegrationAdminTest {
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private HallRepository hallRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private CinemaRepository cinemaRepository;
    @Autowired
    private SystemRoleRepository systemRoleRepository;
    @Autowired
    private SessionMapper sessionMapper;

    @Test
    public void addSessionTest() {
        final SystemRole systemRole = this.systemRoleRepository.findById(1L).orElseThrow(() -> new EntityNotFoundException(1L));

        final User userEntity = new User("Ivan", "Ivanov", "ivanov2000",
                "$2a$12$gad24vYUMb7Wdj4BE8/HO..FwlYPOhFfk5RDLZLsxu/UhHV6yKC9m", "ivanov@email.com",
                "+375291219090", 0, systemRole);

        final User admin = super.getUserRepository().save(userEntity);

        final Cinema cinema = this.cinemaRepository.save(new Cinema("October", "Cinema October", admin));

        final Hall hall = this.hallRepository.save(new Hall("Small Hall", 60, cinema));

        final Movie movie = this.movieRepository.save(new Movie("Green mile", "Movie green mile."));

        final Session session = new Session(hall, movie,
                LocalDateTime.of(2022, 5, 8, 15, 0, 0),
                LocalDateTime.of(2022, 5, 8, 17, 0, 0));

        final SessionDto sessionDto = this.sessionMapper.toDto(session);

        final HttpEntity<SessionDto> httpEntity = this.buildRequest(sessionDto);

        final ResponseEntity<SessionDto> response = super.getRestTemplate()
                .postForEntity("/admin/sessions", httpEntity, SessionDto.class);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sessionDto, response.getBody());


        this.sessionRepository.deleteById(response.getBody().getId());
        this.hallRepository.deleteById(hall.getId());
        this.cinemaRepository.deleteById(cinema.getId());
        super.getUserRepository().deleteById(admin.getId());
        this.movieRepository.deleteById(movie.getId());
    }

    @Test
    public void addInvalidSessionTest() {
        final Movie movie = this.movieRepository.save(new Movie("Green mile", "Movie green mile."));

        final Session session = new Session(null, movie,
                LocalDateTime.of(2022, 5, 8, 15, 0, 0),
                LocalDateTime.of(2022, 5, 8, 17, 0, 0));

        final SessionDto sessionDto = this.sessionMapper.toDto(session);

        final HttpEntity<SessionDto> httpEntity = this.buildRequest(sessionDto);

        final ResponseEntity<SessionDto> response = super.getRestTemplate()
                .postForEntity("/admin/sessions", httpEntity, SessionDto.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void updateSession() {
        final SystemRole systemRole = this.systemRoleRepository.findById(1L).orElseThrow(() -> new EntityNotFoundException(1L));

        final User userEntity = new User("Ivan", "Ivanov", "ivanov2000",
                "$2a$12$gad24vYUMb7Wdj4BE8/HO..FwlYPOhFfk5RDLZLsxu/UhHV6yKC9m", "ivanov@email.com",
                "+375291219090", 0, systemRole);

        final User admin = super.getUserRepository().save(userEntity);

        final Cinema cinema = this.cinemaRepository.save(new Cinema("October", "Cinema October", admin));

        final Hall hall = this.hallRepository.save(new Hall("Small Hall", 60, cinema));

        final Movie movie = this.movieRepository.save(new Movie("Green mile", "Movie green mile."));

        final Session sessionEntity = new Session(hall, movie,
                LocalDateTime.of(2022, 5, 8, 15, 0, 0),
                LocalDateTime.of(2022, 5, 8, 17, 0, 0));

        final Session addedSession = this.sessionRepository.save(sessionEntity);

        final Session updatedSession = new Session(hall, movie,
                LocalDateTime.of(2022, 5, 8, 17, 0, 0),
                LocalDateTime.of(2022, 5, 8, 19, 0, 0));

        final SessionDto updatedSessionDto = this.sessionMapper.toDto(updatedSession);

        final HttpEntity<SessionDto> httpEntity = this.buildRequest(updatedSessionDto);

        final ResponseEntity<SessionDto> response = super.getRestTemplate()
                .exchange(String.format("/admin/sessions/%s", addedSession.getId()), HttpMethod.PUT, httpEntity, SessionDto.class);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedSessionDto, response.getBody());


        this.sessionRepository.deleteById(addedSession.getId());
        this.hallRepository.deleteById(hall.getId());
        this.cinemaRepository.deleteById(cinema.getId());
        super.getUserRepository().deleteById(admin.getId());
        this.movieRepository.deleteById(movie.getId());
    }

    @Test
    public void deleteSession() {
        final SystemRole systemRole = this.systemRoleRepository.findById(1L).orElseThrow(() -> new EntityNotFoundException(1L));

        final User userEntity = new User("Ivan", "Ivanov", "ivanov2000",
                "$2a$12$gad24vYUMb7Wdj4BE8/HO..FwlYPOhFfk5RDLZLsxu/UhHV6yKC9m", "ivanov@email.com",
                "+375291219090", 0, systemRole);

        final User admin = super.getUserRepository().save(userEntity);

        final Cinema cinema = this.cinemaRepository.save(new Cinema("October", "Cinema October", admin));

        final Hall hall = this.hallRepository.save(new Hall("Small Hall", 60, cinema));

        final Movie movie = this.movieRepository.save(new Movie("Green mile", "Movie green mile."));

        final Long addedSessionId = this.sessionRepository.save(new Session(hall, movie,
                        LocalDateTime.of(2022, 5, 8, 15, 0, 0),
                        LocalDateTime.of(2022, 5, 8, 17, 0, 0)))
                .getId();

        final HttpEntity<SessionDto> httpEntity = this.buildRequest(null);

        final ResponseEntity<SessionDto> response = super.getRestTemplate()
                .exchange(String.format("/admin/sessions/%s", addedSessionId), HttpMethod.DELETE, httpEntity,
                        new ParameterizedTypeReference<>() {
                        });


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(this.sessionRepository.findById(addedSessionId).isEmpty());


        this.hallRepository.deleteById(hall.getId());
        this.cinemaRepository.deleteById(cinema.getId());
        super.getUserRepository().deleteById(admin.getId());
        this.movieRepository.deleteById(movie.getId());
    }

    private HttpEntity<SessionDto> buildRequest(final SessionDto sessionDto) {
        final HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", super.getToken());

        return new HttpEntity<>(sessionDto, headers);
    }
}
