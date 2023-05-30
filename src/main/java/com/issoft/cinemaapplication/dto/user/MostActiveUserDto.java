package com.issoft.cinemaapplication.dto.user;

import com.issoft.cinemaapplication.dto.HallDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MostActiveUserDto {
    private UserOutDto userOutDto;
    private HallDto hallDto;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        final MostActiveUserDto that = (MostActiveUserDto) o;
        return Objects.equals(this.userOutDto, that.userOutDto) && Objects.equals(this.hallDto, that.hallDto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.userOutDto, this.hallDto);
    }

    @Override
    public String toString() {
        return "MostActiveUserDto{" +
                "userOutDto=" + this.userOutDto +
                ", hallDto=" + this.hallDto +
                '}';
    }
}
