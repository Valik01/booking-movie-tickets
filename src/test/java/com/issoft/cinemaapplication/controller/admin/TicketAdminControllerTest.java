package com.issoft.cinemaapplication.controller.admin;

import com.issoft.cinemaapplication.dto.TicketDto;
import com.issoft.cinemaapplication.exception.EntityNotFoundException;
import com.issoft.cinemaapplication.mapper.TicketMapper;
import com.issoft.cinemaapplication.model.*;
import com.issoft.cinemaapplication.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TicketAdminControllerTest extends BaseIntegrationAdminTest {
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

    @Test
    public void addTicketTest() {
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

        final Ticket ticket = new Ticket(session, null, 1, 1, 15, null);

        final TicketDto ticketDto = this.ticketMapper.toDto(ticket);

        final ResponseEntity<TicketDto> response = super.getRestTemplate()
                .postForEntity("/admin/tickets", this.buildRequest(ticketDto), TicketDto.class);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ticketDto, response.getBody());


        this.ticketRepository.deleteById(response.getBody().getId());
        this.sessionRepository.deleteById(session.getId());
        this.hallRepository.deleteById(hall.getId());
        this.cinemaRepository.deleteById(cinema.getId());
        super.getUserRepository().deleteById(admin.getId());
        this.movieRepository.deleteById(movie.getId());
    }

    @Test
    public void updateTicketTest() {
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
                new Ticket(session, null, 1, 1, 15, LocalDateTime.now()));

        final Ticket updatedTicket = new Ticket(session, null, 2, 2, 20, null);

        final TicketDto updatedTicketDto = this.ticketMapper.toDto(updatedTicket);

        final ResponseEntity<TicketDto> response = super.getRestTemplate()
                .exchange(String.format("/admin/tickets/%s", addedTicket.getId()), HttpMethod.PUT,
                        this.buildRequest(updatedTicketDto), TicketDto.class);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedTicketDto, response.getBody());


        this.ticketRepository.deleteById(addedTicket.getId());
        this.sessionRepository.deleteById(session.getId());
        this.hallRepository.deleteById(hall.getId());
        this.cinemaRepository.deleteById(cinema.getId());
        super.getUserRepository().deleteById(admin.getId());
        this.movieRepository.deleteById(movie.getId());
    }

    @Test
    public void deleteTicketTest() {
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

        final Long addedTicketId = this.ticketRepository.save(
                new Ticket(session, null, 1, 1, 15, LocalDateTime.now())).getId();

        final ResponseEntity<TicketDto> response = super.getRestTemplate()
                .exchange(String.format("/admin/tickets/%s", addedTicketId), HttpMethod.DELETE,
                        this.buildRequest(null), new ParameterizedTypeReference<>() {
                        });


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(this.ticketRepository.findById(addedTicketId).isEmpty());


        this.sessionRepository.deleteById(session.getId());
        this.hallRepository.deleteById(hall.getId());
        this.cinemaRepository.deleteById(cinema.getId());
        super.getUserRepository().deleteById(admin.getId());
        this.movieRepository.deleteById(movie.getId());
    }

    private HttpEntity<TicketDto> buildRequest(final TicketDto ticketDto) {
        final HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", super.getToken());

        return new HttpEntity<>(ticketDto, headers);
    }
}
