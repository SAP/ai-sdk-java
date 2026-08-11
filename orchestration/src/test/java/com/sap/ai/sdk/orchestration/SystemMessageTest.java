package com.sap.ai.sdk.orchestration;

import static org.assertj.core.api.Assertions.assertThat;

import com.sap.ai.sdk.orchestration.model.ChatMessageContent;
import com.sap.ai.sdk.orchestration.model.SystemChatMessage;
import org.junit.jupiter.api.Test;

class SystemMessageTest {

  @Test
  void createChatMessageListContentWithCacheControl() {
    final var systemChatMessage =
        (SystemChatMessage)
            new SystemMessage("Foo ".repeat(100), new CacheControl("5m")).createChatMessage();

    assertThat(systemChatMessage.getContent())
        .isInstanceOf(ChatMessageContent.ListOfTextContents.class);
  }

  @Test
  void createChatMessageInnerStringContentWithoutCacheControl() {
    final var systemChatMessage =
        (SystemChatMessage) new SystemMessage("Foo ".repeat(100)).createChatMessage();

    assertThat(systemChatMessage.getContent()).isInstanceOf(ChatMessageContent.InnerString.class);
  }
}
