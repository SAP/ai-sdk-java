package com.sap.ai.sdk.core;

import static com.sap.ai.sdk.core.AiCoreServiceWithDeployment.getDeploymentId;
import static com.sap.ai.sdk.core.AiCoreServiceWithDeployment.isDeploymentOfModel;

import com.sap.ai.sdk.core.client.model.AiDeployment;
import com.sap.cloud.sdk.cloudplatform.connectivity.Destination;
import com.sap.cloud.sdk.cloudplatform.connectivity.exception.DestinationAccessException;
import com.sap.cloud.sdk.cloudplatform.connectivity.exception.DestinationNotFoundException;
import java.util.function.Predicate;
import java.util.function.Supplier;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/** Connectivity convenience methods for AI Core. */
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class AiCoreService implements AiCoreServiceStub {

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
    return new AiCoreService(() -> destination);
  }

  /**
   * Set a specific deployment by id.
   *
   * @param deploymentId The deployment id to be used for AI Core service calls.
   * @return A new instance of the AI Core service.
   */
  @Nonnull
  public AiCoreServiceWithDeployment withDeployment(@Nonnull final String deploymentId) {
    return new AiCoreServiceWithDeployment(c -> deploymentId, c -> this.destination());
  }

  /**
   * Set a specific deployment by model name.
   *
   * @param modelName The model name to be used for AI Core service calls.
   * @return A new instance of the AI Core service.
   */
  @Nonnull
  public AiCoreServiceWithDeployment withDeploymentByModel(@Nonnull final String modelName) {
    final Predicate<AiDeployment> p = deployment -> isDeploymentOfModel(modelName, deployment);
    return new AiCoreServiceWithDeployment(c -> getDeploymentId(c, p), c -> this.destination());
  }

  /**
   * Set a specific deployment by scenario id.
   *
   * @param scenarioId The scenario id to be used for AI Core service calls.
   * @return A new instance of the AI Core service.
   */
  @Nonnull
  public AiCoreServiceWithDeployment withDeploymentByScenario(@Nonnull final String scenarioId) {
    final Predicate<AiDeployment> p = deployment -> scenarioId.equals(deployment.getScenarioId());
    return new AiCoreServiceWithDeployment(c -> getDeploymentId(c, p), c -> this.destination());
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
}
