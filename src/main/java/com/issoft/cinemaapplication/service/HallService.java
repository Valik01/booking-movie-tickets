package com.issoft.cinemaapplication.service;

import com.issoft.cinemaapplication.model.Hall;

import java.util.List;

public interface HallService extends BaseService<Hall> {
    List<Hall> findAllByCinemaId(final Long cinemaId);
}
