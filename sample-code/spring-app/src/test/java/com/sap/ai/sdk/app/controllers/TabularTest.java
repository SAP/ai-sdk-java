package com.sap.ai.sdk.app.controllers;

import static com.sap.ai.sdk.tabular.generated.orchestration.model.HDLDataDestinationGetResponse.TypeEnum.HDL;
import static org.assertj.core.api.Assertions.assertThat;

import com.sap.ai.sdk.app.services.TabularService.ArtifactService;
import com.sap.ai.sdk.app.services.TabularService.DataDestinationService;
import com.sap.ai.sdk.app.services.TabularService.PredictionService;
import com.sap.ai.sdk.app.services.TabularService.ScenarioConfigurationService;
import com.sap.ai.sdk.tabular.generated.orchestration.model.HDLDataDestinationGetResponse;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.Test;

@Slf4j
class TabularTest {

  DataDestinationService dataDestinationService = new DataDestinationService();
  ArtifactService artifactService = new ArtifactService();
  ScenarioConfigurationService scenarioConfigurationService = new ScenarioConfigurationService();
  PredictionService predictionService = new PredictionService();

  private static final int ACCEPTED = 202;
  private static final int NO_CONTENT = 204;

  @Test
  void testGetAllDataDestinations() {
    val response = dataDestinationService.getAllDataDestinations();
    assertThat(response.getResources()).isNotEmpty();
  }

  @Test
  void testCreateDeleteDataDestinations() {
    cleanupDataDestinations();

    val dataDestinationName = "data-destination-e2e-test-java-" + System.currentTimeMillis();
    val createResponse =
        dataDestinationService.createHanaDataLakeDataDestination(dataDestinationName);
    assertThat(createResponse.getName()).isEqualTo(dataDestinationName);

    val getAllResponse = dataDestinationService.getAllDataDestinations();
    assertThat(getAllResponse.getResources()).isNotEmpty();
    val dataDestination = getAllResponse.getResources().get(0);
    assertThat(dataDestination.getType()).isEqualTo(HDL);
    val hanaDataLakeDataDestination = (HDLDataDestinationGetResponse) dataDestination;
    assertThat(hanaDataLakeDataDestination.getName()).isEqualTo(dataDestinationName);
  }

  private void cleanupDataDestinations() {
    for (val resource : dataDestinationService.getAllDataDestinations().getResources()) {
      if (resource.getType() == HDL
          && resource instanceof HDLDataDestinationGetResponse oldDataDestination
          && oldDataDestination.getName().startsWith("data-destination-e2e-test-java")) {
        val name = oldDataDestination.getName();

        log.info("Deleting old data destination with name: {}", name);
        val deleteResponse = dataDestinationService.deleteHanaDataLakeDataDestination(name);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(ACCEPTED);
      }
    }
  }

  @Test
  void testGetAllArtifacts() {
    val response = artifactService.getAllArtifacts();
    assertThat(response.getResources()).isNotEmpty();
  }

  @Test
  void testCreateDeleteArtifact() {
    cleanupArtifacts();

    val artifactName = "artifact-e2e-test-java-" + System.currentTimeMillis();
    val createResponse = artifactService.createArtifact(artifactName);
    assertThat(createResponse.getName()).isEqualTo(artifactName);

    val getAllResponse = artifactService.getAllArtifacts();
    assertThat(getAllResponse.getResources()).isNotEmpty();
    val artifact = getAllResponse.getResources().get(0);
    assertThat(artifact.getName()).isEqualTo(artifactName);
  }

  private void cleanupArtifacts() {
    for (val artifact : artifactService.getAllArtifacts().getResources()) {
      if (artifact.getName().startsWith("artifact-e2e-test-java")) {
        val name = artifact.getName();

        log.info("Deleting old artifact with name: {}", name);
        val deleteResponse = artifactService.deleteArtifact(name);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(ACCEPTED);
      }
    }
  }

  @Test
  void testGetAllScenarioConfigurations() {
    val response = scenarioConfigurationService.getAllScenarioConfigurations();
    assertThat(response.getResources()).isNotEmpty();
  }

  @Test
  void testCreateDeleteScenario() {
    cleanupScenarioConfigurations();

    val scenarioConfigName =
        "scenario-e2e-test-java-" + System.currentTimeMillis();
    val createResponse =
        scenarioConfigurationService.createScenarioConfiguration(scenarioConfigName);
    assertThat(createResponse.getName()).isEqualTo(scenarioConfigName);

    val getAllResponse = scenarioConfigurationService.getAllScenarioConfigurations();
    assertThat(getAllResponse.getResources()).isNotEmpty();
    val scenarioConfiguration = getAllResponse.getResources().get(0);
    assertThat(scenarioConfiguration.getName()).isEqualTo(scenarioConfigName);
  }

  private void cleanupScenarioConfigurations() {
    for (val scenarioConfiguration :
        scenarioConfigurationService.getAllScenarioConfigurations().getResources()) {
      if (scenarioConfiguration.getName().startsWith("scenario-e2e-test-java")) {
        val name = scenarioConfiguration.getName();

        log.info("Deleting old scenario configuration with name: {}", name);
        val deleteResponse = scenarioConfigurationService.deleteScenarioConfiguration(name);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(NO_CONTENT);
      }
    }
  }

  @Test
  void testPredict() {
    val response = predictionService.predict();
    assertThat(response.getPredictions()).isNotEmpty();
  }
}
