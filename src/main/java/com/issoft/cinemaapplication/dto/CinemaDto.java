package com.issoft.cinemaapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CinemaDto {
    private Long id;
    private String name;
    private String description;
    @Min(1)
    private Long adminId;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        final CinemaDto cinemaDto = (CinemaDto) o;
        return Objects.equals(this.name, cinemaDto.name) && Objects.equals(this.description, cinemaDto.description) && Objects.equals(this.adminId, cinemaDto.adminId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.description, this.adminId);
    }
}
