package com.issoft.cinemaapplication.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "halls")
public class Hall extends BaseEntity {
    private String name;
    private Integer capacity;
    @ManyToOne
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        final Hall that = (Hall) o;
        return Objects.equals(this.name, that.name) && Objects.equals(this.capacity, that.capacity) && Objects.equals(this.cinema, that.cinema);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.capacity, this.cinema);
    }

    @Override
    public String toString() {
        return "Hall{" +
                "name='" + this.name + '\'' +
                ", capacity=" + this.capacity +
                ", cinema=" + this.cinema +
                '}';
    }
}
