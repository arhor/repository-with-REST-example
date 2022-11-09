package com.github.arhor.examples.restrep.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book implements BaseEntity<Long> {

    private Long id;
    private String name;
    private Long authorId;

    public Book(final String name, final Long authorId) {
        this.name = name;
        this.authorId = authorId;
    }
}
