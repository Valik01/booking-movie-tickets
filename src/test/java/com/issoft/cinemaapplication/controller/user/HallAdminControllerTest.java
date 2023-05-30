package com.issoft.cinemaapplication.controller.user;

import com.issoft.cinemaapplication.dto.HallDto;
import com.issoft.cinemaapplication.dto.TicketDto;
import com.issoft.cinemaapplication.exception.EntityNotFoundException;
import com.issoft.cinemaapplication.mapper.HallMapper;
import com.issoft.cinemaapplication.model.Cinema;
import com.issoft.cinemaapplication.model.Hall;
import com.issoft.cinemaapplication.model.SystemRole;
import com.issoft.cinemaapplication.model.User;
import com.issoft.cinemaapplication.repository.CinemaRepository;
import com.issoft.cinemaapplication.repository.HallRepository;
import com.issoft.cinemaapplication.repository.SystemRoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HallAdminControllerTest extends BaseIntegrationUserTest {
    @Autowired
    private HallMapper hallMapper;
    @Autowired
    private SystemRoleRepository systemRoleRepository;
    @Autowired
    private HallRepository hallRepository;
    @Autowired
    private CinemaRepository cinemaRepository;

    @Test
    public void addTest() {
        final SystemRole systemRole = this.systemRoleRepository.findById(1L).orElseThrow(() -> new EntityNotFoundException(1L));

        final User userEntity = new User("Ivan", "Ivanov", "ivanov2000",
                "$2a$12$gad24vYUMb7Wdj4BE8/HO..FwlYPOhFfk5RDLZLsxu/UhHV6yKC9m", "ivanov@email.com",
                "+375291219090", 0, systemRole);

        final User admin = super.getUserRepository().save(userEntity);

        final Cinema cinema = this.cinemaRepository.save(new Cinema("October", "Cinema October", admin));
        final Hall hallEntity = new Hall("TestHall", 50, cinema);
        final HallDto hallDto = this.hallMapper.toDto(hallEntity);

        final ResponseEntity<TicketDto> response = super.getRestTemplate()
                .postForEntity("/admin/halls", this.buildRequest(hallDto), TicketDto.class);


        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());


        this.cinemaRepository.deleteById(cinema.getId());
        this.getUserRepository().deleteById(admin.getId());
    }

    @Test
    public void updateTest() {
        final SystemRole systemRole = this.systemRoleRepository.findById(1L).orElseThrow(() -> new EntityNotFoundException(1L));

        final User userEntity = new User("Ivan", "Ivanov", "ivanov2000",
                "$2a$12$gad24vYUMb7Wdj4BE8/HO..FwlYPOhFfk5RDLZLsxu/UhHV6yKC9m", "ivanov@email.com",
                "+375291219090", 0, systemRole);

        final User admin = super.getUserRepository().save(userEntity);

        final Cinema cinema = this.cinemaRepository.save(new Cinema("October", "Cinema October", admin));
        Hall hallEntity = new Hall("TestHall", 50, cinema);
        hallEntity = this.hallRepository.save(hallEntity);

        final Hall updatedHallEntity = new Hall("UpdatedTestHall", 60, cinema);
        final HallDto updatedHallDto = this.hallMapper.toDto(updatedHallEntity);

        final ResponseEntity<HallDto> response = super.getRestTemplate()
                .exchange(String.format("/admin/halls/%s", hallEntity.getId()), HttpMethod.PUT,
                        this.buildRequest(updatedHallDto), HallDto.class);


        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());


        this.cinemaRepository.deleteById(cinema.getId());
        this.getUserRepository().deleteById(admin.getId());
    }

    @Test
    public void deleteTest() {
        final SystemRole systemRole = this.systemRoleRepository.findById(1L).orElseThrow(() -> new EntityNotFoundException(1L));

        final User userEntity = new User("Ivan", "Ivanov", "ivanov2000",
                "$2a$12$gad24vYUMb7Wdj4BE8/HO..FwlYPOhFfk5RDLZLsxu/UhHV6yKC9m", "ivanov@email.com",
                "+375291219090", 0, systemRole);

        final User admin = super.getUserRepository().save(userEntity);

        final Cinema cinema = this.cinemaRepository.save(new Cinema("October", "Cinema October", admin));
        Hall hallEntity = new Hall("TestHall", 50, cinema);
        hallEntity = this.hallRepository.save(hallEntity);

        final ResponseEntity<HallDto> response = super.getRestTemplate()
                .exchange(String.format("/admin/movies/%s", hallEntity.getId()), HttpMethod.DELETE, this.buildRequest(null),
                        new ParameterizedTypeReference<>() {
                        });


        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());


        this.cinemaRepository.deleteById(cinema.getId());
        this.getUserRepository().deleteById(admin.getId());
    }

    private HttpEntity<HallDto> buildRequest(final HallDto hallDto) {
        final HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", super.getToken());

        return new HttpEntity<>(hallDto, headers);
    }
}
