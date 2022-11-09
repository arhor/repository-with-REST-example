package com.github.arhor.examples.restrep.data.repository.impl;

import com.github.arhor.examples.restrep.data.model.BaseEntity;
import com.github.arhor.examples.restrep.data.repository.BaseRepository;
import com.github.arhor.examples.restrep.data.repository.exception.DataAccessException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.SerializationUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

@RequiredArgsConstructor
public abstract class AbstractBaseRepository<T extends BaseEntity<K>, K> implements BaseRepository<T, K> {

    private final Supplier<K> idGenerator;
    private final Map<K, T> data = new ConcurrentHashMap<>();

    @Override
    public Optional<T> findById(final K id) {
        return Optional.ofNullable(data.get(id)).map(this::deepClone);
    }

    @Override
    public List<T> findAll() {
        return data.values().stream().map(this::deepClone).toList();
    }

    @Override
    public T create(final T entity) {
        if (entity.getId() != null) {
            throw new DataAccessException(
                "Entity id expected to be null, but actually got: %s".formatted(
                    entity.getId()
                )
            );
        }

        final var currentEntityId = idGenerator.get();
        entity.setId(currentEntityId);
        data.put(currentEntityId, deepClone(entity));

        return entity;
    }

    @Override
    public T update(final K id, final T entity) {
        if (!id.equals(entity.getId())) {
            throw new DataAccessException(
                "Passed id expected to be equal entity id, but actually got id: %s, entity.id: %s".formatted(
                    id,
                    entity.getId()
                )
            );
        }
        if (!data.containsKey(id)) {
            throw new DataAccessException(
                "Entity with id: %s is not found and cannot be updated".formatted(
                    id
                )
            );
        }
        data.put(id, deepClone(entity));
        return entity;
    }

    @Override
    public void deleteById(final K id) {
        data.remove(id);
    }

    @SuppressWarnings("unchecked")
    private T deepClone(final T entity) {
        return (T) SerializationUtils.clone(entity);
    }
}
