package com.sap.ai.sdk.app.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import com.sap.ai.sdk.app.services.TabularService;
import lombok.val;
import org.junit.jupiter.api.Test;

class TabularTest {

  TabularService tabularService = new TabularService();

  @Test
  void testGetAllDataDestinations() {
    val response = tabularService.getAllDataDestinations();
    assertThat(response.getResources()).isNotEmpty();
  }

  @Test
  void testGetAllArtifacts() {
    val response = tabularService.getAllArtifacts();
    assertThat(response.getResources()).isNotEmpty();
  }

  @Test
  void testGetAllScenarioConfigurations() {
    val response = tabularService.getAllScenarioConfigurations();
    assertThat(response.getResources()).isNotEmpty();
  }

  @Test
  void testPredict() {
    val response = tabularService.predict();
    assertThat(response.getPredictions()).isNotEmpty();
  }
}
