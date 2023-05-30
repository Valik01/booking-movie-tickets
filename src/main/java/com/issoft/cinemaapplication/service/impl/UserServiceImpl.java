package com.issoft.cinemaapplication.service.impl;

import com.issoft.cinemaapplication.dto.user.MostActiveUserDto;
import com.issoft.cinemaapplication.exception.EntityNotFoundException;
import com.issoft.cinemaapplication.exception.MostActiveUserNotFoundException;
import com.issoft.cinemaapplication.mapper.HallMapper;
import com.issoft.cinemaapplication.mapper.UserMapper;
import com.issoft.cinemaapplication.model.Hall;
import com.issoft.cinemaapplication.model.User;
import com.issoft.cinemaapplication.repository.UserRepository;
import com.issoft.cinemaapplication.service.HallService;
import com.issoft.cinemaapplication.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {
    private final HallService hallService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final HallMapper hallMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public MostActiveUserDto findMostActive(final Long hallId) {
        final User mostActiveUser =
                this.userRepository.findMostActiveInHallForLastMonth(hallId, LocalDateTime.now().minusMonths(1), LocalDateTime.now())
                        .stream()
                        .findFirst()
                        .orElseThrow(() -> new MostActiveUserNotFoundException("Most active user not found"));

        final Hall hall = this.hallService.findById(hallId).orElseThrow(() -> new EntityNotFoundException(hallId));

        return new MostActiveUserDto(this.userMapper.toDto(mostActiveUser), this.hallMapper.toDto(hall));
    }

    @Override
    public User save(final User userEntity) {
        userEntity.setPassword(this.passwordEncoder.encode(userEntity.getPassword()));
        return super.save(userEntity);
    }

    @Override
    public Optional<User> findByLogin(final String login) {
        return this.userRepository.findByLogin(login);
    }
}
