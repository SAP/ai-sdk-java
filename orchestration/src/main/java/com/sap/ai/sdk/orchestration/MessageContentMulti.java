package com.sap.ai.sdk.orchestration;

import static com.sap.ai.sdk.orchestration.MultiMessageImageContent.DetailLevel;

import com.sap.ai.sdk.orchestration.model.ImageContent;
import com.sap.ai.sdk.orchestration.model.MultiChatMessageContent;
import com.sap.ai.sdk.orchestration.model.TextContent;
import java.util.List;

public record MessageContentMulti(List<MultiMessageContent> multiContentList)
    implements MessageContent {
  public MessageContentMulti {}

  // TODO: Note in PR:
  //  I use MultiChatMessageContent... here since using List<MultiChatMessageContent> results in a
  //  compilation error (constructors have same erasure). Is there a more elegant way?
  public MessageContentMulti(MultiChatMessageContent... multiChatContents) {
    this(convertIntoMultiMessageList(multiChatContents));
  }

  private static List<MultiMessageContent> convertIntoMultiMessageList(
      MultiChatMessageContent... multiChatContents) {
    List<MultiMessageContent> multiContentList = new java.util.ArrayList<>(List.of());
    for (MultiChatMessageContent multiChatContent : multiChatContents) {
      if (multiChatContent instanceof TextContent textContent) {
        multiContentList.add(new MultiMessageTextContent(textContent.getText()));
      } else if (multiChatContent instanceof ImageContent imageContent) {
        var imageUrl = imageContent.getImageUrl();
        multiContentList.add(
            new MultiMessageImageContent(
                imageUrl.getUrl(), DetailLevel.fromString(imageUrl.getDetail())));
      } else {
        throw new IllegalArgumentException(
            "Unknown subtype of MultiChatMessageContent: " + multiChatContent.getClass());
      }
    }
    return multiContentList;
  }
}
