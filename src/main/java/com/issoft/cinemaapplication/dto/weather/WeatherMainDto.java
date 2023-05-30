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
public class WeatherMainDto {
    private Float temp;
    private Float pressure;
    private Float humidity;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        final WeatherMainDto that = (WeatherMainDto) o;
        return Objects.equals(this.temp, that.temp) && Objects.equals(this.pressure, that.pressure) && Objects.equals(this.humidity, that.humidity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.temp, this.pressure, this.humidity);
    }

    @Override
    public String toString() {
        return "WeatherMainDto{" +
                "temp=" + this.temp +
                ", pressure=" + this.pressure +
                ", humidity=" + this.humidity +
                '}';
    }
}
