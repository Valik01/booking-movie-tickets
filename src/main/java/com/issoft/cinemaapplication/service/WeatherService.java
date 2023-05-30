package com.issoft.cinemaapplication.service;

import com.issoft.cinemaapplication.model.Weather;

public interface WeatherService extends BaseService<Weather> {
    void getWeather();

    Weather getLast();
}
