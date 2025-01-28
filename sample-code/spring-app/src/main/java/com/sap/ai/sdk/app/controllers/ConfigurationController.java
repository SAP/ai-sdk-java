package com.sap.ai.sdk.app.controllers;

import com.sap.ai.sdk.core.client.ConfigurationApi;
import com.sap.ai.sdk.core.model.AiConfiguration;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** Endpoint for Configuration operations */
@SuppressWarnings("unused") // debug class that doesn't need to be tested
@RestController
class ConfigurationController {

  private static final ConfigurationApi CLIENT = new ConfigurationApi();

  @GetMapping("/configurations")
  Object getConfigurations(
      @Nullable @RequestParam(value = "view", required = false) final String view) {
    final var configList = CLIENT.query("default");
    if ("json".equals(view)) {
      return configList;
    }
    final var items =
        configList.getResources().stream()
            .map(AiConfiguration::getName)
            .collect(Collectors.joining(", "));
    return "The following configurations are available: %s.".formatted(items);
  }
}
