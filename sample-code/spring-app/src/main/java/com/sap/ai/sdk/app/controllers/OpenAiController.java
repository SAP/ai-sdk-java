package com.sap.ai.sdk.app.controllers;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.ai.sdk.app.services.OpenAiService;
import javax.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

/** Endpoints for OpenAI operations */
@Slf4j
@RestController
@SuppressWarnings("unused")
public class OpenAiController {
  @Autowired private OpenAiService service;
  private final ObjectMapper mapper =
      new ObjectMapper().setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

  /**
   * Chat request to OpenAI
   *
   * @return a ResponseEntity with the response content
   */
  @GetMapping("/chatCompletion")
  @Nonnull
  ResponseEntity<String> chatCompletion(
      @RequestHeader(value = "accept", required = false) final String accept)
      throws JsonProcessingException {
    final var response = service.chatCompletion("Who is the prettiest");
    if (accept.equals("application/json")) {
      return ResponseEntity.ok()
          .contentType(MediaType.APPLICATION_JSON)
          .body(mapper.writeValueAsString(response));
    }
    return ResponseEntity.ok(response.getContent());
  }

  /**
   * Asynchronous stream of an OpenAI chat request
   *
   * @return the emitter that streams the assistant message response
   */
  @SuppressWarnings("unused") // The end-to-end test doesn't use this method
  @GetMapping("/streamChatCompletionDeltas")
  @Nonnull
  ResponseEntity<ResponseBodyEmitter> streamChatCompletionDeltas() {
    final var message = "Can you give me the first 100 numbers of the Fibonacci sequence?";
    return service.streamChatCompletionDeltas(message);
  }

  /**
   * Asynchronous stream of an OpenAI chat request
   *
   * @return the emitter that streams the assistant message response
   */
  @SuppressWarnings("unused") // The end-to-end test doesn't use this method
  @GetMapping("/streamChatCompletion")
  @Nonnull
  ResponseEntity<ResponseBodyEmitter> streamChatCompletion() {
    final var message = "Can you give me the first 100 numbers of the Fibonacci sequence?";
    return service.streamChatCompletion(message);
  }

  /**
   * Chat request to OpenAI with an image
   *
   * @return a ResponseEntity with the response content
   */
  @GetMapping("/chatCompletionImage")
  @Nonnull
  ResponseEntity<String> chatCompletionImage(
      @RequestHeader(value = "accept", required = false) final String accept)
      throws JsonProcessingException {
    final var response =
        service.chatCompletionImage(
            "https://upload.wikimedia.org/wikipedia/commons/thumb/5/59/SAP_2011_logo.svg/440px-SAP_2011_logo.svg.png");
    if (accept.equals("application/json")) {
      return ResponseEntity.ok()
          .contentType(MediaType.APPLICATION_JSON)
          .body(mapper.writeValueAsString(response));
    }
    return ResponseEntity.ok(response.getContent());
  }

  /**
   * Chat request to OpenAI with a tool.
   *
   * @return a ResponseEntity with the response content
   */
  @GetMapping("/chatCompletionTool")
  @Nonnull
  ResponseEntity<String> chatCompletionTools(
      @RequestHeader(value = "accept", required = false) final String accept)
      throws JsonProcessingException {
    final var response =
        service.chatCompletionTools("Calculate the Fibonacci number for given sequence index.");
    if (accept.equals("application/json")) {
      return ResponseEntity.ok()
          .contentType(MediaType.APPLICATION_JSON)
          .body(mapper.writeValueAsString(response));
    }
    return ResponseEntity.ok(response.getContent());
  }

  /**
   * Get the embedding of a text
   *
   * @return a ResponseEntity with the response content
   */
  @GetMapping("/embedding")
  @Nonnull
  ResponseEntity<String> embedding() throws JsonProcessingException {
    final var response = service.embedding("Hello world");
    return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(mapper.writeValueAsString(response));
  }

  /**
   * Chat request to OpenAI filtering by resource group
   *
   * @param resourceGroup The resource group to use
   * @return a ResponseEntity with the response content
   */
  @GetMapping("/chatCompletion/{resourceGroup}")
  @Nonnull
  ResponseEntity<String> chatCompletionWithResource(
      @RequestHeader(value = "accept", required = false) final String accept,
      @Nonnull @PathVariable("resourceGroup") final String resourceGroup)
      throws JsonProcessingException {
    final var response =
        service.chatCompletionWithResource(resourceGroup, "Where is the nearest coffee shop?");
    if (accept.equals("application/json")) {
      return ResponseEntity.ok()
          .contentType(MediaType.APPLICATION_JSON)
          .body(mapper.writeValueAsString(response));
    }
    return ResponseEntity.ok(response.getContent());
  }
}
