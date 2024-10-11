package com.sap.ai.sdk.core;

import static com.sap.ai.sdk.core.AiCoreDeployment.getDeploymentId;
import static com.sap.ai.sdk.core.AiCoreDeployment.isDeploymentOfModel;
import static com.sap.ai.sdk.core.DestinationResolver.AI_CLIENT_TYPE_KEY;
import static com.sap.ai.sdk.core.DestinationResolver.AI_CLIENT_TYPE_VALUE;

import com.sap.ai.sdk.core.client.model.AiDeployment;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import com.sap.cloud.sdk.cloudplatform.connectivity.Destination;
import com.sap.cloud.sdk.cloudplatform.connectivity.DestinationProperties;
import com.sap.cloud.sdk.cloudplatform.connectivity.DestinationProperty;
import com.sap.cloud.sdk.cloudplatform.connectivity.exception.DestinationAccessException;
import com.sap.cloud.sdk.cloudplatform.connectivity.exception.DestinationNotFoundException;
import java.util.function.Predicate;
import java.util.function.Supplier;
import javax.annotation.Nonnull;

import com.sap.cloud.sdk.services.openapi.apiclient.ApiClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/** Connectivity convenience methods for AI Core. */
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class AiCoreService implements AiCoreDestination {

  // the destination to be used for AI Core service calls.
  @Nonnull private final Supplier<Destination> destination;

  /** Create a new instance of the AI Core service. */
  public AiCoreService() {
    this(AiCoreService::getDefaultDestination);
  }

  @Nonnull
  @Override
  public Destination destination() {
    return destination.get();
  }

  /**
   * Set a specific base destination.
   *
   * @param destination The destination to be used for AI Core service calls.
   * @return A new instance of the AI Core service.
   */
  @Nonnull
  public AiCoreService withDestination(@Nonnull final Destination destination) {

    final var newDestinationBuilder = DefaultHttpDestination.fromDestination(destination);
    final var newDestination = refineDestination(newDestinationBuilder, destination);
    return new AiCoreService(() -> newDestination);
  }

  @Nonnull
  protected Destination refineDestination(
      @Nonnull final DefaultHttpDestination.Builder builder,
      @Nonnull final DestinationProperties properties) {
    String uri = properties.get(DestinationProperty.URI).get();
    uri = StringUtils.appendIfMissing(uri, "/") + "v2/";
    return builder.uri(uri).property(AI_CLIENT_TYPE_KEY, AI_CLIENT_TYPE_VALUE).build();
  }

  /**
   * Set a specific deployment by id.
   *
   * @param deploymentId The deployment id to be used for AI Core service calls.
   * @return A new instance of the AI Core service.
   */
  @Nonnull
  public AiCoreDeployment forDeployment(@Nonnull final String deploymentId) {
    return new AiCoreDeployment(this, res -> deploymentId);
  }

  /**
   * Set a specific deployment by model name.
   *
   * @param modelName The model name to be used for AI Core service calls.
   * @return A new instance of the AI Core service.
   */
  @Nonnull
  public AiCoreDeployment forDeploymentByModel(@Nonnull final String modelName) {
    final Predicate<AiDeployment> p = deployment -> isDeploymentOfModel(modelName, deployment);
    return new AiCoreDeployment(this, res -> getDeploymentId(client(), res, p));
  }

  /**
   * Set a specific deployment by scenario id.
   *
   * @param scenarioId The scenario id to be used for AI Core service calls.
   * @return A new instance of the AI Core service.
   */
  @Nonnull
  public AiCoreDeployment forDeploymentByScenario(@Nonnull final String scenarioId) {
    final Predicate<AiDeployment> p = deployment -> scenarioId.equals(deployment.getScenarioId());
    return new AiCoreDeployment(this, res -> getDeploymentId(client(), res, p));
  }

  /**
   * Get a destination using the default service binding loading logic.
   *
   * @return The destination.
   * @throws DestinationAccessException If the destination cannot be accessed.
   * @throws DestinationNotFoundException If the destination cannot be found.
   */
  @Nonnull
  protected static Destination getDefaultDestination()
          throws DestinationAccessException, DestinationNotFoundException {
    final var serviceKey = System.getenv("AICORE_SERVICE_KEY");
    return DestinationResolver.getDestination(serviceKey);
  }
  @Nonnull
  protected static ApiClient getApiClient()
          throws DestinationAccessException, DestinationNotFoundException {
    final var serviceKey = System.getenv("AICORE_SERVICE_KEY");
    return DestinationResolver.getDestination(serviceKey);
  }
}
