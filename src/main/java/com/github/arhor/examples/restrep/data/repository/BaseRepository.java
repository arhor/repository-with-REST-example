package com.github.arhor.examples.restrep.data.repository;

import com.github.arhor.examples.restrep.data.model.BaseEntity;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<T extends BaseEntity> {

    Optional<T> findById(Long id);

    List<T> findAllByIds(Iterable<Long> ids);

    List<T> findAll();

    T create(T entity);

    T update(Long id, T entity);

    void deleteById(Long id);

    default void delete(T entity) {
        deleteById(entity.getId());
    }
}
