package com.sap.ai.sdk.core;

import com.sap.cloud.sdk.cloudplatform.connectivity.Destination;
import com.sap.cloud.sdk.services.openapi.apiclient.ApiClient;
import javax.annotation.Nonnull;

/** Container for an API client and destination. */
@FunctionalInterface
public interface AiCoreDestination {
  /**
   * Get the destination.
   *
   * @return the destination
   */
  @Nonnull
  Destination destination();

  /**
   * Get the API client.
   *
   * @return the API client
   */
  @Nonnull
  default ApiClient client() {
    final var destination = destination();
    return new ApiClient(destination);
  }
}
