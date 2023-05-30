package com.issoft.cinemaapplication.controller.admin;

import com.issoft.cinemaapplication.dto.TicketDto;
import com.issoft.cinemaapplication.exception.EntityNotFoundException;
import com.issoft.cinemaapplication.mapper.TicketMapper;
import com.issoft.cinemaapplication.mapper.UserMapper;
import com.issoft.cinemaapplication.model.*;
import com.issoft.cinemaapplication.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class TicketControllerTest extends BaseIntegrationAdminTest {
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
    private TicketMapper ticketMapper;
    @Autowired
    private UserMapper userMapper;

    @Test
    public void buyTest() {
        final SystemRole systemRole = this.systemRoleRepository.findById(1L).orElseThrow(() -> new EntityNotFoundException(1L));

        final User userEntity = new User("Ivan", "Ivanov", "ivanov2000",
                "$2a$12$gad24vYUMb7Wdj4BE8/HO..FwlYPOhFfk5RDLZLsxu/UhHV6yKC9m", "ivanov@email.com",
                "+375291219090", 0, systemRole);

        final User admin = super.getUserRepository().save(userEntity);

        final Cinema cinema = this.cinemaRepository.save(new Cinema("October", "Cinema October", admin));

        final Hall hall = this.hallRepository.save(new Hall("Small Hall", 60, cinema));

        final Movie movie = this.movieRepository.save(new Movie("Green mile", "Movie green mile."));

        final Session session = this.sessionRepository.save(new Session(hall, movie,
                LocalDateTime.of(2022, 5, 8, 15, 0, 0),
                LocalDateTime.of(2022, 5, 8, 17, 0, 0)));

        final Ticket addedTicket = this.ticketRepository.save(
                new Ticket(session, null, 1, 1, 15, null));

        addedTicket.setUser(this.userMapper.toEntity(super.getUserOutDto()));
        addedTicket.setDateTimePurchase(LocalDateTime.now());

        final TicketDto purchasedTicketDto = this.ticketMapper.toDto(addedTicket);

        final ResponseEntity<TicketDto> response = super.getRestTemplate()
                .exchange(String.format("/tickets/%s", addedTicket.getId()), HttpMethod.PUT, this.buildRequest(), TicketDto.class);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(purchasedTicketDto.getUserId(), response.getBody().getUserId());
        assertNotNull(response.getBody().getDateTimePurchase());


        this.ticketRepository.deleteById(addedTicket.getId());
        this.sessionRepository.deleteById(session.getId());
        this.hallRepository.deleteById(hall.getId());
        this.cinemaRepository.deleteById(cinema.getId());
        this.movieRepository.deleteById(movie.getId());
        super.getUserRepository().deleteById(userEntity.getId());
    }

    @Test
    public void cancelTicketTest() {
        final SystemRole systemRoleAdmin = this.systemRoleRepository.findById(1L).orElseThrow(() -> new EntityNotFoundException(1L));

        final User userEntity = new User("Ivan", "Ivanov", "ivanov2000",
                "$2a$12$gad24vYUMb7Wdj4BE8/HO..FwlYPOhFfk5RDLZLsxu/UhHV6yKC9m", "ivanov@email.com",
                "+375291219090", 0, systemRoleAdmin);

        final User admin = super.getUserRepository().save(userEntity);

        final Cinema cinema = this.cinemaRepository.save(new Cinema("October", "Cinema October", admin));

        final Hall hall = this.hallRepository.save(new Hall("Small Hall", 60, cinema));

        final Movie movie = this.movieRepository.save(new Movie("Green mile", "Movie green mile."));

        final Session session = this.sessionRepository.save(new Session(hall, movie,
                LocalDateTime.of(2022, 5, 8, 15, 0, 0),
                LocalDateTime.of(2022, 5, 8, 17, 0, 0)));

        final User user = this.userMapper.toEntity(super.getUserOutDto());

        final Ticket purchasedTicket = this.ticketRepository.save(
                new Ticket(session, user, 1, 1, 15, LocalDateTime.now()));

        final ResponseEntity<TicketDto> response = super.getRestTemplate()
                .exchange(String.format("/tickets/%s", purchasedTicket.getId()), HttpMethod.POST, this.buildRequest(), TicketDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody().getUserId());
        assertNull(response.getBody().getDateTimePurchase());

        this.ticketRepository.deleteById(purchasedTicket.getId());
        this.sessionRepository.deleteById(session.getId());
        this.hallRepository.deleteById(hall.getId());
        this.cinemaRepository.deleteById(cinema.getId());
        this.movieRepository.deleteById(movie.getId());
        super.getUserRepository().deleteById(userEntity.getId());
    }

    private HttpEntity buildRequest() {
        final HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", super.getToken());

        return new HttpEntity<>(headers);
    }
}
