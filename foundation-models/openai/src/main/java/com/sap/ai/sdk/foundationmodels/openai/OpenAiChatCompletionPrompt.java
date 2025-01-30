package com.sap.ai.sdk.foundationmodels.openai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.Data;
import lombok.experimental.Accessors;

/** Represents a prompt that contains one or more messages for OpenAI chat completion. */
@Accessors(fluent = true)
@Data
public class OpenAiChatCompletionPrompt {
  @Nonnull private final List<OpenAiMessage> messages = new ArrayList<>();

  @Nullable private List<String> stop;

  private OpenAiChatCompletionPrompt(@Nonnull final String message) {
    this.messages.add(OpenAiMessage.user(message));
  }

  private OpenAiChatCompletionPrompt(@Nonnull final List<OpenAiMessage> messages) {
    this.messages.addAll(messages);
  }

  /**
   * Creates an OpenAiChatCompletionPrompt with a single message.
   *
   * @param message the message to be added to the prompt
   * @return a new OpenAiChatCompletionPrompt instance
   */
  @Nonnull
  public static OpenAiChatCompletionPrompt create(@Nonnull final String message) {
    return new OpenAiChatCompletionPrompt(message);
  }

  /**
   * Creates an OpenAiChatCompletionPrompt with a multiple unpacked messages.
   *
   * @param message the primary message to be added to the prompt
   * @param messages additional messages to be added to the prompt
   * @return a new OpenAiChatCompletionPrompt instance
   */
  @Nonnull
  public static OpenAiChatCompletionPrompt create(
      @Nonnull final OpenAiMessage message, @Nonnull final OpenAiMessage... messages) {
    final var messagesList = new ArrayList<OpenAiMessage>();

    messagesList.add(message);
    messagesList.addAll(Arrays.asList(messages));

    return create(messagesList);
  }

  /**
   * Creates an OpenAiChatCompletionPrompt with a list of messages.
   *
   * @param messages the list of messages to be added to the prompt
   * @return a new OpenAiChatCompletionPrompt instance
   */
  @Nonnull
  public static OpenAiChatCompletionPrompt create(@Nonnull final List<OpenAiMessage> messages) {
    // TODO: Enforce null removal?
    return new OpenAiChatCompletionPrompt(messages);
  }

  /**
   * Sets the stop sequences for the prompt.
   *
   * @param values the stop sequences to be set
   * @return the current OpenAiChatCompletionPrompt instance
   */
  @Nonnull
  public OpenAiChatCompletionPrompt stop(@Nonnull final String... values) {
    this.stop = Stream.of(values).filter(Objects::nonNull).toList();
    return this;
  }
}
