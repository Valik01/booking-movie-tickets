package com.issoft.cinemaapplication.mapper;

import com.issoft.cinemaapplication.dto.HallDto;
import com.issoft.cinemaapplication.model.Hall;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HallMapper {

    @Mapping(target = "cinemaId", source = "cinema.id")
    HallDto toDto(final Hall hall);

    List<HallDto> toDto(final List<Hall> halls);

    @Mapping(target = "cinema.id", source = "cinemaId")
    Hall toEntity(final HallDto hallDto);

    @Mappings({
            @Mapping(target = "id", source = "hall.id"),
            @Mapping(target = "name", source = "hallDto.name"),
            @Mapping(target = "capacity", source = "hallDto.capacity")
    })
    Hall fillFrom(final Hall hall, final HallDto hallDto);
}
