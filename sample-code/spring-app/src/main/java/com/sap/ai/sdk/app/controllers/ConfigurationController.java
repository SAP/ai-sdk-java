package com.sap.ai.sdk.app.controllers;

import com.sap.ai.sdk.core.client.ConfigurationApi;
import com.sap.ai.sdk.core.model.AiConfiguration;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

/** Endpoint for Configuration operations */
@SuppressWarnings("unused") // debug class that doesn't need to be tested
@RestController
class ConfigurationController {

  private static final ConfigurationApi CLIENT = new ConfigurationApi();

  /**
   * Get the list of configurations.
   *
   * @param accept the accept header
   * @return a response entity with a string representation of the list of configurations
   */
  @GetMapping("/configurations")
  ResponseEntity<Object> getConfigurations(
      @Nullable @RequestHeader(value = "accept", required = false) final String accept) {
    final var configList = CLIENT.query("default");
    if ("application/json".equals(accept)) {
      return ResponseEntity.ok().body(configList);
    }
    final var items =
        configList.getResources().stream()
            .map(AiConfiguration::getName)
            .collect(Collectors.joining(", "));
    return ResponseEntity.ok("The following configurations are available: %s.".formatted(items));
  }
}
