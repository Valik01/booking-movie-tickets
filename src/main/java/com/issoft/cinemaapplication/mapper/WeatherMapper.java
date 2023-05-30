package com.issoft.cinemaapplication.mapper;

import com.issoft.cinemaapplication.dto.weather.WeatherDto;
import com.issoft.cinemaapplication.model.Weather;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface WeatherMapper {
    @Mappings({
            @Mapping(target = "main.temp", source = "weather.temp"),
            @Mapping(target = "main.pressure", source = "weather.pressure"),
            @Mapping(target = "main.humidity", source = "weather.humidity"),
            @Mapping(target = "wind.speed", source = "weather.speed")
    })
    WeatherDto toDto(final Weather weather);

    @Mappings({
            @Mapping(target = "temp", source = "weatherDto.main.temp"),
            @Mapping(target = "pressure", source = "weatherDto.main.pressure"),
            @Mapping(target = "humidity", source = "weatherDto.main.humidity"),
            @Mapping(target = "speed", source = "weatherDto.wind.speed")
    })
    Weather toEntity(final WeatherDto weatherDto);
}
