package com.issoft.cinemaapplication.service.impl;

import com.issoft.cinemaapplication.exception.EntityNotFoundException;
import com.issoft.cinemaapplication.model.BaseEntity;
import com.issoft.cinemaapplication.repository.BaseRepository;
import com.issoft.cinemaapplication.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public abstract class BaseServiceImpl<T extends BaseEntity> implements BaseService<T> {

    @Autowired
    private BaseRepository<T> repository;

    @Override
    public List<T> findAll() {
        return this.repository.findAll();
    }

    @Override
    public Optional<T> findById(final Long id) {
        this.checkExistEntity(id);
        return this.repository.findById(id);
    }

    @Override
    public T save(final T entity) {
        return this.repository.save(entity);
    }

    @Override
    public void deleteById(final Long id) {
        this.checkExistEntity(id);
        this.repository.deleteById(id);
    }

    public void checkExistEntity(final Long id) {
        if (this.repository.existsById(id)) {
            return;
        }
        throw new EntityNotFoundException(id);
    }
}
