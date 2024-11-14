package com.sap.ai.sdk.app.controllers;

import com.sap.ai.sdk.core.client.ConfigurationApi;
import com.sap.ai.sdk.core.client.model.AiConfigurationList;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/** Endpoint for Configuration operations */
@SuppressWarnings("unused") // debug class that doesn't need to be tested
@RestController
public class ConfigurationController {

  private static final ConfigurationApi CLIENT = new ConfigurationApi();

  /**
   * Get the list of configurations.
   *
   * @return the list of configurations
   */
  @GetMapping("/configurations")
  AiConfigurationList getConfigurations() {
    return CLIENT.query("default");
  }
}
