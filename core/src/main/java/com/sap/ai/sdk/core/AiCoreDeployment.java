package com.sap.ai.sdk.core;

import com.sap.ai.sdk.core.client.DeploymentApi;
import com.sap.ai.sdk.core.client.model.AiDeployment;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import com.sap.cloud.sdk.cloudplatform.connectivity.Destination;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;
import com.sap.cloud.sdk.services.openapi.apiclient.ApiClient;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/** Connectivity convenience methods for AI Core with deployment. */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class AiCoreDeployment implements AiCoreDestination {
  private static final String AI_RESOURCE_GROUP = "URL.headers.AI-Resource-Group";

  // the delegating AI Core Service instance
  @Nonnull private final AiCoreServiceExtension service;

  // the destination handler to be used
  @Nonnull private final Supplier<Destination> destination;

  // the deployment id handler to be used, based on resource group
  @Nonnull private final Function<String, String> deploymentId;

  // the resource group, "default" if null
  @Getter(AccessLevel.PROTECTED)
  @Nonnull
  private final String resourceGroup;

  /**
   * Create a new instance of the AI Core service with a specific deployment id and destination.
   *
   * @param service The AI Core Service instance.
   * @param deploymentId The deployment id handler, based on resource group.
   */
  public AiCoreDeployment(
      @Nonnull final AiCoreService service, @Nonnull final Function<String, String> deploymentId) {
    this(service, service::destination, deploymentId, "default");
  }

  @Nonnull
  @Override
  public Destination destination() {
    final var dest = destination.get().asHttp();
    final var builder = DefaultHttpDestination.fromDestination(dest);
    return createDestination(builder, dest);
  }

  @Nonnull
  @Override
  public ApiClient client() {
    final var destination = destination();
    return service.createApiClient(destination);
  }

  /**
   * Set the resource group.
   *
   * @param resourceGroup The resource group.
   * @return A new instance of the AI Core service.
   */
  @Nonnull
  public AiCoreDeployment withResourceGroup(@Nonnull final String resourceGroup) {
    return new AiCoreDeployment(service, destination, deploymentId, resourceGroup);
  }

  /**
   * Set the destination.
   *
   * @param destination The destination.
   * @return A new instance of the AI Core service.
   */
  @Nonnull
  public AiCoreDeployment withDestination(@Nonnull final Destination destination) {
    return new AiCoreDeployment(service, () -> destination, deploymentId, resourceGroup);
  }

  /**
   * Update the destination builder.
   *
   * @param builder The new destination builder.
   * @param d The original destination.
   * @return The updated destination.
   */
  protected Destination createDestination(
      @Nonnull final DefaultHttpDestination.Builder builder, @Nonnull final HttpDestination d) {
    builder.uri(d.getUri().resolve("inference/deployments/%s/".formatted(getDeploymentId())));
    builder.property(AI_RESOURCE_GROUP, getResourceGroup());
    return service.createDestination(builder, d);
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
   * Get the deployment id from the scenario id. If there are multiple deployments of the same
   * scenario id, the first one is returned.
   *
   * @return the deployment id
   * @throws NoSuchElementException if no deployment is found for the scenario id.
   */
  @Nonnull
  protected static String getDeploymentId(
      @Nonnull final ApiClient client,
      @Nonnull final String resourceGroup,
      @Nonnull final Predicate<AiDeployment> predicate)
      throws NoSuchElementException {
    final var deployments = new DeploymentApi(client).query(resourceGroup);

    final var first =
        deployments.getResources().stream().filter(predicate).map(AiDeployment::getId).findFirst();
    return first.orElseThrow(
        () -> new NoSuchElementException("No deployment found with scenario id orchestration"));
  }
}
