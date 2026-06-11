package com.sap.ai.sdk.orchestration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.Test;

class ChatMemoryTest {

  private static final String CONV = "conversation-1";
  private static final String CONV2 = "conversation-2";

  private static final Message SYS = Message.system("You are a helpful assistant.");
  private static final Message USER1 = Message.user("Hello");
  private static final Message ASSISTANT1 = Message.assistant("Hi there!");
  private static final Message USER2 = Message.user("How are you?");
  private static final Message ASSISTANT2 = Message.assistant("I'm doing well, thanks!");
  private static final Message USER3 = Message.user("Tell me a joke.");
  private static final Message ASSISTANT3 =
      Message.assistant("Why did the chicken cross the road?");

  // ─── SimpleChatMemoryStore ───────────────────────────────────────────────

  @Test
  void storeReturnsEmptyListForUnknownConversation() {
    var store = new SimpleChatMemoryStore();
    assertThat(store.getMessages("unknown")).isEmpty();
  }

  @Test
  void storeIsolatesConversations() {
    var store = new SimpleChatMemoryStore();
    store.updateMessages(CONV, List.of(USER1));
    store.updateMessages(CONV2, List.of(USER2));

    assertThat(store.getMessages(CONV)).containsExactly(USER1);
    assertThat(store.getMessages(CONV2)).containsExactly(USER2);
  }

  @Test
  void storeDeletesConversation() {
    var store = new SimpleChatMemoryStore();
    store.updateMessages(CONV, List.of(USER1, ASSISTANT1));
    store.deleteMessages(CONV);

    assertThat(store.getMessages(CONV)).isEmpty();
  }

  @Test
  void storeDefensivelyCopiesOnWrite() {
    var store = new SimpleChatMemoryStore();
    var list = new java.util.ArrayList<>(List.of(USER1));
    store.updateMessages(CONV, list);

    // Mutate the original list — stored copy should be unaffected
    list.add(USER2);
    assertThat(store.getMessages(CONV)).containsExactly(USER1);
  }

  // ─── MessageWindowChatMemory ──────────────────────────────────────────────

  @Test
  void messageWindowStoresMessagesInOrder() {
    var memory = ChatMemory.messageWindow(10);
    memory.addMessage(CONV, USER1);
    memory.addMessage(CONV, ASSISTANT1);

    assertThat(memory.getMessages(CONV)).containsExactly(USER1, ASSISTANT1);
  }

  @Test
  void messageWindowEvictsOldestNonSystemMessages() {
    var memory = ChatMemory.messageWindow(2);
    memory.addMessage(CONV, USER1);
    memory.addMessage(CONV, ASSISTANT1);
    memory.addMessage(CONV, USER2); // triggers eviction of USER1

    assertThat(memory.getMessages(CONV)).containsExactly(ASSISTANT1, USER2);
  }

  @Test
  void messageWindowPreservesSystemMessages() {
    var memory = ChatMemory.messageWindow(2);
    memory.addMessage(CONV, SYS);
    memory.addMessage(CONV, USER1);
    memory.addMessage(CONV, ASSISTANT1);
    memory.addMessage(CONV, USER2); // triggers eviction of USER1, not SYS

    assertThat(memory.getMessages(CONV)).containsExactly(SYS, ASSISTANT1, USER2);
  }

  @Test
  void messageWindowSystemMessagesDoNotCountTowardWindow() {
    var memory = ChatMemory.messageWindow(2);
    memory.addMessage(CONV, SYS);
    memory.addMessage(CONV, USER1);
    memory.addMessage(CONV, ASSISTANT1);

    // Window is 2, and we have 2 non-system messages — no eviction yet
    assertThat(memory.getMessages(CONV)).containsExactly(SYS, USER1, ASSISTANT1);
  }

  @Test
  void messageWindowClearsConversation() {
    var memory = ChatMemory.messageWindow(10);
    memory.addMessage(CONV, USER1);
    memory.addMessage(CONV, ASSISTANT1);
    memory.clear(CONV);

    assertThat(memory.getMessages(CONV)).isEmpty();
  }

