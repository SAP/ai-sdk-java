package com.sap.ai.sdk.app.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import com.sap.ai.sdk.app.services.OpenAiV1Service;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
class OpenAiV1Test {
  OpenAiV1Service service;

  @BeforeEach
  void setUp() {
    service = new OpenAiV1Service();
  }

  @Test
  void testCreateResponse() {
    final var response = service.createResponse("What is the capital of France?");
    assertThat(response).isNotNull();
    assertThat(response.output()).isNotNull();
    assertThat(response.output()).isNotEmpty();
  }
}
