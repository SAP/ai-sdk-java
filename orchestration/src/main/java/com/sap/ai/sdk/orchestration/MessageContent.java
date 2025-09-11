package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.model.ChatMessageContent;
import com.sap.ai.sdk.orchestration.model.UserChatMessageContent;
import com.sap.ai.sdk.orchestration.model.UserChatMessageContentItem;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import lombok.val;

/**
 * Represents the content of a chat message.
 *
 * @param items a list of the content items
 * @since 1.3.0
 */
public record MessageContent(@Nonnull List<? extends ContentItem> items) {
  @Nonnull
  static MessageContent fromChatMessageContent(final ChatMessageContent chatMessageContent) {
    List<? extends ContentItem> texts = List.of();
    if (chatMessageContent instanceof ChatMessageContent.InnerString innerString) {
      texts = List.of(new TextItem(innerString.value()));
    } else if (chatMessageContent instanceof ChatMessageContent.InnerTextContents innerText) {
      texts = innerText.values().stream().map(content -> new TextItem(content.getText())).toList();
    }
    return new MessageContent(texts);
  }

  @Nonnull
  static MessageContent fromUserChatMessageContent(
      final UserChatMessageContent chatMessageContent) {
    if (chatMessageContent instanceof UserChatMessageContent.InnerString innerString) {
      return new MessageContent(List.of(new TextItem(innerString.value())));
    } else if (chatMessageContent
        instanceof
        final UserChatMessageContent.InnerUserChatMessageContentItems innerContentItems) {
      val items = new ArrayList<ContentItem>();
      for (val value : innerContentItems.values()) {
        if (value.getType().equals(UserChatMessageContentItem.TypeEnum.TEXT)) {
          items.add(new TextItem(value.getText()));
        } else if (value.getType().equals(UserChatMessageContentItem.TypeEnum.IMAGE_URL)) {
          val detailLevel = ImageItem.DetailLevel.fromString(value.getImageUrl().getDetail());
          items.add(new ImageItem(value.getImageUrl().getUrl(), detailLevel));
        }
      }
      return new MessageContent(items);
    }
    return new MessageContent(List.of());
  }
}