  @Test
  void messageWindowIsolatesConversations() {
    var memory = ChatMemory.messageWindow(10);
    memory.addMessage(CONV, USER1);
    memory.addMessage(CONV2, USER2);

    assertThat(memory.getMessages(CONV)).containsExactly(USER1);
    assertThat(memory.getMessages(CONV2)).containsExactly(USER2);
  }

  @Test
  void messageWindowUsesCustomStore() {
    var store = new SimpleChatMemoryStore();
    var memory = ChatMemory.messageWindow(10, store);
    memory.addMessage(CONV, USER1);

    // Same messages should be visible via the store directly
    assertThat(store.getMessages(CONV)).containsExactly(USER1);
  }

  // ─── TokenWindowChatMemory ────────────────────────────────────────────────

  @Test
  void tokenWindowStoresMessagesInOrder() {
    var memory = ChatMemory.tokenWindow(1000, TokenCountEstimator.DEFAULT);
    memory.addMessage(CONV, USER1);
    memory.addMessage(CONV, ASSISTANT1);

    assertThat(memory.getMessages(CONV)).containsExactly(USER1, ASSISTANT1);
  }

  @Test
  void tokenWindowEvictsOldestMessagesOverLimit() {
    // Each message is short: "Hello" ≈ 5 chars → 1 token, "Hi there!" ≈ 9 chars → 2 tokens
    // Use a fixed estimator for determinism: 10 tokens per message
    TokenCountEstimator fixed = m -> 10;
    var memory = ChatMemory.tokenWindow(20, fixed); // fits exactly 2 messages

    memory.addMessage(CONV, USER1);
    memory.addMessage(CONV, ASSISTANT1);
    memory.addMessage(CONV, USER2); // 30 tokens total → evict USER1

    assertThat(memory.getMessages(CONV)).containsExactly(ASSISTANT1, USER2);
  }

  @Test
  void tokenWindowPreservesSystemMessages() {
    TokenCountEstimator fixed = m -> 10;
    var memory = ChatMemory.tokenWindow(20, fixed);

    memory.addMessage(CONV, SYS); // system: always kept (10 tokens)
    memory.addMessage(CONV, USER1); // 10 tokens → total 20, fits
    memory.addMessage(CONV, ASSISTANT1); // 10 tokens → total 30, evict USER1 not SYS

    assertThat(memory.getMessages(CONV)).containsExactly(SYS, ASSISTANT1);
  }

  @Test
  void tokenWindowSystemMessagesCountTowardBudget() {
    TokenCountEstimator fixed = m -> 10;
    // Budget = 15: system alone takes 10, so only 5 remain for non-system.
    // Each non-system message costs 10 → none can fit alongside system message.
    var memory = ChatMemory.tokenWindow(15, fixed);

    memory.addMessage(CONV, SYS);
    memory.addMessage(CONV, USER1); // 10+10=20 > 15 → USER1 evicted

    assertThat(memory.getMessages(CONV)).containsExactly(SYS);
  }

  @Test
  void tokenWindowDefaultEstimatorUsesCharCount() {
    // "Hello" = 5 chars → 5/4 = 1 token
    // "How are you doing today?" = 24 chars → 24/4 = 6 tokens
    // Budget of 4: fits "Hello" (1 token), but after adding the long message total = 7 > 4
    // → "Hello" is evicted (total becomes 6), 6 > 4 → long message also evicted → nothing remains
    var memory = ChatMemory.tokenWindow(4, TokenCountEstimator.DEFAULT);
    memory.addMessage(CONV, Message.user("Hello"));
    memory.addMessage(CONV, Message.user("How are you doing today?"));

    assertThat(memory.getMessages(CONV)).isEmpty();
  }

