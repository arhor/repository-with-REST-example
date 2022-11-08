package com.github.arhor.examples.restrep.service.impl;

import com.github.arhor.examples.restrep.data.model.Author;
import com.github.arhor.examples.restrep.data.repository.BaseRepository;
import com.github.arhor.examples.restrep.service.AuthorService;
import com.github.arhor.examples.restrep.service.dto.AuthorRequestDto;
import com.github.arhor.examples.restrep.service.dto.AuthorResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AuthorServiceImpl implements AuthorService {

    private final BaseRepository<Author, Long> authorRepository;

    @Override
    public List<AuthorResponseDto> getAllAuthors() {
        return Collections.emptyList();
    }

    @Override
    public AuthorResponseDto getAuthorById(Long authorId) {
        return null;
    }

    @Override
    public AuthorResponseDto createAuthor(AuthorRequestDto author) {
        return null;
    }

    @Override
    public AuthorResponseDto updateAuthor(Long authorId, AuthorRequestDto author) {
        return null;
    }

    @Override
    public void deleteAuthor(Long authorId) {

    }
}
