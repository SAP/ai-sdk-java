package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.model.GroundingModuleConfig;
import javax.annotation.Nonnull;

/**
 * Interface for grounding configurations.
 *
 * @link <a href="https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/grounding">SAP AI
 *     Core: Orchestration - Grounding</a>
 */
public interface GroundingProvider {

  /**
   * Create a grounding configuration.
   *
   * @return the grounding configuration
   */
  @Nonnull
  GroundingModuleConfig createConfig();
}
