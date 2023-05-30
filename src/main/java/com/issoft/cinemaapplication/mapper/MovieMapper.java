package com.issoft.cinemaapplication.mapper;

import com.issoft.cinemaapplication.dto.MovieDto;
import com.issoft.cinemaapplication.model.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    MovieDto toDto(final Movie movie);

    List<MovieDto> toDto(final List<Movie> movies);

    Movie toEntity(final MovieDto movieDto);

    @Mappings({
            @Mapping(target = "id", source = "movie.id"),
            @Mapping(target = "name", source = "movieDto.name"),
            @Mapping(target = "description", source = "movieDto.description"),
    })
    Movie fillFrom(final Movie movie, final MovieDto movieDto);
}
