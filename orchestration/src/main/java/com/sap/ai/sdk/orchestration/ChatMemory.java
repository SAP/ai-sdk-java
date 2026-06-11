package com.sap.ai.sdk.orchestration;

import java.util.List;
import javax.annotation.Nonnull;

/**
 * Manages conversation history for multi-turn interactions with the orchestration service.
 *
 * <p>{@code ChatMemory} keeps a sliding window of messages and automatically evicts old messages
 * when the window limit is exceeded. Two eviction policies are available:
 *
 * <ul>
 *   <li><b>Message window</b> — retains the N most recent messages. Create via {@link
 *       #messageWindow(int)} or {@link #messageWindow(int, ChatMemoryStore)}.
 *   <li><b>Token window</b> — retains messages whose estimated total token count stays within a
 *       limit. Create via {@link #tokenWindow(int, TokenCountEstimator)} or {@link
 *       #tokenWindow(int, TokenCountEstimator, ChatMemoryStore)}.
 * </ul>
 *
 * <p>{@link SystemMessage system messages} are never evicted — they are always retained at the
 * front of the conversation, regardless of the window size.
 *
 * <p>Conversations are identified by a {@code conversationId} string. A single {@code ChatMemory}
 * instance can manage multiple independent conversations simultaneously.
 *
 * <p><b>Usage example (message window):</b>
 *
 * <pre>{@code
 * var memory = ChatMemory.messageWindow(10);
 *
 * // Turn 1
 * memory.addMessage("conv1", Message.user("What is the capital of France?"));
 * var response1 = client.chatCompletion(memory.toPrompt("conv1"), config);
 * memory.addMessage("conv1", Message.assistant(response1.getContent()));
 *
 * // Turn 2
 * memory.addMessage("conv1", Message.user("What is the typical food there?"));
 * var response2 = client.chatCompletion(memory.toPrompt("conv1"), config);
 * memory.addMessage("conv1", Message.assistant(response2.getContent()));
 * }</pre>
 *
 * @see ChatMemoryStore
 * @see SimpleChatMemoryStore
 * @see TokenCountEstimator
 */
public abstract class ChatMemory {

  /**
   * Creates a message-window {@code ChatMemory} backed by an in-memory store.
   *
   * <p>Retains the {@code maxMessages} most recent messages (excluding system messages, which are
   * always kept).
   *
   * @param maxMessages the maximum number of non-system messages to retain; must be &gt; 0.
   * @return a new message-window {@code ChatMemory}.
   */
  @Nonnull
  public static ChatMemory messageWindow(final int maxMessages) {
    return new MessageWindowChatMemory(maxMessages, new SimpleChatMemoryStore());
  }

  /**
   * Creates a message-window {@code ChatMemory} backed by a custom store.
   *
   * <p>Retains the {@code maxMessages} most recent messages (excluding system messages, which are
   * always kept).
   *
   * @param maxMessages the maximum number of non-system messages to retain; must be &gt; 0.
   * @param store the backing store to use for persistence.
   * @return a new message-window {@code ChatMemory}.
   */
  @Nonnull
  public static ChatMemory messageWindow(
      final int maxMessages, @Nonnull final ChatMemoryStore store) {
    return new MessageWindowChatMemory(maxMessages, store);
  }

  /**
   * Creates a token-window {@code ChatMemory} backed by an in-memory store.
   *
   * <p>Drops the oldest non-system messages until the total estimated token count is within {@code
   * maxTokens}.
   *
   * @param maxTokens the maximum total estimated token count to retain; must be &gt; 0.
   * @param estimator the estimator used to count tokens per message.
   * @return a new token-window {@code ChatMemory}.
   * @see TokenCountEstimator#DEFAULT
   */
  @Nonnull
  public static ChatMemory tokenWindow(
      final int maxTokens, @Nonnull final TokenCountEstimator estimator) {
    return new TokenWindowChatMemory(maxTokens, estimator, new SimpleChatMemoryStore());
  }

  /**
   * Creates a token-window {@code ChatMemory} backed by a custom store.
   *
   * <p>Drops the oldest non-system messages until the total estimated token count is within {@code
   * maxTokens}.
   *
   * @param maxTokens the maximum total estimated token count to retain; must be &gt; 0.
   * @param estimator the estimator used to count tokens per message.
   * @param store the backing store to use for persistence.
   * @return a new token-window {@code ChatMemory}.
   * @see TokenCountEstimator#DEFAULT
   */
  @Nonnull
  public static ChatMemory tokenWindow(
      final int maxTokens,
      @Nonnull final TokenCountEstimator estimator,
      @Nonnull final ChatMemoryStore store) {
    return new TokenWindowChatMemory(maxTokens, estimator, store);
  }

  /**
   * Adds a message to the conversation, then applies the eviction policy if needed.
   *
   * <p>{@link SystemMessage system messages} are never evicted. All other message types are subject
   * to the configured eviction policy.
   *
   * @param conversationId the identifier of the conversation.
   * @param message the message to add.
   */
  public abstract void addMessage(@Nonnull String conversationId, @Nonnull Message message);

  /**
   * Returns all messages currently stored for the given conversation, ordered from oldest to
   * newest.
   *
   * @param conversationId the identifier of the conversation.
   * @return an unmodifiable list of messages; never {@code null}.
   */
  @Nonnull
  public abstract List<Message> getMessages(@Nonnull String conversationId);

  /**
   * Removes all messages for the given conversation.
   *
   * @param conversationId the identifier of the conversation.
   */
  public abstract void clear(@Nonnull String conversationId);

  /**
   * Builds an {@link OrchestrationPrompt} from the current memory state of the given conversation.
   *
   * <p>The last message in memory is used as the current prompt message; all preceding messages are
   * passed as history via {@link OrchestrationPrompt#messageHistory(List)}.
   *
   * <p>This is a convenience shorthand for:
   *
   * <pre>{@code
   * List<Message> all = memory.getMessages(conversationId);
   * new OrchestrationPrompt(all.get(all.size() - 1))
   *     .messageHistory(all.subList(0, all.size() - 1));
   * }</pre>
   *
   * @param conversationId the identifier of the conversation.
   * @return a prompt ready to pass to {@link OrchestrationClient#chatCompletion}.
   * @throws IllegalStateException if the conversation has no messages.
   */
  @Nonnull
  public OrchestrationPrompt toPrompt(@Nonnull final String conversationId) {
    final List<Message> all = getMessages(conversationId);
    if (all.isEmpty()) {
      throw new IllegalStateException(
          "No messages found for conversation '"
              + conversationId
              + "'. Add at least one message before calling toPrompt().");
    }
    final Message current = all.get(all.size() - 1);
    final List<Message> history = all.subList(0, all.size() - 1);
    return new OrchestrationPrompt(current).messageHistory(history);
  }
}
