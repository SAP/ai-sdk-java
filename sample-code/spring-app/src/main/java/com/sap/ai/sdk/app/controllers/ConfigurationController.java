package com.sap.ai.sdk.app.controllers;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
// import com.fasterxml.jackson.datatype.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sap.ai.sdk.core.client.ConfigurationApi;
import com.sap.ai.sdk.core.model.AiConfigurationList;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

/** Endpoint for Configuration operations */
@SuppressWarnings("unused") // debug class that doesn't need to be tested
@RestController
class ConfigurationController {

  private static final ConfigurationApi CLIENT = new ConfigurationApi();
  private final ObjectMapper mapper =
      new ObjectMapper()
          .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
          .registerModule(new JavaTimeModule());

  /**
   * Get the list of configurations.
   *
   * @param accept the accept header
   * @return a response entity with a string representation of the list of configurations
   */
  @GetMapping("/configurations")
  ResponseEntity<String> getConfigurations(
      @RequestHeader(value = "accept", required = false) final String accept)
      throws JsonProcessingException {
    final var configList = CLIENT.query("default");
    if ("application/json".equals(accept)) {
      return ResponseEntity.ok()
          .contentType(MediaType.APPLICATION_JSON)
          .body(mapper.writeValueAsString(configList));
    }
    return ResponseEntity.ok(buildMessage(configList));
  }

  /**
   * Build a message from the configuration list.
   *
   * @param configList The configuration list.
   * @return the message
   */
  private String buildMessage(final AiConfigurationList configList) {
    final var message = new StringBuilder("The following configurations are available: ");
    for (final var resource : configList.getResources()) {
      message.append(resource.getName()).append(", ");
    }
    message.setCharAt(message.length() - 2, '.');
    return message.toString();
  }
}
