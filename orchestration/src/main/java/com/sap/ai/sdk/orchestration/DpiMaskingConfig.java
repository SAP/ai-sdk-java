package com.sap.ai.sdk.orchestration;

import static com.sap.ai.sdk.orchestration.client.model.DPIConfig.MethodEnum.ANONYMIZATION;
import static com.sap.ai.sdk.orchestration.client.model.DPIConfig.MethodEnum.PSEUDONYMIZATION;
import static com.sap.ai.sdk.orchestration.client.model.DPIConfig.TypeEnum.SAP_DATA_PRIVACY_INTEGRATION;

import com.sap.ai.sdk.orchestration.client.model.DPIConfig;
import com.sap.ai.sdk.orchestration.client.model.DPIEntities;
import com.sap.ai.sdk.orchestration.client.model.DPIEntityConfig;
import com.sap.ai.sdk.orchestration.client.model.MaskingProviderConfig;
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
 * Implementation of {@link MaskingConfig} for SAP Data Privacy Integration (DPI) service. Supports
 * both anonymization and pseudonymization of personally identifiable information.
 */
@Value
@Getter(AccessLevel.PACKAGE)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class DpiMaskingConfig implements MaskingConfig {
  @Nonnull DPIConfig.MethodEnum maskingMethod;
  @Nonnull List<DPIEntities> entities;

  /**
   * Build a configuration applying anonymization.
   *
   * @return A builder configured for anonymization
   */
  @Nonnull
  public static Builder anonymization() {
    return new DpiMaskingConfig.Builder(ANONYMIZATION);
  }

  /**
   * Build a configuration applying pseudonymization.
   *
   * @return A builder configured for pseudonymization
   */
  @Nonnull
  public static Builder pseudonymization() {
    return new DpiMaskingConfig.Builder(PSEUDONYMIZATION);
  }

  @Nonnull
  MaskingProviderConfig toMaskingProviderDto() {
    val entitiesDTO = entities.stream().map(it -> new DPIEntityConfig().type(it)).toList();
    return new DPIConfig()
        .type(SAP_DATA_PRIVACY_INTEGRATION)
        .method(maskingMethod)
        .entities(entitiesDTO);
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
     * @return A configured {@link DpiMaskingConfig} instance
     * @see DPIEntities
     */
    @Nonnull
    public DpiMaskingConfig withEntities(
        @Nonnull final DPIEntities entity, @Nonnull final DPIEntities... entities) {
      val entitiesList = new ArrayList<DPIEntities>();
      entitiesList.add(entity);
      entitiesList.addAll(Arrays.asList(entities));
      return new DpiMaskingConfig(maskingMethod, entitiesList);
    }
  }
}
