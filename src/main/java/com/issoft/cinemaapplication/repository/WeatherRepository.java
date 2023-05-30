package com.issoft.cinemaapplication.repository;

import com.issoft.cinemaapplication.model.Weather;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WeatherRepository extends BaseRepository<Weather> {
    Optional<Weather> findFirstByOrderByDateTimeDesc();
}
