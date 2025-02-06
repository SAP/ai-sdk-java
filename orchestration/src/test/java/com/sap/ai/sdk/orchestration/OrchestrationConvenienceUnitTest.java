package com.sap.ai.sdk.orchestration;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class OrchestrationConvenienceUnitTest {

  @Test
  void testMessageConstructionText() {
    var userMessageTwoTexts = Message.user("Text 1", "Text 2");
    var userMessageTwoTextsAndText = Message.user("Text 1").andText("Text 2");
    var userMessageTwoTextsAnd = Message.user("Text 1").and(MessageContent.text("Text 2"));
    var userMessageTwoTextsContent = Message.user(MessageContent.text("Text 1", "Text 2"));
    assertThat(userMessageTwoTexts).isEqualTo(userMessageTwoTextsAndText);
    assertThat(userMessageTwoTextsAndText).isEqualTo(userMessageTwoTextsAnd);
    assertThat(userMessageTwoTextsAnd).isEqualTo(userMessageTwoTextsContent);

    var systemMessageTwoTexts = Message.system("Text 1", "Text 2");
    var systemMessageTwoTextsAndText = Message.system("Text 1").andText("Text 2");
    var systemMessageTwoTextsAnd = Message.system("Text 1").and(MessageContent.text("Text 2"));
    var systemMessageTwoTextsContent = Message.system(MessageContent.text("Text 1", "Text 2"));
    assertThat(systemMessageTwoTexts).isEqualTo(systemMessageTwoTextsAndText);
    assertThat(systemMessageTwoTextsAndText).isEqualTo(systemMessageTwoTextsAnd);
    assertThat(systemMessageTwoTextsAnd).isEqualTo(systemMessageTwoTextsContent);
  }

  @Test
  void testMessageConstructionImage() {
    var userMessageWithImageAndImage = Message.user("Text 1").andImage("url");
    var userMessageWithImageAnd = Message.user("Text 1").and(MessageContent.image("url"));
    assertThat(userMessageWithImageAndImage).isEqualTo(userMessageWithImageAnd);

    var userMessageWithImageDetailAndImage =
        Message.user("Text 1").andImage("url", ImageItem.DetailLevel.low);
    var userMessageWithImageDetailAnd =
        Message.user("Text 1").and(MessageContent.image("url", ImageItem.DetailLevel.low));
    assertThat(userMessageWithImageDetailAndImage).isEqualTo(userMessageWithImageDetailAnd);
  }
}
