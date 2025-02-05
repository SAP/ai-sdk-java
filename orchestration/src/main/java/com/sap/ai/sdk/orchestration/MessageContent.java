package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.model.ImageContent;
import com.sap.ai.sdk.orchestration.model.MultiChatMessageContent;
import com.sap.ai.sdk.orchestration.model.TextContent;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Represents the content of a chat message.
 *
 * @param contentItemList a list of the content items
 */
public record MessageContent(@Nonnull List<ContentItem> contentItemList) {

  MessageContent(@Nonnull final String singleMessage) {
    this(List.of(new TextItem(singleMessage)));
  }

  /**
   * Creates a new message content containing one {@link ImageItem} with the given image URL and
   * detail level.
   *
   * @param imageUrl the URL of the image
   * @param detailLevel the detail level of the image
   * @return the new message content
   */
  @Nonnull
  public static MessageContent image(
      @Nonnull final String imageUrl, @Nullable final ImageItem.DetailLevel detailLevel) {
    return new MessageContent(List.of(new ImageItem(imageUrl, detailLevel)));
  }

  /**
   * Creates a new message content containing one {@link ImageItem} with the given image URL.
   *
   * @param imageUrl the URL of the image
   * @return the new message content
   */
  @Nonnull
  public static MessageContent image(@Nonnull final String imageUrl) {
    return new MessageContent(List.of(new ImageItem(imageUrl)));
  }

  /**
   * Creates a new message content containing one or more {@link TextItem}s with the given texts.
   *
   * @param firstInput the first text of the message
   * @param moreInputs additional texts of the message
   * @return the new message content
   */
  @Nonnull
  public static MessageContent text(
      @Nonnull final String firstInput, @Nullable final String... moreInputs) {
    return new MessageContent(
        Stream.concat(
                Stream.of(firstInput),
                moreInputs == null ? Stream.empty() : Arrays.stream(moreInputs))
            .map(text -> (ContentItem) new TextItem(text))
            .toList());
  }

  /**
   * Creates a new message content containing multiple {@link ContentItem}s.
   *
   * @param multiChatContents the content items
   */
  public MessageContent(@Nonnull final MultiChatMessageContent... multiChatContents) {
    this(convertIntoMultiMessageList(multiChatContents));
  }

  @Nonnull
  private static List<ContentItem> convertIntoMultiMessageList(
      @Nonnull final MultiChatMessageContent... multiChatContents) {
    final Function<MultiChatMessageContent, ContentItem> convertMultiChatContent =
        content -> {
          if (content instanceof TextContent text) {
            return new TextItem(text.getText());
          } else {
            final var imageUrl = ((ImageContent) content).getImageUrl();
            return new ImageItem(
                imageUrl.getUrl(), ImageItem.DetailLevel.fromString(imageUrl.getDetail()));
          }
        };
    return Arrays.stream(multiChatContents).map(convertMultiChatContent).toList();
  }
}
