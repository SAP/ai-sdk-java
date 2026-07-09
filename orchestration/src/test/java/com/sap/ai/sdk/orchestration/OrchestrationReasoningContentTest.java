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
  void assistantMessageCarriesReasoningContentInSerializedOutput() {
    val blocks =
        List.of(
            ReasoningBlock.create().content("Step 1: think").signature("sig-1"),
            ReasoningBlock.create().content("Step 2: conclude").signature("sig-2"));

    val message = new AssistantMessage("The answer is 42.").withReasoningContent(blocks);

    assertThat(message.reasoningContent()).containsExactlyElementsOf(blocks);

    val serialized = (AssistantChatMessage) message.createChatMessage();
    assertThat(serialized.getReasoningContent()).containsExactlyElementsOf(blocks);
  }

  @Test
  void chatResponseGetReasoningContentReturnsEmptyWhenAbsent() throws Exception {
    val response = parseSyncResponse("");

    assertThat(response.getReasoningContent()).isEmpty();
    assertThat(response.getReasoningText()).isEmpty();
  }

  @Test
  void chatResponseGetReasoningContentReturnsBlocks() throws Exception {
    val response =
        parseSyncResponse(
            ", \"reasoning_content\": [{\"content\": \"thinking...\", \"signature\": \"sig-a\"}]");

    assertThat(response.getReasoningContent()).hasSize(1);
    assertThat(response.getReasoningContent().get(0).getContent()).isEqualTo("thinking...");
    assertThat(response.getReasoningContent().get(0).getSignature()).isEqualTo("sig-a");
    assertThat(response.getReasoningText()).isEqualTo("thinking...");
  }

  @Test
  void getAllMessagesAttachesReasoningToFinalAssistant() throws Exception {
    val response =
        parseSyncResponse(
            ", \"reasoning_content\": [{\"content\": \"thinking...\", \"signature\": \"sig-a\"}]");

    val last = (AssistantMessage) response.getLastMessage();
    assertThat(last.reasoningContent()).hasSize(1);
    assertThat(last.reasoningContent().get(0).getContent()).isEqualTo("thinking...");
  }

  @Test
  void getAllMessagesAttachesReasoningToHistoryAssistant() throws Exception {
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
    val historyAssistant = (AssistantMessage) messages.get(1);
    assertThat(historyAssistant.reasoningContent()).hasSize(1);
    assertThat(historyAssistant.reasoningContent().get(0).getContent()).isEqualTo("past-thought");
  }

  @Test
  void reasoningAccumulatorKeepsBlocksAtDifferentIndexesSeparate() throws Exception {
    val chunk =
        parseStreamingDelta(
            0,
            ", \"reasoning_content\": ["
                + "{\"content\": \"first\", \"signature\": \"s1\"},"
                + "{\"content\": \"second\", \"signature\": \"s2\"}]");

    val accumulator = new ReasoningAccumulator();
    accumulator.accept(chunk);

    val blocks = accumulator.assemble();
    assertThat(blocks).hasSize(2);
    assertThat(blocks.get(0).getContent()).isEqualTo("first");
    assertThat(blocks.get(0).getSignature()).isEqualTo("s1");
    assertThat(blocks.get(1).getContent()).isEqualTo("second");
    assertThat(blocks.get(1).getSignature()).isEqualTo("s2");
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
