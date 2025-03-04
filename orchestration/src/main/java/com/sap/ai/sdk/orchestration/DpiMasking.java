package com.sap.ai.sdk.orchestration;

import static com.sap.ai.sdk.orchestration.model.DPIConfig.MethodEnum.ANONYMIZATION;
import static com.sap.ai.sdk.orchestration.model.DPIConfig.MethodEnum.PSEUDONYMIZATION;
import static com.sap.ai.sdk.orchestration.model.DPIConfig.TypeEnum.SAP_DATA_PRIVACY_INTEGRATION;

import com.sap.ai.sdk.orchestration.model.DPIConfig;
import com.sap.ai.sdk.orchestration.model.DPIConfigMaskGroundingInput;
import com.sap.ai.sdk.orchestration.model.DPIEntities;
import com.sap.ai.sdk.orchestration.model.DPIEntityConfig;
import com.sap.ai.sdk.orchestration.model.MaskingProviderConfig;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;
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
  @Nonnull List<DPIEntities> entities;
  boolean maskGroundingInput;
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
      val entitiesList = new ArrayList<DPIEntities>();
      entitiesList.add(entity);
      entitiesList.addAll(Arrays.asList(entities));
      return new DpiMasking(maskingMethod, entitiesList, false, List.of());
    }
  }

  /**
   * If grounding is used, the input text will be masked.
   *
   * @return A new {@link DpiMasking} instance
   */
  @Nonnull
  public DpiMasking withMaskGroundingEnabled() {
    return new DpiMasking(maskingMethod, entities, true, List.of());
  }

  /**
   * Set words that should not be masked.
   *
   * @param allowList List of strings that should not be masked
   * @return A new {@link DpiMasking} instance
   */
  @Nonnull
  public DpiMasking withAllowList(@Nonnull final List<String> allowList) {
    return new DpiMasking(maskingMethod, entities, maskGroundingInput, allowList);
  }

  @Nonnull
  @Override
  public MaskingProviderConfig createConfig() {
    val entitiesDTO = entities.stream().map(it -> DPIEntityConfig.create().type(it)).toList();
    return DPIConfig.create()
        .type(SAP_DATA_PRIVACY_INTEGRATION)
        .method(maskingMethod)
        .entities(entitiesDTO)
        .maskGroundingInput(DPIConfigMaskGroundingInput.create().enabled(maskGroundingInput))
        .allowlist(allowList);
  }
}
