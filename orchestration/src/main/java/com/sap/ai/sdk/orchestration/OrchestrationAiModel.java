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
   *     "max_completion_tokens", 50,
   *     "temperature", 0.1,
   *     "frequency_penalty", 0,
   *     "presence_penalty", 0)
   * }</pre>
   *
   * <p><a href="https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/harmonized-api">SAP
   * AI Core: Orchestration - Harmonized API</a>
   */
  Map<String, Object> params;

  /** The version of the model, defaults to "latest". */
  String version;

  /** MistralAI Mistral Medium Instruct model */
  public static final OrchestrationAiModel MISTRAL_MEDIUM_INSTRUCT =
      new OrchestrationAiModel("mistralai--mistral-medium-instruct");

  /** MistralAI Mistral Small model */
  public static final OrchestrationAiModel MISTRAL_SMALL =
      new OrchestrationAiModel("mistralai--mistral-small");

  /** MistralAI Mistral Medium model */
  public static final OrchestrationAiModel MISTRAL_MEDIUM =
      new OrchestrationAiModel("mistralai--mistral-medium");

  /** Meta Llama Cinderella DN model */
  public static final OrchestrationAiModel LLAMA_CINDERELLA_DN =
      new OrchestrationAiModel("llama-cinderella-dn");

  /** Cohere Command a Reasoning model */
  public static final OrchestrationAiModel COHERE_COMMAND_A_REASONING =
      new OrchestrationAiModel("cohere--command-a-reasoning");

  /** Anthropic Claude 4 Opus model */
  public static final OrchestrationAiModel CLAUDE_4_OPUS =
      new OrchestrationAiModel("anthropic--claude-4-opus");

  /** Anthropic Claude 4.5 Opus model */
  public static final OrchestrationAiModel CLAUDE_4_5_OPUS =
      new OrchestrationAiModel("anthropic--claude-4.5-opus");

  /** Anthropic Claude 4.5 Sonnet model */
  public static final OrchestrationAiModel CLAUDE_4_5_SONNET =
      new OrchestrationAiModel("anthropic--claude-4.5-sonnet");

  /** Anthropic Claude 4.5 Haiku model */
  public static final OrchestrationAiModel CLAUDE_4_5_HAIKU =
      new OrchestrationAiModel("anthropic--claude-4.5-haiku");

  /** Anthropic Claude 4.6 Opus model */
  public static final OrchestrationAiModel CLAUDE_4_6_OPUS =
      new OrchestrationAiModel("anthropic--claude-4.6-opus");

  /** Anthropic Claude 4.6 Sonnet model */
  public static final OrchestrationAiModel CLAUDE_4_6_SONNET =
      new OrchestrationAiModel("anthropic--claude-4.6-sonnet");

  /** Anthropic Claude 4.7 Opus model */
  public static final OrchestrationAiModel CLAUDE_4_7_OPUS =
      new OrchestrationAiModel("anthropic--claude-4.7-opus");

  /** Anthropic Claude 4.8 Opus model */
  public static final OrchestrationAiModel CLAUDE_4_8_OPUS =
      new OrchestrationAiModel("anthropic--claude-4.8-opus");

  /** Amazon Nova Pro model */
  public static final OrchestrationAiModel NOVA_PRO = new OrchestrationAiModel("amazon--nova-pro");

  /** Amazon Nova Lite model */
  public static final OrchestrationAiModel NOVA_LITE =
      new OrchestrationAiModel("amazon--nova-lite");

  /** Amazon Nova Micro model */
  public static final OrchestrationAiModel NOVA_MICRO =
      new OrchestrationAiModel("amazon--nova-micro");

  /** Amazon Nova Premier model */
  public static final OrchestrationAiModel NOVA_PREMIER =
      new OrchestrationAiModel("amazon--nova-premier");

  /** Azure OpenAI GPT-4.1-mini model */
  public static final OrchestrationAiModel GPT_41_MINI = new OrchestrationAiModel("gpt-4.1-mini");

  /** Azure OpenAI GPT-4.1 model */
  public static final OrchestrationAiModel GPT_41 = new OrchestrationAiModel("gpt-4.1");

  /** Azure OpenAI GPT-4.1-nano model */
  public static final OrchestrationAiModel GPT_41_NANO = new OrchestrationAiModel("gpt-4.1-nano");

  /** Azure OpenAI GPT-4o model */
  public static final OrchestrationAiModel GPT_4O = new OrchestrationAiModel("gpt-4o");

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

  /** Azure OpenAI GPT-5.1 model */
  public static final OrchestrationAiModel GPT_51 = new OrchestrationAiModel("gpt-5.1");

  /** Azure OpenAI GPT-5.2 model */
  public static final OrchestrationAiModel GPT_52 = new OrchestrationAiModel("gpt-5.2");

  /** Azure OpenAI GPT-5.3-codex model */
  public static final OrchestrationAiModel GPT_53_CODEX = new OrchestrationAiModel("gpt-5.3-codex");

  /** Azure OpenAI GPT-5.4 model */
  public static final OrchestrationAiModel GPT_54 = new OrchestrationAiModel("gpt-5.4");

  /** Azure OpenAI GPT-5.4-nano model */
  public static final OrchestrationAiModel GPT_54_NANO = new OrchestrationAiModel("gpt-5.4-nano");

  /** Azure OpenAI GPT-5.5 model */
  public static final OrchestrationAiModel GPT_55 = new OrchestrationAiModel("gpt-5.5");

  /** Azure OpenAI GPT-5.6-sol model */
  public static final OrchestrationAiModel GPT_56_SOL = new OrchestrationAiModel("gpt-5.6-sol");

  /** Azure OpenAI GPT-5.6-luna model */
  public static final OrchestrationAiModel GPT_56_LUNA = new OrchestrationAiModel("gpt-5.6-luna");

  /** Azure OpenAI GPT-5.6-terra model */
  public static final OrchestrationAiModel GPT_56_TERRA = new OrchestrationAiModel("gpt-5.6-terra");

  /** Google Cloud Platform Gemini 2.5 Flash model */
  public static final OrchestrationAiModel GEMINI_2_5_FLASH =
      new OrchestrationAiModel("gemini-2.5-flash");

  /** Google Cloud Platform Gemini 2.5 Flash Lite model */
  public static final OrchestrationAiModel GEMINI_2_5_FLASH_LITE =
      new OrchestrationAiModel("gemini-2.5-flash-lite");

  /** Google Cloud Platform Gemini 2.5 Pro model */
  public static final OrchestrationAiModel GEMINI_2_5_PRO =
      new OrchestrationAiModel("gemini-2.5-pro");

  /** Google Cloud Platform Gemini 3.1 Flash Lite model */
  public static final OrchestrationAiModel GEMINI_3_1_FLASH_LITE =
      new OrchestrationAiModel("gemini-3.1-flash-lite");

  /** Google Cloud Platform Gemini 3.5 Flash model */
  public static final OrchestrationAiModel GEMINI_3_5_FLASH =
      new OrchestrationAiModel("gemini-3.5-flash");

  /** Google Cloud Platform Gemini 3.1 Pro preview early access model */
  public static final OrchestrationAiModel GEMINI_3_1_PRO_PREVIEW_EA =
      new OrchestrationAiModel("gemini-3.1-pro-preview-ea");

  /** Google Cloud Platform Gemini 3.5 Flash Lite model */
  public static final OrchestrationAiModel GEMINI_3_5_FLASH_LITE =
      new OrchestrationAiModel("gemini-3.5-flash-lite");

  /** Google Cloud Platform Gemini 3.6 Flash model */
  public static final OrchestrationAiModel GEMINI_3_6_FLASH =
      new OrchestrationAiModel("gemini-3.6-flash");

  /** Google Cloud Platform Gemini 3.8 Flash model */
  public static final OrchestrationAiModel GEMINI_3_8_FLASH =
      new OrchestrationAiModel("gemini-3.8-flash");

  /** Perplexity AI Sonar model */
  public static final OrchestrationAiModel SONAR = new OrchestrationAiModel("sonar");

  /** Perplexity AI Sonar Pro model */
  public static final OrchestrationAiModel SONAR_PRO = new OrchestrationAiModel("sonar-pro");

  /** Perplexity AI Sonar Deep Research model */
  public static final OrchestrationAiModel SONAR_DEEP_RESEARCH =
      new OrchestrationAiModel("sonar-deep-research");

  /** SAP ABAP 1 model */
  public static final OrchestrationAiModel SAP_ABAP_1 = new OrchestrationAiModel("sap-abap-1");

  /** Alibaba Qwen 3 max model */
  public static final OrchestrationAiModel QWEN_3_MAX = new OrchestrationAiModel("qwen3-max");

  /** Alibaba Qwen 3.6 plus model */
  public static final OrchestrationAiModel QWEN_3_6_PLUS = new OrchestrationAiModel("qwen3.6-plus");

  /** Alibaba Qwen 3.6 flash model */
  public static final OrchestrationAiModel QWEN_3_6_FLASH =
      new OrchestrationAiModel("qwen3.6-flash");

  /** Alibaba Qwen 3.7 max model */
  public static final OrchestrationAiModel QWEN_3_7_MAX = new OrchestrationAiModel("qwen3.7-max");

  /** Alibaba Qwen 3.7 plus model */
  public static final OrchestrationAiModel QWEN_3_7_PLUS = new OrchestrationAiModel("qwen3.7-plus");

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
   *     <p><a
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
   *     <p><a
   *     href="https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/harmonized-api">SAP
   *     AI Core: Orchestration - Harmonized API</a>
   */
  @Nonnull
  public <ValueT> OrchestrationAiModel withParam(
      @Nonnull final Parameter<ValueT> param, @Nullable final ValueT value) {
    return withParam(param.getName(), value);
  }

  /**
   * Set the {@code reasoning_effort} parameter on this model.
   *
   * @param effort the reasoning effort level.
   * @return a new model with the {@code reasoning_effort} parameter set.
   * @see <a href="https://help.sap.com/docs/sap-ai-core/generative-ai/reasoning">SAP AI Core:
   *     Orchestration - Reasoning</a>
   */
  @Nonnull
  public OrchestrationAiModel withReasoningEffort(@Nonnull final ReasoningEffort effort) {
    return withParam(Parameter.REASONING_EFFORT, effort.getValue());
  }

  /**
   * Parameter key for a model.
   *
   * @param <ValueT> the parameter value type.
   */
  @FunctionalInterface
  public interface Parameter<ValueT> {
    /** The maximum number of tokens to generate. */
    Parameter<Integer> MAX_TOKENS = () -> "max_completion_tokens";

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

    /** The reasoning effort for reasoning-capable models. */
    Parameter<String> REASONING_EFFORT = () -> "reasoning_effort";

    /**
     * The name of the parameter.
     *
     * @return the name of the parameter.
     */
    @Nonnull
    String getName();
  }
}
