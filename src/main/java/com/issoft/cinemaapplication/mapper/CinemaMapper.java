package com.issoft.cinemaapplication.mapper;

import com.issoft.cinemaapplication.dto.CinemaDto;
import com.issoft.cinemaapplication.model.Cinema;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CinemaMapper {

    @Mapping(target = "adminId", source = "admin.id")
    CinemaDto toDto(final Cinema cinema);

    List<CinemaDto> toDto(final List<Cinema> cinemas);

    @Mapping(target = "admin.id", source = "adminId")
    Cinema toEntity(final CinemaDto cinemaDto);

    @Mappings({
            @Mapping(target = "id", source = "cinema.id"),
            @Mapping(target = "name", source = "cinemaDto.name"),
            @Mapping(target = "description", source = "cinemaDto.description"),
    })
    Cinema fillFrom(final Cinema cinema, final CinemaDto cinemaDto);
}
