package com.sap.ai.sdk.foundationmodels.openai;

import com.sap.ai.sdk.foundationmodels.openai.model2.ChatCompletionFunctions;
import com.sap.ai.sdk.foundationmodels.openai.model2.ChatCompletionStreamOptions;
import com.sap.ai.sdk.foundationmodels.openai.model2.ChatCompletionTool;
import com.sap.ai.sdk.foundationmodels.openai.model2.ChatCompletionToolChoiceOption;
import com.sap.ai.sdk.foundationmodels.openai.model2.CreateChatCompletionRequestAllOfFunctionCall;
import com.sap.ai.sdk.foundationmodels.openai.model2.CreateChatCompletionRequestAllOfResponseFormat;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import lombok.Data;
import lombok.experimental.Accessors;

/** Configuration class for OpenAI Chat Completion. */
@Accessors(fluent = true)
@Data
public class OpenAiChatCompletionConfig {

  /** Temperature for the completion. */
  @Nullable private BigDecimal temperature = new BigDecimal("1");

  /** Top-p sampling parameter. */
  @Nullable private BigDecimal topP = new BigDecimal("1");

  /** Whether to stream the completion. */
  @Nullable private Boolean stream = false;

  /** Maximum number of tokens for the completion. */
  @Nullable private Integer maxTokens;

  /** Maximum number of tokens for the completion response. */
  @Nullable private Integer maxCompletionTokens;

  /** Presence penalty for the completion. */
  @Nullable private BigDecimal presencePenalty = new BigDecimal("0");

  /** Frequency penalty for the completion. */
  @Nullable private BigDecimal frequencyPenalty = new BigDecimal("0");

  /** Logit bias for the completion. */
  @Nullable private Map<String, Integer> logitBias;

  /** User identifier for the completion. */
  @Nullable private String user;

  /** Whether to include log probabilities in the response. */
  @Nullable private Boolean logprobs = false;

  /** Number of top log probabilities to include. */
  @Nullable private Integer topLogprobs;

  /** Number of completions to generate. */
  @Nullable private Integer n = 1;

  /** Whether to allow parallel tool calls. */
  @Nullable private Boolean parallelToolCalls;

  /** Seed for random number generation. */
  @Nullable private Integer seed;

  /** Options for streaming the completion. */
  @Nullable private ChatCompletionStreamOptions streamOptions;

  /** Response format for the completion. */
  @Nullable private CreateChatCompletionRequestAllOfResponseFormat responseFormat;

  /** List of tools for the completion. */
  @Nullable private List<ChatCompletionTool> tools;

  /** Tool choice option for the completion. */
  @Nullable private ChatCompletionToolChoiceOption toolChoice;

  // TODO: Leave them here or remove deprecated out of convenience?
  /** Deprecated: Function call for the completion. */
  @Nullable private CreateChatCompletionRequestAllOfFunctionCall functionCall;

  /** List of functions for the completion. */
  @Nullable private List<ChatCompletionFunctions> functions;
}
