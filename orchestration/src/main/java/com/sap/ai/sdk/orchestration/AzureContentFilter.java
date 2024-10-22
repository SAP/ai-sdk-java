package com.sap.ai.sdk.orchestration;

import static com.sap.ai.sdk.orchestration.client.model.AzureThreshold.fromValue;

import com.sap.ai.sdk.orchestration.client.model.AzureContentSafety;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import lombok.val;

@Data
@Accessors(fluent = true)
public final class AzureContentFilter implements ContentFilter {
  @Nullable private Sensitivity hate;
  @Nullable private Sensitivity selfHarm;
  @Nullable private Sensitivity sexual;
  @Nullable private Sensitivity violence;

  @RequiredArgsConstructor
  public enum Sensitivity {
    HIGH(0),
    MEDIUM(2),
    LOW(4);

    private final int value;
  }

  @Nonnull
  com.sap.ai.sdk.orchestration.client.model.FilterConfig toFilterConfigDTO() {
    val dto = AzureContentSafety.create();
    if (hate != null) {
      dto.hate(fromValue(hate.value));
    }
    if (selfHarm != null) {
      dto.selfHarm(fromValue(selfHarm.value));
    }
    if (sexual != null) {
      dto.sexual(fromValue(sexual.value));
    }
    if (violence != null) {
      dto.violence(fromValue(violence.value));
    }
    return com.sap.ai.sdk.orchestration.client.model.FilterConfig.create()
        .type(com.sap.ai.sdk.orchestration.client.model.FilterConfig.TypeEnum.AZURE_CONTENT_SAFETY)
        .config(dto);
  }
}
