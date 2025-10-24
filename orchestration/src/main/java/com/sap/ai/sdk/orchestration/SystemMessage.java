package com.sap.ai.sdk.orchestration;

import static com.sap.ai.sdk.orchestration.model.SystemChatMessage.RoleEnum.SYSTEM;

import com.google.common.annotations.Beta;
import com.sap.ai.sdk.orchestration.model.ChatMessage;
import com.sap.ai.sdk.orchestration.model.ChatMessageContent;
import com.sap.ai.sdk.orchestration.model.SystemChatMessage;
import com.sap.ai.sdk.orchestration.model.TextContent;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.experimental.Accessors;
import lombok.experimental.Tolerate;
import lombok.val;

/** Represents a chat message as 'system' to the orchestration service. */
@Value
@Accessors(fluent = true)
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class SystemMessage implements Message {

  /** The role of the assistant. */
  @Nonnull String role = "system";

  /** The content of the message. */
  @Nonnull
  @Getter(onMethod_ = @Beta)
  MessageContent content;

  /**
   * Creates a new system message from a string.
   *
   * @param message the first message.
   */
  @Tolerate
  public SystemMessage(@Nonnull final String message) {
    content = new MessageContent(List.of(new TextItem(message)));
  }

  /**
   * Add text to the message.
   *
   * @param message the text to add.
   * @return the new message.
   * @since 1.3.0
   */
  @Nonnull
  public SystemMessage withText(@Nonnull final String message) {
    final var contentItems = new LinkedList<>(content.items());
    contentItems.add(new TextItem(message));
    return new SystemMessage(new MessageContent(contentItems));
  }

  @Nonnull
  @Override
  public ChatMessage createChatMessage() {
    if (content.items().size() == 1 && content.items().get(0) instanceof TextItem textItem) {
      return SystemChatMessage.create()
          .role(SYSTEM)
          .content(ChatMessageContent.create(textItem.text()));
    }
    val texts =
        content.items().stream()
            .filter(item -> item instanceof TextItem)
            .map(item -> (TextItem) item)
            .map(item -> TextContent.create().type(TextContent.TypeEnum.TEXT).text(item.text()))
            .toList();
    return SystemChatMessage.create()
        .role(SYSTEM)
        .content(ChatMessageContent.createListOfTextContents(texts));
  }
}
