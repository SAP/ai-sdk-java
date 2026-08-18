package com.sap.ai.sdk.orchestration;

import static org.assertj.core.api.Assertions.assertThat;

import com.sap.ai.sdk.orchestration.model.ChatMessageContent;
import com.sap.ai.sdk.orchestration.model.ToolChatMessage;
import org.junit.jupiter.api.Test;

class ToolMessageTest {

  @Test
  void createChatMessageCacheControlSet() {
    final var toolMessage = new ToolMessage("12344", "Hello world", new CacheControl("5m"));

    final var convertedMessage = toolMessage.createChatMessage();

    assertThat(convertedMessage).isNotNull();
    assertThat(convertedMessage).isInstanceOf(ToolChatMessage.class);
    final ToolChatMessage toolChatMessage = (ToolChatMessage) convertedMessage;
    assertThat(toolChatMessage.getContent()).isNotNull();
    assertThat(toolChatMessage.getContent())
        .isInstanceOf(ChatMessageContent.ListOfTextContents.class);
    final var listOfTextContents =
        (ChatMessageContent.ListOfTextContents) toolChatMessage.getContent();
    assertThat(listOfTextContents.values()).hasSize(1);
    final var textContent = listOfTextContents.values().get(0);
    assertThat(textContent).isNotNull();
    assertThat(textContent.getText()).isEqualTo(toolMessage.content);
    assertThat(textContent.getCacheControl().getTtl().getValue())
        .isEqualTo(toolMessage.cacheControl.getTtl());
  }
}
