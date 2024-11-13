package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.client.model.LLMModuleConfig;
import java.util.Map;
import javax.annotation.Nonnull;

/** Large language models available in Orchestration. */
// https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/models-and-scenarios-in-generative-ai-hub
@SuppressWarnings("unused") // tested in OrchestrationTest.orchestrationModelAvailability()
public class OrchestrationAiModel extends LLMModuleConfig {

  /** Azure OpenAI GPT-3.5 Turbo chat completions model */
  public static final OrchestrationAiModel GPT_35_TURBO = new OrchestrationAiModel("gpt-35-turbo");

  /** Azure OpenAI GPT-3.5 Turbo chat completions model */
  public static final OrchestrationAiModel GPT_35_TURBO_16K =
      new OrchestrationAiModel("gpt-35-turbo-16k");

  /** Azure OpenAI GPT-4 chat completions model */
  public static final OrchestrationAiModel GPT_4 = new OrchestrationAiModel("gpt-4");

  /** Azure OpenAI GPT-4-32k chat completions model */
  public static final OrchestrationAiModel GPT_4_32K = new OrchestrationAiModel("gpt-4-32k");

  /** Azure OpenAI GPT-4o chat completions model */
  public static final OrchestrationAiModel GPT_4O = new OrchestrationAiModel("gpt-4o");

  /** Azure OpenAI GPT-4o-mini chat completions model */
  public static final OrchestrationAiModel GPT_4O_MINI = new OrchestrationAiModel("gpt-4o-mini");

  OrchestrationAiModel(@Nonnull final String modelName) {
    setModelName(modelName);
    setModelParams(Map.of());
  }
}
