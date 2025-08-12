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
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
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
    this(List.copyOf(messages), new OpenAiChatCompletionConfig());
  }

  /**
   * Creates a new OpenAiChatCompletionRequest with the specified messages and configuration.
   *
   * @param stop the stop sequences to be used in the request
   * @return a new OpenAiChatCompletionRequest instance with the specified stop sequences
   */
  @Nonnull
  public OpenAiChatCompletionRequest withStop(@Nonnull final List<String> stop) {
    return this.withConfig(config.withStop(stop));
  }

  /**
   * Sets the temperature for the request.
   *
   * @param temperature the temperature value to be used in the request.
   * @return a new OpenAiChatCompletionRequest instance with the specified temperature
   */
  @Nonnull
  public OpenAiChatCompletionRequest withTemperature(@Nonnull final BigDecimal temperature) {
    return this.withConfig(config.withTemperature(temperature));
  }

  /**
   * Sets the top-p sampling parameter for the request.
   *
   * @param topP the top-p value to be used in the request
   * @return a new OpenAiChatCompletionRequest instance with the specified top-p value
   */
  @Nonnull
  public OpenAiChatCompletionRequest withTopP(@Nonnull final BigDecimal topP) {
    return this.withConfig(config.withTopP(topP));
  }

  /**
   * Sets the maximum number of tokens for the request.
   *
   * @param maxTokens the maximum number of tokens to be used in the request
   * @return a new OpenAiChatCompletionRequest instance with the specified maximum tokens
   */
  @Nonnull
  public OpenAiChatCompletionRequest withMaxTokens(@Nonnull final Integer maxTokens) {
    return this.withConfig(config.withMaxTokens(maxTokens));
  }

  /**
   * Sets the maximum number of completion tokens for the request.
   *
   * @param maxCompletionTokens the maximum number of completion tokens to be used in the request
   * @return a new OpenAiChatCompletionRequest instance with the specified maximum completion tokens
   */
  @Nonnull
  public OpenAiChatCompletionRequest withMaxCompletionTokens(
      @Nonnull final Integer maxCompletionTokens) {
    return this.withConfig(config.withMaxCompletionTokens(maxCompletionTokens));
  }

  /**
   * Sets the presence penalty for the request.
   *
   * @param presencePenalty the presence penalty value to be used in the request
   * @return a new OpenAiChatCompletionRequest instance with the specified presence penalty
   */
  @Nonnull
  public OpenAiChatCompletionRequest withPresencePenalty(
      @Nonnull final BigDecimal presencePenalty) {
    return this.withConfig(config.withPresencePenalty(presencePenalty));
  }

  /**
   * Sets the frequency penalty for the request.
   *
   * @param frequencyPenalty the frequency penalty value to be used in the request
   * @return a new OpenAiChatCompletionRequest instance with the specified frequency penalty
   */
  @Nonnull
  public OpenAiChatCompletionRequest withFrequencyPenalty(
      @Nonnull final BigDecimal frequencyPenalty) {
    return this.withConfig(config.withFrequencyPenalty(frequencyPenalty));
  }

  /**
   * Sets the top log probabilities for the request.
   *
   * @param topLogprobs the number of top log probabilities to be included in the response
   * @return a new OpenAiChatCompletionRequest instance with the specified top log probabilities
   */
  @Nonnull
  public OpenAiChatCompletionRequest withTopLogprobs(@Nonnull final Integer topLogprobs) {
    return this.withConfig(config.withTopLogprobs(topLogprobs));
  }

  /**
   * Sets the user identifier for the request.
   *
   * @param user the user identifier to be used in the request
   * @return a new OpenAiChatCompletionRequest instance with the specified user identifier
   */
  @Nonnull
  public OpenAiChatCompletionRequest withUser(@Nonnull final String user) {
    return this.withConfig(config.withUser(user));
  }

  /**
   * Sets the logit bias for the request.
   *
   * @param logitBias the logit bias map to be used in the request
   * @return a new OpenAiChatCompletionRequest instance with the specified logit bias
   */
  @Nonnull
  public OpenAiChatCompletionRequest withLogitBias(@Nonnull final Map<String, Integer> logitBias) {
    return this.withConfig(config.withLogitBias(logitBias));
  }

  /**
   * Sets the number of completions to generate for the request.
   *
   * @param n the number of completions to generate
   * @return a new OpenAiChatCompletionRequest instance with the specified number of completions
   */
  @Nonnull
  public OpenAiChatCompletionRequest withN(@Nonnull final Integer n) {
    return this.withConfig(config.withN(n));
  }

  /**
   * Sets the random seed for the request.
   *
   * @param seed the random seed to be used in the request
   * @return a new OpenAiChatCompletionRequest instance with the specified random seed
   */
  @Nonnull
  public OpenAiChatCompletionRequest withSeed(@Nonnull final Integer seed) {
    return this.withConfig(config.withSeed(seed));
  }

  /**
   * Sets the stream options for the request.
   *
   * @param streamOptions the stream options to be used in the request
   * @return a new OpenAiChatCompletionRequest instance with the specified stream options
   */
  @Nonnull
  public OpenAiChatCompletionRequest withStreamOptions(
      @Nonnull final ChatCompletionStreamOptions streamOptions) {
    return this.withConfig(config.withStreamOptions(streamOptions));
  }

  /**
   * Sets the response format for the request.
   *
   * @param responseFormat the response format to be used in the request
   * @return a new OpenAiChatCompletionRequest instance with the specified response format
   */
  @Nonnull
  public OpenAiChatCompletionRequest withResponseFormat(
      @Nonnull final CreateChatCompletionRequestAllOfResponseFormat responseFormat) {
    return this.withConfig(config.withResponseFormat(responseFormat));
  }

  /**
   * Sets the tools for the request.
   *
   * @param tools the list of tools to be used in the request
   * @return a new OpenAiChatCompletionRequest instance with the specified tools
   */
  @Nonnull
  public OpenAiChatCompletionRequest withTools(@Nonnull final List<ChatCompletionTool> tools) {
    return this.withConfig(config.withTools(tools));
  }

  /**
   * Sets the executable tools for the request.
   *
   * @param toolsExecutable the list of executable tools to be used in the request
   * @return a new OpenAiChatCompletionRequest instance with the specified executable tools
   */
  @Nonnull
  public OpenAiChatCompletionRequest withToolsExecutable(
      @Nonnull final List<OpenAiTool> toolsExecutable) {
    return this.withConfig(config.withToolsExecutable(toolsExecutable));
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
    if (config.getToolsExecutable() != null) {
      for (final OpenAiTool tool : config.getToolsExecutable()) {
        toolsCombined.add(tool.createChatCompletionTool());
      }
    }
    return toolsCombined.isEmpty() ? null : toolsCombined;
  }
}
