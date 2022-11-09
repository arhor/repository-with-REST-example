package com.github.arhor.examples.restrep.data.model;

import java.io.Serializable;

public interface BaseEntity<K> extends Serializable {

    K getId();

    void setId(K id);
}
