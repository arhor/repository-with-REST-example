package com.github.arhor.examples.restrep.controller;

import com.github.arhor.examples.restrep.service.BookService;
import com.github.arhor.examples.restrep.service.dto.BookRequestDto;
import com.github.arhor.examples.restrep.service.dto.BookResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class BookController {

    private final BookService bookService;

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public List<BookResponseDto> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping(path = "/{bookId}", produces = APPLICATION_JSON_VALUE)
    public BookResponseDto getBookById(@PathVariable final Long bookId) {
        return bookService.getBookById(bookId);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<BookResponseDto> createBook(@RequestBody final BookRequestDto book) {
        var createdBook = bookService.createBook(book);

        var location =
            ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{bookId}")
                .build(createdBook.id());

        return ResponseEntity.created(location).body(createdBook);
    }

    @PutMapping(path = "/{bookId}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public BookResponseDto updateBook(
        @PathVariable final Long bookId,
        @RequestBody final BookRequestDto book
    ) {
        return bookService.updateBook(bookId, book);
    }

    @DeleteMapping("/{bookId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable final Long bookId) {
        bookService.deleteBook(bookId);
    }
}
