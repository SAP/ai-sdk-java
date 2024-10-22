package com.sap.ai.sdk.orchestration;

import static com.sap.ai.sdk.orchestration.client.model.MaskingProviderConfig.MethodEnum.ANONYMIZATION;
import static com.sap.ai.sdk.orchestration.client.model.MaskingProviderConfig.MethodEnum.PSEUDONYMIZATION;
import static com.sap.ai.sdk.orchestration.client.model.MaskingProviderConfig.TypeEnum.SAP_DATA_PRIVACY_INTEGRATION;

import com.sap.ai.sdk.orchestration.client.model.DPIEntities;
import com.sap.ai.sdk.orchestration.client.model.DPIEntityConfig;
import com.sap.ai.sdk.orchestration.client.model.MaskingProviderConfig;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.val;

@Value
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class DpiMaskingConfig implements MaskingConfig {
  @Nonnull MaskingProviderConfig.MethodEnum maskingMethod;
  @Nonnull List<DPIEntities> entities;

  @Nonnull
  public static Builder anonymization() {
    return new DpiMaskingConfig.Builder(ANONYMIZATION);
  }

  @Nonnull
  public static Builder pseudonymization() {
    return new DpiMaskingConfig.Builder(PSEUDONYMIZATION);
  }

  @Nonnull
  MaskingProviderConfig toMaskingProviderDTO() {
    val entitiesDTO = entities.stream().map(it -> DPIEntityConfig.create().type(it)).toList();
    return MaskingProviderConfig.create()
        .type(SAP_DATA_PRIVACY_INTEGRATION)
        .method(maskingMethod)
        .entities(entitiesDTO);
  }

  @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Builder {
    private final MaskingProviderConfig.MethodEnum maskingMethod;

    @Nonnull
    public DpiMaskingConfig withEntities(
        @Nonnull final DPIEntities entity, @Nullable final DPIEntities... entities) {
      val entitiesList = new ArrayList<DPIEntities>();
      entitiesList.add(entity);
      if (entities != null) {
        entitiesList.addAll(Arrays.asList(entities));
      }
      return new DpiMaskingConfig(maskingMethod, entitiesList);
    }
  }
}
