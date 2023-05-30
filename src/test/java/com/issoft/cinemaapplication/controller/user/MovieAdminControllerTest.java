package com.issoft.cinemaapplication.controller.user;

import com.issoft.cinemaapplication.dto.MovieDto;
import com.issoft.cinemaapplication.mapper.MovieMapper;
import com.issoft.cinemaapplication.model.Movie;
import com.issoft.cinemaapplication.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MovieAdminControllerTest extends BaseIntegrationUserTest {
    @Autowired
    private MovieMapper movieMapper;
    @Autowired
    private MovieRepository movieRepository;

    @Test
    public void addTest() {
        final Movie movieEntity = new Movie("TestMovie", "Test movie description.");
        final MovieDto movieDto = this.movieMapper.toDto(movieEntity);

        final ResponseEntity<MovieDto> response = super.getRestTemplate()
                .postForEntity("/admin/movies", this.buildRequest(movieDto), MovieDto.class);


        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void updateTest() {
        Movie movieEntity = new Movie("TestMovie", "Test movie description.");
        movieEntity = this.movieRepository.save(movieEntity);

        final Movie updatedMovieEntity = new Movie("UpdatedTestMovie", "Updated Test movie description.");
        final MovieDto updatedMovieDto = this.movieMapper.toDto(updatedMovieEntity);

        final ResponseEntity<MovieDto> response = super.getRestTemplate()
                .exchange(String.format("/admin/tickets/%s", movieEntity.getId()), HttpMethod.PUT,
                        this.buildRequest(updatedMovieDto), MovieDto.class);


        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void deleteTest() {
        Movie movieEntity = new Movie("TestMovie", "Test movie description.");
        movieEntity = this.movieRepository.save(movieEntity);

        final ResponseEntity<MovieDto> response = super.getRestTemplate()
                .exchange(String.format("/admin/movies/%s", movieEntity.getId()), HttpMethod.DELETE, this.buildRequest(null),
                        new ParameterizedTypeReference<>() {
                        });


        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    private HttpEntity<MovieDto> buildRequest(final MovieDto movieDto) {
        final HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", super.getToken());

        return new HttpEntity<>(movieDto, headers);
    }
}
