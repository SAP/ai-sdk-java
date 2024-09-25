package com.sap.ai.sdk.core;

import static com.sap.ai.sdk.core.ApiClientResolver.DestinationProcessor.ADD_HEADER;
import static com.sap.ai.sdk.core.ApiClientResolver.DestinationProcessor.UPDATE_URI;

import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;
import javax.annotation.Nonnull;
import lombok.Value;

@FunctionalInterface
public interface ApiClientResolver extends ApiClientContainer {

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

  @FunctionalInterface
  interface ForDeployment {
    @Nonnull
    default ApiClientContainer resourceGroup(@Nonnull final String resourceGroup) {
      return withProcessors(UPDATE_URI, ADD_HEADER).resourceGroup(resourceGroup);
    }

    @Nonnull
    WithProcessors withProcessors(@Nonnull final DestinationProcessor... processors);
  }

  @FunctionalInterface
  interface WithProcessors {
    @Nonnull
    ApiClientContainer resourceGroup(@Nonnull final String resourceGroup);
  }

  @FunctionalInterface
  interface DestinationProcessor {
    void process(DefaultHttpDestination.Builder builder, Context context);

    DestinationProcessor UPDATE_URI =
        (builder, context) ->
            builder.uri(
                context
                    .origin
                    .getUri()
                    .resolve("/v2/inference/deployments/%s/".formatted(context.deploymentId)));
    DestinationProcessor ADD_HEADER =
        (builder, context) -> builder.header("AI-Resource-Group", context.resourceGroup);

    @Value
    class Context {
      HttpDestination origin;
      String deploymentId;
      String resourceGroup;
    }
  }
}
