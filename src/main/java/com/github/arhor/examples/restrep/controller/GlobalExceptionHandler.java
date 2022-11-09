package com.github.arhor.examples.restrep.controller;

import com.github.arhor.examples.restrep.service.exception.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Clock;
import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponse handleDefaultException(final Exception exception) {
        log.error("Unhandled exception. Consider appropriate exception handler", exception);

        final var timestamp = LocalDateTime.now(Clock.systemUTC());
        final var message = "An error occurred processing request";
        final var details = exception.getMessage();

        return new ErrorResponse(timestamp, message, details);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ErrorResponse handleEntityNotFoundException(final EntityNotFoundException exception) {
        final var timestamp = LocalDateTime.now(Clock.systemUTC());
        final var message = "Requested entity is not found";
        final var details = "Entity name: %s, failed condition: %s".formatted(
            exception.getEntityName(),
            exception.getCondition()
        );

        return new ErrorResponse(timestamp, message, details);
    }

    private record ErrorResponse(LocalDateTime timestamp, String message, String details) {
    }
}
