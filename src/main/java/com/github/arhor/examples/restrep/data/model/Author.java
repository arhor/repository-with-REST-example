package com.github.arhor.examples.restrep.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Author implements BaseEntity<Long> {

    private Long id;
    private String firstName;
    private String lastName;

    public Author(final String firstName, final String lastName) {
        this(null, firstName, lastName);
    }
}
