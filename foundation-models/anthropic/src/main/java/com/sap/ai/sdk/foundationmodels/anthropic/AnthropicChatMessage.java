package com.sap.ai.sdk.foundationmodels.anthropic;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * A message in the Anthropic Messages API format.
 *
 * @since 1.26.0
 */
@Data
@Accessors(chain = true)
public class AnthropicChatMessage {

  /**
   * The role of the message sender.
   *
   * @since 1.26.0
   */
  public enum Role {
    /** The user role. */
    @JsonProperty("user")
    USER,
    /** The assistant role. */
    @JsonProperty("assistant")
    ASSISTANT
  }

  @Nullable
  @JsonProperty("role")
  private Role role;

  @Nullable
  @JsonProperty("content")
  private List<AnthropicContentBlock> content;

  /**
   * Create a user message with the given text.
   *
   * @param text the text content.
   * @return a user message.
   */
  @Nonnull
  public static AnthropicChatMessage ofUser(@Nonnull final String text) {
    return new AnthropicChatMessage()
        .setRole(Role.USER)
        .setContent(List.of(AnthropicContentBlock.ofText(text)));
  }

  /**
   * Create an assistant message with the given text.
   *
   * @param text the text content.
   * @return an assistant message.
   */
  @Nonnull
  public static AnthropicChatMessage ofAssistant(@Nonnull final String text) {
    return new AnthropicChatMessage()
        .setRole(Role.ASSISTANT)
        .setContent(List.of(AnthropicContentBlock.ofText(text)));
  }
}
