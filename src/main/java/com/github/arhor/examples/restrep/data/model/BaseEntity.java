package com.github.arhor.examples.restrep.data.model;

public interface BaseEntity<K> {

    K getId();

    void setId(K id);
}
