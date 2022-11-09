package com.github.arhor.examples.restrep.service.impl;

import com.github.arhor.examples.restrep.data.model.Author;
import com.github.arhor.examples.restrep.data.model.Book;
import com.github.arhor.examples.restrep.data.repository.BaseRepository;
import com.github.arhor.examples.restrep.service.BookService;
import com.github.arhor.examples.restrep.service.dto.BookRequestDto;
import com.github.arhor.examples.restrep.service.dto.BookResponseDto;
import com.github.arhor.examples.restrep.service.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class BookServiceImpl implements BookService {

    private final BaseRepository<Author, Long> authorRepository;
    private final BaseRepository<Book, Long> bookRepository;

    @Override
    public List<BookResponseDto> getAllBooks() {
        return bookRepository.findAll().stream()
            .map(book ->
                new BookResponseDto(
                    book.getId(),
                    book.getName(),
                    book.getAuthorId()
                ))
            .toList();
    }

    @Override
    public BookResponseDto getBookById(final Long bookId) {
        return bookRepository.findById(bookId)
            .map(book ->
                new BookResponseDto(
                    book.getId(),
                    book.getName(),
                    book.getAuthorId()
                ))
            .orElseThrow(() -> new EntityNotFoundException("Book", "id = " + bookId));
    }

    @Override
    public BookResponseDto createBook(final BookRequestDto bookRequestDto) {
        final var author = authorRepository
            .findById(bookRequestDto.authorId())
            .orElseThrow(() -> new EntityNotFoundException("Author", "id = " + bookRequestDto.authorId()));

        final var createdBook = bookRepository.create(
            new Book(
                bookRequestDto.name(),
                author.getId()
            )
        );
        return new BookResponseDto(
            createdBook.getId(),
            createdBook.getName(),
            createdBook.getAuthorId()
        );
    }

    @Override
    public BookResponseDto updateBook(final Long bookId, final BookRequestDto bookRequestDto) {
        final var book = bookRepository
            .findById(bookId)
            .orElseThrow(() -> new EntityNotFoundException("Book", "id = " + bookId));

        if (bookRequestDto.name() != null) {
            book.setName(bookRequestDto.name());
        }
        if (bookRequestDto.authorId() != null) {
            final var author = authorRepository
                .findById(bookRequestDto.authorId())
                .orElseThrow(() -> new EntityNotFoundException("Author", "id = " + bookRequestDto.authorId()));

            book.setAuthorId(author.getId());
        }

        final var updatedBook = bookRepository.update(bookId, book);

        return new BookResponseDto(
            updatedBook.getId(),
            updatedBook.getName(),
            updatedBook.getAuthorId()
        );
    }

    @Override
    public void deleteBook(final Long bookId) {
        final var book = bookRepository
            .findById(bookId)
            .orElseThrow(() -> new EntityNotFoundException("Book", "id = " + bookId));

        bookRepository.deleteById(book.getId());
    }
}
