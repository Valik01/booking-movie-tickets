package com.issoft.cinemaapplication.service.impl;

import com.issoft.cinemaapplication.model.Hall;
import com.issoft.cinemaapplication.repository.HallRepository;
import com.issoft.cinemaapplication.service.HallService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HallServiceImpl extends BaseServiceImpl<Hall> implements HallService {
    private final HallRepository hallRepository;

    @Override
    public List<Hall> findAllByCinemaId(final Long cinemaId) {
        return this.hallRepository.findAllByCinemaId(cinemaId);
    }
}
