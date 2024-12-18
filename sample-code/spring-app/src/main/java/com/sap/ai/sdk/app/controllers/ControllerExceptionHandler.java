package com.sap.ai.sdk.app.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import javax.annotation.Nonnull;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/** Exception handler for the controllers. */
@SuppressWarnings("unused")
@RestControllerAdvice
public class ControllerExceptionHandler {

  /**
   * Handle JSON processing exceptions.
   *
   * @param e the exception
   * @return the error response
   */
  @Nonnull
  @ExceptionHandler(JsonProcessingException.class)
  public ErrorResponse handleJsonProcessingException(@Nonnull final JsonProcessingException e) {
    return ErrorResponse.create(
        e, HttpStatus.INTERNAL_SERVER_ERROR, "Failed to process JSON: " + e.getMessage());
  }
}
