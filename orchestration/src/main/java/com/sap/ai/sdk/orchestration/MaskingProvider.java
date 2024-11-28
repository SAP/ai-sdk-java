package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.generated.MaskingProviderConfig;
import javax.annotation.Nonnull;

/** Interface for masking configurations. */
public interface MaskingProvider {

  /**
   * Create a masking provider for the configuration.
   *
   * @return the masking provider
   */
  @Nonnull
  MaskingProviderConfig createConfig();
}
