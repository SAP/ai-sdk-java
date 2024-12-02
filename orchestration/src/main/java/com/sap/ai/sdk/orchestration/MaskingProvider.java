package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.model.MaskingProviderConfig;
import javax.annotation.Nonnull;

/** Interface for masking configurations.
 *
 * @link <a
 *    href="https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/data-masking">SAP
 *    AI Core: Orchestration - Data Masking</a>
 */
public interface MaskingProvider {

  /**
   * Create a masking provider for the configuration.
   *
   * @return the masking provider
   */
  @Nonnull
  MaskingProviderConfig createConfig();
}
