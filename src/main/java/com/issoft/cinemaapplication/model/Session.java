package com.issoft.cinemaapplication.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sessions")
public class Session extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "hall_id")
    private Hall hall;
    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;
    @Column(name = "start_date_time")
    private LocalDateTime startDateTime;
    @Column(name = "end_date_time")
    private LocalDateTime endDateTime;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        final Session session = (Session) o;
        return Objects.equals(this.hall, session.hall) && Objects.equals(this.movie, session.movie) && Objects.equals(this.startDateTime, session.startDateTime) && Objects.equals(this.endDateTime, session.endDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.hall, this.movie, this.startDateTime, this.endDateTime);
    }

    @Override
    public String toString() {
        return "Session{" +
                "hall=" + this.hall +
                ", movie=" + this.movie +
                ", startDateTime=" + this.startDateTime +
                ", endDateTime=" + this.endDateTime +
                '}';
    }
}
