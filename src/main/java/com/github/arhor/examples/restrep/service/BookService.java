package com.github.arhor.examples.restrep.service;

import com.github.arhor.examples.restrep.service.dto.BookRequestDto;
import com.github.arhor.examples.restrep.service.dto.BookResponseDto;

import java.util.List;

public interface BookService {

    List<BookResponseDto> getAllBooks();

    BookResponseDto getBookById(Long bookId);

    BookResponseDto createBook(BookRequestDto bookRequestDto);

    BookResponseDto updateBook(Long bookId, BookRequestDto bookRequestDto);

    void deleteBook(Long bookId);
}
