package com.sap.ai.sdk.orchestration;

import com.google.common.annotations.Beta;
import com.sap.ai.sdk.orchestration.model.ChatMessage;
import com.sap.ai.sdk.orchestration.model.ChatMessagesInner;
import com.sap.ai.sdk.orchestration.model.ImageContent;
import com.sap.ai.sdk.orchestration.model.ImageContentImageUrl;
import com.sap.ai.sdk.orchestration.model.MultiChatMessage;
import com.sap.ai.sdk.orchestration.model.TextContent;
import javax.annotation.Nonnull;

/** Interface representing convenience wrappers of chat message to the orchestration service. */
public sealed interface Message permits UserMessage, AssistantMessage, SystemMessage {

  /**
   * A convenience method to create a user message.
   *
   * @param msg the message content.
   * @return the user message.
   */
  @Nonnull
  static UserMessage user(@Nonnull final String msg) {
    return new UserMessage(msg);
  }

  /**
   * A convenience method to create a user message.
   *
   * @param msg the message content.
   * @return the user message.
   */
  @Nonnull
  static UserMessage user(@Nonnull final MessageContent msg) {
    return new UserMessage(msg);
  }

  /**
   * A convenience method to create an assistant message.
   *
   * @param msg the message content.
   * @return the assistant message.
   */
  @Nonnull
  static AssistantMessage assistant(@Nonnull final String msg) {
    return new AssistantMessage(msg);
  }

  /**
   * A convenience method to create a system message.
   *
   * @param msg the message content.
   * @return the system message.
   */
  @Nonnull
  static SystemMessage system(@Nonnull final String msg) {
    return new SystemMessage(msg);
  }

  /**
   * A convenience method to create a system message.
   *
   * @param msg the message content.
   * @return the system message.
   */
  @Nonnull
  static SystemMessage system(@Nonnull final MessageContent msg) {
    return new SystemMessage(msg);
  }

  /**
   * Converts the message to a serializable ChatMessage object.
   *
   * @return the corresponding {@code ChatMessage} object.
   */
  @Nonnull
  default ChatMessagesInner createChatMessage() {
    if (this.content() instanceof MessageContentSingle) {
      return ChatMessage.create()
          .role(role())
          .content(((MessageContentSingle) content()).content());
    } else if (this.content() instanceof MessageContentMulti mCMulti) {
      return MultiChatMessage.create()
          .role(role())
          .content(
              mCMulti.multiContentList().stream()
                  .map(
                      multiMessageContent -> {
                        if (multiMessageContent instanceof MultiMessageTextContent mMTContent) {
                          return TextContent.create()
                              .type(TextContent.TypeEnum.TEXT)
                              .text(mMTContent.text());
                        } else if (multiMessageContent
                            instanceof MultiMessageImageContent mMIContent) {
                          return ImageContent.create()
                              .type(ImageContent.TypeEnum.IMAGE_URL)
                              .imageUrl(
                                  ImageContentImageUrl.create()
                                      .url(mMIContent.imageUrl())
                                      .detail(mMIContent.detailLevel().toString()));
                        } else {
                          throw new IllegalArgumentException(
                              "Unknown subtype of MultiMessageContent: "
                                  + multiMessageContent.getClass());
                        }
                      })
                  .toList());
    } else {
      throw new IllegalArgumentException("Unknown content type: " + this.content().getClass());
    }
  }

  /**
   * Returns the role of the assistant.
   *
   * @return the role.
   */
  @Nonnull
  String role();

  /**
   * Returns the content of the message.
   *
   * @return the content.
   */
  @Nonnull
  @Beta
  Object content();
}
