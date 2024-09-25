package com.sap.ai.sdk.core;

import static com.sap.ai.sdk.core.ApiClientResolver.DestinationProcessor.ADD_HEADER;
import static com.sap.ai.sdk.core.ApiClientResolver.DestinationProcessor.UPDATE_URI;

import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;
import javax.annotation.Nonnull;
import lombok.Value;

/** Utility class. */
@FunctionalInterface
public interface ApiClientResolver extends ApiClientContainer {

  /**
   * Focus on a specific deployment.
   *
   * @param choice the deployment choice.
   * @return the deployment context.
   */
  @Nonnull
  default ForDeployment forDeployment(@Nonnull final DeploymentChoice choice) {
    return processors ->
        resourceGroup ->
            () -> {
              final var id = choice.getDeploymentId(this, resourceGroup);
              final var destination = this.getDestination().asHttp();
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
    default ApiClientContainer resourceGroup(@Nonnull final String resourceGroup) {
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
    ApiClientContainer resourceGroup(@Nonnull final String resourceGroup);
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
}
