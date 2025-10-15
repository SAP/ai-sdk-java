package com.sap.ai.sdk.foundationmodels.openai;

import com.google.common.collect.Lists;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionStreamOptions;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionTool;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionToolChoiceOption;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.CreateChatCompletionRequest;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.CreateChatCompletionRequestAllOfResponseFormat;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.CreateChatCompletionRequestAllOfStop;
import java.math.BigDecimal;
import java.util.ArrayList;
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
 * @see <a
 *     href="https://platform.openai.com/docs/api-reference/chat/create#chat-create-messages">OpenAI
 *     API Reference</a>
 * @since 1.4.0
 */
@Value
@With
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter(value = AccessLevel.NONE)
public class OpenAiChatCompletionRequest {
  /** List of messages from the conversation. */
  @Nonnull List<OpenAiMessage> messages;

  /** Upto 4 Stop sequences to interrupts token generation and returns a response without them. */
  @Nullable List<String> stop;

  /**
   * Controls the randomness of the completion.
   *
   * <p>Lower values (e.g. 0.0) make the model more deterministic and repetitive, while higher
   * values (e.g. 1.0) make the model more random and creative.
   */
  @Nullable BigDecimal temperature;

  /**
   * Controls the cumulative probability threshold used for nucleus sampling. Alternative to {@link
   * #temperature}.
   *
   * <p>Lower values (e.g. 0.1) limit the model to consider only the smallest set of tokens whose
   * combined probabilities add up to at least 10% of the total.
   */
  @Nullable BigDecimal topP;

  /** Maximum number of tokens that can be generated for the completion. */
  @Nullable Integer maxTokens;

  /**
   * Maximum number of tokens that can be generated for the completion, including consumed reasoning
   * tokens. This field supersedes {@link #maxTokens} and should be used with newer models.
   */
  @Nullable Integer maxCompletionTokens;

  /**
   * Encourage new topic by penalising token based on their presence in the completion.
   *
   * <p>Value should be in range [-2, 2].
   */
  @Nullable BigDecimal presencePenalty;

  /**
   * Encourage new topic by penalising tokens based on their frequency in the completion.
   *
   * <p>Value should be in range [-2, 2].
   */
  @Nullable BigDecimal frequencyPenalty;

  /**
   * A map that adjusts the likelihood of specified tokens by adding a bias value (between -100 and
   * 100) to the logits before sampling. Extreme values can effectively ban or enforce the selection
   * of tokens.
   */
  @Nullable Map<String, Integer> logitBias;

  /**
   * Unique identifier for the end-user making the request. This can help with monitoring and abuse
   * detection.
   */
  @Nullable String user;

  /** Whether to include log probabilities in the response. */
  @With(AccessLevel.NONE)
  @Nullable
  Boolean logprobs;

  /**
   * Number of top log probabilities to return for each token. An integer between 0 and 20. This is
   * only relevant if {@code logprobs} is enabled.
   */
  @Nullable Integer topLogprobs;

  /** Number of completions to generate. */
  @Nullable Integer n;

  /** Whether to allow parallel tool calls. */
  @With(AccessLevel.NONE)
  @Nullable
  Boolean parallelToolCalls;

  /** Seed for random number generation. */
  @Nullable Integer seed;

  /** Options for streaming the completion response. */
  @Nullable ChatCompletionStreamOptions streamOptions;

  /** Response format for the completion. */
  @Nullable CreateChatCompletionRequestAllOfResponseFormat responseFormat;

  /**
   * Tools the model may invoke during chat completion (metadata only).
   *
   * <p>Use {@link #withToolsExecutable} for registering executable tools.
   */
  @Nullable List<ChatCompletionTool> tools;

  /**
   * Tools the model may invoke during chat completion that are also executable at application
   * runtime.
   *
   * @since 1.8.0
   */
  @Getter(value = AccessLevel.PACKAGE)
  @Nullable
  List<OpenAiTool> toolsExecutable;

  /** Option to control which tool is invoked by the model. */
  @With(AccessLevel.PRIVATE)
  @Nullable
  ChatCompletionToolChoiceOption toolChoice;

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
    this(Lists.asList(message, messages));
  }

  /**
   * Creates an OpenAiChatCompletionPrompt with a list of messages.
   *
   * @param messages the list of messages to be added to the prompt
   * @since 1.6.0
   */
  @Tolerate
  public OpenAiChatCompletionRequest(@Nonnull final List<OpenAiMessage> messages) {
    this(
        List.copyOf(messages),
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
        null,
        null);
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
    return this.withStop(Lists.asList(sequence, sequences));
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
            this.toolsExecutable,
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
            this.toolsExecutable,
            this.toolChoice);
  }

  /**
   * Define the model behavior towards calling functions.
   *
   * <p>Example:
   *
   * <ul>
   *   <li><code>.withToolChoice(OpenAiToolChoice.NONE)</code>
   *   <li><code>.withToolChoice(OpenAiToolChoice.OPTIONAL)</code>
   *   <li><code>.withToolChoice(OpenAiToolChoice.REQUIRED)</code>
   *   <li><code>.withToolChoice(OpenAiToolChoice.function("fibonacci")</code>
   * </ul>
   *
   * @param choice the generic tool choice.
   * @return the current OpenAiChatCompletionRequest instance.
   */
  @Nonnull
  @Tolerate
  public OpenAiChatCompletionRequest withToolChoice(@Nonnull final OpenAiToolChoice choice) {
    return this.withToolChoice(choice.toolChoice);
  }

  /**
   * Converts the request to a generated model class CreateChatCompletionRequest.
   *
   * @return the CreateChatCompletionRequest
   */
  CreateChatCompletionRequest createCreateChatCompletionRequest() {
    final var request = new CreateChatCompletionRequest();
    this.messages.forEach(
        message ->
            request.addMessagesItem(OpenAiUtils.createChatCompletionRequestMessage(message)));

    if (stop != null) {
      request.stop(CreateChatCompletionRequestAllOfStop.createListOfStrings(this.stop));
    }

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
    request.tools(getChatCompletionTools());
    request.toolChoice(this.toolChoice);
    request.functionCall(null);
    request.functions(null);
    return request;
  }

  @Nullable
  private List<ChatCompletionTool> getChatCompletionTools() {
    final var toolsCombined = new ArrayList<ChatCompletionTool>();
    if (this.tools != null) {
      toolsCombined.addAll(this.tools);
    }
    if (this.toolsExecutable != null) {
      for (final OpenAiTool tool : this.toolsExecutable) {
        toolsCombined.add(tool.createChatCompletionTool());
      }
    }
    return toolsCombined.isEmpty() ? null : toolsCombined;
  }
}
