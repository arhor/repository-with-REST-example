package com.github.arhor.examples.restrep.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponse handleDefaultException(final Exception exception) {
        log.error("Unhandled exception. Consider appropriate exception handler", exception);

        final var timestamp = LocalDateTime.now(Clock.systemUTC());
        final var message = "Internal server error";
        final var details = List.of(exception.getMessage());

        return new ErrorResponse(timestamp, message, details);
    }

    private record ErrorResponse(LocalDateTime timestamp, String message, List<String> details) {
    }
}
