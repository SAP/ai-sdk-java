package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.model.AzureContentSafetyInput;
import com.sap.ai.sdk.orchestration.model.AzureContentSafetyInputFilterConfig;
import com.sap.ai.sdk.orchestration.model.AzureContentSafetyOutput;
import com.sap.ai.sdk.orchestration.model.AzureContentSafetyOutputFilterConfig;
import java.util.Optional;
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

  /* A flag to set prompt shield on in*/
  @Nullable Boolean promptShield;

  /**
   * Converts {@link AzureContentFilter} to its serializable counterpart {@link
   * AzureContentSafetyInputFilterConfig}.
   *
   * @return the corresponding {@link AzureContentSafetyInputFilterConfig} object.
   * @throws IllegalArgumentException if no policies are set.
   */
  @Override
  @Nonnull
  public AzureContentSafetyInputFilterConfig createInputFilterConfig() {
    if (hate == null && selfHarm == null && sexual == null && violence == null) {
      throw new IllegalArgumentException("At least one filter category must be set");
    }

    final var config =
        AzureContentSafetyInput.create()
            .hate(
                Optional.ofNullable(hate).map(AzureFilterThreshold::getAzureThreshold).orElse(null))
            .selfHarm(
                Optional.ofNullable(selfHarm)
                    .map(AzureFilterThreshold::getAzureThreshold)
                    .orElse(null))
            .sexual(
                Optional.ofNullable(sexual)
                    .map(AzureFilterThreshold::getAzureThreshold)
                    .orElse(null))
            .violence(
                Optional.ofNullable(violence)
                    .map(AzureFilterThreshold::getAzureThreshold)
                    .orElse(null))
            .promptShield(Optional.ofNullable(promptShield).orElse(null));

    return AzureContentSafetyInputFilterConfig.create()
        .type(AzureContentSafetyInputFilterConfig.TypeEnum.AZURE_CONTENT_SAFETY)
        .config(config);
  }

  /**
   * Converts {@link AzureContentFilter} to its serializable counterpart {@link
   * AzureContentSafetyOutput}.
   *
   * @return the corresponding {@link AzureContentSafetyOutputFilterConfig} object.
   * @throws IllegalArgumentException if no policies are set.
   */
  @Override
  @Nonnull
  public AzureContentSafetyOutputFilterConfig createOutputFilterConfig() {
    if (hate == null && selfHarm == null && sexual == null && violence == null) {
      throw new IllegalArgumentException("At least one filter category must be set");
    }

    final var config =
        AzureContentSafetyOutput.create()
            .hate(
                Optional.ofNullable(hate).map(AzureFilterThreshold::getAzureThreshold).orElse(null))
            .selfHarm(
                Optional.ofNullable(selfHarm)
                    .map(AzureFilterThreshold::getAzureThreshold)
                    .orElse(null))
            .sexual(
                Optional.ofNullable(sexual)
                    .map(AzureFilterThreshold::getAzureThreshold)
                    .orElse(null))
            .violence(
                Optional.ofNullable(violence)
                    .map(AzureFilterThreshold::getAzureThreshold)
                    .orElse(null));

    return AzureContentSafetyOutputFilterConfig.create()
        .type(AzureContentSafetyOutputFilterConfig.TypeEnum.AZURE_CONTENT_SAFETY)
        .config(config);
  }
}
