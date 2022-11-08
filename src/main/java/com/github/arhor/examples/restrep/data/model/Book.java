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
    private String isbn;
    private Long authorId;
}
