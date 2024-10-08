package com.sap.ai.sdk.app;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@SuppressWarnings("unused") // Used by Spring
class ControllerExceptionHandler {
  /** Exceptions thrown by the Spring Boot controllers are turned into a readable text response. */
  @ExceptionHandler(Exception.class)
  ResponseEntity<String> handleError(final Exception ex) {
    final var headers = new HttpHeaders();
    headers.setContentType(MediaType.TEXT_PLAIN);

    final var txt =
        ex.getMessage()
            + "\n\n"
            + Stream.of(ex.getStackTrace()).map(Object::toString).collect(Collectors.joining("\n"))
            + "\n\n"
            + Stream.of(ex.getSuppressed()).map(Object::toString).collect(Collectors.joining("\n"));

    return new ResponseEntity<>(txt, headers, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
