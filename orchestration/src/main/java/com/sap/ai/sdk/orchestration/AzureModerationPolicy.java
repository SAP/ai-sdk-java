package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.client.model.AzureThreshold;
import javax.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** An Enum wrapping Azure thresholds with readable names. */
@Getter
@AllArgsConstructor
public enum AzureModerationPolicy {
  ALLOW_SAFE(AzureThreshold.NUMBER_0),
  ALLOW_SAFE_LOW(AzureThreshold.NUMBER_2),
  ALLOW_SAFE_LOW_MEDIUM(AzureThreshold.NUMBER_4),
  ALLOW_ALL(AzureThreshold.NUMBER_6);

  @Nonnull final AzureThreshold azureThreshold;
}
