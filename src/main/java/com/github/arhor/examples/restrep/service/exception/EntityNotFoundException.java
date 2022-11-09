package com.github.arhor.examples.restrep.service.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EntityNotFoundException extends RuntimeException {

    private final String entityName;
    private final String condition;
}
