package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.model.MultiChatMessageContent;
import java.util.List;
import javax.annotation.Nonnull;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.Accessors;

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
    return MessageContent.toString(content);
  }

  @Override
  @Nonnull
  public MessageContent getContent() {
    return content != null ? content : new MessageContentSingle("");
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
    //    List<MultiMessageContent> newContentList = new java.util.ArrayList<>(List.of());
    //    for (var chatMessageContent : multiChatMessageContentList ) {
    //      if (chatMessageContent instanceof TextContent textContent) {
    //        newContentList.add(new MultiMessageTextContent(textContent.getText()));
    //      } else if (chatMessageContent instanceof ImageContent imageContent) {
    //        var imageUrl = imageContent.getImageUrl();
    //        newContentList.add(new MultiMessageImageContent(
    //                imageUrl.getUrl(),
    // MultiMessageImageContent.DetailLevel.fromString(imageUrl.getDetail())));
    //      } else {
    //        throw new NotImplementedException("Unknown subtype of MultiChatMessageContent: " +
    // chatMessageContent.getClass());
    //      }
    //    }
    //    content = new MessageContentMulti(newContentList);
    content =
        new MessageContentMulti(
            multiChatMessageContentList.toArray(MultiChatMessageContent[]::new));
  }

  @Nonnull
  public UserMessage addTextMessages(@Nonnull String... messages) {
    //    if (content == null && messages.length == 1) {
    //      //      this.content will be of type MessageContentSingle only here
    //      return new UserMessage(messages[0]);
    //    }
    //    var multiContentList = new ArrayList<MultiMessageContent>();
    //    if (content instanceof MessageContentSingle mCSingle) {
    //      multiContentList.add(new MultiMessageTextContent(mCSingle.content()));
    //    } else {
    //      multiContentList.addAll(((MessageContentMulti) content).multiContentList());
    //    }
    //
    // multiContentList.addAll((Arrays.stream(messages).map(MultiMessageTextContent::new)).toList());
    //    return new UserMessage(new MessageContentMulti(multiContentList));
    return ((UserMessage) Message.addTextMessages(content, role, messages));
  }

  @Nonnull
  public UserMessage addImage(
      @Nonnull String imageUrl, MultiMessageImageContent.DetailLevel detailLevel) {
    //    var multiContentList = new ArrayList<MultiMessageContent>();
    //    if (content instanceof MessageContentSingle mCSingle) {
    //      multiContentList.add(new MultiMessageTextContent(mCSingle.content()));
    //    } else {
    //      multiContentList.addAll(((MessageContentMulti) content).multiContentList());
    //    }
    //    multiContentList.add(new MultiMessageImageContent(imageUrl, detailLevel));
    //    return new UserMessage(new MessageContentMulti(multiContentList));
    return ((UserMessage) Message.addImage(content, role, imageUrl, detailLevel));
  }
}
