package com.github.arhor.examples.restrep.service.dto;

public record BookResponseDto(
    Long id,
    String name,
    String isbn,
    Long authorId
) {
}
