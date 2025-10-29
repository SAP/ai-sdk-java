package com.sap.ai.sdk.core;

import com.sap.ai.sdk.core.client.DeploymentApi;
import com.sap.ai.sdk.core.model.AiDeployment;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

/**
 * Cache for deployment IDs. This class is used to get the deployment id for the orchestration
 * scenario or for a model.
 */
@Slf4j
@RequiredArgsConstructor
class DeploymentResolver {

  private static final Map<String, Set<AiDeployment>> DEFAULT_GLOBAL_CACHE =
      new ConcurrentHashMap<>();

  @Nonnull private final AiCoreService service;

  /** Cache for deployment ids. The key is the model name and the value is the deployment id. */
  @Nonnull private final Map<String, Set<AiDeployment>> cache;

  DeploymentResolver(@Nonnull final AiCoreService service) {
    this(service, DEFAULT_GLOBAL_CACHE);
  }

  /**
   * Remove all entries from the cache then load all deployments into the cache.
   *
   * <p><b>Call this whenever a deployment is deleted.</b>
   *
   * @param resourceGroup the resource group, usually "default".
   */
  void reloadDeployments(@Nonnull final String resourceGroup) {
    cache.remove(resourceGroup);
    try {
      val apiClient = new DeploymentApi(service);
      val deployments = new HashSet<>(apiClient.query(resourceGroup).getResources());
      log.info("Found {} deployments in resource group '{}'", deployments.size(), resourceGroup);
      cache.put(resourceGroup, deployments);
    } catch (final RuntimeException e) {
      throw new DeploymentResolutionException(
          "Failed to load deployments for resource group " + resourceGroup, e);
    }
  }

  /**
   * Get the deployment id from the foundation model object. If there are multiple deployments of
   * the same model, the first one is returned.
   *
   * @param resourceGroup the resource group, usually "default".
   * @param model the foundation model.
   * @return the deployment id.
   * @throws DeploymentResolutionException if no running deployment is found for the model.
   */
  @Nonnull
  String getDeploymentIdByModel(@Nonnull final String resourceGroup, @Nonnull final AiModel model)
      throws DeploymentResolutionException {
    final Predicate<AiDeployment> predicate = deployment -> isDeploymentOfModel(model, deployment);
    return resolveDeployment(resourceGroup, predicate)
        .orElseThrow(
            () ->
                new DeploymentResolutionException(
                    "No running deployment found for model: " + model.name()));
  }

  /**
   * Get the deployment id from the scenario id. If there are multiple deployments of the * same
   * model, the first one is returned.
   *
   * @param resourceGroup the resource group, usually "default".
   * @param scenarioId the scenario id, can be "orchestration".
   * @return the deployment id.
   * @throws DeploymentResolutionException if no running deployment is found for the scenario.
   */
  @Nonnull
  String getDeploymentIdByScenario(
      @Nonnull final String resourceGroup, @Nonnull final String scenarioId)
      throws DeploymentResolutionException {
    final Predicate<AiDeployment> predicate =
        deployment -> scenarioId.equals(deployment.getScenarioId());
    return resolveDeployment(resourceGroup, predicate)
        .orElseThrow(
            () ->
                new DeploymentResolutionException(
                    "No running deployment found for scenario: " + scenarioId));
  }

  @Nonnull
  Optional<String> resolveDeployment(
      @Nonnull final String resourceGroup, @Nonnull final Predicate<AiDeployment> predicate) {
    var deployment = getCachedDeployment(resourceGroup, predicate);
    if (deployment.isPresent()) {
      return deployment;
    }
    // double-checked locking to allow for safe cache resets under parallel access
    synchronized (cache) {
      deployment = getCachedDeployment(resourceGroup, predicate);
      if (deployment.isPresent()) {
        return deployment;
      }
      reloadDeployments(resourceGroup);
      return getCachedDeployment(resourceGroup, predicate);
    }
  }

  private Optional<String> getCachedDeployment(
      @Nonnull final String resourceGroup, @Nonnull final Predicate<AiDeployment> predicate) {
    return cache.getOrDefault(resourceGroup, new HashSet<>()).stream()
        .filter(predicate)
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
    final Object detailsObject = resources.getBackendDetails();
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
