package com.sap.ai.sdk.orchestration;

import static com.sap.ai.sdk.orchestration.OrchestrationJacksonConfiguration.getOrchestrationObjectMapper;
import static org.assertj.core.api.Assertions.assertThat;

import com.sap.ai.sdk.orchestration.model.AssistantChatMessage;
import com.sap.ai.sdk.orchestration.model.CompletionPostResponse;
import com.sap.ai.sdk.orchestration.model.CompletionPostResponseStreaming;
import com.sap.ai.sdk.orchestration.model.ReasoningBlock;
import java.util.List;
import javax.annotation.Nonnull;
import lombok.val;
import org.junit.jupiter.api.Test;

/** Unit tests for the reasoning-content SDK surface */
class OrchestrationReasoningContentTest {

  private static final String SYNC_RESPONSE_TEMPLATE =
      """
      {
        "intermediate_results": {"templating": []},
        "final_result": {
          "choices": [
            {
              "message": {"role": "assistant", "content": "The answer is 42."%s}
            }
          ]
        }
      }
      """;

  private static final String STREAM_CHUNK_TEMPLATE =
      """
      {
        "final_result": {
          "choices": [
            {
              "index": %d,
              "delta": {"role": "assistant", "content": "hello"%s}
            }
          ]
        }
      }
      """;

  @Test
  void withReasoningEffortSetsParam() {
    val model = OrchestrationAiModel.CLAUDE_4_5_SONNET.withReasoningEffort(ReasoningEffort.MEDIUM);

    assertThat(model.getParams()).containsEntry("reasoning_effort", "medium");
    assertThat(model.createConfig().getParams()).containsEntry("reasoning_effort", "medium");
  }

  @Test
  void assistantMessageSerializesReasoningContentVerbatim() {
    // Reasoning content (including the opaque signatures) is attached internally by the SDK and
    // must be serialized verbatim so it can be re-submitted in messages_history.
    val blocks =
        List.of(
            ReasoningBlock.create().content("Step 1: think").signature("sig-1"),
            ReasoningBlock.create().content("Step 2: conclude").signature("sig-2"));

    val message = new AssistantMessage("The answer is 42.").withReasoningContent(blocks);

    val serialized = (AssistantChatMessage) message.createChatMessage();
    assertThat(serialized.getReasoningContent()).hasSize(2);
    assertThat(serialized.getReasoningContent().get(0).getContent()).isEqualTo("Step 1: think");
    assertThat(serialized.getReasoningContent().get(0).getSignature()).isEqualTo("sig-1");
    assertThat(serialized.getReasoningContent().get(1).getContent()).isEqualTo("Step 2: conclude");
    assertThat(serialized.getReasoningContent().get(1).getSignature()).isEqualTo("sig-2");
  }

  @Test
  void chatResponseGetReasoningContentReturnsEmptyWhenAbsent() throws Exception {
    val response = parseSyncResponse("");

    assertThat(response.getReasoningContent()).isEmpty();
    assertThat(response.getReasoningText()).isEmpty();
  }

  @Test
  void chatResponseGetReasoningContentReturnsText() throws Exception {
    val response =
        parseSyncResponse(
            ", \"reasoning_content\": [{\"content\": \"thinking...\", \"signature\": \"sig-a\"}]");

    assertThat(response.getReasoningContent()).containsExactly("thinking...");
    assertThat(response.getReasoningText()).isEqualTo("thinking...");
  }

  @Test
  void getReasoningTextJoinsBlocksWithDelimiter() throws Exception {
    val response =
        parseSyncResponse(
            ", \"reasoning_content\": ["
                + "{\"content\": \"first\", \"signature\": \"sig-a\"},"
                + "{\"content\": \"second\", \"signature\": \"sig-b\"}]");

    // No-arg overload defaults to an empty delimiter.
    assertThat(response.getReasoningText()).isEqualTo("firstsecond");
    // Explicit delimiter is placed between blocks.
    assertThat(response.getReasoningText("\n\n")).isEqualTo("first\n\nsecond");
  }

  @Test
  void getAllMessagesReSubmitsReasoningVerbatim() throws Exception {
    // The final assistant message reconstructed by getAllMessages() must serialize the reasoning
    // content (content + opaque signature) exactly as returned, ready for the next request.
    val response =
        parseSyncResponse(
            ", \"reasoning_content\": [{\"content\": \"thinking...\", \"signature\": \"sig-a\"}]");

    val last = response.getLastMessage().createChatMessage();
    val serialized = (AssistantChatMessage) last;
    assertThat(serialized.getReasoningContent()).hasSize(1);
    assertThat(serialized.getReasoningContent().get(0).getContent()).isEqualTo("thinking...");
    assertThat(serialized.getReasoningContent().get(0).getSignature()).isEqualTo("sig-a");
  }

  @Test
  void getAllMessagesReSubmitsHistoryAssistantReasoningVerbatim() throws Exception {
    val json =
        new String(
            java.util.Objects.requireNonNull(
                    getClass()
                        .getClassLoader()
                        .getResourceAsStream("reasoningHistoryResponse.json"))
                .readAllBytes());
    val parsed = getOrchestrationObjectMapper().readValue(json, CompletionPostResponse.class);
    val response = new OrchestrationChatResponse(parsed);

    val messages = response.getAllMessages();
    // messages[0] user, messages[1] history assistant with reasoning, messages[2] final assistant
    val serialized = (AssistantChatMessage) messages.get(1).createChatMessage();
    assertThat(serialized.getReasoningContent()).hasSize(1);
    assertThat(serialized.getReasoningContent().get(0).getContent()).isEqualTo("past-thought");
    assertThat(serialized.getReasoningContent().get(0).getSignature()).isEqualTo("past-sig");
  }

  @Test
  void streamingDeltaExposesReasoningText() throws Exception {
    val delta =
        parseStreamingDelta(
            0, ", \"reasoning_content\": [{\"content\": \"chunk\", \"signature\": \"\"}]");

    assertThat(delta.getDeltaContent()).isEqualTo("hello");
    assertThat(delta.getDeltaReasoningContent()).containsExactly("chunk");
  }

  @Test
  void streamingDeltaReturnsEmptyForNonZeroIndex() throws Exception {
    val delta =
        parseStreamingDelta(
            1, ", \"reasoning_content\": [{\"content\": \"x\", \"signature\": \"\"}]");

    assertThat(delta.getDeltaContent()).isEmpty();
    assertThat(delta.getDeltaReasoningContent()).isEmpty();
  }

  private static @Nonnull OrchestrationChatResponse parseSyncResponse(
      final String reasoningJsonFragment) throws Exception {
    val json = String.format(SYNC_RESPONSE_TEMPLATE, reasoningJsonFragment);
    val parsed = getOrchestrationObjectMapper().readValue(json, CompletionPostResponse.class);
    return new OrchestrationChatResponse(parsed);
  }

  private static @Nonnull OrchestrationChatCompletionDelta parseStreamingDelta(
      final int index, final String reasoningJsonFragment) throws Exception {
    val json = String.format(STREAM_CHUNK_TEMPLATE, index, reasoningJsonFragment);
    val parsed =
        getOrchestrationObjectMapper().readValue(json, CompletionPostResponseStreaming.class);
    val delta = new OrchestrationChatCompletionDelta();
    delta.setRequestId(parsed.getRequestId());
    delta.setFinalResult(parsed.getFinalResult());
    return delta;
  }
}
