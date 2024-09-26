package com.sap.ai.sdk.core;

import static com.sap.ai.sdk.core.AiClientCustom.DestinationProcessor.ADD_HEADER;
import static com.sap.ai.sdk.core.AiClientCustom.DestinationProcessor.UPDATE_URI;

import com.sap.ai.sdk.core.client.DeploymentApi;
import com.sap.ai.sdk.core.client.model.AiDeployment;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import com.sap.cloud.sdk.cloudplatform.connectivity.Destination;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import javax.annotation.Nonnull;
import lombok.Value;

/** Utility class. */
@FunctionalInterface
public interface AiClientCustom extends AiClientAuto {

  /**
   * Get an API client resolver for destination.
   *
   * @param destination the destination.
   * @return the API client resolver.
   */
  @Nonnull
  default AiClientCustom withDestination(@Nonnull final Destination destination) {
    return () -> destination;
  }

  default ForDeployment forDeployment(@Nonnull final String deploymentId) {
    return getDestinationForDeployment((resolver, resourceGroup) -> deploymentId);
  }

  default ForDeployment forDeploymentByModel(@Nonnull final String modelName) {
    final Predicate<AiDeployment> p = deployment -> isDeploymentOfModel(modelName, deployment);
    return getDestinationForDeployment((r, resourceGroup) -> getDeploymentId(r, resourceGroup, p));
  }

  default ForDeployment forDeploymentByScenario(@Nonnull final String scenarioId) {
    final Predicate<AiDeployment> p = deployment -> scenarioId.equals(deployment.getScenarioId());
    return getDestinationForDeployment((r, resourceGroup) -> getDeploymentId(r, resourceGroup, p));
  }

  /**
   * Focus on a specific deployment.
   *
   * @param deploymentIdResolver the deployment choice.
   * @return the deployment context.
   */
  private ForDeployment getDestinationForDeployment(
      @Nonnull final BiFunction<AiClientCustom, String, String> deploymentIdResolver) {
    return processors ->
        resourceGroup ->
            () -> {
              final var id = deploymentIdResolver.apply(this, resourceGroup);
              final var destination = this.destination().asHttp();
              final var builder = DefaultHttpDestination.fromDestination(destination);
              final var context = new DestinationProcessor.Context(destination, id, resourceGroup);
              for (final DestinationProcessor processor : processors) {
                processor.process(builder, context);
              }
              return builder.build();
            };
  }

  /** Helper interface to focus on a specific deployment. */
  @FunctionalInterface
  interface ForDeployment {
    /**
     * Focus on a specific resource group.
     *
     * @param resourceGroup the resource group.
     * @return the client and destination builder.
     */
    @Nonnull
    default AiClientAuto resourceGroup(@Nonnull final String resourceGroup) {
      return withProcessors(UPDATE_URI, ADD_HEADER).resourceGroup(resourceGroup);
    }

    /**
     * Assign destination processors.
     *
     * @param processors the processors.
     * @return the client and destination builder.
     */
    @Nonnull
    WithProcessors withProcessors(@Nonnull final DestinationProcessor... processors);
  }

  /** Helper interface to focus on a specific resource group. */
  @FunctionalInterface
  interface WithProcessors {
    /**
     * Focus on a specific resource group.
     *
     * @param resourceGroup the resource group.
     * @return the client and destination builder.
     */
    @Nonnull
    AiClientAuto resourceGroup(@Nonnull final String resourceGroup);
  }

  /** Helper interface to process the destination. */
  @FunctionalInterface
  interface DestinationProcessor {

    /** Update the URI. */
    DestinationProcessor UPDATE_URI =
        (builder, context) ->
            builder.uri(
                context
                    .origin
                    .getUri()
                    .resolve("/v2/inference/deployments/%s/".formatted(context.deploymentId)));

    /** Add the AI-Resource-Group header. */
    DestinationProcessor ADD_HEADER =
        (builder, context) -> builder.header("AI-Resource-Group", context.resourceGroup);

    /**
     * Process the destination.
     *
     * @param builder the destination builder.
     * @param context the context.
     */
    void process(
        @Nonnull final DefaultHttpDestination.Builder builder, @Nonnull final Context context);

    /** Helper class to hold the context. */
    @Value
    class Context {
      HttpDestination origin;
      String deploymentId;
      String resourceGroup;
    }
  }

  /** This exists because getBackendDetails() is broken */
  private static boolean isDeploymentOfModel(
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
   * @param resourceGroup the resource group.
   * @return the deployment id
   * @throws NoSuchElementException if no deployment is found for the scenario id.
   */
  @Nonnull
  private static String getDeploymentId(
      @Nonnull final AiClientCustom resolver,
      @Nonnull final String resourceGroup,
      @Nonnull final Predicate<AiDeployment> predicate)
      throws NoSuchElementException {
    final var deploymentService = new DeploymentApi(resolver.client());
    final var deployments = deploymentService.deploymentQuery(resourceGroup);

    final var first =
        deployments.getResources().stream().filter(predicate).map(AiDeployment::getId).findFirst();
    return first.orElseThrow(
        () -> new NoSuchElementException("No deployment found with scenario id orchestration"));
  }
}
