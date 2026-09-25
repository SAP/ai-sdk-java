package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.model.GroundingModuleConfig;
import javax.annotation.Nonnull;

/**
 * API Contract for grounding configurations.
 *
 * <p><a href="https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/grounding">SAP AI
 * Core: Orchestration - Grounding</a>
 */
public abstract class GroundingProvider {

  /**
   * Internal class. Package-local inheritance only. Not supposed to be implemented outside of AI
   * SDK.
   */
  GroundingProvider() {}

  /**
   * Create a grounding configuration.
   *
   * @return the grounding configuration
   */
  @Nonnull
  abstract GroundingModuleConfig createConfig();
}
