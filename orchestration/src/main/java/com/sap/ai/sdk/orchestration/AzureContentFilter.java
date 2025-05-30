package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.model.AzureContentSafetyInput;
import com.sap.ai.sdk.orchestration.model.AzureContentSafetyInputFilterConfig;
import com.sap.ai.sdk.orchestration.model.AzureContentSafetyOutput;
import com.sap.ai.sdk.orchestration.model.AzureContentSafetyOutputFilterConfig;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * A content filter wrapping Azure Content Safety.
 *
 * <p>This class allows setting filtration thresholds for different content categories such as hate,
 * self-harm, sexual, and violence.
 *
 * <p>Example usage:
 *
 * <pre>{@code
 * AzureContentFilter filter = new AzureContentFilter()
 *     .hate(AzureFilterThreshold.ALLOW_SAFE)
 *     .selfHarm(AzureFilterThreshold.ALLOW_SAFE_LOW);
 * }</pre>
 *
 * @link <a
 *     href="https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/input-filtering">SAP AI
 *     Core: Orchestration - Input Filtering</a>
 * @link <a
 *     href="https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/output-filtering">SAP
 *     AI Core: Orchestration - Output Filtering</a>
 */
@Setter
@NoArgsConstructor
@Accessors(fluent = true)
public class AzureContentFilter implements ContentFilter {

  /* The filter category for hate content. */
  @Nullable AzureFilterThreshold hate;

  /* The filter category for self-harm content. */
  @Nullable AzureFilterThreshold selfHarm;

  /* The filter category for sexual content. */
  @Nullable AzureFilterThreshold sexual;

  /* The filter category for violence content. */
  @Nullable AzureFilterThreshold violence;

  /**
   * Converts {@code AzureContentFilter} to its serializable counterpart {@link
   * AzureContentSafetyInputFilterConfig}.
   *
   * @return the corresponding {@code AzureContentSafetyInputFilterConfig} object.
   * @throws IllegalArgumentException if no policies are set.
   */
  @Override
  @Nonnull
  public AzureContentSafetyInputFilterConfig createInputConfig() {
    if (hate == null && selfHarm == null && sexual == null && violence == null) {
      throw new IllegalArgumentException("At least one filter category must be set");
    }

    return AzureContentSafetyInputFilterConfig.create()
        .type(AzureContentSafetyInputFilterConfig.TypeEnum.AZURE_CONTENT_SAFETY)
        .config(
            AzureContentSafetyInput.create()
                .hate(hate != null ? hate.getAzureThreshold() : null)
                .selfHarm(selfHarm != null ? selfHarm.getAzureThreshold() : null)
                .sexual(sexual != null ? sexual.getAzureThreshold() : null)
                .violence(violence != null ? violence.getAzureThreshold() : null));
  }

  /**
   * Converts {@code AzureContentFilter} to its serializable counterpart {@link
   * AzureContentSafetyOutputFilterConfig}.
   *
   * @return the corresponding {@code AzureContentSafetyOutputFilterConfig} object.
   * @throws IllegalArgumentException if no policies are set.
   */
  @Override
  @Nonnull
  public AzureContentSafetyOutputFilterConfig createOutputConfig() {
    if (hate == null && selfHarm == null && sexual == null && violence == null) {
      throw new IllegalArgumentException("At least one filter category must be set");
    }

    return AzureContentSafetyOutputFilterConfig.create()
        .type(AzureContentSafetyOutputFilterConfig.TypeEnum.AZURE_CONTENT_SAFETY)
        .config(
            AzureContentSafetyOutput.create()
                .hate(hate != null ? hate.getAzureThreshold() : null)
                .selfHarm(selfHarm != null ? selfHarm.getAzureThreshold() : null)
                .sexual(sexual != null ? sexual.getAzureThreshold() : null)
                .violence(violence != null ? violence.getAzureThreshold() : null));
  }
}
