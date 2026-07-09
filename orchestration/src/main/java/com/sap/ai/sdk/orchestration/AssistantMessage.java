package com.sap.ai.sdk.orchestration;

import static com.sap.ai.sdk.orchestration.model.AssistantChatMessage.RoleEnum.ASSISTANT;

import com.sap.ai.sdk.orchestration.model.AssistantChatMessage;
import com.sap.ai.sdk.orchestration.model.ChatMessage;
import com.sap.ai.sdk.orchestration.model.ChatMessageContent;
import com.sap.ai.sdk.orchestration.model.MessageToolCall;
import com.sap.ai.sdk.orchestration.model.ReasoningBlock;
import com.sap.ai.sdk.orchestration.model.TextContent;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.Getter;
import lombok.Value;
import lombok.experimental.Accessors;
import lombok.val;

/** Represents a chat message as 'assistant' to the orchestration service. */
@Value
@Getter
@Accessors(fluent = true)
public class AssistantMessage implements Message {

  /** The role of the assistant. */
  @Nonnull String role = "assistant";

  /** The content of the message. */
  @Nonnull @Getter MessageContent content;

  /** Tool call if there is any. */
  @Nullable List<MessageToolCall> toolCalls;

  /** Reasoning (thinking) content produced by the model, if any. */
  @Nullable List<ReasoningBlock> reasoningContent;

  /**
   * Creates a new assistant message with the given single message.
   *
   * @param singleMessage the single message.
   */
  public AssistantMessage(@Nonnull final String singleMessage) {
    content = new MessageContent(List.of(new TextItem(singleMessage)));
    toolCalls = null;
    reasoningContent = null;
  }

  /**
   * Creates a new assistant message with the given single message.
   *
   * @param content the single message.
   */
  AssistantMessage(@Nonnull final MessageContent content) {
    this.content = content;
    toolCalls = null;
    reasoningContent = null;
  }

  /**
   * Creates a new assistant message with the given tool calls.
   *
   * @param toolCalls list of tool call objects
   * @deprecated Please use {@link #withToolCalls(List)} instead.
   */
  @Deprecated
  public AssistantMessage(@Nonnull final List<MessageToolCall> toolCalls) {
    content = new MessageContent(List.of());
    this.toolCalls = toolCalls;
    reasoningContent = null;
  }

  private AssistantMessage(
      @Nonnull final MessageContent content,
      @Nullable final List<MessageToolCall> toolCalls,
      @Nullable final List<ReasoningBlock> reasoningContent) {
    this.content = content;
    this.toolCalls = toolCalls;
    this.reasoningContent = reasoningContent;
  }

  /**
   * Returns a new AssistantMessage instance with the provided tool calls added to the existing
   * ones.
   *
   * @param toolCalls the list of tool calls to add.
   * @return a new AssistantMessage instance with the combined tool calls.
   */
  @SuppressWarnings("PMD.PublicApiExposesModelType")
  @Nonnull
  public AssistantMessage withToolCalls(@Nonnull final List<MessageToolCall> toolCalls) {
    val newToolcalls = new ArrayList<>(this.toolCalls != null ? this.toolCalls : List.of());
    newToolcalls.addAll(toolCalls);
    return new AssistantMessage(this.content, newToolcalls, this.reasoningContent);
  }

  /**
   * Returns a new AssistantMessage instance carrying the given reasoning content.
   *
   * @param reasoningContent the reasoning blocks from the previous assistant turn.
   * @return a new AssistantMessage instance with the reasoning content set.
   * @see <a href="https://help.sap.com/docs/sap-ai-core/generative-ai/reasoning">SAP AI Core:
   *     Orchestration - Reasoning</a>
   */
  @SuppressWarnings("PMD.PublicApiExposesModelType")
  @Nonnull
  public AssistantMessage withReasoningContent(
      @Nonnull final List<ReasoningBlock> reasoningContent) {
    return new AssistantMessage(this.content, this.toolCalls, List.copyOf(reasoningContent));
  }

  @Nonnull
  @Override
  public ChatMessage createChatMessage() {
    val assistantChatMessage = AssistantChatMessage.create().role(ASSISTANT);

    if (toolCalls != null) {
      assistantChatMessage.setToolCalls(toolCalls);
    }

    if (reasoningContent != null) {
      assistantChatMessage.setReasoningContent(reasoningContent);
    }

    ChatMessageContent text;
    if (content.items().size() == 1 && content.items().get(0) instanceof TextItem textItem) {
      text = ChatMessageContent.create(textItem.text());
    } else {
      val texts =
          content.items().stream()
              .filter(item -> item instanceof TextItem)
              .map(item -> (TextItem) item)
              .map(item -> TextContent.create().type(TextContent.TypeEnum.TEXT).text(item.text()))
              .toList();
      text = ChatMessageContent.createListOfTextContents(texts);
    }
    return assistantChatMessage.content(text);
  }
}
