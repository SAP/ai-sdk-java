package com.sap.ai.sdk.foundationmodels.anthropic;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * A request to Claude via the Anthropic Messages API.
 *
 * @since 1.26.0
 */
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AnthropicChatCompletionRequest {

  /**
   * The Anthropic API version to use. Required by the Claude model.
   *
   * @since 1.26.0
   */
  @Nonnull
  @JsonProperty("anthropic_version")
  private String anthropicVersion = "bedrock-2023-05-31";

  @Nonnull
  @JsonProperty("messages")
  private List<AnthropicChatMessage> messages = new ArrayList<>();

  @Nullable
  @JsonProperty("system")
  private String system;

  @Nullable
  @JsonProperty("max_tokens")
  private Integer maxTokens;

  @Nullable
  @JsonProperty("temperature")
  private Double temperature;

  @Nullable
  @JsonProperty("top_p")
  private Double topP;

  @Nullable
  @JsonProperty("stop_sequences")
  private List<String> stopSequences;

  /**
   * Add a user message to this request.
   *
   * @param text the text content.
   * @return this request.
   */
  @Nonnull
  public AnthropicChatCompletionRequest withUserMessage(@Nonnull final String text) {
    messages.add(AnthropicChatMessage.ofUser(text));
    return this;
  }

  /**
   * Add an assistant message to this request (for multi-turn conversations).
   *
   * @param text the text content.
   * @return this request.
   */
  @Nonnull
  public AnthropicChatCompletionRequest withAssistantMessage(@Nonnull final String text) {
    messages.add(AnthropicChatMessage.ofAssistant(text));
    return this;
  }

  /**
   * Set the system prompt for this request.
   *
   * @param systemPrompt the system prompt text.
   * @return this request.
   */
  @Nonnull
  public AnthropicChatCompletionRequest withSystemPrompt(@Nonnull final String systemPrompt) {
    system = systemPrompt;
    return this;
  }

  /**
   * Set the maximum number of output tokens. Required by Claude models.
   *
   * @param maxTokens the maximum token count.
   * @return this request.
   */
  @Nonnull
  public AnthropicChatCompletionRequest withMaxTokens(final int maxTokens) {
    this.maxTokens = maxTokens;
    return this;
  }

  /**
   * Set the sampling temperature.
   *
   * @param temperature the temperature value (0.0 to 1.0).
   * @return this request.
   */
  @Nonnull
  public AnthropicChatCompletionRequest withTemperature(final double temperature) {
    this.temperature = temperature;
    return this;
  }
}
