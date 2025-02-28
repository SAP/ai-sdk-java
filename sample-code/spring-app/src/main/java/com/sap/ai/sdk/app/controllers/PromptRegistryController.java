package com.sap.ai.sdk.app.controllers;

import com.sap.ai.sdk.prompt.registry.PromptClient;
import com.sap.ai.sdk.prompt.registry.client.DefaultApi;
import com.sap.ai.sdk.prompt.registry.model.PromptTemplateListResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Endpoint for Prompt Registry operations */
@SuppressWarnings("unused") // debug class that doesn't need to be tested
@RestController
@RequestMapping("/prompt-registry")
class PromptRegistryController {
  private static final DefaultApi client = new PromptClient();

  @GetMapping("/listTemplates")
  PromptTemplateListResponse listTemplates() {
    return client.listPromptTemplates();
  }
}
