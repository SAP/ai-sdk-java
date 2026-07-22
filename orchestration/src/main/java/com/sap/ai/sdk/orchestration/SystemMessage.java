package com.sap.ai.sdk.orchestration;

import static com.sap.ai.sdk.orchestration.model.SystemChatMessage.RoleEnum.SYSTEM;
import static com.sap.ai.sdk.orchestration.model.UserChatMessageContentItem.TypeEnum.TEXT;

import com.sap.ai.sdk.orchestration.model.CacheControl;
import com.sap.ai.sdk.orchestration.model.ChatMessage;
import com.sap.ai.sdk.orchestration.model.ChatMessageContent;
import com.sap.ai.sdk.orchestration.model.SystemChatMessage;
import com.sap.ai.sdk.orchestration.model.TextContent;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.sap.ai.sdk.orchestration.model.UserChatMessageContentItem;
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
  @Nonnull @Getter MessageContent content;

  /**
   * Creates a new system message from a string.
   *
   * @param message the first message.
   */
  @Tolerate
  public SystemMessage(@Nonnull final String message) {
    this(message, null);
  }

  /**
   * Creates a new system message from a string, allows for cache checkpoint configuration
   *
   * @since 1.23.0
   * @param message the first message.
   */
  public SystemMessage(@Nonnull final String message,
                       @Nullable final com.sap.ai.sdk.orchestration.CacheControl cacheControl) {
    content = new MessageContent(List.of(new TextItem(message, cacheControl)));
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
    return withText(message, null);
  }

  /**
   * Add text to the message
   *
   * @since 1.23.0
   * @param message the text to add
   * @param cacheControl optional cache checkpoint configuration
   * @return the new message
   */
  @Nonnull
  public SystemMessage withText(@Nonnull final String message,
                                @Nullable final com.sap.ai.sdk.orchestration.CacheControl cacheControl) {
    final var contentItems = new LinkedList<>(content.items());
    contentItems.add(new TextItem(message, cacheControl));
    return new SystemMessage(new MessageContent(contentItems));
  }

  @Nonnull
  @Override
  public ChatMessage createChatMessage() {
    final Function<TextItem, TextContent> toTextContent = (item) -> {
      var convertedItem = TextContent.create().type(TextContent.TypeEnum.TEXT).text(item.text());
      var cacheControl = item.getCacheControl();
      if (cacheControl != null) {
        var cacheControlConverted = com.sap.ai.sdk.orchestration.model.CacheControl.create()
                .type(com.sap.ai.sdk.orchestration.model.CacheControl.TypeEnum.EPHEMERAL)
                .ttl(CacheControl.TtlEnum.fromValue(cacheControl.getTtl()));
        convertedItem.setCacheControl(cacheControlConverted);
      }
      return convertedItem;
    };
    if (content.items().size() == 1 && content.items().get(0) instanceof TextItem textItem) {
      if (textItem.getCacheControl() != null) {
        return SystemChatMessage.create()
                .role(SYSTEM)
                .content(ChatMessageContent.createListOfTextContents(List.of(toTextContent.apply(textItem))));
      }
      return SystemChatMessage.create()
          .role(SYSTEM)
          .content(ChatMessageContent.create(textItem.text()));
    }
    val texts =
        content.items().stream()
            .filter(item -> item instanceof TextItem)
            .map(item -> (TextItem) item)
            .map(toTextContent)
            .toList();
    return SystemChatMessage.create()
        .role(SYSTEM)
        .content(ChatMessageContent.createListOfTextContents(texts));
  }
}
