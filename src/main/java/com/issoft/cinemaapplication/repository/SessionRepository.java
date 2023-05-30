package com.issoft.cinemaapplication.repository;

import com.issoft.cinemaapplication.model.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionRepository extends BaseRepository<Session> {
    List<Session> findSessionsByHallId(final Long hallId);
}
