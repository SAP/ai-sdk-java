package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.model.ImageContent;
import com.sap.ai.sdk.orchestration.model.MultiChatMessageContent;
import com.sap.ai.sdk.orchestration.model.TextContent;
import java.util.Arrays;
import java.util.List;

/**
 * Represents the content of a chat message.
 *
 * @param contentItemList a list of the content items
 */
public record MessageContent(List<ContentItem> contentItemList) {

  MessageContent(String singleMessage) {
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
  public static MessageContent image(String imageUrl, ImageItem.DetailLevel detailLevel) {
    return new MessageContent(List.of(new ImageItem(imageUrl, detailLevel)));
  }

  /**
   * Creates a new message content containing one {@link ImageItem} with the given image URL.
   *
   * @param imageUrl the URL of the image
   * @return the new message content
   */
  public static MessageContent image(String imageUrl) {
    return new MessageContent(List.of(new ImageItem(imageUrl)));
  }

  /**
   * Creates a new message content containing one {@link TextItem} with the given text.
   *
   * @param text the text of the message
   * @return the new message content
   */
  public static MessageContent text(String text) {
    return new MessageContent(List.of(new TextItem(text)));
  }

  /**
   * Creates a new message content containing multiple {@link TextItem}s with the given texts.
   *
   * @param texts the texts of the messages
   * @return the new message content
   */
  public static MessageContent text(String... texts) {
    return new MessageContent(
        Arrays.stream(texts).map(text -> ((ContentItem) new TextItem(text))).toList());
  }

  /**
   * Creates a new message content containing multiple {@link ContentItem}s.
   *
   * @param multiChatContents the content items
   * @return the new message content
   */
  public MessageContent(MultiChatMessageContent... multiChatContents) {
    this(convertIntoMultiMessageList(multiChatContents));
  }

  private static List<ContentItem> convertIntoMultiMessageList(
      MultiChatMessageContent... multiChatContents) {
    List<ContentItem> multiContentList = new java.util.ArrayList<>(List.of());
    for (MultiChatMessageContent multiChatContent : multiChatContents) {
      if (multiChatContent instanceof TextContent textContent) {
        multiContentList.add(new TextItem(textContent.getText()));
      } else if (multiChatContent instanceof ImageContent imageContent) {
        var imageUrl = imageContent.getImageUrl();
        multiContentList.add(
            new ImageItem(
                imageUrl.getUrl(), ImageItem.DetailLevel.fromString(imageUrl.getDetail())));
      } else {
        throw new IllegalArgumentException(
            "Unknown subtype of MultiChatMessageContent: " + multiChatContent.getClass());
      }
    }
    return multiContentList;
  }
}
