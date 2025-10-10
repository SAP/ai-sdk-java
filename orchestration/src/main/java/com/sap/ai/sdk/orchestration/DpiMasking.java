package com.sap.ai.sdk.orchestration;

import static com.sap.ai.sdk.orchestration.model.DPIConfig.MethodEnum.ANONYMIZATION;
import static com.sap.ai.sdk.orchestration.model.DPIConfig.MethodEnum.PSEUDONYMIZATION;
import static com.sap.ai.sdk.orchestration.model.DPIConfig.TypeEnum.SAP_DATA_PRIVACY_INTEGRATION;

import com.sap.ai.sdk.orchestration.model.DPIConfig;
import com.sap.ai.sdk.orchestration.model.DPIConfigMaskGroundingInput;
import com.sap.ai.sdk.orchestration.model.DPICustomEntity;
import com.sap.ai.sdk.orchestration.model.DPIEntities;
import com.sap.ai.sdk.orchestration.model.DPIEntityConfig;
import com.sap.ai.sdk.orchestration.model.DPIMethodConstant;
import com.sap.ai.sdk.orchestration.model.DPIStandardEntity;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.With;
import lombok.val;

/**
 * SAP Data Privacy Integration (DPI) can mask personally identifiable information using either
 * anonymization or pseudonymization.
 *
 * @link <a href="https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/data-masking">SAP
 *     AI Core: Orchestration - Data Masking</a>
 */
@Value
@Getter(AccessLevel.PACKAGE)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class DpiMasking implements MaskingProvider {
  @Nonnull DPIConfig.MethodEnum maskingMethod;
  @Nonnull List<DPIEntityConfig> entitiesConfig;
  @With boolean maskGroundingInput;
  @Nonnull List<String> allowList;

  /**
   * Build a configuration applying anonymization.
   *
   * @return A builder configured for anonymization
   */
  @Nonnull
  public static Builder anonymization() {
    return new DpiMasking.Builder(ANONYMIZATION);
  }

  /**
   * Build a configuration applying pseudonymization.
   *
   * @return A builder configured for pseudonymization
   */
  @Nonnull
  public static Builder pseudonymization() {
    return new DpiMasking.Builder(PSEUDONYMIZATION);
  }

  /**
   * Builder for creating DPI masking configurations. Allows specifying which entity types should be
   * masked in the input text.
   */
  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Builder {
    private final DPIConfig.MethodEnum maskingMethod;

    /**
     * Specifies which entities should be masked in the input text.
     *
     * @param entity An entity type to mask (required)
     * @param entities Additional entity types to mask (optional)
     * @return A new {@link DpiMasking} instance
     * @see DPIEntities
     */
    @Nonnull
    public DpiMasking withEntities(
        @Nonnull final DPIEntities entity, @Nonnull final DPIEntities... entities) {
      val entitiesConfig =
          Stream.concat(Stream.of(entity), Arrays.stream(entities))
              .map(it -> (DPIEntityConfig) DPIStandardEntity.create().type(it))
              .toList();
      return new DpiMasking(maskingMethod, entitiesConfig, false, List.of());
    }

    /**
     * Adds a custom regex pattern for masking.
     *
     * @param regex The regex pattern to match
     * @param replacement The replacement string
     * @return A new {@link DpiMasking} instance
     */
    @Nonnull
    public DpiMasking withRegex(@Nonnull final String regex, @Nonnull final String replacement) {
      val customEntity =
          DPICustomEntity.create()
              .regex(regex)
              .replacementStrategy(
                  DPIMethodConstant.create()
                      .method(DPIMethodConstant.MethodEnum.CONSTANT)
                      .value(replacement));

      return new DpiMasking(maskingMethod, List.of(customEntity), false, List.of());
    }
  }

  /**
   * Specifies a custom regex pattern for masking.
   *
   * @param regex The regex pattern to match
   * @param replacement The replacement string
   * @return A new {@link DpiMasking} instance
   */
  @Nonnull
  public DpiMasking withRegex(@Nonnull final String regex, @Nonnull final String replacement) {
    val customEntity =
        DPICustomEntity.create()
            .regex(regex)
            .replacementStrategy(
                DPIMethodConstant.create()
                    .method(DPIMethodConstant.MethodEnum.CONSTANT)
                    .value(replacement));
    val newEntities = new java.util.ArrayList<>(entitiesConfig);
    newEntities.add(customEntity);
    return new DpiMasking(maskingMethod, newEntities, maskGroundingInput, allowList);
  }

  /**
   * Set words that should not be masked.
   *
   * @param allowList List of strings that should not be masked
   * @return A new {@link DpiMasking} instance
   */
  @Nonnull
  public DpiMasking withAllowList(@Nonnull final List<String> allowList) {
    return new DpiMasking(maskingMethod, entitiesConfig, maskGroundingInput, allowList);
  }

  @Nonnull
  @Override
  public DPIConfig createConfig() {
    return DPIConfig.create()
        .type(SAP_DATA_PRIVACY_INTEGRATION)
        .method(maskingMethod)
        .entities(entitiesConfig)
        .maskGroundingInput(DPIConfigMaskGroundingInput.create().enabled(maskGroundingInput))
        .allowlist(allowList);
  }
}
