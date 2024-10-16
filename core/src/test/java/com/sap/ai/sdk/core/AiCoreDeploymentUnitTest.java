package com.sap.ai.sdk.core;

import static org.assertj.core.api.Assertions.assertThat;

import com.sap.ai.sdk.core.client.model.AiDeployment;
import com.sap.ai.sdk.core.client.model.AiDeploymentDetails;
import com.sap.ai.sdk.core.client.model.AiDeploymentStatus;
import com.sap.ai.sdk.core.client.model.AiResourcesDetails;
import java.time.OffsetDateTime;
import java.util.Map;
import javax.annotation.Nonnull;
import org.junit.jupiter.api.Test;

public class AiCoreDeploymentUnitTest {

  @Test
  public void isDeploymentOfModel() {
    // Create a target model
    final AiModel targetModel =
        new AiModel() {
          @Nonnull
          @Override
          public String name() {
            return "gpt-4-32k";
          }

          @Override
          public String version() {
            return "1.0";
          }
        };

    // Create a deployment with a different model by version
    final var model = Map.of("model", Map.of("name", "gpt-4-32k", "version", "latest"));
    final var deployment =
        AiDeployment.create()
            .id("test-deployment")
            .configurationId("test-configuration")
            .status(AiDeploymentStatus.RUNNING)
            .createdAt(OffsetDateTime.parse("2024-01-22T17:57:23+00:00"))
            .modifiedAt(OffsetDateTime.parse("2024-02-08T08:41:23+00:00"));
    deployment.setDetails(
        AiDeploymentDetails.create().resources(AiResourcesDetails.create().backendDetails(model)));

    // Check if the deployment is of the target model
    assertThat(AiCoreDeployment.isDeploymentOfModel(targetModel, deployment)).isFalse();
  }

  @Test
  public void isDeploymentOfModelWithNullVersion() {
    // Create a target model
    final AiModel targetModel =
        new AiModel() {
          @Nonnull
          @Override
          public String name() {
            return "gpt-4-32k";
          }

          @Override
          public String version() {
            return null;
          }
        };

    // Create a deployment with a different model by version
    final var model = Map.of("model", Map.of("name", "gpt-4-32k", "version", "latest"));
    final var deployment =
        AiDeployment.create()
            .id("test-deployment")
            .configurationId("test-configuration")
            .status(AiDeploymentStatus.RUNNING)
            .createdAt(OffsetDateTime.parse("2024-01-22T17:57:23+00:00"))
            .modifiedAt(OffsetDateTime.parse("2024-02-08T08:41:23+00:00"));
    deployment.setDetails(
        AiDeploymentDetails.create().resources(AiResourcesDetails.create().backendDetails(model)));

    // Check if the deployment is of the target model
    assertThat(AiCoreDeployment.isDeploymentOfModel(targetModel, deployment)).isTrue();
  }
}
