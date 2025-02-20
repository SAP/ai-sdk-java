package com.sap.ai.sdk.app.controllers;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PromptRegistryTest {

  @Test
  void listTemplates() {
    var controller = new PromptRegistryController();
    var result = controller.listTemplates();
    assertThat(result.getCount()).isGreaterThan(0);
  }
}
