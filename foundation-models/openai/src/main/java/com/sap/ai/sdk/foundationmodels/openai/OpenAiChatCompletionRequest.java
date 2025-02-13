package com.sap.ai.sdk.foundationmodels.openai;

import com.google.common.annotations.Beta;
import com.sap.ai.sdk.foundationmodels.openai.model2.ChatCompletionStreamOptions;
import com.sap.ai.sdk.foundationmodels.openai.model2.ChatCompletionTool;
import com.sap.ai.sdk.foundationmodels.openai.model2.ChatCompletionToolChoiceOption;
import com.sap.ai.sdk.foundationmodels.openai.model2.CreateChatCompletionRequest;
import com.sap.ai.sdk.foundationmodels.openai.model2.CreateChatCompletionRequestAllOfResponseFormat;
import com.sap.ai.sdk.foundationmodels.openai.model2.CreateChatCompletionRequestAllOfStop;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Represents a request for OpenAI chat completion, including conversation messages and parameters.
 *
 * @since 1.3.0
 */
@Beta
@Accessors(fluent = true)
@Data
public class OpenAiChatCompletionRequest {
  /** List of messages from the conversation. */
  @Nonnull private final List<OpenAiMessage> messages = new ArrayList<>();

  /** Stop sequences for the completion. */
  @Nullable private List<String> stop;

  /** Temperature for the completion. */
  @Nullable private BigDecimal temperature;

  /** Top-p sampling parameter. */
  @Nullable private BigDecimal topP;

  /** Whether to stream the completion. */
  @Nullable private Boolean stream;

  /** Maximum number of tokens for the completion. */
  @Nullable private Integer maxTokens;

  /** Maximum number of tokens for the completion response. */
  @Nullable private Integer maxCompletionTokens;

  /** Presence penalty for the completion. */
  @Nullable private BigDecimal presencePenalty;

  /** Frequency penalty for the completion. */
  @Nullable private BigDecimal frequencyPenalty;

  /** Logit bias for the completion. */
  @Nullable private Map<String, Integer> logitBias;

  /** User identifier for the completion. */
  @Nullable private String user;

  /** Whether to include log probabilities in the response. */
  @Nullable private Boolean logprobs;

  /** Number of top log probabilities to include. */
  @Nullable private Integer topLogprobs;

  /** Number of completions to generate. */
  @Nullable private Integer n;

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

  /**
   * Creates an OpenAiChatCompletionPrompt with a single message.
   *
   * @param message the message to be added to the prompt
   */
  public OpenAiChatCompletionRequest(@Nonnull final String message) {
    messages.add(OpenAiMessage.user(message));
  }

  /**
   * Creates an OpenAiChatCompletionPrompt with a multiple unpacked messages.
   *
   * @param message the primary message to be added to the prompt
   * @param messages additional messages to be added to the prompt
   */
  public OpenAiChatCompletionRequest(
      @Nonnull final OpenAiMessage message, @Nonnull final OpenAiMessage... messages) {
    this.messages.add(message);
    this.messages.addAll(Arrays.asList(messages));
  }

  /**
   * Sets the stop sequences for the prompt.
   *
   * @param values the stop sequences to be set
   * @return the current OpenAiChatCompletionPrompt instance
   */
  @Nonnull
  public OpenAiChatCompletionRequest stop(
      @Nonnull final String value, @Nonnull final String... values) {
    this.stop = new ArrayList<>();

    this.stop.add(value);
    this.stop.addAll(Arrays.asList(values));

    return this;
  }

  CreateChatCompletionRequest toCreateChatCompletionRequest() {
    final var request = new CreateChatCompletionRequest();
    this.messages().forEach(message -> request.addMessagesItem(message.createDTO()));

    request.stop(
        this.stop() != null ? CreateChatCompletionRequestAllOfStop.create(this.stop()) : null);

    request.temperature(this.temperature());
    request.topP(this.topP());
    request.stream(this.stream());
    request.maxTokens(this.maxTokens());
    request.maxCompletionTokens(this.maxCompletionTokens());
    request.presencePenalty(this.presencePenalty());
    request.frequencyPenalty(this.frequencyPenalty());
    request.logitBias(this.logitBias());
    request.user(this.user());
    request.logprobs(this.logprobs());
    request.topLogprobs(this.topLogprobs());
    request.n(this.n());
    request.parallelToolCalls(this.parallelToolCalls());
    request.seed(this.seed());
    request.streamOptions(this.streamOptions());
    request.responseFormat(this.responseFormat());
    request.tools(this.tools());
    request.toolChoice(this.toolChoice());
    request.functionCall(null);
    request.functions(null);
    return request;
  }
}
