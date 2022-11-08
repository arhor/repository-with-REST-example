package com.github.arhor.examples.restrep.data.repository.impl;

import com.github.arhor.examples.restrep.data.model.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.function.Supplier;

@Repository
public class AuthorRepositoryImpl extends AbstractBaseRepository<Author, Long> {

    @Autowired
    public AuthorRepositoryImpl(final Supplier<Long> idGenerator) {
        super(idGenerator);
    }
}
