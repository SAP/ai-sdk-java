package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.model.ChatMessageContent;
import com.sap.ai.sdk.orchestration.model.UserChatMessageContent;
import com.sap.ai.sdk.orchestration.model.UserChatMessageContentItem;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

/**
 * Represents the content of a chat message.
 *
 * @param items a list of the content items
 * @since 1.3.0
 */
public record MessageContent(@Nonnull List<ContentItem> items) {
  //  @Nonnull
  //  static MessageContent fromMCMContentList(@Nonnull final List<ChatMessageContent>
  // mCMContentList) {
  //    final var itemList =
  //        mCMContentList.stream()
  //            .map(
  //                content -> {
  //                  if (content instanceof TextContent text) {
  //                    return new TextItem(text.getText());
  //                  } else {
  //                    final var imageUrl = ((ImageContent) content).getImageUrl();
  //                    return (ContentItem)
  //                        new ImageItem(
  //                            imageUrl.getUrl(),
  //                            ImageItem.DetailLevel.fromString(imageUrl.getDetail()));
  //                  }
  //                })
  //            .toList();
  //    return new MessageContent(itemList);
  //  }
  @Nonnull
  static MessageContent fromChatMessageContent(ChatMessageContent chatMessageContent) {
    if (chatMessageContent instanceof ChatMessageContent.InnerString innerString) {
      return new MessageContent(List.of(new TextItem(innerString.value())));
    } else if (chatMessageContent
        instanceof ChatMessageContent.InnerTextContents innerTextContents) {
      var items =
          innerTextContents.values().stream()
              .map(textContent -> ((ContentItem) new TextItem(textContent.getText())))
              .toList();
      return new MessageContent(items);
    } else {
      throw new IllegalArgumentException(
          "Contents of type " + chatMessageContent.getClass() + " are not supported.");
    }
  }

  @Nonnull
  static MessageContent fromUserChatMessageContent(UserChatMessageContent chatMessageContent) {
    if (chatMessageContent instanceof UserChatMessageContent.InnerString innerString) {
      return new MessageContent(List.of(new TextItem(innerString.value())));
    } else if (chatMessageContent
        instanceof UserChatMessageContent.InnerUserChatMessageContentItems innerContentItems) {
      var items = new ArrayList<ContentItem>();
      for (var value : innerContentItems.values()) {
        if (value.getType().equals(UserChatMessageContentItem.TypeEnum.TEXT)) {
          items.add(new TextItem(value.getText()));
        } else if (value.getType().equals(UserChatMessageContentItem.TypeEnum.IMAGE_URL)) {
          var detailLevel = ImageItem.DetailLevel.fromString(value.getImageUrl().getDetail());
          items.add(new ImageItem(value.getImageUrl().getUrl(), detailLevel));
        }
      }
      return new MessageContent(items);
    } else {
      throw new IllegalArgumentException(
          "Contents of type " + chatMessageContent.getClass() + " are not supported.");
    }
  }
}
