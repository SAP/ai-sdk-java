package com.sap.ai.sdk.orchestration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;

/**
 * A {@link ChatMemory} implementation that retains messages whose total estimated token count stays
 * within a configured limit, dropping the oldest non-system messages first when the limit is
 * exceeded.
 *
 * <p>{@link SystemMessage system messages} are always preserved and their tokens always count
 * toward the limit.
 *
 * @see ChatMemory#tokenWindow(int, TokenCountEstimator)
 * @see ChatMemory#tokenWindow(int, TokenCountEstimator, ChatMemoryStore)
 * @see TokenCountEstimator
 */
@RequiredArgsConstructor
final class TokenWindowChatMemory extends ChatMemory {

  private final int maxTokens;
  private final TokenCountEstimator estimator;
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

  /** Applies the token-window eviction policy. System messages are always kept. */
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

    final int systemTokens = systemMessages.stream().mapToInt(estimator::estimate).sum();

    // Drop oldest non-system messages until total token count fits within the limit
    int nonSystemTokens = nonSystemMessages.stream().mapToInt(estimator::estimate).sum();
    while (!nonSystemMessages.isEmpty() && systemTokens + nonSystemTokens > maxTokens) {
      final Message removed = nonSystemMessages.remove(0);
      nonSystemTokens -= estimator.estimate(removed);
    }

    final List<Message> result = new ArrayList<>(systemMessages.size() + nonSystemMessages.size());
    result.addAll(systemMessages);
    result.addAll(nonSystemMessages);
    return Collections.unmodifiableList(result);
  }
}
