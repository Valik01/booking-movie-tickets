package com.issoft.cinemaapplication.dto.weather;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WeatherWindDto {
    private Float speed;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        final WeatherWindDto that = (WeatherWindDto) o;
        return Objects.equals(this.speed, that.speed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.speed);
    }

    @Override
    public String toString() {
        return "WeatherWindDto{" +
                "speed=" + this.speed +
                '}';
    }
}
