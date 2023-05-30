package com.issoft.cinemaapplication.service.impl;

import com.issoft.cinemaapplication.model.Session;
import com.issoft.cinemaapplication.repository.SessionRepository;
import com.issoft.cinemaapplication.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl extends BaseServiceImpl<Session> implements SessionService {
    private final SessionRepository sessionRepository;

    @Override
    public List<Session> findSessionByHallId(final Long hallId) {
        return this.sessionRepository.findSessionsByHallId(hallId);
    }
}
