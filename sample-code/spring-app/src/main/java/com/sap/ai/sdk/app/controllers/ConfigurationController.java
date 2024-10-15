package com.sap.ai.sdk.app.controllers;

import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.core.client.ConfigurationApi;
import com.sap.ai.sdk.core.client.model.AiConfigurationList;
import com.sap.cloud.sdk.services.openapi.apiclient.ApiClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/** Endpoint for Configuration operations */
@SuppressWarnings("unused") // debug class that doesn't need to be tested
@RestController
public class ConfigurationController {

  private static final ApiClient API_CLIENT = new AiCoreService().client();
  private static final ConfigurationApi API = new ConfigurationApi(API_CLIENT);

  /**
   * Get the list of configurations.
   *
   * @return the list of configurations
   */
  @GetMapping("/configurations")
  AiConfigurationList getConfigurations() {
    return API.query("default");
  }
}
