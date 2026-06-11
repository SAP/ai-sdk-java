package com.sap.ai.sdk.orchestration;

import java.util.List;
import javax.annotation.Nonnull;

/**
 * Storage backend for {@link ChatMemory}. Implement this interface to persist conversation history
 * in a custom store (e.g. a database, Redis, or a file system).
 *
 * <p>The default implementation is {@link SimpleChatMemoryStore}, which keeps messages in memory.
 *
 * @see ChatMemory
 * @see SimpleChatMemoryStore
 */
public interface ChatMemoryStore {

  /**
   * Retrieve all messages for the given conversation.
   *
   * @param conversationId the identifier of the conversation.
   * @return an unmodifiable list of messages, ordered from oldest to newest; never {@code null}.
   */
  @Nonnull
  List<Message> getMessages(@Nonnull String conversationId);

  /**
   * Persist the current (post-eviction) message list for the given conversation. This method is
   * called by {@link ChatMemory} after every add or eviction operation.
   *
   * @param conversationId the identifier of the conversation.
   * @param messages the complete, ordered list of messages to store.
   */
  void updateMessages(@Nonnull String conversationId, @Nonnull List<Message> messages);

  /**
   * Delete all messages for the given conversation.
   *
   * @param conversationId the identifier of the conversation.
   */
  void deleteMessages(@Nonnull String conversationId);
}
