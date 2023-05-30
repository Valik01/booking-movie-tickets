package com.issoft.cinemaapplication.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserInDto {
    private Long id;
    @NotEmpty(message = "Name cannot be empty.")
    private String name;
    @NotEmpty(message = "Surname cannot be empty.")
    private String surname;
    @NotEmpty(message = "Login cannot be empty.")
    private String login;
    @Size(min = 6, max = 20, message = "Password length must be greater than or equal to 6 and less than or equal to 20.")
    private String password;
    @Email(message = "Email address must be a well-formed.")
    @NotEmpty(message = "Email cannot be empty.")
    private String email;
    @NotEmpty(message = "Phone cannot be empty.")
    private String phone;
    @Min(value = 0, message = "Discount must be greater than or equals to 0.")
    @Max(value = 100, message = "Discount must be less than or equal to 100.")
    private Integer discount;
    private Long systemRoleId;
}
