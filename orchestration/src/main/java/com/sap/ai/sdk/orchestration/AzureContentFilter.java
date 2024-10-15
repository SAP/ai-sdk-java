package com.sap.ai.sdk.orchestration;

import static com.sap.ai.sdk.orchestration.client.model.AzureThreshold.fromValue;

import com.sap.ai.sdk.orchestration.client.model.AzureContentSafety;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class AzureContentFilter implements ContentFilter {
  @Nullable private Setting hate;
  @Nullable private Setting selfHarm;
  @Nullable private Setting sexual;
  @Nullable private Setting violence;

  @RequiredArgsConstructor
  public enum Setting {
    VERY_STRICT(0),
    STRICT(2),
    MODERATE(4),
    LENIENT(6);

    private final int value;
  }

  @Nonnull
  com.sap.ai.sdk.orchestration.client.model.FilterConfig toFilterConfigDTO() {
    var dto = AzureContentSafety.create();
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
