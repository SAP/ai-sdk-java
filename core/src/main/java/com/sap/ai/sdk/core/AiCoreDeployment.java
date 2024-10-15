package com.sap.ai.sdk.core;

import static com.sap.ai.sdk.core.DestinationResolver.AI_CLIENT_TYPE_KEY;
import static com.sap.ai.sdk.core.DestinationResolver.AI_CLIENT_TYPE_VALUE;

import com.sap.ai.sdk.core.client.DeploymentApi;
import com.sap.ai.sdk.core.client.model.AiDeployment;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import com.sap.cloud.sdk.cloudplatform.connectivity.Destination;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;
import com.sap.cloud.sdk.services.openapi.apiclient.ApiClient;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/** Connectivity convenience methods for AI Core with deployment. */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class AiCoreDeployment implements AiCoreDestination {
  private static final String AI_RESOURCE_GROUP = "URL.headers.AI-Resource-Group";

  private static final Map<ApiClient, DeploymentCache> DEPLOYMENT_CACHES = new HashMap<>();

  // the deployment id handler to be used, based on resource group
  @Nonnull private final Function<String, String> deploymentId;

  // the base destination handler to be used
  @Nonnull private final Supplier<Destination> destination;

  // the resource group, "default" if null
  @Getter(AccessLevel.PROTECTED)
  @Nonnull
  private final String resourceGroup;

  /**
   * Create a new instance of the AI Core service with a specific deployment id and destination.
   *
   * @param deploymentId The deployment id handler, based on resource group.
   * @param destination The destination handler.
   */
  public AiCoreDeployment(
      @Nonnull final Function<String, String> deploymentId,
      @Nonnull final Supplier<Destination> destination) {
    this(deploymentId, destination, "default");
  }

  @Nonnull
  @Override
  public Destination destination() {
    final var dest = destination.get().asHttp();
    final var builder = DefaultHttpDestination.fromDestination(dest);
    updateDestination(builder, dest);
    return builder.build();
  }

  /**
   * Set the resource group.
   *
   * @param resourceGroup The resource group.
   * @return A new instance of the AI Core service.
   */
  @Nonnull
  public AiCoreDeployment withResourceGroup(@Nonnull final String resourceGroup) {
    return new AiCoreDeployment(deploymentId, destination, resourceGroup);
  }

  /**
   * Set the destination.
   *
   * @param destination The destination.
   * @return A new instance of the AI Core service.
   */
  @Nonnull
  public AiCoreDeployment withDestination(@Nonnull final Destination destination) {
    return new AiCoreDeployment(deploymentId, () -> destination, resourceGroup);
  }

  /**
   * Update the destination builder.
   *
   * @param builder The new destination builder.
   * @param d The original destination.
   */
  protected void updateDestination(
      @Nonnull final DefaultHttpDestination.Builder builder, @Nonnull final HttpDestination d) {
    builder.uri(d.getUri().resolve("/v2/inference/deployments/%s/".formatted(getDeploymentId())));
    builder.property(AI_CLIENT_TYPE_KEY, AI_CLIENT_TYPE_VALUE);
    builder.property(AI_RESOURCE_GROUP, getResourceGroup());
  }

  /**
   * Get the deployment id.
   *
   * @return The deployment id.
   */
  @Nonnull
  protected String getDeploymentId() {
    return deploymentId.apply(getResourceGroup());
  }

  /**
   * This exists because getBackendDetails() is broken
   *
   * @param modelName The model name.
   * @param deployment The deployment.
   * @return true if the deployment is of the model.
   */
  protected static boolean isDeploymentOfModel(
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
    Object detailsObject = resources.getBackendDetails();
    // workaround for AIWDF-2124
    if (detailsObject == null) {
      if (!resources.getCustomFieldNames().contains("backend_details")) {
        return false;
      }
      detailsObject = resources.getCustomField("backend_details");
    }

    if (detailsObject instanceof Map<?, ?> details
        && details.get("model") instanceof Map<?, ?> model
        && model.get("name") instanceof String name) {
      return modelName.equals(name);
    }
    return false;
  }

  /**
   * Get the deployment id from the foundation model name. If there are multiple deployments of the
   * same model, the first one is returned.
   *
   * @param resourceGroup the resource group, usually "default".
   * @param modelName the name of the foundation model.
   * @return the deployment id.
   * @throws NoSuchElementException if no running deployment is found for the model.
   */
  @Nonnull
  protected static String getDeploymentIdByModel(
      @Nonnull final ApiClient client,
      @Nonnull final String resourceGroup,
      @Nonnull final String modelName)
      throws NoSuchElementException {
    final var deploymentCache =
        DEPLOYMENT_CACHES.computeIfAbsent(
            client, c -> new DeploymentCache(new DeploymentApi(client), resourceGroup));
    System.out.println("DEPLOYMENT_CACHES size" + DEPLOYMENT_CACHES.size());
    return deploymentCache.getDeploymentIdByModel(resourceGroup, modelName);
  }

  /**
   * Get the deployment id from the scenario id. If there are multiple deployments of the * same
   * model, the first one is returned.
   *
   * @param client The API client to do HTTP requests to AI Core.
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
    final var deploymentCache =
        DEPLOYMENT_CACHES.computeIfAbsent(
            client, c -> new DeploymentCache(new DeploymentApi(client), resourceGroup));
    return deploymentCache.getDeploymentIdByScenario(resourceGroup, scenarioId);
  }
}
