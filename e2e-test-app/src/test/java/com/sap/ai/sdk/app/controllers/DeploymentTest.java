package com.sap.ai.sdk.app.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import com.sap.ai.sdk.core.client.model.AiDeployment;
import com.sap.ai.sdk.core.client.model.AiDeploymentStatus;
import org.junit.jupiter.api.Test;

class DeploymentTest {
  /** Java end-to-end test specific configuration ID. "name":"config-java-e2e-test" */
  public static final String CONFIG_ID = "67e8d039-c7f1-4179-9f8f-60d158a36b0e";

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
