package com.sap.ai.sdk.orchestration;

import com.google.common.annotations.Beta;
import com.sap.ai.sdk.orchestration.model.ChatMessage;
import com.sap.ai.sdk.orchestration.model.ChatMessagesInner;
import com.sap.ai.sdk.orchestration.model.ImageContent;
import com.sap.ai.sdk.orchestration.model.ImageContentImageUrl;
import com.sap.ai.sdk.orchestration.model.MultiChatMessage;
import com.sap.ai.sdk.orchestration.model.TextContent;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

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
   * Converts the message to a serializable ChatMessage object.
   *
   * @return the corresponding {@code ChatMessage} object.
   */
  @Nonnull
  default ChatMessagesInner createChatMessage() {
    if (this.getContent() instanceof MessageContentSingle) {
      return ChatMessage.create().role(role()).content(content());
    } else if (this.getContent() instanceof MessageContentMulti mCMulti) {
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
      throw new IllegalArgumentException("Unknown content type: " + this.getContent().getClass());
    }
  }

  //  TODO: ask in PR:
  //    Is there a better way to distribute the logic of the `add...` methods between the Message
  //    interface and its implementing classes?
  static Message addTextMessages(
      @Nullable MessageContent oldContent, @Nonnull String role, @Nonnull String... messages) {
    if (oldContent == null && messages.length == 1) {
      return switch (role) {
        case "user" -> new UserMessage(messages[0]);
        case "assistant" -> new AssistantMessage(messages[0]);
        case "system" -> new SystemMessage(messages[0]);
        default -> throw new IllegalArgumentException("Unknown role: " + role);
      };
    }
    var messagesAsMultiMessageContents =
        Stream.of(messages).map(MultiMessageTextContent::new).toList();
    return switch (role) {
      case "user" ->
          new UserMessage(createMultiContent(messagesAsMultiMessageContents, oldContent));
      case "assistant" ->
          new AssistantMessage(createMultiContent(messagesAsMultiMessageContents, oldContent));
      case "system" ->
          new SystemMessage(createMultiContent(messagesAsMultiMessageContents, oldContent));
      default -> throw new IllegalArgumentException("Unknown role: " + role);
    };
  }

  static Message addImage(
      @Nullable MessageContent oldContent,
      @Nonnull String role,
      @Nonnull String imageUrl,
      @Nonnull MultiMessageImageContent.DetailLevel detailLevel) {
    return switch (role) {
      case "user" ->
          new UserMessage(
              createMultiContent(
                  List.of(new MultiMessageImageContent(imageUrl, detailLevel)), oldContent));
      case "assistant" ->
          new AssistantMessage(
              createMultiContent(
                  List.of(new MultiMessageImageContent(imageUrl, detailLevel)), oldContent));
      case "system" ->
          new SystemMessage(
              createMultiContent(
                  List.of(new MultiMessageImageContent(imageUrl, detailLevel)), oldContent));
      default -> throw new IllegalArgumentException("Unknown role: " + role);
    };
  }

  private static MessageContentMulti createMultiContent(
      @Nonnull List<? extends MultiMessageContent> newContent,
      @Nullable MessageContent oldContent) {
    var multiContentList = new ArrayList<MultiMessageContent>();
    if (oldContent instanceof MessageContentSingle mCSingle) {
      multiContentList.add(new MultiMessageTextContent(mCSingle.content()));
    } else if (oldContent != null) {
      multiContentList.addAll(((MessageContentMulti) oldContent).multiContentList());
    }
    multiContentList.addAll(newContent);
    return new MessageContentMulti(multiContentList);
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
  String content();

  @Nonnull
  @Beta
  MessageContent getContent();

  @Nonnull
  @Beta
  Message addTextMessages(@Nonnull String... messages);

  @Nonnull
  @Beta
  Message addImage(@Nonnull String imageUrl, MultiMessageImageContent.DetailLevel detailLevel);
}
