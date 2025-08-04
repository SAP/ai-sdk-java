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
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
  @Getter(AccessLevel.NONE)
  OpenAiChatCompletionConfig config;

  OpenAiChatCompletionRequest withStop(@Nonnull final List<String> stop) {
    return this.withConfig(config.withStop(stop));
  }

  OpenAiChatCompletionRequest withTemperature(@Nonnull final BigDecimal temperature) {
    return this.withConfig(config.withTemperature(temperature));
  }

  OpenAiChatCompletionRequest withTopP(@Nonnull final BigDecimal topP) {
    return this.withConfig(config.withTopP(topP));
  }

  OpenAiChatCompletionRequest withMaxTokens(@Nonnull final Integer maxTokens) {
    return this.withConfig(config.withMaxTokens(maxTokens));
  }

  OpenAiChatCompletionRequest withMaxCompletionTokens(@Nonnull final Integer maxCompletionTokens) {
    return this.withConfig(config.withMaxCompletionTokens(maxCompletionTokens));
  }

  OpenAiChatCompletionRequest withPresencePenalty(@Nonnull final BigDecimal presencePenalty) {
    return this.withConfig(config.withPresencePenalty(presencePenalty));
  }

  OpenAiChatCompletionRequest withFrequencyPenalty(@Nonnull final BigDecimal frequencyPenalty) {
    return this.withConfig(config.withFrequencyPenalty(frequencyPenalty));
  }

  OpenAiChatCompletionRequest withTopLogprobs(@Nonnull final Integer topLogprobs) {
    return this.withConfig(config.withTopLogprobs(topLogprobs));
  }

  OpenAiChatCompletionRequest withUser(@Nonnull final String user) {
    return this.withConfig(config.withUser(user));
  }

  OpenAiChatCompletionRequest withLogitBias(@Nonnull final Map<String, Integer> logitBias) {
    return this.withConfig(config.withLogitBias(logitBias));
  }

  OpenAiChatCompletionRequest withN(@Nonnull final Integer n) {
    return this.withConfig(config.withN(n));
  }

  OpenAiChatCompletionRequest withSeed(@Nonnull final Integer seed) {
    return this.withConfig(config.withSeed(seed));
  }

  OpenAiChatCompletionRequest withStreamOptions(
      @Nonnull final ChatCompletionStreamOptions streamOptions) {
    return this.withConfig(config.withStreamOptions(streamOptions));
  }

  OpenAiChatCompletionRequest withResponseFormat(
      @Nonnull final CreateChatCompletionRequestAllOfResponseFormat responseFormat) {
    return this.withConfig(config.withResponseFormat(responseFormat));
  }

  OpenAiChatCompletionRequest withTools(@Nonnull final List<ChatCompletionTool> tools) {
    return this.withConfig(config.withTools(tools));
  }

  OpenAiChatCompletionRequest withToolsExecutable(@Nonnull final List<OpenAiTool> toolsExecutable) {
    return this.withConfig(config.withToolsExecutable(toolsExecutable));
  }

  /**
   * Creates an OpenAiChatCompletionPrompt with string as user message.
   *
   * @param message the message to be added to the prompt
   */
  @Tolerate
  public OpenAiChatCompletionRequest(@Nonnull final String message) {
    this(List.of(OpenAiMessage.user(message)), new OpenAiChatCompletionConfig());
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
    return this;
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
    return this;
  }

  /**
   * Sets the log probabilities option.
   *
   * @param logprobs Whether to include log probabilities in the response.
   * @return A new instance with the specified option.
   */
  @Nonnull
  public OpenAiChatCompletionRequest withLogprobs(@Nonnull final Boolean logprobs) {
    return this;
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
    return this;
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
    request.toolChoice(config.toolChoice);
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
