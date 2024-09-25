package com.sap.ai.sdk.core;

import com.sap.ai.sdk.core.client.DeploymentApi;
import com.sap.ai.sdk.core.client.model.AiDeployment;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Predicate;
import javax.annotation.Nonnull;

@FunctionalInterface
public interface DeploymentChoice {
  @Nonnull
  String getDeploymentId(
      @Nonnull final ApiClientResolver resolver, @Nonnull final String resourceGroup);

  DeploymentChoice ORCHESTRATION = withScenario("orchestration");

  @Nonnull
  static DeploymentChoice withId(@Nonnull final String deploymentId) {
    return (resolver, resourceGroup) -> deploymentId;
  }

  @Nonnull
  static DeploymentChoice withModel(@Nonnull final String modelName) {
    final Predicate<AiDeployment> p = deployment -> isDeploymentOfModel(modelName, deployment);
    return (resolver, resourceGroup) -> getDeploymentId(resolver, resourceGroup, p);
  }

  @Nonnull
  static DeploymentChoice withScenario(@Nonnull final String scenarioId) {
    final Predicate<AiDeployment> p = deployment -> scenarioId.equals(deployment.getScenarioId());
    return (resolver, resourceGroup) -> getDeploymentId(resolver, resourceGroup, p);
  }

  /**
   * Get the deployment id from the scenario id. If there are multiple deployments of the same
   * scenario id, the first one is returned.
   *
   * @param resourceGroup the resource group.
   * @return the deployment id
   * @throws NoSuchElementException if no deployment is found for the scenario id.
   */
  @Nonnull
  private static String getDeploymentId(
      @Nonnull final ApiClientResolver resolver,
      @Nonnull final String resourceGroup,
      @Nonnull final Predicate<AiDeployment> predicate)
      throws NoSuchElementException {
    final var deploymentService = new DeploymentApi(resolver.getClient());
    final var deployments = deploymentService.deploymentQuery(resourceGroup);

    final var first =
        deployments.getResources().stream().filter(predicate).map(AiDeployment::getId).findFirst();
    return first.orElseThrow(
        () -> new NoSuchElementException("No deployment found with scenario id orchestration"));
  }

  /** This exists because getBackendDetails() is broken */
  private static boolean isDeploymentOfModel(
      @Nonnull final String modelName, @Nonnull final AiDeployment deployment) {
    final var deploymentDetails = deployment.getDetails();
    // The AI Core specification doesn't mention that this is nullable, but it can be.
    // Remove this check when the specification is fixed.
    if (deploymentDetails == null) {
      return false;
    }
    final var resources = deploymentDetails.getResources();
    if (resources == null) {
      return false;
    }
    if (!resources.getCustomFieldNames().contains("backend_details")) {
      return false;
    }
    final var detailsObject = resources.getCustomField("backend_details");

    if (detailsObject instanceof Map<?, ?> details
        && details.get("model") instanceof Map<?, ?> model
        && model.get("name") instanceof String name) {
      return modelName.equals(name);
    }
    return false;
  }
}