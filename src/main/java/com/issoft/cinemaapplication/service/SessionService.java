package com.issoft.cinemaapplication.service;

import com.issoft.cinemaapplication.model.Session;

import java.util.List;

public interface SessionService extends BaseService<Session> {
    List<Session> findSessionByHallId(final Long hallId);
}
