package com.sap.ai.sdk.core;

import lombok.extern.slf4j.Slf4j;

/** Connectivity convenience methods for AI Core. */
@Slf4j
public class AiClient {

  /**
   * Get a destination pointing to the AI Core service.
   *
   * <p><b>Requires an AI Core service binding OR a service key in the environment variable {@code
   * AICORE_SERVICE_KEY}.</b>
   *
   * @return a destination pointing to the AI Core service.
   */
  public static AiClientAuto auto() {
    final var serviceKey = System.getenv("AICORE_SERVICE_KEY");
    return () -> DestinationResolver.getDestination(serviceKey);
  }

  public static AiClientCustom custom() {
    final var serviceKey = System.getenv("AICORE_SERVICE_KEY");
    return () -> DestinationResolver.getDestination(serviceKey);
  }
}
