package com.sap.ai.sdk.foundationmodels.openai;

import com.google.common.annotations.Beta;
import com.google.common.collect.Lists;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionStreamOptions;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionTool;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.CreateChatCompletionRequest;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.CreateChatCompletionRequestAllOfResponseFormat;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.CreateChatCompletionRequestAllOfStop;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.*;
import lombok.experimental.Tolerate;

/**
 * Represents a request for OpenAI chat completion, including conversation messages and parameters.
 *
 * @see <a
 *     href="https://platform.openai.com/docs/api-reference/chat/create#chat-create-messages">OpenAI
 *     API Reference</a>
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

  @Setter(AccessLevel.NONE)
  @Getter(AccessLevel.PACKAGE)
  OpenAiChatCompletionConfig config;

  public OpenAiChatCompletionRequest withStop(@Nonnull final List<String> stop) {
    return this.withConfig(config.withStop(stop));
  }

  public OpenAiChatCompletionRequest withTemperature(@Nonnull final BigDecimal temperature) {
    return this.withConfig(config.withTemperature(temperature));
  }

  public OpenAiChatCompletionRequest withTopP(@Nonnull final BigDecimal topP) {
    return this.withConfig(config.withTopP(topP));
  }

  public OpenAiChatCompletionRequest withMaxTokens(@Nonnull final Integer maxTokens) {
    return this.withConfig(config.withMaxTokens(maxTokens));
  }

  public OpenAiChatCompletionRequest withMaxCompletionTokens(
      @Nonnull final Integer maxCompletionTokens) {
    return this.withConfig(config.withMaxCompletionTokens(maxCompletionTokens));
  }

  public OpenAiChatCompletionRequest withPresencePenalty(
      @Nonnull final BigDecimal presencePenalty) {
    return this.withConfig(config.withPresencePenalty(presencePenalty));
  }

  public OpenAiChatCompletionRequest withFrequencyPenalty(
      @Nonnull final BigDecimal frequencyPenalty) {
    return this.withConfig(config.withFrequencyPenalty(frequencyPenalty));
  }

  public OpenAiChatCompletionRequest withTopLogprobs(@Nonnull final Integer topLogprobs) {
    return this.withConfig(config.withTopLogprobs(topLogprobs));
  }

  public OpenAiChatCompletionRequest withUser(@Nonnull final String user) {
    return this.withConfig(config.withUser(user));
  }

  public OpenAiChatCompletionRequest withLogitBias(@Nonnull final Map<String, Integer> logitBias) {
    return this.withConfig(config.withLogitBias(logitBias));
  }

  public OpenAiChatCompletionRequest withN(@Nonnull final Integer n) {
    return this.withConfig(config.withN(n));
  }

  public OpenAiChatCompletionRequest withSeed(@Nonnull final Integer seed) {
    return this.withConfig(config.withSeed(seed));
  }

  public OpenAiChatCompletionRequest withStreamOptions(
      @Nonnull final ChatCompletionStreamOptions streamOptions) {
    return this.withConfig(config.withStreamOptions(streamOptions));
  }

  public OpenAiChatCompletionRequest withResponseFormat(
      @Nonnull final CreateChatCompletionRequestAllOfResponseFormat responseFormat) {
    return this.withConfig(config.withResponseFormat(responseFormat));
  }

  public OpenAiChatCompletionRequest withTools(@Nonnull final List<ChatCompletionTool> tools) {
    return this.withConfig(config.withTools(tools));
  }

  public OpenAiChatCompletionRequest withToolsExecutable(
      @Nonnull final List<OpenAiTool> toolsExecutable) {
    return this.withConfig(config.withToolsExecutable(toolsExecutable));
  }

  /**
   * Creates an OpenAiChatCompletionPrompt with string as user message.
   *
   * @param message the message to be added to the prompt
   */
  @Tolerate
  public OpenAiChatCompletionRequest(@Nonnull final String message) {
    this(List.of(OpenAiMessage.user(message)));
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
    this(List.copyOf(messages), new OpenAiChatCompletionConfig());
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
    return withStop(Lists.asList(sequence, sequences));
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
    return this.withConfig(config.withParallelToolCalls(parallelToolCalls));
  }

  /**
   * Sets the log probabilities option.
   *
   * @param logprobs Whether to include log probabilities in the response.
   * @return A new instance with the specified option.
   */
  @Nonnull
  public OpenAiChatCompletionRequest withLogprobs(@Nonnull final Boolean logprobs) {
    return this.withConfig(config.withLogprobs(logprobs));
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
  public OpenAiChatCompletionRequest withToolChoice(@Nonnull final OpenAiToolChoice choice) {
    return this.withConfig(config.withToolChoice(choice));
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

    request.stop(
        config.stop != null ? CreateChatCompletionRequestAllOfStop.create(config.stop) : null);

    request.temperature(config.temperature);
    request.topP(config.topP);

    request.stream(null);
    request.maxTokens(config.maxTokens);
    request.maxCompletionTokens(config.maxCompletionTokens);
    request.presencePenalty(config.presencePenalty);
    request.frequencyPenalty(config.frequencyPenalty);
    request.logitBias(config.logitBias);
    request.user(config.user);
    request.logprobs(config.logprobs);
    request.topLogprobs(config.topLogprobs);
    request.n(config.n);
    request.parallelToolCalls(config.parallelToolCalls);
    request.seed(config.seed);
    request.streamOptions(config.streamOptions);
    request.responseFormat(config.responseFormat);
    request.tools(getChatCompletionTools());
    request.toolChoice(config.toolChoice != null ? config.toolChoice.toolChoice : null);
    request.functionCall(null);
    request.functions(null);
    return request;
  }

  @Nullable
  private List<ChatCompletionTool> getChatCompletionTools() {
    final var toolsCombined = new ArrayList<ChatCompletionTool>();
    if (config.tools != null) {
      toolsCombined.addAll(config.tools);
    }
    if (config.toolsExecutable != null) {
      for (final OpenAiTool tool : config.toolsExecutable) {
        toolsCombined.add(tool.createChatCompletionTool());
      }
    }
    return toolsCombined.isEmpty() ? null : toolsCombined;
  }
}
