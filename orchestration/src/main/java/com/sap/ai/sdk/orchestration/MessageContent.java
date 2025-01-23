package com.sap.ai.sdk.orchestration;

public sealed interface MessageContent permits MessageContentSingle, MessageContentMulti {

  //  TODO: Ask in PR: This is to turn the message content into a string.
  //      Is there a more elegant way/place to do this? Also, I put a semicolon between multiple
  //       messages. Is that okay? Or just a space?
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
