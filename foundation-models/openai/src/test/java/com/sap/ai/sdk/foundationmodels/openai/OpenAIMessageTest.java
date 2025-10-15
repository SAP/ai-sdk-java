package com.sap.ai.sdk.foundationmodels.openai;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionRequestAssistantMessage;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionRequestAssistantMessageContent;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionRequestMessageContentPartImage;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionRequestMessageContentPartImageImageUrl;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionRequestMessageContentPartText;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionRequestSystemMessage;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionRequestSystemMessageContent;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionRequestToolMessage;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionRequestToolMessageContent;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionRequestUserMessage;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionRequestUserMessageContent;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ToolCallType;
import java.net.URI;
import java.util.List;
import java.util.stream.Stream;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class OpenAIMessageTest {
  static String validText = "Some string";
  static String validImageUrl = "http://valid-url.com";

  static Stream<Arguments> provideValidTextMessageByRole() {
    return Stream.of(
        Arguments.of(OpenAiMessage.user(validText)),
        Arguments.of(OpenAiMessage.assistant(validText)),
        Arguments.of(OpenAiMessage.system(validText)),
        Arguments.of(OpenAiMessage.tool(validText, "some call id")));
  }

  @ParameterizedTest
  @MethodSource("provideValidTextMessageByRole")
  void createMessagesWithText(OpenAiMessage message) {
    assertThat(message.content().items()).hasSize(1);
    assertThat(((OpenAiTextItem) message.content().items().get(0)).text()).isEqualTo(validText);

    if (message instanceof OpenAiToolMessage) {
      assertThat(((OpenAiToolMessage) message).toolCallId()).isEqualTo("some call id");
    }
  }

  @ParameterizedTest
  @MethodSource("provideValidTextMessageByRole")
  void createMessagesWithAdditionalText(OpenAiMessage message) {

    if (message instanceof OpenAiAssistantMessage || message instanceof OpenAiToolMessage) {
      return;
    }

    var additionalText = "Additional text";
    var extendedMessage =
        switch (message.role()) {
          case "user" -> ((OpenAiUserMessage) message).withText(additionalText);
          case "system" -> ((OpenAiSystemMessage) message).withText(additionalText);
          default -> throw new IllegalArgumentException("Invalid message role: " + message.role());
        };

    assertThat(extendedMessage.content().items()).hasSize(2);
    assertThat(((OpenAiTextItem) extendedMessage.content().items().get(1)).text())
        .isEqualTo(additionalText);
    assertThat(message).isNotSameAs(extendedMessage);
  }

  @Test
  void createUserMessageWithImage() {

    var userMessage = OpenAiMessage.user(new OpenAiImageItem(validImageUrl));
    var userMessageWithHighAndLowDetail =
        userMessage
            .withImage(validImageUrl, OpenAiImageItem.DetailLevel.HIGH)
            .withImage(validImageUrl, OpenAiImageItem.DetailLevel.LOW);

    assertThat(userMessage.content().items()).hasSize(1);
    assertThat(userMessageWithHighAndLowDetail.content().items()).hasSize(3);

    assertThat(((OpenAiImageItem) userMessage.content().items().get(0)).imageUrl())
        .isEqualTo(validImageUrl);
    assertThat(((OpenAiImageItem) userMessage.content().items().get(0)).detailLevel())
        .isEqualTo(OpenAiImageItem.DetailLevel.AUTO);
    assertThat(
            ((OpenAiImageItem) userMessageWithHighAndLowDetail.content().items().get(1))
                .detailLevel())
        .isEqualTo(OpenAiImageItem.DetailLevel.HIGH);
    assertThat(
            ((OpenAiImageItem) userMessageWithHighAndLowDetail.content().items().get(2))
                .detailLevel())
        .isEqualTo(OpenAiImageItem.DetailLevel.LOW);
  }

  static Stream<Arguments> provideInvalidImageUrls() {
    return Stream.of(
        Arguments.of("<invalid-characters>"),
        Arguments.of("://missing-scheme.com"),
        Arguments.of("http://invalid-url with spaces"));
  }

  @ParameterizedTest
  @MethodSource("provideInvalidImageUrls")
  void invalidImageUrlUserMessage(String invalidUrl) {
    OpenAiUserMessage userMessage = OpenAiMessage.user(new OpenAiImageItem(invalidUrl));

    Assertions.assertThatThrownBy(userMessage::createChatCompletionRequestMessage)
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Provided image URL has invalid syntax.");
  }

  @SneakyThrows
  @Test
  void userMessageToDto() {
    var singleText = OpenAiMessage.user(validText);
    var multiItemMessage = singleText.withImage(validImageUrl);

    var requestMessage = singleText.createChatCompletionRequestMessage();

    assertThat(requestMessage.getRole()).isEqualTo(ChatCompletionRequestUserMessage.RoleEnum.USER);
    assertThat(
            ((ChatCompletionRequestUserMessageContent.InnerString) requestMessage.getContent())
                .value())
        .isEqualTo(validText);

    var requestMessageWithImage = multiItemMessage.createChatCompletionRequestMessage();

    assertThat(requestMessageWithImage.getRole())
        .isEqualTo(ChatCompletionRequestUserMessage.RoleEnum.USER);

    var values =
        ((ChatCompletionRequestUserMessageContent
                    .ListOfChatCompletionRequestUserMessageContentParts)
                requestMessageWithImage.getContent())
            .values();
    assertThat(values).hasSize(2);
    assertThat(((ChatCompletionRequestMessageContentPartText) values.get(0)).getType())
        .isEqualTo(ChatCompletionRequestMessageContentPartText.TypeEnum.TEXT);
    assertThat(((ChatCompletionRequestMessageContentPartText) values.get(0)).getText())
        .isEqualTo(validText);
    assertThat(((ChatCompletionRequestMessageContentPartImage) values.get(1)).getType())
        .isEqualTo(ChatCompletionRequestMessageContentPartImage.TypeEnum.IMAGE_URL);
    assertThat(
            ((ChatCompletionRequestMessageContentPartImage) values.get(1)).getImageUrl().getUrl())
        .isEqualTo(new URI(validImageUrl));
    assertThat(
            ((ChatCompletionRequestMessageContentPartImage) values.get(1))
                .getImageUrl()
                .getDetail())
        .isEqualTo(ChatCompletionRequestMessageContentPartImageImageUrl.DetailEnum.AUTO);
  }

  @Test
  void systemMessageToDto() {
    var singleText = OpenAiMessage.system(validText);
    var multiItemMessage = singleText.withText("Additional text");

    var requestMessage = singleText.createChatCompletionRequestMessage();
    assertThat(requestMessage.getRole())
        .isEqualTo(ChatCompletionRequestSystemMessage.RoleEnum.SYSTEM);
    assertThat(
            ((ChatCompletionRequestSystemMessageContent.InnerString) requestMessage.getContent())
                .value())
        .isEqualTo(validText);

    var requestMessageWithText = multiItemMessage.createChatCompletionRequestMessage();
    assertThat(requestMessageWithText.getRole())
        .isEqualTo(ChatCompletionRequestSystemMessage.RoleEnum.SYSTEM);
    var values =
        ((ChatCompletionRequestSystemMessageContent
                    .ListOfChatCompletionRequestMessageContentPartTexts)
                requestMessageWithText.getContent())
            .values();
    assertThat(values).hasSize(2);
    assertThat(values.get(0).getType())
        .isEqualTo(ChatCompletionRequestMessageContentPartText.TypeEnum.TEXT);
    assertThat(values.get(0).getText()).isEqualTo(validText);
    assertThat(values.get(1).getType())
        .isEqualTo(ChatCompletionRequestMessageContentPartText.TypeEnum.TEXT);
    assertThat(values.get(1).getText()).isEqualTo("Additional text");
  }

  @Test
  void assistantMessageToDto() {
    var singleText = OpenAiMessage.assistant(validText);

    var requestMessage = singleText.createChatCompletionRequestMessage();
    assertThat(requestMessage.getRole())
        .isEqualTo(ChatCompletionRequestAssistantMessage.RoleEnum.ASSISTANT);
    assertThat(
            ((ChatCompletionRequestAssistantMessageContent.InnerString) requestMessage.getContent())
                .value())
        .isEqualTo(validText);

    var messageWithFunctionCall =
        new OpenAiAssistantMessage(
            new OpenAiMessageContent(List.of()),
            List.of(new OpenAiFunctionCall("id", "name", "arguments")));
    var requestMessageWithFunctionCall =
        messageWithFunctionCall.createChatCompletionRequestMessage();

    assertThat(requestMessageWithFunctionCall.getToolCalls()).hasSize(1);
    assertThat(requestMessageWithFunctionCall.getToolCalls().get(0).getType())
        .isEqualTo(ToolCallType.FUNCTION);
    assertThat(requestMessageWithFunctionCall.getToolCalls().get(0).getId()).isEqualTo("id");
    assertThat(requestMessageWithFunctionCall.getToolCalls().get(0).getFunction().getName())
        .isEqualTo("name");
    assertThat(requestMessageWithFunctionCall.getToolCalls().get(0).getFunction().getArguments())
        .isEqualTo("arguments");
  }

  @Test
  void toolMessageToDto() {
    var singleText = OpenAiMessage.tool(validText, "some call id");

    var requestMessage = singleText.createChatCompletionRequestMessage();
    assertThat(requestMessage.getRole()).isEqualTo(ChatCompletionRequestToolMessage.RoleEnum.TOOL);
    assertThat(
            ((ChatCompletionRequestToolMessageContent.InnerString) requestMessage.getContent())
                .value())
        .isEqualTo(validText);
    assertThat(requestMessage.getToolCallId()).isEqualTo("some call id");
  }

  @Test
  void throwOnSystemMessageWithImage() {
    var systemMessage =
        new OpenAiSystemMessage(
            new OpenAiMessageContent(List.of(new OpenAiImageItem(validImageUrl))));

    Assertions.assertThatThrownBy(systemMessage::createChatCompletionRequestMessage)
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining(
            "Unknown content type for class com.sap.ai.sdk.foundationmodels.openai.OpenAiImageItem messages.");
  }

  @ParameterizedTest
  @MethodSource("provideValidTextMessageByRole")
  void testCreateChatCompletionRequestMessage(OpenAiMessage message) {
    assertThatNoException()
        .isThrownBy(() -> OpenAiUtils.createChatCompletionRequestMessage(message));
  }
}
