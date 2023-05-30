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
@Table(name = "cinemas")
public class Cinema extends BaseEntity {
    private String name;
    private String description;
    @ManyToOne
    @JoinColumn(name = "admin_id")
    private User admin;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        final Cinema that = (Cinema) o;
        return Objects.equals(this.name, that.name) && Objects.equals(this.description, that.description) && Objects.equals(this.admin, that.admin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.description, this.admin);
    }

    @Override
    public String toString() {
        return "Cinema{" +
                "name='" + this.name + '\'' +
                ", description='" + this.description + '\'' +
                ", admin=" + this.admin +
                '}';
    }
}
