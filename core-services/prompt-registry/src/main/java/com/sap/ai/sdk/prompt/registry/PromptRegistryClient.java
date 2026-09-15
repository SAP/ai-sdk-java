package com.sap.ai.sdk.prompt.registry;

import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.prompt.registry.client.OrchestrationConfigsApi;
import com.sap.ai.sdk.prompt.registry.client.PromptTemplatesApi;
import javax.annotation.Nonnull;

/**
 * Unified client to use Prompt Registry API
 *
 * @since 2.0
 */
public class PromptRegistryClient {

  private final AiCoreService aiCoreService;

  /** Constructs default PromptRegistryClient */
  public PromptRegistryClient() {
    this(new AiCoreService());
  }

  /**
   * Constructs PromptRegistryClient with customized AiCoreService
   *
   * @param service customized AiCoreService
   */
  public PromptRegistryClient(@Nonnull final AiCoreService service) {
    aiCoreService = service;
  }

  /**
   * Get the prompt templates client
   *
   * @return the client
   */
  @Nonnull
  public PromptTemplatesApi prompt() {
    return new PromptTemplatesApi(PromptClientMixin.addMixin(aiCoreService));
  }

  /**
   * Get the orchestration configs client
   *
   * @return the client
   */
  @Nonnull
  public OrchestrationConfigsApi orchestration() {
    return new OrchestrationConfigsApi(OrchestrationConfigMixin.addMixin(aiCoreService));
  }
}
