package com.issoft.cinemaapplication.dto.user;

import com.issoft.cinemaapplication.dto.weather.WeatherDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserPageDto {
    private UserOutDto userOutDto;
    private WeatherDto weatherDto;
}
