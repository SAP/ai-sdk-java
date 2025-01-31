package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.model.ImageContent;
import com.sap.ai.sdk.orchestration.model.MultiChatMessageContent;
import com.sap.ai.sdk.orchestration.model.TextContent;
import java.util.Arrays;
import java.util.List;

public record MessageContent(List<ContentItem> contentItemList) {

  MessageContent(String singleMessage) {
    this(List.of(new TextItem(singleMessage)));
  }

  public static MessageContent image(String imageUrl, ImageItem.DetailLevel detailLevel) {
    return new MessageContent(List.of(new ImageItem(imageUrl, detailLevel)));
  }

  public static MessageContent text(String text) {
    return new MessageContent(List.of(new TextItem(text)));
  }

  public static MessageContent text(String... texts) {
    return new MessageContent(
        Arrays.stream(texts).map(text -> ((ContentItem) new TextItem(text))).toList());
  }

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
