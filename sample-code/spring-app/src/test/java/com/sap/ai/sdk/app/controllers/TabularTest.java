package com.sap.ai.sdk.app.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import com.sap.ai.sdk.app.services.TabularService.ArtifactService;
import com.sap.ai.sdk.app.services.TabularService.DataDestinationService;
import com.sap.ai.sdk.app.services.TabularService.PredictionService;
import com.sap.ai.sdk.app.services.TabularService.ScenarioConfigurationService;
import lombok.val;
import org.junit.jupiter.api.Test;

class TabularTest {

  DataDestinationService dataDestinationService = new DataDestinationService();
  ArtifactService artifactService = new ArtifactService();
  ScenarioConfigurationService scenarioConfigurationService = new ScenarioConfigurationService();
  PredictionService predictionService = new PredictionService();

  @Test
  void testGetAllDataDestinations() {
    val response = dataDestinationService.getAllDataDestinations();
    assertThat(response.getResources()).isNotEmpty();
  }

  @Test
  void testGetAllArtifacts() {
    val response = artifactService.getAllArtifacts();
    assertThat(response.getResources()).isNotEmpty();
  }

  @Test
  void testGetAllScenarioConfigurations() {
    val response = scenarioConfigurationService.getAllScenarioConfigurations();
    assertThat(response.getResources()).isNotEmpty();
  }

  @Test
  void testPredict() {
    val response = predictionService.predict();
    assertThat(response.getPredictions()).isNotEmpty();
  }
}
