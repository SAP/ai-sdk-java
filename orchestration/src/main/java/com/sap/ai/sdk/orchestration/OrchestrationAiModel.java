package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.generated.LLMModuleConfig;
import java.util.Map;
import javax.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.With;

/** Large language models available in Orchestration. */
@Value
@With
@AllArgsConstructor
public class OrchestrationAiModel {
  /** The name of the model */
  String name;

  /**
   * Optional parameters on this model.
   *
   * <pre>{@code
   * Map.of(
   *     "max_tokens", 50,
   *     "temperature", 0.1,
   *     "frequency_penalty", 0,
   *     "presence_penalty", 0)
   * }</pre>
   */
  Map<String, Object> params;

  /** The version of the model, defaults to "latest". */
  String version;

  /** IBM Granite 13B chat completions model */
  public static final OrchestrationAiModel IBM_GRANITE_13B_CHAT =
      new OrchestrationAiModel("ibm--granite-13b-chat");

  /** MistralAI Mistral Large Instruct model */
  public static final OrchestrationAiModel MISTRAL_LARGE_INSTRUCT =
      new OrchestrationAiModel("mistralai--mistral-large-instruct");

  /** MistralAI Mixtral 8x7B Instruct v01 model */
  public static final OrchestrationAiModel MIXTRAL_8X7B_INSTRUCT_V01 =
      new OrchestrationAiModel("mistralai--mixtral-8x7b-instruct-v01");

  /** Meta Llama3 70B Instruct model */
  public static final OrchestrationAiModel LLAMA3_70B_INSTRUCT =
      new OrchestrationAiModel("meta--llama3-70b-instruct");

  /** Meta Llama3.1 70B Instruct model */
  public static final OrchestrationAiModel LLAMA3_1_70B_INSTRUCT =
      new OrchestrationAiModel("meta--llama3.1-70b-instruct");

  /** Anthropic Claude 3 Sonnet model */
  public static final OrchestrationAiModel CLAUDE_3_SONNET =
      new OrchestrationAiModel("anthropic--claude-3-sonnet");

  /** Anthropic Claude 3 Haiku model */
  public static final OrchestrationAiModel CLAUDE_3_HAIKU =
      new OrchestrationAiModel("anthropic--claude-3-haiku");

  /** Anthropic Claude 3 Opus model */
  public static final OrchestrationAiModel CLAUDE_3_OPUS =
      new OrchestrationAiModel("anthropic--claude-3-opus");

  /** Anthropic Claude 3.5 Sonnet model */
  public static final OrchestrationAiModel CLAUDE_3_5_SONNET =
      new OrchestrationAiModel("anthropic--claude-3.5-sonnet");

  /** Amazon Titan Text Lite model */
  public static final OrchestrationAiModel TITAN_TEXT_LITE =
      new OrchestrationAiModel("amazon--titan-text-lite");

  /** Amazon Titan Text Express model */
  public static final OrchestrationAiModel TITAN_TEXT_EXPRESS =
      new OrchestrationAiModel("amazon--titan-text-express");

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

  /** Google Cloud Platform Gemini 1.0 Pro model */
  public static final OrchestrationAiModel GEMINI_1_0_PRO =
      new OrchestrationAiModel("gemini-1.0-pro");

  /** Google Cloud Platform Gemini 1.5 Pro model */
  public static final OrchestrationAiModel GEMINI_1_5_PRO =
      new OrchestrationAiModel("gemini-1.5-pro");

  /** Google Cloud Platform Gemini 1.5 Flash model */
  public static final OrchestrationAiModel GEMINI_1_5_FLASH =
      new OrchestrationAiModel("gemini-1.5-flash");

  OrchestrationAiModel(@Nonnull final String name) {
    this(name, Map.of(), "latest");
  }

  @Nonnull
  LLMModuleConfig createConfig() {
    return LLMModuleConfig.create().modelName(name).modelParams(params).modelVersion(version);
  }
}
