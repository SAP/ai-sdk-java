package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.client.model.AzureContentSafety;
import com.sap.ai.sdk.orchestration.client.model.AzureContentSafetyFilterConfig;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * A content filter wrapping Azure Content Safety.
 *
 * <p>This class allows setting moderation policies for different content categories such as hate,
 * self-harm, sexual, and violence.
 *
 * <p>Example usage:
 *
 * <pre>{@code
 * AzureContentFilter filter = new AzureContentFilter()
 *     .hate(AzureModerationPolicy.ALLOW_SAFE)
 *     .selfHarm(AzureModerationPolicy.ALLOW_SAFE_LOW);
 * }</pre>
 */
@Setter
@NoArgsConstructor
@Accessors(fluent = true)
public class AzureContentFilter implements ContentFilter {

  /* The moderation policy for hate content. */
  @Nullable AzureModerationPolicy hate;

  /* The moderation policy for self-harm content. */
  @Nullable AzureModerationPolicy selfHarm;

  /* The moderation policy for sexual content. */
  @Nullable AzureModerationPolicy sexual;

  /* The moderation policy for violence content. */
  @Nullable AzureModerationPolicy violence;

  /**
   * Converts {@code AzureContentFilter} to its serializable counterpart {@link
   * AzureContentSafetyFilterConfig}.
   *
   * @return the corresponding {@code AzureContentSafetyFilterConfig} object.
   * @throws IllegalArgumentException if no policies are set.
   */
  @Override
  @Nonnull
  public AzureContentSafetyFilterConfig createConfig() {
    if (hate == null && selfHarm == null && sexual == null && violence == null) {
      throw new IllegalArgumentException("At least one filter moderation policy must be set");
    }

    return new AzureContentSafetyFilterConfig()
        .type(AzureContentSafetyFilterConfig.TypeEnum.AZURE_CONTENT_SAFETY)
        .config(
            new AzureContentSafety()
                .hate(hate != null ? hate.getAzureThreshold() : null)
                .selfHarm(selfHarm != null ? selfHarm.getAzureThreshold() : null)
                .sexual(sexual != null ? sexual.getAzureThreshold() : null)
                .violence(violence != null ? violence.getAzureThreshold() : null));
  }
}
