package com.github.arhor.examples.restrep.controller;

import com.github.arhor.examples.restrep.service.AuthorService;
import com.github.arhor.examples.restrep.service.dto.AuthorRequestDto;
import com.github.arhor.examples.restrep.service.dto.AuthorResponseDto;
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
@RequestMapping("/authors")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public List<AuthorResponseDto> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @GetMapping(path = "/{authorId}", produces = APPLICATION_JSON_VALUE)
    public AuthorResponseDto getAuthorById(@PathVariable final Long authorId) {
        return authorService.getAuthorById(authorId);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorResponseDto> createAuthor(@RequestBody final AuthorRequestDto author) {
        var createdAuthor = authorService.createAuthor(author);

        var location =
            ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{authorId}")
                .build(createdAuthor.id());

        return ResponseEntity.created(location).body(createdAuthor);
    }

    @PutMapping(path = "/{authorId}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public AuthorResponseDto updateAuthor(
        @PathVariable final Long authorId,
        @RequestBody final AuthorRequestDto author
    ) {
        return authorService.updateAuthor(authorId, author);
    }

    @DeleteMapping("/{authorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAuthor(@PathVariable final Long authorId) {
        authorService.deleteAuthor(authorId);
    }
}
