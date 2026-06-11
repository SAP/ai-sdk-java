package com.sap.ai.sdk.orchestration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.Nonnull;

/**
 * A {@link ChatMemoryStore} that keeps messages in memory, backed by a {@link ConcurrentHashMap}.
 *
 * <p>This is the default store used by {@link ChatMemory} when no custom store is provided.
 * Multiple conversations are stored independently using their conversation identifiers as keys.
 * Stored message lists are defensively copied on every read and write to prevent external
 * modification.
 *
 * <p>Note: messages are lost when the JVM process exits. For durable storage implement a custom
 * {@link ChatMemoryStore}.
 *
 * @see ChatMemory
 * @see ChatMemoryStore
 */
public class SimpleChatMemoryStore implements ChatMemoryStore {

  private final ConcurrentHashMap<String, List<Message>> store = new ConcurrentHashMap<>();

  @Nonnull
  @Override
  public List<Message> getMessages(@Nonnull final String conversationId) {
    final List<Message> messages = store.get(conversationId);
    return messages == null ? Collections.emptyList() : Collections.unmodifiableList(messages);
  }

  @Override
  public void updateMessages(
      @Nonnull final String conversationId, @Nonnull final List<Message> messages) {
    store.put(conversationId, new ArrayList<>(messages));
  }

  @Override
  public void deleteMessages(@Nonnull final String conversationId) {
    store.remove(conversationId);
  }
}
