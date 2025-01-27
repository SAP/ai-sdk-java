package com.sap.ai.sdk.orchestration;

import static com.sap.ai.sdk.orchestration.MultiMessageImageContent.DetailLevel;

import com.sap.ai.sdk.orchestration.model.ImageContent;
import com.sap.ai.sdk.orchestration.model.MultiChatMessageContent;
import com.sap.ai.sdk.orchestration.model.TextContent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public record MessageContentMulti(List<MultiMessageContent> multiContentList)
    implements MessageContent {

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

  MessageContentMulti(
      @Nonnull List<? extends MultiMessageContent> newContent,
      @Nullable MessageContent oldContent) {
    this(mergeContent(newContent, oldContent));
  }

  private static List<MultiMessageContent> mergeContent(
      @Nonnull List<? extends MultiMessageContent> newContent,
      @Nullable MessageContent oldContent) {
    var multiContentList = new ArrayList<MultiMessageContent>();
    if (oldContent instanceof MessageContentSingle mCSingle) {
      multiContentList.add(new MultiMessageTextContent(mCSingle.content()));
    } else if (oldContent != null) {
      multiContentList.addAll(((MessageContentMulti) oldContent).multiContentList());
    }
    multiContentList.addAll(newContent);
    return multiContentList;
  }
}
