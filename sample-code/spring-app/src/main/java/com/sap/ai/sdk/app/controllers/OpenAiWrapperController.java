package com.sap.ai.sdk.app.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.openai.core.ObjectMappers;
import com.sap.ai.sdk.app.services.OpenAiWrapperService;
import javax.annotation.Nonnull;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** Endpoints for OpenAI operations using the official OpenAI Java SDK wrapper. */
@RestController
@RequestMapping("/openai-wrapper")
class OpenAiWrapperController {

  private final OpenAiWrapperService service;

  OpenAiWrapperController(@Nonnull final OpenAiWrapperService service) {
    this.service = service;
  }

  /**
   * Chat completion using the official OpenAI SDK via SAP AI Core.
   *
   * @return a chat completion response as JSON string
   * @throws JsonProcessingException if serialization fails
   */
  @GetMapping(value = "/chatCompletion", produces = MediaType.APPLICATION_JSON_VALUE)
  @Nonnull
  String chatCompletion() throws JsonProcessingException {
    return ObjectMappers.jsonMapper().writeValueAsString(service.chatCompletion());
  }

  /**
   * Chat completion with a custom user message.
   *
   * @param message the user message
   * @return a chat completion response as JSON string
   * @throws JsonProcessingException if serialization fails
   */
  @GetMapping(value = "/chat", produces = MediaType.APPLICATION_JSON_VALUE)
  @Nonnull
  String chat(@RequestParam @Nonnull final String message) throws JsonProcessingException {
    return ObjectMappers.jsonMapper().writeValueAsString(service.chatCompletion(message));
  }
}
