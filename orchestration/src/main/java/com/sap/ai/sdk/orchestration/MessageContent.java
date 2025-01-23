package com.sap.ai.sdk.orchestration;

public sealed interface MessageContent permits MessageContentSingle, MessageContentMulti {

  static String toString(MessageContent content) {
    if (content instanceof MessageContentSingle mCSingle) {
      return mCSingle.content();
    } else if (content instanceof MessageContentMulti mCMulti) {
      var strBuilder = new StringBuilder();
      mCMulti
          .multiContentList()
          .forEach(
              multiContent -> {
                if (multiContent instanceof MultiMessageTextContent mMText) {
                  strBuilder.append(mMText.text()).append("; ");
                } else if (multiContent instanceof MultiMessageImageContent mMImage) {
                  strBuilder.append(mMImage.imageUrl());
                } else {
                  throw new IllegalArgumentException(
                      "Unknown subtype of MultiChatMessageContent: " + multiContent.getClass());
                }
              });
      return strBuilder.toString();
    } else {
      throw new IllegalArgumentException(
          "Unknown subtype of MessageContent: " + content.getClass());
    }
  }
}
