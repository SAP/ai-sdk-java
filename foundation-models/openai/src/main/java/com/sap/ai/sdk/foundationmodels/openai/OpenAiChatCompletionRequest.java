package com.sap.ai.sdk.foundationmodels.openai;

import com.google.common.annotations.Beta;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionStreamOptions;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionTool;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionToolChoiceOption;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.CreateChatCompletionRequest;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.CreateChatCompletionRequestAllOfResponseFormat;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.CreateChatCompletionRequestAllOfStop;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;
import lombok.With;
import lombok.experimental.Tolerate;

/**
 * Represents a request for OpenAI chat completion, including conversation messages and parameters.
 *
 * @since 1.4.0
 */
@Beta
@Value
@With
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter(value = AccessLevel.NONE)
public class OpenAiChatCompletionRequest {
  /** List of messages from the conversation. */
  @Nonnull List<OpenAiMessage> messages;

  /** Stop sequences for the completion. */
  @Nullable List<String> stop;

  /** Temperature for the completion. */
  @Nullable BigDecimal temperature;

  /** Top-p sampling parameter. */
  @Nullable BigDecimal topP;

  /** Maximum number of tokens for the completion. */
  @Nullable Integer maxTokens;

  /** Maximum number of tokens for the completion response. */
  @Nullable Integer maxCompletionTokens;

  /** Presence penalty for the completion. */
  @Nullable BigDecimal presencePenalty;

  /** Frequency penalty for the completion. */
  @Nullable BigDecimal frequencyPenalty;

  /** Logit bias for the completion. */
  @Nullable Map<String, Integer> logitBias;

  /** User identifier for the completion. */
  @Nullable String user;

  /** Whether to include log probabilities in the response. */
  @With(AccessLevel.NONE)
  @Nullable
  Boolean logprobs;

  /** Number of top log probabilities to include. */
  @Nullable Integer topLogprobs;

  /** Number of completions to generate. */
  @Nullable Integer n;

  /** Whether to allow parallel tool calls. */
  @With(AccessLevel.NONE)
  @Nullable
  Boolean parallelToolCalls;

  /** Seed for random number generation. */
  @Nullable Integer seed;

  /** Options for streaming the completion. */
  @Nullable ChatCompletionStreamOptions streamOptions;

  /** Response format for the completion. */
  @Nullable CreateChatCompletionRequestAllOfResponseFormat responseFormat;

  /** List of tools for the completion. */
  @Nullable List<ChatCompletionTool> tools;

  /** Tool choice option for the completion. */
  @Nullable ChatCompletionToolChoiceOption toolChoice;

  /**
   * Creates an OpenAiChatCompletionPrompt with string as user message.
   *
   * @param message the message to be added to the prompt
   */
  @Tolerate
  public OpenAiChatCompletionRequest(@Nonnull final String message) {
    this(OpenAiMessage.user(message));
  }

  /**
   * Creates an OpenAiChatCompletionPrompt with a multiple unpacked messages.
   *
   * @param message the primary message to be added to the prompt
   * @param messages additional messages to be added to the prompt
   */
  @Tolerate
  public OpenAiChatCompletionRequest(
      @Nonnull final OpenAiMessage message, @Nonnull final OpenAiMessage... messages) {
    // Keeps default values for boolean fields. @With introduces bug comparison of Boolean
    this(
        new ArrayList<OpenAiMessage>(),
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null);

    this.messages.add(message);
    this.messages.addAll(Arrays.asList(messages));
  }

  /**
   * Adds stop sequences to the request.
   *
   * @param sequence the primary stop sequence
   * @param sequences additional stop sequences
   * @return a new OpenAiChatCompletionRequest instance with the specified stop sequences
   */
  @Tolerate
  @Nonnull
  public OpenAiChatCompletionRequest withStop(
      @Nonnull final String sequence, @Nonnull final String... sequences) {
    final var allSequences = new ArrayList<String>();

    allSequences.add(sequence);
    allSequences.addAll(Arrays.asList(sequences));

    return this.withStop(allSequences);
  }

  /**
   * Sets the parallel tool calls option.
   *
   * @param parallelToolCalls Whether to allow parallel tool calls.
   * @return A new instance with the specified option.
   */
  @Nonnull
  public OpenAiChatCompletionRequest withParallelToolCalls(
      @Nonnull final Boolean parallelToolCalls) {
    return Objects.equals(this.parallelToolCalls, parallelToolCalls)
        ? this
        : new OpenAiChatCompletionRequest(
            this.messages,
            this.stop,
            this.temperature,
            this.topP,
            this.maxTokens,
            this.maxCompletionTokens,
            this.presencePenalty,
            this.frequencyPenalty,
            this.logitBias,
            this.user,
            this.logprobs,
            this.topLogprobs,
            this.n,
            parallelToolCalls,
            this.seed,
            this.streamOptions,
            this.responseFormat,
            this.tools,
            this.toolChoice);
  }

  /**
   * Sets the log probabilities option.
   *
   * @param logprobs Whether to include log probabilities in the response.
   * @return A new instance with the specified option.
   */
  @Nonnull
  public OpenAiChatCompletionRequest withLogprobs(@Nonnull final Boolean logprobs) {
    return Objects.equals(this.logprobs, logprobs)
        ? this
        : new OpenAiChatCompletionRequest(
            this.messages,
            this.stop,
            this.temperature,
            this.topP,
            this.maxTokens,
            this.maxCompletionTokens,
            this.presencePenalty,
            this.frequencyPenalty,
            this.logitBias,
            this.user,
            logprobs,
            this.topLogprobs,
            this.n,
            this.parallelToolCalls,
            this.seed,
            this.streamOptions,
            this.responseFormat,
            this.tools,
            this.toolChoice);
  }

  /**
   * Converts the request to a generated model class CreateChatCompletionRequest.
   *
   * @return the CreateChatCompletionRequest
   */
  CreateChatCompletionRequest toCreateChatCompletionRequest() {
    final var request = new CreateChatCompletionRequest();
    this.messages.forEach(
        message ->
            request.addMessagesItem(OpenAiUtils.createChatCompletionRequestMessage(message)));

    request.stop(this.stop != null ? CreateChatCompletionRequestAllOfStop.create(this.stop) : null);

    request.temperature(this.temperature);
    request.topP(this.topP);

    request.stream(null);
    request.maxTokens(this.maxTokens);
    request.maxCompletionTokens(this.maxCompletionTokens);
    request.presencePenalty(this.presencePenalty);
    request.frequencyPenalty(this.frequencyPenalty);
    request.logitBias(this.logitBias);
    request.user(this.user);
    request.logprobs(this.logprobs);
    request.topLogprobs(this.topLogprobs);
    request.n(this.n);
    request.parallelToolCalls(this.parallelToolCalls);
    request.seed(this.seed);
    request.streamOptions(this.streamOptions);
    request.responseFormat(this.responseFormat);
    request.tools(this.tools);
    request.toolChoice(this.toolChoice);
    request.functionCall(null);
    request.functions(null);
    return request;
  }
}
