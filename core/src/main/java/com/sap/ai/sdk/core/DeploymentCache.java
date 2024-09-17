package com.sap.ai.sdk.core;

import com.sap.ai.sdk.core.client.DeploymentApi;
import com.sap.ai.sdk.core.client.model.AiDeployment;
import com.sap.cloud.sdk.services.openapi.core.OpenApiRequestException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import javax.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;

/**
 * Cache for deployment IDs. This class is used to get the deployment id for the orchestration
 * scenario or for a model.
 */
@Slf4j
public class DeploymentCache {
  /** The client to use for deployment queries. */
  static DeploymentApi API;

  /** Cache for deployment ids. The key is the model name and the value is the deployment id. */
  private static final Map<String, String> CACHE = new HashMap<>();

  static boolean isLoaded() {
    return API != null;
  }

  /**
   * Eagerly load the deployment cache with the given client.
   *
   * @param client the deployment client.
   */
  public static void eagerlyLoaded(@Nonnull final DeploymentApi client) {
    API = client;
    resetCache();
  }

  /**
   * Lazy load the deployment cache with the given client.
   *
   * @param client the deployment client.
   */
  public static void lazyLoaded(@Nonnull final DeploymentApi client) {
    API = client;
  }

  /**
   * Remove all entries from the cache and reload all deployments.
   *
   * <p><b>Call this method whenever a deployment is deleted.</b>
   */
  public static void resetCache() {
    CACHE.clear();
    try {
      final var deployments = API.deploymentQuery("default").getResources();
      deployments.forEach(deployment -> CACHE.put(getModelName(deployment), deployment.getId()));
    } catch (final OpenApiRequestException e) {
      log.error("Failed to load deployments into cache", e);
    }
  }

  /**
   * Get the deployment id for the orchestration scenario or any foundation model.
   *
   * @param resourceGroup the resource group, usually "default".
   * @param name "orchestration" or the model name.
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
        API.deploymentQuery(
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
   * @param resourceGroup the resource group.
   * @param modelName the model name.
   * @return the deployment id
   * @throws NoSuchElementException if no deployment is found for the model name.
   */
  private static String getDeploymentForModel(
      @Nonnull final String resourceGroup, @Nonnull final String modelName)
      throws NoSuchElementException {
    final var deployments =
        API.deploymentQuery(
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
    if ("orchestration".equals(deployment.getScenarioId())) {
      return "orchestration";
    }
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
