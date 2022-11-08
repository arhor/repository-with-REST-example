package com.github.arhor.examples.restrep.controller;

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

    private record ErrorResponse(LocalDateTime timestamp, String message, String details) {
    }
}
