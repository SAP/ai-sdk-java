package com.sap.ai.sdk.core;

import static com.sap.ai.sdk.core.DeploymentChoice.ORCHESTRATION;
import static com.sap.ai.sdk.core.DeploymentChoice.withId;
import static com.sap.ai.sdk.core.DeploymentChoice.withModel;

import com.google.common.annotations.Beta;
import com.sap.cloud.sdk.cloudplatform.connectivity.Destination;
import com.sap.cloud.sdk.services.openapi.apiclient.ApiClient;
import java.util.concurrent.Callable;
import javax.annotation.Nonnull;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/** Connectivity convenience methods for AI Core. */
@Slf4j
public class Core implements ApiClientResolver {

  @Getter private static Core instance = new Core();

  /**
   * ONLY USE FOR TESTING.
   *
   * @param core
   * @param callable
   * @return
   * @param <T>
   * @throws Exception
   */
  @Beta
  public static synchronized <T> T executeWithCore(Core core, Callable<T> callable)
      throws Exception {
    Core oldInstance = instance;
    instance = core;
    try {
      return callable.call();
    } finally {
      instance = oldInstance;
    }
  }

  @Nonnull
  public ApiClientResolver withDestination(@Nonnull final Destination destination) {
    return () -> destination;
  }

  /**
   * <b>Requires an AI Core service binding.</b>
   *
   * @param resourceGroup the resource group.
   * @return a generic <code>Orchestration</code> ApiClient.
   */
  @Nonnull
  @Deprecated
  public static ApiClient getOrchestrationClient(@Nonnull final String resourceGroup) {
    return getInstance().forDeployment(ORCHESTRATION).resourceGroup(resourceGroup).getClient();
  }

  /**
   * Get a generic <code>AI Core</code> ApiClient <b>for testing purposes</b>.
   *
   * @param destination The destination to use.
   * @return a generic <code>AI Core</code> ApiClient.
   */
  @Nonnull
  @Deprecated
  public static ApiClient getClient(@Nonnull final Destination destination) {
    return getInstance().withDestination(destination).getClient();
  }

  /**
   * Get a destination pointing to the AI Core service.
   *
   * <p><b>Requires an AI Core service binding OR a service key in the environment variable {@code
   * AICORE_SERVICE_KEY}.</b>
   *
   * @return a destination pointing to the AI Core service.
   */
  @Nonnull
  @Override
  public Destination getDestination() {
    final var serviceKey = System.getenv("AICORE_SERVICE_KEY");
    return DestinationResolver.getDestination(serviceKey);
  }

  /**
   * Get a destination pointing to the inference endpoint of a deployment on AI Core. <b>Requires an
   * AI Core service binding.</b>
   *
   * @param deploymentId The deployment id.
   * @param resourceGroup The resource group.
   * @return a destination that can be used for inference calls.
   */
  @Nonnull
  @Deprecated
  public static Destination getDestinationForDeployment(
      @Nonnull final String deploymentId, @Nonnull final String resourceGroup) {
    return getInstance()
        .forDeployment(withId(deploymentId))
        .resourceGroup(resourceGroup)
        .getDestination();
  }

  /**
   * Get a destination pointing to the inference endpoint of a deployment on AI Core. <b>Requires an
   * AI Core service binding.</b>
   *
   * @param modelName The name of the foundation model that is used by a deployment.
   * @param resourceGroup The resource group.
   * @return a destination that can be used for inference calls.
   */
  @Nonnull
  @Deprecated
  public static Destination getDestinationForModel(
      @Nonnull final String modelName, @Nonnull final String resourceGroup) {
    return getInstance()
        .forDeployment(withModel(modelName))
        .resourceGroup(resourceGroup)
        .getDestination();
  }
}
