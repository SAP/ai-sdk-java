package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.model.LLMModelDetails;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.With;

/**
 * Large language models available in Orchestration.
 *
 * <p>Please note that the template of models provided in this class might be outdated. To check the
 * latest availability of models in Orchestration, please refer to <a
 * href="https://me.sap.com/notes/3437766">SAP Availability of Generative AI Models </a> and pay
 * attention to the <i>Available in Orchestration</i> column.
 */
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
   *
   * @link <a
   *     href="https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/harmonized-api">SAP
   *     AI Core: Orchestration - Harmonized API</a>
   */
  Map<String, Object> params;

  /** The version of the model, defaults to "latest". */
  String version;

  /**
   * IBM Granite 13B Chat model
   *
   * @deprecated This model is deprecated on AI Core with a planned retirement not earlier than
   *     2025-09-30.
   */
  @Deprecated
  public static final OrchestrationAiModel IBM_GRANITE_13B_CHAT =
      new OrchestrationAiModel("ibm--granite-13b-chat");

  /** MistralAI Mistral Large Instruct model */
  public static final OrchestrationAiModel MISTRAL_LARGE_INSTRUCT =
      new OrchestrationAiModel("mistralai--mistral-large-instruct");

  /** MistralAI Mistral Small Instruct model */
  public static final OrchestrationAiModel MISTRAL_SMALL_INSTRUCT =
      new OrchestrationAiModel("mistralai--mistral-small-instruct");

  /** MistralAI Mistral Medium Instruct model */
  public static final OrchestrationAiModel MISTRAL_MEDIUM_INSTRUCT =
      new OrchestrationAiModel("mistralai--mistral-medium-instruct");

  /**
   * MistralAI Mixtral 8x7B Instruct v01 model
   *
   * @deprecated This model is deprecated on AI Core. The suggested replacement model is {@link
   *     OrchestrationAiModel#MISTRAL_SMALL_INSTRUCT}.
   */
  @Deprecated
  public static final OrchestrationAiModel MIXTRAL_8X7B_INSTRUCT_V01 =
      new OrchestrationAiModel("mistralai--mixtral-8x7b-instruct-v01");

  /**
   * Meta Llama3 70B Instruct model
   *
   * @deprecated This model is deprecated on AI Core.
   */
  @Deprecated
  public static final OrchestrationAiModel LLAMA3_70B_INSTRUCT =
      new OrchestrationAiModel("meta--llama3-70b-instruct");

  /**
   * Meta Llama3.1 70B Instruct model
   *
   * @deprecated This model is deprecated on AI Core with a planned retirement on 2025-07-30.
   */
  @Deprecated
  public static final OrchestrationAiModel LLAMA3_1_70B_INSTRUCT =
      new OrchestrationAiModel("meta--llama3.1-70b-instruct");

  /**
   * Anthropic Claude 3 Sonnet model
   *
   * @deprecated This model is deprecated on AI Core with a planned retirement on 2025-07-21. The
   *     suggested replacement model is {@link OrchestrationAiModel#CLAUDE_4_SONNET}.
   */
  @Deprecated
  public static final OrchestrationAiModel CLAUDE_3_SONNET =
      new OrchestrationAiModel("anthropic--claude-3-sonnet");

  /** Anthropic Claude 3 Haiku model */
  public static final OrchestrationAiModel CLAUDE_3_HAIKU =
      new OrchestrationAiModel("anthropic--claude-3-haiku");

  /**
   * Anthropic Claude 3 Opus model
   *
   * @deprecated This model is deprecated on AI Core. The suggested replacement model is {@link
   *     OrchestrationAiModel#CLAUDE_4_OPUS}.
   */
  @Deprecated
  public static final OrchestrationAiModel CLAUDE_3_OPUS =
      new OrchestrationAiModel("anthropic--claude-3-opus");

  /**
   * Anthropic Claude 3.5 Sonnet model
   *
   * @deprecated This model is deprecated on AI Core. The suggested replacement model is {@link
   *     OrchestrationAiModel#CLAUDE_4_SONNET}.
   */
  @Deprecated
  public static final OrchestrationAiModel CLAUDE_3_5_SONNET =
      new OrchestrationAiModel("anthropic--claude-3.5-sonnet");

  /** Anthropic Claude 3.7 Sonnet model */
  public static final OrchestrationAiModel CLAUDE_3_7_SONNET =
      new OrchestrationAiModel("anthropic--claude-3.7-sonnet");

  /** Anthropic Claude 4 Opus model */
  public static final OrchestrationAiModel CLAUDE_4_OPUS =
      new OrchestrationAiModel("anthropic--claude-4-opus");

  /** Anthropic Claude 4 Sonnet model */
  public static final OrchestrationAiModel CLAUDE_4_SONNET =
      new OrchestrationAiModel("anthropic--claude-4-sonnet");

  /** Anthropic Claude 4.5 Sonnet model */
  public static final OrchestrationAiModel CLAUDE_4_5_SONNET =
      new OrchestrationAiModel("anthropic--claude-4.5-sonnet");

  /**
   * Amazon Titan Text Lite model
   *
   * @deprecated This model is deprecated on AI Core with a planned retirement on 2025-08-15.
   */
  @Deprecated
  public static final OrchestrationAiModel TITAN_TEXT_LITE =
      new OrchestrationAiModel("amazon--titan-text-lite");

  /**
   * Amazon Titan Text Express model
   *
   * @deprecated This model is deprecated on AI Core with a planned retirement on 2025-08-15.
   */
  @Deprecated
  public static final OrchestrationAiModel TITAN_TEXT_EXPRESS =
      new OrchestrationAiModel("amazon--titan-text-express");

  /** Amazon Nova Pro model */
  public static final OrchestrationAiModel NOVA_PRO = new OrchestrationAiModel("amazon--nova-pro");

  /** Amazon Nova Lite model */
  public static final OrchestrationAiModel NOVA_LITE =
      new OrchestrationAiModel("amazon--nova-lite");

  /** Amazon Nova Micro model */
  public static final OrchestrationAiModel NOVA_MICRO =
      new OrchestrationAiModel("amazon--nova-micro");

  /**
   * Azure OpenAI GPT-3.5 Turbo model
   *
   * @deprecated This model is deprecated on AI Core with a planned retirement on 2025-09-01. The
   *     suggested replacement model is {@link OrchestrationAiModel#GPT_4O_MINI}.
   */
  @Deprecated
  public static final OrchestrationAiModel GPT_35_TURBO = new OrchestrationAiModel("gpt-35-turbo");

  /**
   * Azure OpenAI GPT-3.5 Turbo model
   *
   * @deprecated This model is deprecated on AI Core.
   */
  @Deprecated
  public static final OrchestrationAiModel GPT_35_TURBO_16K =
      new OrchestrationAiModel("gpt-35-turbo-16k");

  /**
   * Azure OpenAI GPT-3.5 Turbo model
   *
   * @deprecated This model is deprecated on AI Core with a planned retirement on 2025-09-01. The
   *     suggested replacement model is {@link OrchestrationAiModel#GPT_4O_MINI}.
   */
  @Deprecated
  public static final OrchestrationAiModel GPT_35_TURBO_0125 =
      new OrchestrationAiModel("gpt-35-turbo-0125");

  /**
   * Azure OpenAI GPT-4 model
   *
   * @deprecated This model is deprecated on AI Core with a planned retirement on 2025-09-01. The
   *     suggested replacement model is {@link OrchestrationAiModel#GPT_4O} or {@link
   *     OrchestrationAiModel#GPT_41}.
   */
  @Deprecated public static final OrchestrationAiModel GPT_4 = new OrchestrationAiModel("gpt-4");

  /** Azure OpenAI GPT-4.1-mini model */
  public static final OrchestrationAiModel GPT_41_MINI = new OrchestrationAiModel("gpt-4.1-mini");

  /** Azure OpenAI GPT-4.1 model */
  public static final OrchestrationAiModel GPT_41 = new OrchestrationAiModel("gpt-4.1");

  /** Azure OpenAI GPT-4.1-nano model */
  public static final OrchestrationAiModel GPT_41_NANO = new OrchestrationAiModel("gpt-4.1-nano");

  /**
   * Azure OpenAI GPT-4 model
   *
   * @deprecated This model is deprecated on AI Core.
   */
  @Deprecated
  public static final OrchestrationAiModel GPT_4_32K = new OrchestrationAiModel("gpt-4-32k");

  /**
   * Azure OpenAI GPT-4 version 0613 model
   *
   * @deprecated This model is deprecated on AI Core.The suggested replacement model is {@link
   *     OrchestrationAiModel#GPT_4O} or {@link OrchestrationAiModel#GPT_41}.
   */
  @Deprecated
  public static final OrchestrationAiModel GPT_4_0613 = new OrchestrationAiModel("gpt-4-0613");

  /** Azure OpenAI GPT-4o model */
  public static final OrchestrationAiModel GPT_4O = new OrchestrationAiModel("gpt-4o");

  /** Azure OpenAI GPT-4o-mini model */
  public static final OrchestrationAiModel GPT_4O_MINI = new OrchestrationAiModel("gpt-4o-mini");

  /** Azure OpenAI o1 model */
  public static final OrchestrationAiModel OPENAI_O1 = new OrchestrationAiModel("o1");

  /** Azure OpenAI o3-mini model */
  public static final OrchestrationAiModel OPENAI_O3_MINI = new OrchestrationAiModel("o3-mini");

  /** Azure OpenAI o4-mini model */
  public static final OrchestrationAiModel OPENAI_O4_MINI = new OrchestrationAiModel("o4-mini");

  /** Azure OpenAI o3 model */
  public static final OrchestrationAiModel OPENAI_O3 = new OrchestrationAiModel("o3");

  /** Azure OpenAI GPT-5 model */
  public static final OrchestrationAiModel GPT_5 = new OrchestrationAiModel("gpt-5");

  /** Azure OpenAI GPT-5-mini model */
  public static final OrchestrationAiModel GPT_5_MINI = new OrchestrationAiModel("gpt-5-mini");

  /** Azure OpenAI GPT-5-nano model */
  public static final OrchestrationAiModel GPT_5_NANO = new OrchestrationAiModel("gpt-5-nano");

  /**
   * Google Cloud Platform Gemini 1.0 Pro model
   *
   * @deprecated This model is deprecated on AI Core with a planned retirement on 2025-04-09. The
   *     suggested replacement model is {@link OrchestrationAiModel#GEMINI_2_0_FLASH} or {@link
   *     OrchestrationAiModel#GEMINI_2_0_FLASH_LITE}.
   */
  @Deprecated
  public static final OrchestrationAiModel GEMINI_1_0_PRO =
      new OrchestrationAiModel("gemini-1.0-pro");

  /**
   * Google Cloud Platform Gemini 1.5 Pro model
   *
   * @deprecated This model is deprecated on AI Core with a planned retirement on 2025-09-24. The
   *     suggested replacement model is {@link OrchestrationAiModel#GEMINI_2_5_PRO}.
   */
  @Deprecated
  public static final OrchestrationAiModel GEMINI_1_5_PRO =
      new OrchestrationAiModel("gemini-1.5-pro");

  /**
   * Google Cloud Platform Gemini 1.5 Flash model
   *
   * @deprecated This model is deprecated on AI Core with a planned retirement on 2025-09-24. The
   *     suggested replacement model is {@link OrchestrationAiModel#GEMINI_2_5_FLASH}.
   */
  @Deprecated
  public static final OrchestrationAiModel GEMINI_1_5_FLASH =
      new OrchestrationAiModel("gemini-1.5-flash");

  /** Google Cloud Platform Gemini 2.0 Flash model */
  public static final OrchestrationAiModel GEMINI_2_0_FLASH =
      new OrchestrationAiModel("gemini-2.0-flash");

  /** Google Cloud Platform Gemini 2.0 Flash-Lite model */
  public static final OrchestrationAiModel GEMINI_2_0_FLASH_LITE =
      new OrchestrationAiModel("gemini-2.0-flash-lite");

  /** Google Cloud Platform Gemini 2.5 Flash model */
  public static final OrchestrationAiModel GEMINI_2_5_FLASH =
      new OrchestrationAiModel("gemini-2.5-flash");

  /** Google Cloud Platform Gemini 2.5 Pro model */
  public static final OrchestrationAiModel GEMINI_2_5_PRO =
      new OrchestrationAiModel("gemini-2.5-pro");

  /** Alephalpha-pharia-1-7b-control model */
  public static final OrchestrationAiModel ALEPHALPHA_PHARIA_1_7B_CONTROL =
      new OrchestrationAiModel("alephalpha-pharia-1-7b-control");

  /** DeepSeek-R1 */
  public static final OrchestrationAiModel DEEPSEEK_R1 =
      new OrchestrationAiModel("deepseek-ai--deepseek-r1");

  OrchestrationAiModel(@Nonnull final String name) {
    this(name, Map.of(), "latest");
  }

  @Nonnull
  LLMModelDetails createConfig() {
    return LLMModelDetails.create().name(name).params(params).version(version);
  }

  /**
   * Additional parameter on this model.
   *
   * @param key the parameter key.
   * @param value the parameter value, nullable.
   * @return A new model with the additional parameter.
   * @link <a
   *     href="https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/harmonized-api">SAP
   *     AI Core: Orchestration - Harmonized API</a>
   */
  @Nonnull
  public OrchestrationAiModel withParam(@Nonnull final String key, @Nullable final Object value) {
    final var params = new LinkedHashMap<>(getParams());
    params.put(key, value);
    return withParams(params);
  }

  /**
   * Additional parameter on this model.
   *
   * @param param the parameter key.
   * @param value the parameter value, nullable.
   * @param <ValueT> the parameter value type.
   * @return A new model with the additional parameter.
   * @link <a
   *     href="https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/harmonized-api">SAP
   *     AI Core: Orchestration - Harmonized API</a>
   */
  @Nonnull
  public <ValueT> OrchestrationAiModel withParam(
      @Nonnull final Parameter<ValueT> param, @Nullable final ValueT value) {
    return withParam(param.getName(), value);
  }

  /**
   * Parameter key for a model.
   *
   * @param <ValueT> the parameter value type.
   */
  @FunctionalInterface
  public interface Parameter<ValueT> {
    /** The maximum number of tokens to generate. */
    Parameter<Integer> MAX_TOKENS = () -> "max_tokens";

    /** The sampling temperature. */
    Parameter<Number> TEMPERATURE = () -> "temperature";

    /** The frequency penalty. */
    Parameter<Number> FREQUENCY_PENALTY = () -> "frequency_penalty";

    /** The presence penalty. */
    Parameter<Number> PRESENCE_PENALTY = () -> "presence_penalty";

    /** The probability mass to be considered . */
    Parameter<Number> TOP_P = () -> "top_p";

    /** The number of chat completion choices to generate for each input message. */
    Parameter<Integer> N = () -> "n";

    /**
     * The name of the parameter.
     *
     * @return the name of the parameter.
     */
    @Nonnull
    String getName();
  }
}
