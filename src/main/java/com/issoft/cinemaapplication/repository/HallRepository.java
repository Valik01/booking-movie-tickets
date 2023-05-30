package com.issoft.cinemaapplication.repository;

import com.issoft.cinemaapplication.model.Hall;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HallRepository extends BaseRepository<Hall> {
    List<Hall> findAllByCinemaId(final Long cinemaId);
}
