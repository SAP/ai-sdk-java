package com.sap.ai.sdk.orchestration;

import javax.annotation.Nonnull;

import com.sap.ai.sdk.orchestration.model.MultiChatMessageContent;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.NotImplementedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/** Represents a chat message as 'user' to the orchestration service. */
@Value
@Accessors(fluent = true)
@NoArgsConstructor(force = true)
public class UserMessage implements Message {

  /** The role of the assistant. */
  @Nonnull String role = "user";

  //  TODO: mention in PR:
  //   Deleted non-null annotation from content to allow for dynamic creation using a no-args
  //   constructor.
  /** The content of the message. */
  MessageContent content;

  @Nonnull
  @Override
  public String content() {
    //    TODO: Ask in PR:
    //     Maybe we want this to be called "contentAsString()" and have an actual getter instead?
    //     Right now, this version avoids a breaking change.
    if (content instanceof MessageContentSingle mCSingle) {
      return mCSingle.content();
    } else if (content instanceof MessageContentMulti mCMulti) {
      var strBuilder = new StringBuilder();
      mCMulti
          .multiContentList()
          .forEach(
              multiContent -> {
                if (multiContent instanceof MultiMessageTextContent mMText) {
                  strBuilder.append(mMText.text());
                } else if (multiContent instanceof MultiMessageImageContent mMImage) {
                  strBuilder.append(mMImage.imageUrl());
                } else {
                  throw new NotImplementedException(
                      "Unknown subtype of MultiChatMessageContent: " + multiContent.getClass());
                }
              });
      return strBuilder.toString();
    } else {
      throw new NotImplementedException("Unknown subtype of MessageContent: " + content.getClass());
    }
  }

  //  Not the best, just a placeholder for the actual getter of content
  @Override
  public MessageContent getContent() {
    return content;
  }

  //  TODO: mention in PR
  //   I added these constructors for backwards compatibility and convenience.
  //   To enable dynamic message building, I added the addTextMessages and addImage methods.
  //   Instead, we could use a builder pattern, but I did not know how to make that work without
  //   breaking changes to the constructors.
  public UserMessage(String singleMessage) {
    content = new MessageContentSingle(singleMessage);
  }

  public UserMessage(MessageContent messageContent) {
    content = messageContent;
  }

  public UserMessage(List<MultiChatMessageContent> multiChatMessageContentList) {
    content =
        new MessageContentMulti(
            multiChatMessageContentList.toArray(MultiChatMessageContent[]::new));
  }

  public UserMessage addTextMessages(@Nonnull String... messages) {
    if(content == null && messages.length == 1) {
//      this.content will be of type MessageContentSingle only here
      return new UserMessage(messages[0]);
    }
    var multiContentList =
        new LinkedList<MultiMessageContent>(
            (Arrays.stream(messages).map(MultiMessageTextContent::new)).toList());
    if (content instanceof MessageContentSingle mCSingle) {
      multiContentList.addFirst(new MultiMessageTextContent(mCSingle.content()));
    } else if (content instanceof MessageContentMulti mCMulti) {
      multiContentList.addAll(0, mCMulti.multiContentList());
    }
    return new UserMessage(new MessageContentMulti(multiContentList));
  }

  public UserMessage addImage(
      @Nonnull String imageUrl, MultiMessageImageContent.DetailLevel detailLevel) {
    var multiContentList = new ArrayList<MultiMessageContent>();
    if (content instanceof MessageContentSingle mCSingle) {
      multiContentList.add(new MultiMessageTextContent(mCSingle.content()));
    } else {
      multiContentList.addAll(((MessageContentMulti) content).multiContentList());
    }
    multiContentList.add(new MultiMessageImageContent(imageUrl, detailLevel));
    return new UserMessage(new MessageContentMulti(multiContentList));
  }
}
