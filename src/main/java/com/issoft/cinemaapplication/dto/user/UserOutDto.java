package com.issoft.cinemaapplication.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserOutDto {
    private Long id;
    private String name;
    private String surname;
    private String login;
    private String email;
    private String phone;
    private Integer discount;
    private Long systemRoleId;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        final UserOutDto that = (UserOutDto) o;
        return Objects.equals(this.name, that.name) && Objects.equals(this.surname, that.surname) && Objects.equals(this.login, that.login) && Objects.equals(this.email, that.email) && Objects.equals(this.phone, that.phone) && Objects.equals(this.discount, that.discount) && this.systemRoleId == that.systemRoleId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.surname, this.login, this.email, this.phone, this.discount, this.systemRoleId);
    }

    @Override
    public String toString() {
        return "UserOutDto{" +
                "id=" + this.id +
                ", name='" + this.name + '\'' +
                ", surname='" + this.surname + '\'' +
                ", login='" + this.login + '\'' +
                ", email='" + this.email + '\'' +
                ", phone='" + this.phone + '\'' +
                ", discount=" + this.discount +
                ", systemRoleId=" + this.systemRoleId +
                '}';
    }
}