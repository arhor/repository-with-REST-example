package com.github.arhor.examples.restrep.service.dto;

public record BookRequestDto(
    String name,
    String isbn,
    Long authorId
) {
}
