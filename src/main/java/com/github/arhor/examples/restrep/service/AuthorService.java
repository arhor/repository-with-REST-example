package com.github.arhor.examples.restrep.service;

import com.github.arhor.examples.restrep.service.dto.AuthorRequestDto;
import com.github.arhor.examples.restrep.service.dto.AuthorResponseDto;

import java.util.List;

public interface AuthorService {

    List<AuthorResponseDto> getAllAuthors();

    AuthorResponseDto getAuthorById(Long authorId);

    AuthorResponseDto createAuthor(AuthorRequestDto authorRequestDto);

    AuthorResponseDto updateAuthor(Long authorId, AuthorRequestDto authorRequestDto);

    void deleteAuthor(Long authorId);
}
