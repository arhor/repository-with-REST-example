package com.github.arhor.examples.restrep.data.repository.impl;

import com.github.arhor.examples.restrep.data.model.BaseEntity;
import com.github.arhor.examples.restrep.data.repository.BaseRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public abstract class AbstractBaseRepository<T extends BaseEntity> implements BaseRepository<T> {

    private final AtomicLong idCounter = new AtomicLong(0);
    private final Map<Long, T> data = new ConcurrentHashMap<>();

    @Override
    public Optional<T> findById(final Long id) {
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public List<T> findAllByIds(final Iterable<Long> ids) {
        final var result = new ArrayList<T>();

        for (final Long id : ids) {
            if (data.containsKey(id)) {
                result.add(data.get(id));
            }
        }
        return result;
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(data.values());
    }

    @Override
    public T create(final T entity) {
        if (entity.getId() != null) {
            throw new IllegalArgumentException(
                "Entity id expected to be null, but actually got: %d".formatted(
                    entity.getId()
                )
            );
        }

        final var currentEntityId = idCounter.incrementAndGet();
        entity.setId(currentEntityId);
        data.put(currentEntityId, entity);

        return entity;
    }

    @Override
    public T update(final Long id, final T entity) {
        if (!id.equals(entity.getId())) {
            throw new IllegalArgumentException(
                "Passed id expected to be equal entity id, but actually got id: %d, entity.id: %d".formatted(
                    id,
                    entity.getId()
                )
            );
        }
        if (!data.containsKey(id)) {
            throw new IllegalArgumentException(
                "Entity with id: %s is not found and cannot be updated".formatted(
                    id
                )
            );
        }
        data.put(id, entity);
        return entity;
    }

    @Override
    public void deleteById(final Long id) {
        data.remove(id);
    }
}
