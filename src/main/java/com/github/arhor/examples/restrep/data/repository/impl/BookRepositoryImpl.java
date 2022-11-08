package com.github.arhor.examples.restrep.data.repository.impl;

import com.github.arhor.examples.restrep.data.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.function.Supplier;

@Repository
public class BookRepositoryImpl extends AbstractBaseRepository<Book, Long> {

    @Autowired
    public BookRepositoryImpl(final Supplier<Long> idGenerator) {
        super(idGenerator);
    }
}
