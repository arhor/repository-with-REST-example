package com.github.arhor.examples.restrep.data.repository;

import com.github.arhor.examples.restrep.data.model.BaseEntity;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<T extends BaseEntity<K>, K> {

    Optional<T> findById(K id);

    List<T> findAllByIds(Iterable<K> ids);

    List<T> findAll();

    T create(T entity);

    T update(K id, T entity);

    void deleteById(K id);
}
