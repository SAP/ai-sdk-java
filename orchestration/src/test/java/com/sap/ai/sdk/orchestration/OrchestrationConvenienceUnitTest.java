package com.sap.ai.sdk.orchestration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

public class OrchestrationConvenienceUnitTest {

  @Test
  void testMessageConstructionText() {
    var userMessageViaStaticFactory = Message.user("Text 1");
    var userMessageViaConstructor = new UserMessage(("Text 1"));
    assertThat(userMessageViaStaticFactory).isEqualTo(userMessageViaConstructor);

    var systemMessageViaStaticFactory = Message.system("Text 1");
    var systemMessageViaConstructor = new SystemMessage("Text 1");
    assertThat(systemMessageViaStaticFactory).isEqualTo(systemMessageViaConstructor);
  }

  @Test
  void testMessageConstructionImage() {
    var userMessageOnlyImageConvenience = Message.user(new ImageItem("url"));
    var userMessageOnlyImageBase =
        new UserMessage(new MessageContent(List.of(new ImageItem("url"))));
    assertThat(userMessageOnlyImageBase).isEqualTo(userMessageOnlyImageConvenience);

    var userMessageWithImage = Message.user("Text 1").withImage("url");
    var userMessageWithImageAndDetail =
        Message.user("Text 1").withImage("url", ImageItem.DetailLevel.AUTO);
    assertThat(userMessageWithImage).isEqualTo(userMessageWithImageAndDetail);
  }
}
