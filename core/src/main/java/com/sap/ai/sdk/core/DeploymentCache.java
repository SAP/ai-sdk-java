package com.sap.ai.sdk.core;

import com.sap.ai.sdk.core.client.DeploymentApi;
import com.sap.ai.sdk.core.client.model.AiDeployment;
import com.sap.cloud.sdk.services.openapi.apiclient.ApiClient;
import com.sap.cloud.sdk.services.openapi.core.OpenApiRequestException;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

/**
 * Cache for deployment IDs. This class is used to get the deployment id for the orchestration
 * scenario or for a model.
 */
@Slf4j
class DeploymentCache {

  /** Cache for deployment ids. The key is the model name and the value is the deployment id. */
  private final Map<String, Set<AiDeployment>> cache = new ConcurrentHashMap<>();

  /**
   * Remove all entries from the cache then load all deployments into the cache.
   *
   * <p><b>Call this whenever a deployment is deleted.</b>
   *
   * @param client the API client to query deployments.
   * @param resourceGroup the resource group, usually "default".
   */
  void resetCache(@Nonnull final ApiClient client, @Nonnull final String resourceGroup) {
    cache.remove(resourceGroup);
    try {
      val deployments =
          new HashSet<>(new DeploymentApi(client).query(resourceGroup).getResources());
      cache.put(resourceGroup, deployments);
    } catch (final OpenApiRequestException e) {
      log.error("Failed to load deployments into cache", e);
    }
  }

  /**
   * Get the deployment id from the foundation model object. If there are multiple deployments of
   * the same model, the first one is returned.
   *
   * @param client the API client to maybe reset the cache if the deployment is not found.
   * @param resourceGroup the resource group, usually "default".
   * @param model the foundation model.
   * @return the deployment id.
   * @throws NoSuchElementException if no running deployment is found for the model.
   */
  @Nonnull
  String getDeploymentIdByModel(
      @Nonnull final ApiClient client,
      @Nonnull final String resourceGroup,
      @Nonnull final AiModel model)
      throws NoSuchElementException {
    return getDeploymentIdByModel(resourceGroup, model)
        .orElseGet(
            () -> {
              resetCache(client, resourceGroup);
              return getDeploymentIdByModel(resourceGroup, model)
                  .orElseThrow(
                      () ->
                          new NoSuchElementException(
                              "No running deployment found for model: " + model));
            });
  }

  private Optional<String> getDeploymentIdByModel(
      @Nonnull final String resourceGroup, @Nonnull final AiModel model) {
    return cache.getOrDefault(resourceGroup, new HashSet<>()).stream()
        .filter(deployment -> isDeploymentOfModel(model, deployment))
        .findFirst()
        .map(AiDeployment::getId);
  }

  /**
   * Get the deployment id from the scenario id. If there are multiple deployments of the * same
   * model, the first one is returned.
   *
   * @param client the API client to maybe reset the cache if the deployment is not found.
   * @param resourceGroup the resource group, usually "default".
   * @param scenarioId the scenario id, can be "orchestration".
   * @return the deployment id.
   * @throws NoSuchElementException if no running deployment is found for the scenario.
   */
  @Nonnull
  String getDeploymentIdByScenario(
      @Nonnull final ApiClient client,
      @Nonnull final String resourceGroup,
      @Nonnull final String scenarioId)
      throws NoSuchElementException {
    return getDeploymentIdByScenario(resourceGroup, scenarioId)
        .orElseGet(
            () -> {
              resetCache(client, resourceGroup);
              return getDeploymentIdByScenario(resourceGroup, scenarioId)
                  .orElseThrow(
                      () ->
                          new NoSuchElementException(
                              "No running deployment found for scenario: " + scenarioId));
            });
  }

  private Optional<String> getDeploymentIdByScenario(
      @Nonnull final String resourceGroup, @Nonnull final String scenarioId) {
    return cache.getOrDefault(resourceGroup, new HashSet<>()).stream()
        .filter(deployment -> scenarioId.equals(deployment.getScenarioId()))
        .findFirst()
        .map(AiDeployment::getId);
  }

  /**
   * This exists because getBackendDetails() is broken
   *
   * @param targetModel The target model object.
   * @param deployment The deployment.
   * @return true if the deployment is of the model.
   */
  protected static boolean isDeploymentOfModel(
      @Nonnull final AiModel targetModel, @Nonnull final AiDeployment deployment) {
    val deploymentDetails = deployment.getDetails();
    // The AI Core specification doesn't mention that this is nullable, but it can be.
    // Remove this check when the specification is fixed.
    if (deploymentDetails == null) {
      return false;
    }
    val resources = deploymentDetails.getResources();
    if (resources == null) {
      return false;
    }
    Object detailsObject = resources.getBackendDetails();
    if (detailsObject == null) {
      return false;
    }

    if (detailsObject instanceof Map<?, ?> details
        && details.get("model") instanceof Map<?, ?> model
        && model.get("name") instanceof String name
        && targetModel.name().equals(name)) {
      // if target version is not specified (null), any version is accepted, otherwise they must
      // match
      return targetModel.version() == null
          || Objects.equals(targetModel.version(), model.get("version"));
    }

    return false;
  }
}