  @Test
  void tokenWindowKeepsMessagesThatFitWithinBudget() {
    // "Hello" = 5 chars → 1 token, "Hi there!" = 9 chars → 2 tokens, total = 3
    // budget=10 → no eviction needed
    var memory = ChatMemory.tokenWindow(10, TokenCountEstimator.DEFAULT);
    memory.addMessage(CONV, Message.user("Hello"));
    memory.addMessage(CONV, Message.assistant("Hi there!"));

    assertThat(memory.getMessages(CONV))
        .containsExactly(Message.user("Hello"), Message.assistant("Hi there!"));
  }

  @Test
  void tokenWindowClearsConversation() {
    var memory = ChatMemory.tokenWindow(1000, TokenCountEstimator.DEFAULT);
    memory.addMessage(CONV, USER1);
    memory.clear(CONV);

    assertThat(memory.getMessages(CONV)).isEmpty();
  }

  @Test
  void tokenWindowUsesCustomStore() {
    var store = new SimpleChatMemoryStore();
    var memory = ChatMemory.tokenWindow(1000, TokenCountEstimator.DEFAULT, store);
    memory.addMessage(CONV, USER1);

    assertThat(store.getMessages(CONV)).containsExactly(USER1);
  }

  // ─── ChatMemory.toPrompt() ────────────────────────────────────────────────

  @Test
  void toPromptThrowsOnEmptyConversation() {
    var memory = ChatMemory.messageWindow(10);

    assertThatThrownBy(() -> memory.toPrompt(CONV))
        .isInstanceOf(IllegalStateException.class)
        .hasMessageContaining(CONV);
  }

  @Test
  void toPromptWithSingleMessageHasNoHistory() {
    var memory = ChatMemory.messageWindow(10);
    memory.addMessage(CONV, USER1);

    var prompt = memory.toPrompt(CONV);

    assertThat(prompt.getMessages()).containsExactly(USER1);
    assertThat(prompt.getMessagesHistory()).isEmpty();
  }

  @Test
  void toPromptSplitsLastMessageFromHistory() {
    var memory = ChatMemory.messageWindow(10);
    memory.addMessage(CONV, USER1);
    memory.addMessage(CONV, ASSISTANT1);
    memory.addMessage(CONV, USER2);

    var prompt = memory.toPrompt(CONV);

    assertThat(prompt.getMessages()).containsExactly(USER2);
    assertThat(prompt.getMessagesHistory()).containsExactly(USER1, ASSISTANT1);
  }

  @Test
  void toPromptReflectsEvictedMessages() {
    var memory = ChatMemory.messageWindow(2);
    memory.addMessage(CONV, USER1); // [U1]
    memory.addMessage(CONV, ASSISTANT1); // [U1, A1]
    memory.addMessage(CONV, USER2); // [A1, U2]   — U1 evicted
    memory.addMessage(CONV, ASSISTANT2); // [U2, A2]  — A1 evicted
    memory.addMessage(CONV, USER3); // [A2, U3]   — U2 evicted

    var prompt = memory.toPrompt(CONV);

    assertThat(prompt.getMessages()).containsExactly(USER3);
    assertThat(prompt.getMessagesHistory()).containsExactly(ASSISTANT2);
  }

  // ─── Integration: use getMessages() with OrchestrationPrompt ─────────────

  @Test
  void getMessagesCanBeUsedAsMessageHistory() {
    var memory = ChatMemory.messageWindow(10);
    memory.addMessage(CONV, USER1);
    memory.addMessage(CONV, ASSISTANT1);
    memory.addMessage(CONV, USER2);

    var all = memory.getMessages(CONV);
    // Build prompt manually: last message is current, rest is history
    var prompt =
        new OrchestrationPrompt(all.get(all.size() - 1))
            .messageHistory(all.subList(0, all.size() - 1));

    assertThat(prompt.getMessages()).containsExactly(USER2);
    assertThat(prompt.getMessagesHistory()).containsExactly(USER1, ASSISTANT1);
  }
}
