package com.issoft.cinemaapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HallDto {
    private Long id;
    @NotEmpty(message = "Name cannot be empty.")
    private String name;
    @Min(value = 1, message = "Capacity must be greater than or equals to 1.")
    private Integer capacity;
    @Min(1)
    private Long cinemaId;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        final HallDto hallDto = (HallDto) o;
        return Objects.equals(this.name, hallDto.name) && Objects.equals(this.capacity, hallDto.capacity) && Objects.equals(this.cinemaId, hallDto.cinemaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.capacity, this.cinemaId);
    }

    @Override
    public String toString() {
        return "HallDto{" +
                "id=" + this.id +
                ", name='" + this.name + '\'' +
                ", capacity=" + this.capacity +
                ", cinemaId=" + this.cinemaId +
                '}';
    }
}
