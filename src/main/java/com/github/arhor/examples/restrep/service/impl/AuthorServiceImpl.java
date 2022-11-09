package com.github.arhor.examples.restrep.service.impl;

import com.github.arhor.examples.restrep.data.model.Author;
import com.github.arhor.examples.restrep.data.model.Book;
import com.github.arhor.examples.restrep.data.repository.BaseRepository;
import com.github.arhor.examples.restrep.service.AuthorService;
import com.github.arhor.examples.restrep.service.dto.AuthorRequestDto;
import com.github.arhor.examples.restrep.service.dto.AuthorResponseDto;
import com.github.arhor.examples.restrep.service.dto.BookResponseDto;
import com.github.arhor.examples.restrep.service.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AuthorServiceImpl implements AuthorService {

    private final BaseRepository<Author, Long> authorRepository;
    private final BaseRepository<Book, Long> bookRepository;

    @Override
    public List<AuthorResponseDto> getAllAuthors() {
        return authorRepository.findAll().stream()
            .map(this::mapEntityToDto)
            .toList();
    }

    @Override
    public AuthorResponseDto getAuthorById(final Long authorId) {
        return authorRepository.findById(authorId)
            .map(this::mapEntityToDto)
            .orElseThrow(() -> new EntityNotFoundException("Author", "id = " + authorId));
    }

    @Override
    public AuthorResponseDto createAuthor(final AuthorRequestDto authorRequestDto) {
        final var createdAuthor = authorRepository.create(
            new Author(
                authorRequestDto.firstName(),
                authorRequestDto.lastName()
            )
        );
        return new AuthorResponseDto(
            createdAuthor.getId(),
            createdAuthor.getFirstName(),
            createdAuthor.getLastName(),
            Collections.emptyList()
        );
    }

    @Override
    public AuthorResponseDto updateAuthor(final Long authorId, final AuthorRequestDto authorRequestDto) {
        final var author = authorRepository
            .findById(authorId)
            .orElseThrow(() -> new EntityNotFoundException("Author", "id = " + authorId));

        if (authorRequestDto.firstName() != null) {
            author.setFirstName(authorRequestDto.firstName());
        }
        if (authorRequestDto.lastName() != null) {
            author.setLastName(authorRequestDto.lastName());
        }

        final var updatedAuthor = authorRepository.update(authorId, author);

        return mapEntityToDto(updatedAuthor);
    }

    @Override
    public void deleteAuthor(final Long authorId) {
        final var author = authorRepository
            .findById(authorId)
            .orElseThrow(() -> new EntityNotFoundException("Author", "id = " + authorId));

        authorRepository.deleteById(author.getId());

        bookRepository.findAll().stream()
            .filter(book -> book.getAuthorId().equals(author.getId()))
            .forEach(book -> bookRepository.deleteById(book.getId()));
    }

    private AuthorResponseDto mapEntityToDto(final Author author) {
        return new AuthorResponseDto(
            author.getId(),
            author.getFirstName(),
            author.getLastName(),
            findAllBooksByAuthorId(author.getId())
        );
    }

    private List<BookResponseDto> findAllBooksByAuthorId(final Long authorId) {
        return bookRepository.findAll().stream()
            .filter(book -> book.getAuthorId().equals(authorId))
            .map(book ->
                new BookResponseDto(
                    book.getId(),
                    book.getName(),
                    book.getAuthorId()
                )
            )
            .toList();
    }
}
