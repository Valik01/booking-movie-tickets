package com.issoft.cinemaapplication.service;

import com.issoft.cinemaapplication.model.BaseEntity;

import java.util.List;
import java.util.Optional;

public interface BaseService<T extends BaseEntity> {

    List<T> findAll();

    Optional<T> findById(final Long id);

    T save(final T entity);

    void deleteById(final Long id);
}
