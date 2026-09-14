package com.sap.ai.sdk.prompt.registry;

import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.prompt.registry.client.OrchestrationConfigsApi;
import com.sap.ai.sdk.prompt.registry.client.PromptTemplatesApi;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

/**
 * Unified client to use Prompt Registry API
 *
 * @since 2.0
 */
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class PromptRegistryClient {

  private final PromptTemplatesApi promptTemplatesApi;
  private final OrchestrationConfigsApi orchestrationConfigsApi;

  /** Constructs default PromptRegistryClient */
  public PromptRegistryClient() {
    final var aiCoreService = new AiCoreService();
    promptTemplatesApi = new PromptTemplatesApi(PromptClientMixin.addMixin(aiCoreService));
    orchestrationConfigsApi =
        new OrchestrationConfigsApi(OrchestrationConfigMixin.addMixin(aiCoreService));
  }

  /**
   * Constructs PromptRegistryClient with customized AiCoreService
   *
   * @param service customized AiCoreService
   */
  public PromptRegistryClient(@Nonnull final AiCoreService service) {
    promptTemplatesApi = new PromptTemplatesApi(PromptClientMixin.addMixin(service));
    orchestrationConfigsApi =
        new OrchestrationConfigsApi(OrchestrationConfigMixin.addMixin(service));
  }

  /**
   * Provides caller with PromptTemplatesAPI client
   *
   * @return the client
   */
  @Nonnull
  public PromptTemplatesApi prompt() {
    return promptTemplatesApi;
  }

  /**
   * Provides caller with OrchestrationConfigsAPI client
   *
   * @return the client
   */
  @Nonnull
  public OrchestrationConfigsApi orchestration() {
    return orchestrationConfigsApi;
  }
}
