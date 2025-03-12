package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.model.LLMModuleConfig;
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

  /** IBM Granite 13B Chat model */
  public static final OrchestrationAiModel IBM_GRANITE_13B_CHAT =
      new OrchestrationAiModel("ibm--granite-13b-chat");

  /** MistralAI Mistral Large Instruct model */
  public static final OrchestrationAiModel MISTRAL_LARGE_INSTRUCT =
      new OrchestrationAiModel("mistralai--mistral-large-instruct");

  /** MistralAI Mixtral 8x7B Instruct v01 model */
  public static final OrchestrationAiModel MIXTRAL_8X7B_INSTRUCT_V01 =
      new OrchestrationAiModel("mistralai--mixtral-8x7b-instruct-v01");

  /**
   * Meta Llama3 70B Instruct model
   *
   * @deprecated This model is deprecated on AI Core with a planned retirement on 2024-12-15.
   * The suggested replacement model is {@link OrchestrationAiModel#LLAMA3_1_70B_INSTRUCT}.
   * */
  @Deprecated
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
   * @deprecated This model is deprecated on AI Core with a planned retirement on 2025-02-13.
   * The suggested replacement model is {@link OrchestrationAiModel#GPT_4O_MINI}.
   * */
  @Deprecated
  public static final OrchestrationAiModel GPT_35_TURBO = new OrchestrationAiModel("gpt-35-turbo");

  /**
   * Azure OpenAI GPT-3.5 Turbo model
   *
   * @deprecated This model is deprecated on AI Core with a planned retirement on 2025-02-13.
   * The suggested replacement model is {@link OrchestrationAiModel#GPT_4O_MINI}.
   * */
  @Deprecated
  public static final OrchestrationAiModel GPT_35_TURBO_16K =
      new OrchestrationAiModel("gpt-35-turbo-16k");

  /**
   * Azure OpenAI GPT-3.5 Turbo model
   *
   * @deprecated This model is deprecated on AI Core with a planned retirement on 2025-02-22.
   * The suggested replacement model is {@link OrchestrationAiModel#GPT_4O_MINI}.
   * */
  @Deprecated
  public static final OrchestrationAiModel GPT_35_TURBO_0125 =
      new OrchestrationAiModel("gpt-35-turbo-0125");

  /** Azure OpenAI GPT-4 model */
  public static final OrchestrationAiModel GPT_4 = new OrchestrationAiModel("gpt-4");

  /**
   * Azure OpenAI GPT-4 model
   *
   * @deprecated This model is deprecated on AI Core with a planned retirement on 2025-05-13.
   * The suggested replacement model is {@link OrchestrationAiModel#GPT_4O}.
   * */
  @Deprecated
  public static final OrchestrationAiModel GPT_4_32K = new OrchestrationAiModel("gpt-4-32k");

  /** Azure OpenAI GPT-4o chat completions model */
  public static final OrchestrationAiModel GPT_4O = new OrchestrationAiModel("gpt-4o");

  /** Azure OpenAI GPT-4o-mini chat completions model */
  public static final OrchestrationAiModel GPT_4O_MINI = new OrchestrationAiModel("gpt-4o-mini");

  /** Azure OpenAI o1 chat completions model */
  public static final OrchestrationAiModel OPENAI_O1 = new OrchestrationAiModel("o1");

  /** Azure OpenAI o3-mini chat completions model */
  public static final OrchestrationAiModel OPENAI_O3_MINI = new OrchestrationAiModel("o3-mini");

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
