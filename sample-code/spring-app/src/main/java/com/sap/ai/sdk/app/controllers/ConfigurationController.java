package com.sap.ai.sdk.app.controllers;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sap.ai.sdk.core.client.ConfigurationApi;
import com.sap.ai.sdk.core.model.AiConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import java.util.stream.Collectors;

/** Endpoint for Configuration operations */
@SuppressWarnings("unused") // debug class that doesn't need to be tested
@RestController
class ConfigurationController {

  private static final ConfigurationApi CLIENT = new ConfigurationApi();
  private static final ObjectMapper MAPPER =
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
          .body(MAPPER.writeValueAsString(configList));
    }
    final var items =
        configList.getResources().stream()
            .map(AiConfiguration::getName)
            .collect(Collectors.joining(", "));
    return ResponseEntity.ok("The following configurations are available: %s.".formatted(items));
  }
}
