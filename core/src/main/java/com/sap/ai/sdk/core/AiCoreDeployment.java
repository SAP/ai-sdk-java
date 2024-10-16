package com.sap.ai.sdk.core;

import com.sap.ai.sdk.core.client.model.AiDeployment;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import com.sap.cloud.sdk.cloudplatform.connectivity.Destination;
import com.sap.cloud.sdk.cloudplatform.connectivity.DestinationProperty;
import com.sap.cloud.sdk.services.openapi.apiclient.ApiClient;
import java.util.Map;
import java.util.function.Function;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/** Connectivity convenience methods for AI Core with deployment. */
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class AiCoreDeployment implements AiCoreDestination {

  private static final String AI_RESOURCE_GROUP = "URL.headers.AI-Resource-Group";

  // the delegating AI Core Service instance
  @Nonnull private final AiCoreService service;

  // the deployment id handler to be used, based on instance
  @Nonnull private final Function<AiCoreDeployment, String> deploymentId;

  // the resource group, "default" if null
  @Getter(AccessLevel.PROTECTED)
  @Nonnull
  private final String resourceGroup;

  /**
   * Default constructor with "default" resource group.
   *
   * @param service The AI Core service.
   * @param deploymentId The deployment id handler.
   */
  public AiCoreDeployment(
      @Nonnull final AiCoreService service,
      @Nonnull final Function<AiCoreDeployment, String> deploymentId) {
    this(service, deploymentId, "default");
  }

  @Nonnull
  @Override
  public Destination destination() {
    final var dest = service.baseDestinationHandler.apply(service);
    final var builder = service.builderHandler.apply(service, dest);
    destinationSetUrl(builder, dest);
    destinationSetHeaders(builder, dest);
    return builder.build();
  }

  @Nonnull
  @Override
  public ApiClient client() {
    final var destination = destination();
    return service.clientHandler.apply(service, destination);
  }

  /**
   * Update and set the URL for the destination.
   *
   * @param builder The destination builder.
   * @param dest The original destination reference.
   */
  protected void destinationSetUrl(
      @Nonnull final DefaultHttpDestination.Builder builder, @Nonnull final Destination dest) {
    String uri = dest.get(DestinationProperty.URI).get();
    if (!uri.endsWith("/")) {
      uri = uri + "/";
    }
    builder.uri(uri + "v2/inference/deployments/%s/".formatted(getDeploymentId()));
  }

  /**
   * Update and set the default request headers for the destination.
   *
   * @param builder The destination builder.
   * @param dest The original destination reference.
   */
  protected void destinationSetHeaders(
      @Nonnull final DefaultHttpDestination.Builder builder, @Nonnull final Destination dest) {
    builder.property(AI_RESOURCE_GROUP, getResourceGroup());
  }

  /**
   * Set the resource group.
   *
   * @param resourceGroup The resource group.
   * @return A new instance of the AI Core service.
   */
  @Nonnull
  public AiCoreDeployment withResourceGroup(@Nonnull final String resourceGroup) {
    return new AiCoreDeployment(service, deploymentId, resourceGroup);
  }

  /**
   * Set the destination.
   *
   * @param destination The destination.
   * @return A new instance of the AI Core service.
   */
  @Nonnull
  public AiCoreDeployment withDestination(@Nonnull final Destination destination) {
    return new AiCoreDeployment(service.withDestination(destination), deploymentId, resourceGroup);
  }

  /**
   * Get the deployment id.
   *
   * @return The deployment id.
   */
  @Nonnull
  protected String getDeploymentId() {
    return deploymentId.apply(this);
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
}
