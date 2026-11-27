package com.sap.ai.sdk.app.controllers;

import static com.sap.ai.sdk.core.model.AiDeploymentTargetStatus.STOPPED;
import static com.sap.ai.sdk.tabular.orchestration.generated.model.TFMEnum.SAP_RPT_1_6;
import static org.assertj.core.api.Assertions.assertThat;

import com.sap.ai.sdk.core.client.ConfigurationApi;
import com.sap.ai.sdk.core.client.DeploymentApi;
import com.sap.ai.sdk.core.model.AiConfigurationBaseData;
import com.sap.ai.sdk.core.model.AiDeployment;
import com.sap.ai.sdk.core.model.AiDeploymentCreationRequest;
import com.sap.ai.sdk.core.model.AiDeploymentModificationRequest;
import com.sap.ai.sdk.core.model.AiDeploymentStatus;
import com.sap.ai.sdk.core.model.AiParameterArgumentBinding;
import org.junit.jupiter.api.Test;

class DeploymentTest {
  /** Java end-to-end test specific configuration ID. "name":"config-java-e2e-test-gpt-5" */
  public static final String CONFIG_ID = "c9311361-d8dc-440a-9c9a-7ecd8eea5f88";

  /**
   * Manual execution to create a new config for a new model when the current model gets deprecated.
   *
   * <p>1) Run the below method with a non-deprecated model.
   *
   * <p>2) Copy the printed ID into CONFIG_ID above.
   */
  // @Test
  void createConfiguration() {
    String modelToDeploy = SAP_RPT_1_6.getValue();
    var response =
        new ConfigurationApi()
            .create(
                "default",
                AiConfigurationBaseData.create()
                    .name("config-java-e2e-test-" + modelToDeploy)
                    .executableId("azure-openai")
                    .scenarioId("foundation-models")
                    .addParameterBindingsItem(
                        AiParameterArgumentBinding.create().key("modelName").value(modelToDeploy)));
    System.out.println("Created configuration with ID: " + response.getId());
  }

  @Test
  void createDeployment() {
    // Create a deployment from the configuration
    final var deploymentCreationRequest =
        AiDeploymentCreationRequest.create().configurationId(CONFIG_ID);

    new DeploymentApi().create("default", deploymentCreationRequest);
  }

  @Test
  void deleteDeployment() {
    new DeploymentApi()
        .modify(
            "default",
            "df9fdb3f93a25049",
            AiDeploymentModificationRequest.create().targetStatus(STOPPED));
    new DeploymentApi().delete("default", "df9fdb3f93a25049");
  }

  @Test
  void deletedDeployment() {
    // new DeploymentApi().modify("default", "decf2bc4ca20dbcf",
    // AiDeploymentModificationRequest.create().targetStatus(STOPPED));
    new DeploymentApi().delete("default", "decf2bc4ca20dbcf");
  }

  @Test
  void createDelete() {
    final var controller = new DeploymentController();

    final var deleteResponse = controller.createAndDeleteDeploymentByConfigId(CONFIG_ID);

    // check if the deployment is deleted by listing all test deployments
    final var allJavaTestDeployments = controller.getAllByConfigId(CONFIG_ID);
    final var deletedDeployment =
        allJavaTestDeployments.stream()
            .filter(deployment -> deployment.getId().equals(deleteResponse.getId()))
            .toList();

    assertThat(deletedDeployment).hasSize(1);
    assertThat(deletedDeployment.get(0).getTargetStatus())
        .isEqualTo(AiDeployment.TargetStatusEnum.DELETED);
  }

  @Test
  void getAll() {
    final var controller = new DeploymentController();

    final var result = controller.getAll();
    final var deploymentList = result.getResources();

    assertThat(result.getCount()).isEqualTo(deploymentList.size());
    assertThat(deploymentList.size()).isGreaterThan(1);
    for (var deployment : deploymentList) {
      if (deployment.getStatus() == AiDeploymentStatus.RUNNING) {
        assertThat(deployment.getConfigurationId()).isNotEmpty();
        assertThat(deployment.getConfigurationName()).isNotEmpty();
        assertThat(deployment.getCreatedAt()).isInThePast();
        assertThat(deployment.getDeploymentUrl()).isNotEmpty();
        assertThat(deployment.getDetails()).isNotNull();
        assertThat(deployment.getId()).isNotEmpty();
        assertThat(deployment.getLastOperation()).isNotNull();
        assertThat(deployment.getLatestRunningConfigurationId()).isNotEmpty();
        assertThat(deployment.getModifiedAt()).isInThePast();
        deployment.getStatusMessage(); // TODO: investigate
        assertThat(deployment.getScenarioId()).isNotEmpty();
        assertThat(deployment.getStartTime()).isInThePast();
        assertThat(deployment.getSubmissionTime()).isInThePast();
        assertThat(deployment.getTargetStatus()).isEqualTo(AiDeployment.TargetStatusEnum.RUNNING);
      }
    }
  }
}
