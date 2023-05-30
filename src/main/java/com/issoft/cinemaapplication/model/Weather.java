package com.issoft.cinemaapplication.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "weather")
public class Weather extends BaseEntity {
    @Column(name = "city")
    private String name;
    @Column(name = "temperature")
    private Float temp;
    private Float pressure;
    private Float humidity;
    @Column(name = "wind_speed")
    private Float speed;
    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        final Weather that = (Weather) o;
        return Objects.equals(this.name, that.name) && Objects.equals(this.temp, that.temp) && Objects.equals(this.pressure, that.pressure) && Objects.equals(this.humidity, that.humidity) && Objects.equals(this.speed, that.speed) && Objects.equals(this.dateTime, that.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.temp, this.pressure, this.humidity, this.speed, this.dateTime);
    }

    @Override
    public String toString() {
        return "WeatherEntity{" +
                "name='" + this.name + '\'' +
                ", temp=" + this.temp +
                ", pressure=" + this.pressure +
                ", humidity=" + this.humidity +
                ", speed=" + this.speed +
                ", dateTime=" + this.dateTime +
                '}';
    }
}
