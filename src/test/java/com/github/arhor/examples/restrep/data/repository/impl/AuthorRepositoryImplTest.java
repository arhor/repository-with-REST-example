package com.github.arhor.examples.restrep.data.repository.impl;

import com.github.arhor.examples.restrep.data.model.Author;
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
class AuthorRepositoryImplTest {

    @Mock
    private Supplier<Long> idGenerator;

    @InjectMocks
    private AuthorRepositoryImpl repository;

    @Test
    void should_correctly_create_then_retrieve_multiple_authors() {
        // given
        var itemsToGenerate = 5;
        var currentId = new AtomicLong(0);

        when(idGenerator.get()).thenAnswer(invocation -> currentId.incrementAndGet());

        // when
        final var expectedAuthors = IntStream.rangeClosed(1, itemsToGenerate)
            .mapToObj(num -> new Author("Test First name " + num, "Test Last name " + num))
            .map(repository::create)
            .toList();

        final var actualAuthors = repository.findAll();

        // then
        then(idGenerator)
            .should(times(itemsToGenerate))
            .get();

        assertThat(actualAuthors)
            .containsExactlyElementsOf(expectedAuthors);
    }

    @Test
    void should_correctly_create_then_retrieve_single_author_by_id() {
        // given
        var currentId = new AtomicLong(0);

        when(idGenerator.get()).thenAnswer(invocation -> currentId.incrementAndGet());

        // when
        final var expectedAuthor = repository.create(new Author("Test First name", "Test Last name"));
        final var actualAuthor = repository.findById(expectedAuthor.getId());

        // then
        then(idGenerator)
            .should()
            .get();

        assertThat(actualAuthor)
            .contains(expectedAuthor);
    }

    @Test
    void should_return_empty_optional_retrieving_single_author_by_non_existing_id() {
        // when
        final var actualAuthor = repository.findById(-1L);

        // then
        assertThat(actualAuthor)
            .isEmpty();
    }

    @Test
    void should_throw_an_exception_trying_to_create_author_with_existing_id() {
        // given
        final var id = 1L;
        final var author = new Author(id, "Test First name", "Test Last name");

        // when
        final var result = catchException(() -> repository.create(author));

        // then
        assertThat(result)
            .isInstanceOf(DataAccessException.class)
            .hasMessageContaining(String.valueOf(id));
    }

    @Test
    void should_correctly_update_author() {
        // given
        final var initialFirstName = "Test First Name";
        final var initialLastName = "Test Last Name";
        final var currentId = new AtomicLong(0);

        when(idGenerator.get()).thenAnswer(invocation -> currentId.incrementAndGet());

        final var initialAuthor = repository.create(new Author(initialFirstName, initialLastName));

        // when
        initialAuthor.setFirstName(initialFirstName + "-updated");
        initialAuthor.setLastName(initialLastName + "-updated");

        final var updatedAuthor = repository.update(initialAuthor.getId(), initialAuthor);

        // then
        assertThat(updatedAuthor)
            .returns("Test First Name-updated", from(Author::getFirstName))
            .returns("Test Last Name-updated", from(Author::getLastName));
    }

    @Test
    void should_throw_an_exception_trying_to_update_author_with_inconsistent_id() {
        // given
        final var id = 1L;
        final var author = new Author(id + 1, "Test", "Test");

        // when
        final var result = catchException(() -> repository.update(id, author));

        // then
        assertThat(result)
            .isInstanceOf(DataAccessException.class)
            .hasMessageContaining(String.valueOf(id));
    }

    @Test
    void should_throw_an_exception_trying_to_update_author_with_non_existing_id() {
        // given
        final var id = -1L;
        final var author = new Author(id, "Test", "Test");

        // when
        final var result = catchException(() -> repository.update(id, author));

        // then
        assertThat(result)
            .isInstanceOf(DataAccessException.class)
            .hasMessageContaining(String.valueOf(id));
    }

    @Test
    void should_correctly_delete_author_by_id() {
        // given
        final var currentId = new AtomicLong(0);

        when(idGenerator.get()).thenAnswer(invocation -> currentId.incrementAndGet());

        final var initialAuthor = repository.create(new Author("Test", "Test"));
        final var initialAuthorId = initialAuthor.getId();

        // when
        final var resultBeforeDelete = repository.findById(initialAuthorId);
        repository.deleteById(initialAuthorId);
        final var resultAfterDelete = repository.findById(initialAuthorId);

        // then
        assertThat(resultBeforeDelete)
            .contains(initialAuthor);
        assertThat(resultAfterDelete)
            .isEmpty();
    }
}
