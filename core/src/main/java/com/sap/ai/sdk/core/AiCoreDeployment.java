package com.sap.ai.sdk.core;

import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import com.sap.cloud.sdk.cloudplatform.connectivity.Destination;
import com.sap.cloud.sdk.cloudplatform.connectivity.DestinationProperty;
import com.sap.cloud.sdk.services.openapi.apiclient.ApiClient;
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
    destinationSetHeaders(builder);
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
   */
  protected void destinationSetHeaders(@Nonnull final DefaultHttpDestination.Builder builder) {
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
   * Get the deployment id.
   *
   * @return The deployment id.
   */
  @Nonnull
  protected String getDeploymentId() {
    return deploymentId.apply(this);
  }
}
