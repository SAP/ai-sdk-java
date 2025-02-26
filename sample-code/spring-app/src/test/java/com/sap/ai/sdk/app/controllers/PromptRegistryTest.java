package com.sap.ai.sdk.app.controllers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;

public class PromptRegistryTest {

  @Test
  void listTemplates() {
    var controller = new PromptRegistryController();
    var result = controller.listTemplates();
    assertThat(result.getCount()).isGreaterThan(0);
  }
}
