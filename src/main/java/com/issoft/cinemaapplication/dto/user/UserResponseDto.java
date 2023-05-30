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
public class UserResponseDto {
    private String login;
    private String jwtToken;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        final UserResponseDto that = (UserResponseDto) o;
        return Objects.equals(this.login, that.login) && Objects.equals(this.jwtToken, that.jwtToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.login, this.jwtToken);
    }

    @Override
    public String toString() {
        return "UserResponseDto{" +
                "login='" + this.login + '\'' +
                ", jwtToken='" + this.jwtToken + '\'' +
                '}';
    }
}