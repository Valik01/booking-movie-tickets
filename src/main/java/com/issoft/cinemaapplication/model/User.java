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
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity {
    private String name;
    private String surname;
    private String login;
    private String password;
    private String email;
    private String phone;
    private Integer discount;
    @ManyToOne
    @JoinColumn(name = "system_role")
    private SystemRole systemRole;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        final User that = (User) o;
        return Objects.equals(this.name, that.name) && Objects.equals(this.surname, that.surname) && Objects.equals(this.login, that.login) && Objects.equals(this.password, that.password) && Objects.equals(this.email, that.email) && Objects.equals(this.phone, that.phone) && Objects.equals(this.discount, that.discount) && this.systemRole == that.systemRole;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.surname, this.login, this.password, this.email, this.phone, this.discount, this.systemRole);
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "name='" + this.name + '\'' +
                ", surname='" + this.surname + '\'' +
                ", login='" + this.login + '\'' +
                ", password='" + this.password + '\'' +
                ", email='" + this.email + '\'' +
                ", phone='" + this.phone + '\'' +
                ", discount=" + this.discount +
                ", systemRole=" + this.systemRole +
                '}';
    }
}
