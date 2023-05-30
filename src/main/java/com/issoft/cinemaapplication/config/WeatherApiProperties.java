package com.issoft.cinemaapplication.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class WeatherApiProperties {

    @Value("${weather-api.uri}")
    private String uri;
    @Value("${weather-api.appid}")
    private String appid;
    @Value("${weather-api.city}")
    private String city;
}
