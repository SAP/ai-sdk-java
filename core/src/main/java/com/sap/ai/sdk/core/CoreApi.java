package com.sap.ai.sdk.core;

import com.sap.ai.sdk.core.client.ScenarioApi;
import com.sap.ai.sdk.core.model.AiModelList;
import com.sap.cloud.sdk.services.openapi.apache.core.OpenApiRequestException;
import javax.annotation.Nonnull;

/** Convenience class for the AI Core API. */
public class CoreApi {

  private final ScenarioApi scenarioApi;

  /** The default constructor. Uses the default {@link AiCoreService}. */
  public CoreApi() {
    this(new AiCoreService());
  }

  /**
   * Constructor with a custom {@link AiCoreService}.
   *
   * @param aiCoreService the configured connectivity instance to AI Core.
   */
  public CoreApi(@Nonnull final AiCoreService aiCoreService) {
    this.scenarioApi = new ScenarioApi(aiCoreService);
  }

  /**
   * Query all foundation models available in SAP AI Core Gen AI Hub for the given scenario ID and
   * resource group.
   *
   * @param scenarioId the scenario identifier.
   * @param resourceGroup the resource group to query models for.
   * @return the list of available models.
   * @throws OpenApiRequestException if the request fails.
   */
  @Nonnull
  public AiModelList availableAiModels(
      @Nonnull final String scenarioId, @Nonnull final String resourceGroup) {
    try {
      return scenarioApi.queryModels(scenarioId, resourceGroup);
    } catch (OpenApiRequestException e) {
      throw new OpenApiRequestException(
          e.getMessage().replace("queryModels", "availableAiModels"), e);
    }
  }

  /**
   * Query all foundation models available in SAP AI Core Gen AI Hub for the default resource group.
   *
   * @return the list of available models.
   * @throws OpenApiRequestException if the request fails.
   */
  @Nonnull
  public AiModelList availableAiModels() {
    return availableAiModels("foundation-models", AiCoreService.DEFAULT_RESOURCE_GROUP);
  }
}
