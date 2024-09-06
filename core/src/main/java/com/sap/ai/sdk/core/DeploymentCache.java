package com.sap.ai.sdk.core;

import static com.sap.ai.sdk.core.Core.getClient;

import com.sap.ai.sdk.core.client.DeploymentApi;
import com.sap.ai.sdk.core.client.model.AiDeployment;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import javax.annotation.Nonnull;

class DeploymentCache {
  private static final DeploymentApi API = new DeploymentApi(getClient());

  /**
   * Cache for deployment ids. The key is the scenario + model name and the value is the deployment
   * id.
   */
  private static final Map<String, String> CACHE = new HashMap<>();

  static {
    final var deployments = API.deploymentQuery("default").getResources();
    deployments.forEach(
        deployment ->
            CACHE.put(deployment.getScenarioId() + getModelName(deployment), deployment.getId()));
  }

  /**
   * Get the deployment id for the given scenario or model name.
   *
   * @param resourceGroup the resource group, usually "default".
   * @param name the scenario or model name.
   * @return the deployment id.
   */
  public static String getDeploymentId(
      @Nonnull final String resourceGroup, @Nonnull final String name) {
    return CACHE.computeIfAbsent(
        name,
        n -> {
          if ("orchestration".equals(n)) {
            return getOrchestrationDeployment(resourceGroup);
          } else {
            return getDeploymentForModel(resourceGroup, name);
          }
        });
  }

  /**
   * Get the deployment id from the scenario id. If there are multiple deployments of the same
   * scenario id, the first one is returned.
   *
   * @param resourceGroup the resource group.
   * @return the deployment id
   * @throws NoSuchElementException if no deployment is found for the scenario id.
   */
  private static String getOrchestrationDeployment(@Nonnull final String resourceGroup)
      throws NoSuchElementException {
    final var deployments =
        new DeploymentApi(getClient())
            .deploymentQuery(
                resourceGroup, null, null, "orchestration", "RUNNING", null, null, null);

    return deployments.getResources().stream()
        .map(AiDeployment::getId)
        .findFirst()
        .orElseThrow(
            () ->
                new NoSuchElementException(
                    "No running deployment found with scenario id \"orchestration\""));
  }

  /**
   * Get the deployment id from the model name. If there are multiple deployments of the same model,
   * the first one is returned.
   *
   * @param modelName the model name.
   * @param resourceGroup the resource group.
   * @return the deployment id
   * @throws NoSuchElementException if no deployment is found for the model name.
   */
  private static String getDeploymentForModel(
      @Nonnull final String resourceGroup, @Nonnull final String modelName)
      throws NoSuchElementException {
    final var deployments =
        new DeploymentApi(getClient())
            .deploymentQuery(
                resourceGroup, null, null, "foundation-models", "RUNNING", null, null, null);

    return deployments.getResources().stream()
        .filter(deployment -> modelName.equals(getModelName(deployment)))
        .map(AiDeployment::getId)
        .findFirst()
        .orElseThrow(
            () ->
                new NoSuchElementException(
                    "No running deployment found with model name " + modelName));
  }

  /** This exists because getBackendDetails() is broken */
  private static String getModelName(@Nonnull final AiDeployment deployment) {
    final var deploymentDetails = deployment.getDetails();
    // The AI Core specification doesn't mention that this is nullable, but it can be.
    // Remove this check when the specification is fixed.
    if (deploymentDetails == null) {
      return "";
    }
    final var resources = deploymentDetails.getResources();
    if (resources == null) {
      return "";
    }
    if (!resources.getCustomFieldNames().contains("backend_details")) {
      return "";
    }
    final var detailsObject = resources.getCustomField("backend_details");

    if (detailsObject instanceof Map<?, ?> details
        && details.get("model") instanceof Map<?, ?> model
        && model.get("name") instanceof String name) {
      return name;
    }
    return "";
  }
}
