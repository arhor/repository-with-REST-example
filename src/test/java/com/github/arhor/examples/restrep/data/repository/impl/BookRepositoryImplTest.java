package com.github.arhor.examples.restrep.data.repository.impl;

import com.github.arhor.examples.restrep.data.model.Book;
import com.github.arhor.examples.restrep.data.repository.exception.DataAccessException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;
import static org.assertj.core.api.Assertions.from;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookRepositoryImplTest {

    @Mock
    private Supplier<Long> idGenerator;

    @InjectMocks
    private BookRepositoryImpl repository;

    @Test
    void should_correctly_create_then_retrieve_multiple_books() {
        // given
        var itemsToGenerate = 5;
        var currentId = new AtomicLong(0);

        when(idGenerator.get()).thenAnswer(invocation -> currentId.incrementAndGet());

        // when
        final var expectedBooks = IntStream.rangeClosed(1, itemsToGenerate)
            .mapToObj(num -> new Book("Test-" + num, 0L))
            .map(repository::create)
            .toList();

        final var actualBooks = repository.findAll();

        // then
        then(idGenerator)
            .should(times(itemsToGenerate))
            .get();

        assertThat(actualBooks)
            .containsExactlyElementsOf(expectedBooks);
    }

    @Test
    void should_correctly_create_then_retrieve_single_book_by_id() {
        // given
        var currentId = new AtomicLong(0);

        when(idGenerator.get()).thenAnswer(invocation -> currentId.incrementAndGet());

        // when
        final var expectedBook = repository.create(new Book("Test", 0L));
        final var actualBook = repository.findById(expectedBook.getId());

        // then
        then(idGenerator)
            .should()
            .get();

        assertThat(actualBook)
            .contains(expectedBook);
    }

    @Test
    void should_return_empty_optional_retrieving_single_book_by_non_existing_id() {
        // when
        final var actualBook = repository.findById(-1L);

        // then
        assertThat(actualBook)
            .isEmpty();
    }

    @Test
    void should_throw_an_exception_trying_to_create_book_with_existing_id() {
        // given
        final var id = 1L;
        final var book = new Book(id, "Test", 0L);

        // when
        final var result = catchException(() -> repository.create(book));

        // then
        assertThat(result)
            .isInstanceOf(DataAccessException.class)
            .hasMessageContaining(String.valueOf(id));
    }

    @Test
    void should_correctly_update_book() {
        // given
        final var initialName = "Test";
        final var initialAuthorId = 0L;
        final var currentId = new AtomicLong(0);

        when(idGenerator.get()).thenAnswer(invocation -> currentId.incrementAndGet());

        final var initialBook = repository.create(new Book(initialName, initialAuthorId));

        // when
        initialBook.setName(initialName + "-updated");
        initialBook.setAuthorId(initialAuthorId + 1);

        final var updatedBook = repository.update(initialBook.getId(), initialBook);

        // then
        assertThat(updatedBook)
            .returns("Test-updated", from(Book::getName))
            .returns(1L, from(Book::getAuthorId));
    }

    @Test
    void should_throw_an_exception_trying_to_update_book_with_inconsistent_id() {
        // given
        final var id = 1L;
        final var book = new Book(id + 1, "Test", 0L);

        // when
        final var result = catchException(() -> repository.update(id, book));

        // then
        assertThat(result)
            .isInstanceOf(DataAccessException.class)
            .hasMessageContaining(String.valueOf(id));
    }

    @Test
    void should_throw_an_exception_trying_to_update_book_with_non_existing_id() {
        // given
        final var id = -1L;
        final var book = new Book(id, "Test", 0L);

        // when
        final var result = catchException(() -> repository.update(id, book));

        // then
        assertThat(result)
            .isInstanceOf(DataAccessException.class)
            .hasMessageContaining(String.valueOf(id));
    }

    @Test
    void should_correctly_delete_book_by_id() {
        // given
        final var currentId = new AtomicLong(0);

        when(idGenerator.get()).thenAnswer(invocation -> currentId.incrementAndGet());

        final var initialBook = repository.create(new Book("Test", 0L));
        final var initialBookId = initialBook.getId();

        // when
        final var resultBeforeDelete = repository.findById(initialBookId);
        repository.deleteById(initialBookId);
        final var resultAfterDelete = repository.findById(initialBookId);

        // then
        assertThat(resultBeforeDelete)
            .contains(initialBook);
        assertThat(resultAfterDelete)
            .isEmpty();
    }
}
