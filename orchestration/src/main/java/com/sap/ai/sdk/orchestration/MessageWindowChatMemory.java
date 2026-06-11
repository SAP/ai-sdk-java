package com.sap.ai.sdk.orchestration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;

/**
 * A {@link ChatMemory} implementation that retains the N most recent non-system messages.
 *
 * <p>{@link SystemMessage system messages} are always preserved and do not count toward the window.
 *
 * @see ChatMemory#messageWindow(int)
 * @see ChatMemory#messageWindow(int, ChatMemoryStore)
 */
@RequiredArgsConstructor
final class MessageWindowChatMemory extends ChatMemory {

  private final int maxMessages;
  private final ChatMemoryStore store;

  @Override
  public void addMessage(@Nonnull final String conversationId, @Nonnull final Message message) {
    final List<Message> messages = new ArrayList<>(store.getMessages(conversationId));
    messages.add(message);
    store.updateMessages(conversationId, evict(messages));
  }

  @Nonnull
  @Override
  public List<Message> getMessages(@Nonnull final String conversationId) {
    return store.getMessages(conversationId);
  }

  @Override
  public void clear(@Nonnull final String conversationId) {
    store.deleteMessages(conversationId);
  }

  /** Applies the sliding-window eviction policy. System messages are always kept. */
  @Nonnull
  private List<Message> evict(@Nonnull final List<Message> messages) {
    final List<Message> systemMessages =
        messages.stream()
            .filter(m -> m instanceof SystemMessage)
            .collect(Collectors.toCollection(ArrayList::new));

    final List<Message> nonSystemMessages =
        messages.stream()
            .filter(m -> !(m instanceof SystemMessage))
            .collect(Collectors.toCollection(ArrayList::new));

    final int excess = nonSystemMessages.size() - maxMessages;
    if (excess > 0) {
      nonSystemMessages.subList(0, excess).clear();
    }

    final List<Message> result = new ArrayList<>(systemMessages.size() + nonSystemMessages.size());
    result.addAll(systemMessages);
    result.addAll(nonSystemMessages);
    return Collections.unmodifiableList(result);
  }
}
