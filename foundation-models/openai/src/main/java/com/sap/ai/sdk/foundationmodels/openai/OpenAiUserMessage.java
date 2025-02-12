package com.sap.ai.sdk.foundationmodels.openai;

import com.google.common.annotations.Beta;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionRequestMessageContentPartImage;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionRequestMessageContentPartImageImageUrl;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionRequestMessageContentPartText;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionRequestUserMessage;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionRequestUserMessageContent;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionRequestUserMessageContentPart;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.Nonnull;
import lombok.Getter;
import lombok.Value;
import lombok.experimental.Accessors;
import lombok.experimental.Tolerate;

/**
 * Represents a chat message as 'user' to OpenAI service. *
 *
 * @since 1.4.0
 */
@Beta
@Value
@Accessors(fluent = true)
public class OpenAiUserMessage implements OpenAiMessage {

  /** The role associated with this message. */
  @Nonnull String role = "user";

  /** The content of the message. */
  @Getter(onMethod_ = @Beta)
  @Nonnull
  OpenAiMessageContent content;

  /**
   * Creates a new user message from a string.
   *
   * @param message the first message.
   */
  @Tolerate
  public OpenAiUserMessage(@Nonnull final String message) {
    this(new OpenAiMessageContent(List.of(new OpenAiTextItem(message))));
  }

  /**
   * Add text to the message.
   *
   * @param message the text to add.
   * @return the new message.
   * @since 1.3.0
   */
  @Nonnull
  public OpenAiUserMessage withText(@Nonnull final String message) {
    final var contentItems = new LinkedList<>(content.items());
    contentItems.add(new OpenAiTextItem(message));
    return new OpenAiUserMessage(new OpenAiMessageContent(contentItems));
  }

  /**
   * Add an image to the message with the given image URL and detail level.
   *
   * @param imageUrl the URL of the image.
   * @param detailLevel the detail level of the image.
   * @return the new message.
   * @since 1.3.0
   */
  @Nonnull
  public OpenAiUserMessage withImage(
      @Nonnull final String imageUrl, @Nonnull final OpenAiImageItem.DetailLevel detailLevel) {
    final var contentItems = new LinkedList<>(content.items());
    contentItems.add(new OpenAiImageItem(imageUrl, detailLevel));
    return new OpenAiUserMessage(new OpenAiMessageContent(contentItems));
  }

  /**
   * Add an image to the message with the given image URL.
   *
   * @param imageUrl the URL of the image.
   * @return the new message.
   * @since 1.3.0
   */
  @Nonnull
  public OpenAiUserMessage withImage(@Nonnull final String imageUrl) {
    final var contentItems = new LinkedList<>(content.items());
    contentItems.add(new OpenAiImageItem(imageUrl));
    return new OpenAiUserMessage(new OpenAiMessageContent(contentItems));
  }

  /**
   * Converts the message to a serializable object.
   *
   * @return the corresponding serializable object.
   */
  @Nonnull
  ChatCompletionRequestUserMessage createChatCompletionRequestMessage()
      throws IllegalArgumentException {
    final var itemList = this.content().items();
    if (itemList.size() == 1 && itemList.get(0) instanceof OpenAiTextItem textItem) {
      return new ChatCompletionRequestUserMessage()
          .role(ChatCompletionRequestUserMessage.RoleEnum.fromValue(role()))
          .content(ChatCompletionRequestUserMessageContent.create(textItem.text()));
    }

    var messageParts = new LinkedList<ChatCompletionRequestUserMessageContentPart>();
    for (var item : itemList) {
      if (item instanceof OpenAiTextItem textItem) {
        messageParts.add(createTextContentPart(textItem));
      } else if (item instanceof OpenAiImageItem imageItem) {
        messageParts.add(createImageContentPart(imageItem));
      } else {
        throw new IllegalArgumentException("Unknown content type for " + role() + " messages.");
      }
    }

    return new ChatCompletionRequestUserMessage()
        .role(ChatCompletionRequestUserMessage.RoleEnum.fromValue(role()))
        .content(ChatCompletionRequestUserMessageContent.create(messageParts));
  }

  private ChatCompletionRequestMessageContentPartText createTextContentPart(
      OpenAiTextItem textItem) {
    return new ChatCompletionRequestMessageContentPartText()
        .type(ChatCompletionRequestMessageContentPartText.TypeEnum.TEXT)
        .text(textItem.text());
  }

  private ChatCompletionRequestMessageContentPartImage createImageContentPart(
      OpenAiImageItem imageItem) throws IllegalArgumentException {
    try {
      var imageUrl =
          new ChatCompletionRequestMessageContentPartImageImageUrl()
              .url(new URI(imageItem.imageUrl()))
              .detail(
                  ChatCompletionRequestMessageContentPartImageImageUrl.DetailEnum.fromValue(
                      imageItem.detailLevel().toString()));

      return new ChatCompletionRequestMessageContentPartImage()
          .type(ChatCompletionRequestMessageContentPartImage.TypeEnum.IMAGE_URL)
          .imageUrl(imageUrl);

    } catch (URISyntaxException e) {
      throw new IllegalArgumentException("Provided image URL has invalid syntax.", e);
    }
  }
}
