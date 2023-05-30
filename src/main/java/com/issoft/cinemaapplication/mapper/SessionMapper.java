package com.issoft.cinemaapplication.mapper;

import com.issoft.cinemaapplication.dto.SessionDto;
import com.issoft.cinemaapplication.model.Hall;
import com.issoft.cinemaapplication.model.Movie;
import com.issoft.cinemaapplication.model.Session;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SessionMapper {

    @Mappings({
            @Mapping(target = "hallId", source = "hall.id"),
            @Mapping(target = "movieId", source = "movie.id")
    })
    SessionDto toDto(final Session session);

    List<SessionDto> toDto(final List<Session> sessions);

    @Mappings({
            @Mapping(target = "hall.id", source = "hallId"),
            @Mapping(target = "movie.id", source = "movieId")
    })
    Session toEntity(final SessionDto sessionDto);

    @Mappings({
            @Mapping(target = "id", source = "session.id"),
            @Mapping(target = "movie", source = "movie"),
            @Mapping(target = "hall", source = "hall"),
            @Mapping(target = "startDateTime", source = "sessionDto.startDateTime"),
            @Mapping(target = "endDateTime", source = "sessionDto.endDateTime")
    })
    Session fillFrom(final Session session, final SessionDto sessionDto, final Movie movie, final Hall hall);
}
