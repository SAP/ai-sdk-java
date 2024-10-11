package com.sap.ai.sdk.core;

import com.sap.cloud.sdk.cloudplatform.connectivity.Destination;
import com.sap.cloud.sdk.cloudplatform.connectivity.exception.DestinationAccessException;
import com.sap.cloud.sdk.cloudplatform.connectivity.exception.DestinationNotFoundException;
import java.util.NoSuchElementException;
import java.util.function.Supplier;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
    return new AiCoreService(() -> destination);
  }

  /**
   * Set a specific deployment by id.
   *
   * @param deploymentId The deployment id to be used for AI Core service calls.
   * @return A new instance of the AI Core service.
   */
  @Nonnull
  public AiCoreDeployment forDeployment(@Nonnull final String deploymentId) {
    return new AiCoreDeployment(res -> deploymentId, this::destination);
  }

  /**
   * Set a specific deployment by model name.
   *
   * @param modelName The model name to be used for AI Core service calls.
   * @return A new instance of the AI Core service.
   */
  @Nonnull
  public AiCoreDeployment forDeploymentByModel(@Nonnull final String modelName)
      throws NoSuchElementException {
    return new AiCoreDeployment(this::destination).getDeploymentId(client(), modelName);
  }

  /**
   * Set a specific deployment by scenario id.
   *
   * @param scenarioId The scenario id to be used for AI Core service calls.
   * @return A new instance of the AI Core service.
   */
  @Nonnull
  public AiCoreDeployment forDeploymentByScenario(@Nonnull final String scenarioId)
      throws NoSuchElementException {
    return new AiCoreDeployment(this::destination).getDeploymentId(client(), scenarioId);
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
