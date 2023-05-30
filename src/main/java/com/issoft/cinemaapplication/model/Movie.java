package com.issoft.cinemaapplication.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "movies")
public class Movie extends BaseEntity {
    private String name;
    private String description;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        final Movie movie = (Movie) o;
        return Objects.equals(this.name, movie.name) && Objects.equals(this.description, movie.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.description);
    }

    @Override
    public String toString() {
        return "Movie{" +
                "name='" + this.name + '\'' +
                ", description='" + this.description + '\'' +
                '}';
    }
}
