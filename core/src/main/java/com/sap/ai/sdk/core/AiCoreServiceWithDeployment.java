package com.sap.ai.sdk.core;

import com.sap.ai.sdk.core.client.DeploymentApi;
import com.sap.ai.sdk.core.client.model.AiDeployment;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import com.sap.cloud.sdk.cloudplatform.connectivity.Destination;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Predicate;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/** Connectivity convenience methods for AI Core with deployment. */
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class AiCoreServiceWithDeployment implements AiCoreServiceStub {

  // the deployment id to be used
  @Nonnull private final Function<AiCoreServiceWithDeployment, String> deploymentId;

  // the base destination to be used
  @Nonnull private final Function<AiCoreServiceWithDeployment, Destination> destination;

  // the resource group, "default" if null
  @Nullable private final Function<AiCoreServiceWithDeployment, String> resourceGroup;

  /**
   * Create a new instance of the AI Core service with a specific deployment id and destination.
   *
   * @param deploymentId The deployment id handler.
   * @param destination The destination handler.
   */
  public AiCoreServiceWithDeployment(
      @Nonnull final Function<AiCoreServiceWithDeployment, String> deploymentId,
      @Nonnull final Function<AiCoreServiceWithDeployment, Destination> destination) {
    this(deploymentId, destination, null);
  }

  @Nonnull
  @Override
  public Destination destination() {
    final var dest = destination.apply(this).asHttp();
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
  public AiCoreServiceWithDeployment withResourceGroup(@Nonnull final String resourceGroup) {
    return withResourceGroup((core) -> resourceGroup);
  }

  /**
   * Set the resource group.
   *
   * @param resourceGroup The resource group handler.
   * @return A new instance of the AI Core service.
   */
  @Nonnull
  public AiCoreServiceWithDeployment withResourceGroup(
      @Nonnull final Function<AiCoreServiceWithDeployment, String> resourceGroup) {
    return new AiCoreServiceWithDeployment(deploymentId, destination, resourceGroup);
  }

  /**
   * Set the destination.
   *
   * @param destination The destination.
   * @return A new instance of the AI Core service.
   */
  @Nonnull
  public AiCoreServiceWithDeployment withDestination(@Nonnull final Destination destination) {
    return withDestination((core) -> destination);
  }

  /**
   * Set the destination.
   *
   * @param destination The destination handler.
   * @return A new instance of the AI Core service.
   */
  @Nonnull
  public AiCoreServiceWithDeployment withDestination(
      @Nonnull final Function<AiCoreServiceWithDeployment, Destination> destination) {
    return new AiCoreServiceWithDeployment(deploymentId, destination, resourceGroup);
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
    builder.header("AI-Resource-Group", getResourceGroup());
  }

  /**
   * Get the resource group.
   *
   * @return The resource group.
   */
  @Nonnull
  protected String getResourceGroup() {
    return resourceGroup == null ? "default" : resourceGroup.apply(this);
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

  /** This exists because getBackendDetails() is broken */
  static boolean isDeploymentOfModel(
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
  static String getDeploymentId(
      @Nonnull final AiCoreServiceWithDeployment core,
      @Nonnull final Predicate<AiDeployment> predicate)
      throws NoSuchElementException {
    final var deploymentService = new DeploymentApi(core.client());
    final var deployments = deploymentService.query(core.getDeploymentId());

    final var first =
        deployments.getResources().stream().filter(predicate).map(AiDeployment::getId).findFirst();
    return first.orElseThrow(
        () -> new NoSuchElementException("No deployment found with scenario id orchestration"));
  }
}
