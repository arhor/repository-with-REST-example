package com.github.arhor.examples.restrep.service.dto;

public record BookResponseDto(
    Long id,
    String name,
    Long authorId
) {
}
