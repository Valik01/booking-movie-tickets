package com.issoft.cinemaapplication.service.impl;

import com.issoft.cinemaapplication.config.WeatherApiProperties;
import com.issoft.cinemaapplication.dto.weather.WeatherDto;
import com.issoft.cinemaapplication.exception.EntityNotFoundException;
import com.issoft.cinemaapplication.mapper.WeatherMapper;
import com.issoft.cinemaapplication.model.Weather;
import com.issoft.cinemaapplication.repository.WeatherRepository;
import com.issoft.cinemaapplication.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;

@Service
@EnableScheduling
@RequiredArgsConstructor
public class WeatherServiceImpl extends BaseServiceImpl<Weather> implements WeatherService {
    private final WebClient client;
    private final WeatherApiProperties weatherApiProperties;
    private final WeatherRepository weatherRepository;
    private final WeatherMapper weatherMapper;

    @Scheduled(fixedDelay = 3_600_000)
    @Override
    public void getWeather() {
        final WeatherDto weatherDto = this.client
                .get()
                .uri(String.format(this.weatherApiProperties.getUri(),
                        this.weatherApiProperties.getCity(),
                        this.weatherApiProperties.getAppid()))
                .retrieve()
                .bodyToMono(WeatherDto.class)
                .block();

        if (weatherDto != null) {
            weatherDto.setDateTime(LocalDateTime.now());
            this.weatherRepository.save(this.weatherMapper.toEntity(weatherDto));
        } else {
            throw new RuntimeException("No response received from openweathermap.");
        }
    }

    @Override
    public Weather getLast() {
        return this.weatherRepository.findFirstByOrderByDateTimeDesc()
                .orElseThrow(() -> new EntityNotFoundException("Weather not yet added."));
    }
}


