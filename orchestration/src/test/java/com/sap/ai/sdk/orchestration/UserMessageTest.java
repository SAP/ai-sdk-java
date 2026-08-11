package com.sap.ai.sdk.orchestration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.sap.ai.sdk.orchestration.model.UserChatMessage;
import com.sap.ai.sdk.orchestration.model.UserChatMessageContent;
import org.junit.jupiter.api.Test;

class UserMessageTest {

  @Test
  void createChatMessageListContentWithCacheControl() {
    final var userChatMessage =
        (UserChatMessage)
            new UserMessage("Foo ".repeat(100), new CacheControl("5m")).createChatMessage();

    assertThat(userChatMessage.getContent())
        .isInstanceOf(UserChatMessageContent.ListOfUserChatMessageContentItems.class);
  }

  @Test
  void createChatMessageInnerStringContentWithoutCacheControl() {
    final var userChatMessage =
        (UserChatMessage) new UserMessage("Foo ".repeat(100)).createChatMessage();

    assertThat(userChatMessage.getContent()).isInstanceOf(UserChatMessageContent.InnerString.class);
  }
}
