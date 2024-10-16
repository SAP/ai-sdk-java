package com.sap.ai.sdk.core;

import static com.sap.ai.sdk.core.AiCoreDeployment.isDeploymentOfModel;

import com.sap.ai.sdk.core.client.DeploymentApi;
import com.sap.ai.sdk.core.client.model.AiDeployment;
import com.sap.cloud.sdk.services.openapi.apiclient.ApiClient;
import com.sap.cloud.sdk.services.openapi.core.OpenApiRequestException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import javax.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;

/**
 * Cache for deployment IDs. This class is used to get the deployment id for the orchestration
 * scenario or for a model.
 */
@Slf4j
public class DeploymentCache {

  /** Cache for deployment ids. The key is the model name and the value is the deployment id. */
  protected static final List<AiDeployment> CACHE = new ArrayList<>();

  /**
   * Remove all entries from the cache then load all deployments into the cache.
   *
   * <p><b>Call this whenever a deployment is deleted.</b>
   *
   * @param client the API client to query deployments.
   * @param resourceGroup the resource group, usually "default".
   */
  public static void resetCache(
      @Nonnull final ApiClient client, @Nonnull final String resourceGroup) {
    clearCache();
    loadCache(client, resourceGroup);
  }

  /**
   * Remove all entries from the cache.
   *
   * <p><b>Call {@link #resetCache} whenever a deployment is deleted.</b>
   */
  protected static void clearCache() {
    CACHE.clear();
  }

  /**
   * Load all deployments into the cache
   *
   * <p><b>Call {@link #resetCache} whenever a deployment is deleted.</b>
   *
   * @param client the API client to query deployments.
   * @param resourceGroup the resource group, usually "default".
   */
  protected static void loadCache(
      @Nonnull final ApiClient client, @Nonnull final String resourceGroup) {
    try {
      final var deployments = new DeploymentApi(client).query(resourceGroup).getResources();
      CACHE.addAll(deployments);
    } catch (final OpenApiRequestException e) {
      log.error("Failed to load deployments into cache", e);
    }
  }

  /**
   * Get the deployment id from the foundation model name. If there are multiple deployments of the
   * same model, the first one is returned.
   *
   * @param client the API client to maybe reset the cache if the deployment is not found.
   * @param resourceGroup the resource group, usually "default".
   * @param modelName the name of the foundation model.
   * @return the deployment id.
   * @throws NoSuchElementException if no running deployment is found for the model.
   */
  @Nonnull
  public static String getDeploymentIdByModel(
      @Nonnull final ApiClient client,
      @Nonnull final String resourceGroup,
      @Nonnull final String modelName)
      throws NoSuchElementException {
    return getDeploymentIdByModel(modelName)
        .orElseGet(
            () -> {
              resetCache(client, resourceGroup);
              return getDeploymentIdByModel(modelName)
                  .orElseThrow(
                      () ->
                          new NoSuchElementException(
                              "No running deployment found for model: " + modelName));
            });
  }

  private static Optional<String> getDeploymentIdByModel(@Nonnull final String modelName) {
    return CACHE.stream()
        .filter(deployment -> isDeploymentOfModel(modelName, deployment))
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
  public static String getDeploymentIdByScenario(
      @Nonnull final ApiClient client,
      @Nonnull final String resourceGroup,
      @Nonnull final String scenarioId)
      throws NoSuchElementException {
    return getDeploymentIdByScenario(scenarioId)
        .orElseGet(
            () -> {
              resetCache(client, resourceGroup);
              return getDeploymentIdByScenario(scenarioId)
                  .orElseThrow(
                      () ->
                          new NoSuchElementException(
                              "No running deployment found for scenario: " + scenarioId));
            });
  }

  private static Optional<String> getDeploymentIdByScenario(@Nonnull final String scenarioId) {
    return CACHE.stream()
        .filter(deployment -> scenarioId.equals(deployment.getScenarioId()))
        .findFirst()
        .map(AiDeployment::getId);
  }
}
