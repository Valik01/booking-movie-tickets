package com.issoft.cinemaapplication.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SessionDto {
    private Long id;
    @Min(1)
    private Long hallId;
    @Min(1)
    private Long movieId;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime startDateTime;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime endDateTime;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        final SessionDto that = (SessionDto) o;
        return Objects.equals(this.hallId, that.hallId) && Objects.equals(this.movieId, that.movieId) && Objects.equals(this.startDateTime, that.startDateTime) && Objects.equals(this.endDateTime, that.endDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.hallId, this.movieId, this.startDateTime, this.endDateTime);
    }

    @Override
    public String toString() {
        return "SessionDto{" +
                "id=" + this.id +
                ", hallId=" + this.hallId +
                ", movieId=" + this.movieId +
                ", startDateTime=" + this.startDateTime +
                ", endDateTime=" + this.endDateTime +
                '}';
    }
}
