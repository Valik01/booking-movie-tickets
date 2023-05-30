package com.issoft.cinemaapplication.controller.admin;

import com.issoft.cinemaapplication.dto.user.MostActiveUserDto;
import com.issoft.cinemaapplication.dto.user.UserInDto;
import com.issoft.cinemaapplication.dto.user.UserOutDto;
import com.issoft.cinemaapplication.exception.EntityNotFoundException;
import com.issoft.cinemaapplication.mapper.HallMapper;
import com.issoft.cinemaapplication.mapper.UserMapper;
import com.issoft.cinemaapplication.model.*;
import com.issoft.cinemaapplication.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserAdminControllerTest extends BaseIntegrationAdminTest {
    @Autowired
    private HallRepository hallRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private SystemRoleRepository systemRoleRepository;
    @Autowired
    private CinemaRepository cinemaRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private HallMapper hallMapper;

    @Test
    public void getAllTest() {
        final SystemRole systemRole = this.systemRoleRepository.findById(2L).orElseThrow(() -> new EntityNotFoundException(2L));

        final Long userIvanId = super.getUserRepository().save(new User("Ivan", "Ivanov", "ivanov2000", "$2a$12$JdMG5zJI.WAVomGfO4kPjew9ypyYQ9pEi9rhuUcZCzFJQu0kqkAR2",
                "ivanov@email.com", "+375291219090", 0, systemRole)).getId();
        final Long userPetrId = super.getUserRepository().save(new User("Petr", "Petrov", "petrov2001", "$2a$12$gydowYgk.hcuLumeHNEo7.FzgSP58rd3hbAX1cf/HEYntGOqzzdW6",
                "petrov@email.com", "+375291211010", 0, systemRole)).getId();

        final ResponseEntity<List<UserOutDto>> response = super.getRestTemplate().exchange("/admin/users", HttpMethod.GET, this.buildRequest(),
                new ParameterizedTypeReference<>() {
                });

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(3, Objects.requireNonNull(response.getBody()).size());

        super.getUserRepository().deleteById(userIvanId);
        super.getUserRepository().deleteById(userPetrId);
    }

    @Test
    public void getMostActiveTest() {
        final SystemRole systemRoleAdmin = this.systemRoleRepository.findById(1L).orElseThrow(() -> new EntityNotFoundException(1L));
        final SystemRole systemRoleUser = this.systemRoleRepository.findById(2L).orElseThrow(() -> new EntityNotFoundException(2L));

        final User userIvan = super.getUserRepository().save(new User("Ivan", "Ivanov",
                "ivanov2000", "$2a$12$JdMG5zJI.WAVomGfO4kPjew9ypyYQ9pEi9rhuUcZCzFJQu0kqkAR2",
                "ivanov@email.com", "+375291219090", 0, systemRoleUser));
        final User userPetr = super.getUserRepository().save(new User("Petr", "Petrov",
                "petrov2001", "$2a$12$gydowYgk.hcuLumeHNEo7.FzgSP58rd3hbAX1cf/HEYntGOqzzdW6",
                "petrov@email.com", "+375291211010", 0, systemRoleUser));

        final User userEntity = new User("Vasia", "Pupkin", "admin",
                "$2a$12$gad24vYUMb7Wdj4BE8/HO..FwlYPOhFfk5RDLZLsxu/UhHV6yKC9m", "admin@email.com",
                "+375291219090", 0, systemRoleAdmin);

        final User admin = super.getUserRepository().save(userEntity);
        final Cinema cinema = this.cinemaRepository.save(new Cinema("October", "Cinema October", admin));

        final Hall hall = this.hallRepository.save(new Hall("Small Hall", 60, cinema));

        final Movie movie = this.movieRepository.save(new Movie("Green mile", "Movie green mile."));

        final Session session = this.sessionRepository.save(new Session(hall, movie,
                LocalDateTime.of(2022, 5, 8, 15, 0, 0),
                LocalDateTime.of(2022, 5, 8, 17, 0, 0)));

        final Long firstTicketId = this.ticketRepository.save(new Ticket(session, userIvan, 1, 1, 10, LocalDateTime.now())).getId();
        final Long secondTicketId = this.ticketRepository.save(new Ticket(session, userPetr, 1, 2, 10, LocalDateTime.now())).getId();
        final Long thirdTicketId = this.ticketRepository.save(new Ticket(session, userPetr, 1, 3, 10, LocalDateTime.now())).getId();

        final MostActiveUserDto mostActiveUserDto =
                new MostActiveUserDto(this.userMapper.toDto(userPetr), this.hallMapper.toDto(hall));

        final ResponseEntity<MostActiveUserDto> response = super.getRestTemplate()
                .exchange(
                        String.format("/admin/users/most-active/%s", hall.getId()),
                        HttpMethod.GET,
                        this.buildRequest(),
                        new ParameterizedTypeReference<>() {
                        });


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mostActiveUserDto, response.getBody());


        this.ticketRepository.deleteById(firstTicketId);
        this.ticketRepository.deleteById(secondTicketId);
        this.ticketRepository.deleteById(thirdTicketId);

        super.getUserRepository().deleteById(userIvan.getId());
        super.getUserRepository().deleteById(userPetr.getId());

        this.sessionRepository.deleteById(session.getId());
        this.hallRepository.deleteById(hall.getId());
        this.movieRepository.deleteById(movie.getId());
    }

    private HttpEntity<UserInDto> buildRequest() {
        final HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", super.getToken());

        return new HttpEntity<>(headers);
    }
}
