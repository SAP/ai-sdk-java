package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.model.ImageContent;
import com.sap.ai.sdk.orchestration.model.MultiChatMessageContent;
import com.sap.ai.sdk.orchestration.model.TextContent;
import java.util.List;
import javax.annotation.Nonnull;

/**
 * Represents the content of a chat message.
 *
 * @param items a list of the content items
 * @since 1.3.0
 */
public record MessageContent(@Nonnull List<ContentItem> items) {
  @Nonnull
  static MessageContent fromMCMContentList(
      @Nonnull final List<MultiChatMessageContent> mCMContentList) {
    final var itemList =
        mCMContentList.stream()
            .map(
                content -> {
                  if (content instanceof TextContent text) {
                    return new TextItem(text.getText());
                  } else {
                    final var imageUrl = ((ImageContent) content).getImageUrl();
                    return (ContentItem)
                        new ImageItem(
                            imageUrl.getUrl(),
                            ImageItem.DetailLevel.fromString(imageUrl.getDetail()));
                  }
                })
            .toList();
    return new MessageContent(itemList);
  }
}
