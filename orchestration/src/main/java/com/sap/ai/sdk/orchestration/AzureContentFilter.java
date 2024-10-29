package com.sap.ai.sdk.orchestration;

import static com.sap.ai.sdk.orchestration.client.model.AzureThreshold.fromValue;

import com.sap.ai.sdk.orchestration.client.model.AzureContentSafety;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import lombok.val;

/**
 * Represents a content filter based on Azure AI Content Safety. Filters content based on up to four
 * categories: hate speech, self-harm, sexual content, and violence. Any category not set will not
 * be filtered.
 */
@Data
@Accessors(fluent = true)
public final class AzureContentFilter implements ContentFilter {
  /** The sensitivity level for hate speech, if any. */
  @Nullable private Sensitivity hate;

  /** The sensitivity level for self-harm, if any. */
  @Nullable private Sensitivity selfHarm;

  /** The sensitivity level for sexual content, if any. */
  @Nullable private Sensitivity sexual;

  /** The sensitivity level for violence, if any. */
  @Nullable private Sensitivity violence;

  /** The sensitivity level for the content filter categories. */
  @RequiredArgsConstructor
  public enum Sensitivity {
    /** High filter sensitivity, filtering out more content. */
    HIGH(0),
    /** Medium filter sensitivity, filtering out less content. */
    MEDIUM(2),
    /** Low filter sensitivity, filtering out the least content. */
    LOW(4);

    // note: we leave out the value 6, as setting it is equivalent to not setting the filter at all
    /** The integer value of the sensitivity level, will be applied as threshold. */
    private final int value;
  }

  @Nonnull
  com.sap.ai.sdk.orchestration.client.model.FilterConfig toFilterConfigDto() {
    val dto = AzureContentSafety.create();
    if (hate == null && selfHarm == null && sexual == null && violence == null) {
      throw new IllegalStateException(
          "When configuring an azure content filter, at least one filter category must be set");
    }

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
