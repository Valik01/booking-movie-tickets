package com.issoft.cinemaapplication.service;

import com.issoft.cinemaapplication.dto.user.MostActiveUserDto;
import com.issoft.cinemaapplication.model.User;

import java.util.Optional;

public interface UserService extends BaseService<User> {
    MostActiveUserDto findMostActive(final Long hallId);

    Optional<User> findByLogin(final String login);
}

