package com.github.arhor.examples.restrep.service.dto;

import java.util.List;

public record AuthorResponseDto(
    Long id,
    String firstName,
    String lastName,
    List<BookResponseDto> books
) {
}
