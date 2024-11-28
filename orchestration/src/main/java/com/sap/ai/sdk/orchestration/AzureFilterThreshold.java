package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.generated.AzureThreshold;
import javax.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** An Enum wrapping Azure thresholds with readable names. */
@Getter
@AllArgsConstructor
public enum AzureFilterThreshold {
  /** Only safe content is allowed. */
  ALLOW_SAFE(AzureThreshold.NUMBER_0),

  /** Safe and low-risk content is allowed. */
  ALLOW_SAFE_LOW(AzureThreshold.NUMBER_2),

  /** Safe, low-risk, and medium-risk content is allowed. */
  ALLOW_SAFE_LOW_MEDIUM(AzureThreshold.NUMBER_4),

  /** All content is allowed. */
  ALLOW_ALL(AzureThreshold.NUMBER_6);

  @Nonnull final AzureThreshold azureThreshold;
}
