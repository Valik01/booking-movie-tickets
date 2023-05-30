package com.issoft.cinemaapplication.dto.weather;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WeatherDto {
    private String name;
    private WeatherMainDto main;
    private WeatherWindDto wind;
    private LocalDateTime dateTime;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        final WeatherDto that = (WeatherDto) o;
        return Objects.equals(this.name, that.name) && Objects.equals(this.main, that.main) && Objects.equals(this.wind, that.wind) && Objects.equals(this.dateTime, that.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.main, this.wind, this.dateTime);
    }

    @Override
    public String toString() {
        return "WeatherDto{" +
                "name='" + this.name + '\'' +
                ", main=" + this.main +
                ", wind=" + this.wind +
                ", dateTime=" + this.dateTime +
                '}';
    }
}
