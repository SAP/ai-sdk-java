package com.sap.ai.sdk.foundationmodels.openai;

import static org.assertj.core.api.Assertions.assertThat;

import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionRequestUserMessage;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionRequestUserMessageContent;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionTool;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionToolChoiceOption;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.CreateChatCompletionRequestAllOfStop;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class OpenAiChatCompletionRequestTest {

  @Test
  void stopSequence() {
    var request =
        new OpenAiChatCompletionRequest(OpenAiMessage.user("Hello, world"))
            .withStop("stop1", "stop2 stopNot3", "stop3");

    var lowLevelRequest = request.createCreateChatCompletionRequest();
    assertThat(
            ((CreateChatCompletionRequestAllOfStop.ListOfStrings) lowLevelRequest.getStop())
                .values())
        .containsExactly("stop1", "stop2 stopNot3", "stop3");
  }

  @Test
  void createWithExistingRequest() {
    var originalRequest =
        new OpenAiChatCompletionRequest(OpenAiMessage.user("First message"))
            .withSeed(123)
            .withTemperature(BigDecimal.valueOf(0.5));

    var newRequest = originalRequest.withMessages(List.of(OpenAiMessage.user("Another message")));

    var lowlevelRequest = newRequest.createCreateChatCompletionRequest();

    assertThat(newRequest).isNotEqualTo(originalRequest);
    assertThat(lowlevelRequest.getMessages()).hasSize(1);
    assertThat(lowlevelRequest.getMessages().get(0))
        .isInstanceOf(ChatCompletionRequestUserMessage.class);
    assertThat(
            ((ChatCompletionRequestUserMessage) lowlevelRequest.getMessages().get(0)).getContent())
        .isEqualTo(ChatCompletionRequestUserMessageContent.create("Another message"));
    assertThat(lowlevelRequest.getSeed()).isEqualTo(123);
    assertThat(lowlevelRequest.getTemperature()).isEqualTo(BigDecimal.valueOf(0.5));
  }

  @Test
  void withToolChoiceNone() {
    OpenAiChatCompletionRequest request =
        new OpenAiChatCompletionRequest("message").withToolChoice(OpenAiToolChoice.NONE);

    var lowLevelRequest = request.createCreateChatCompletionRequest();
    var choice =
        ((ChatCompletionToolChoiceOption.InnerString) lowLevelRequest.getToolChoice()).value();
    assertThat(choice).isEqualTo("none");
  }

  @Test
  void withToolChoiceOptional() {
    OpenAiChatCompletionRequest request =
        new OpenAiChatCompletionRequest("message").withToolChoice(OpenAiToolChoice.OPTIONAL);

    var lowLevelRequest = request.createCreateChatCompletionRequest();
    var choice =
        ((ChatCompletionToolChoiceOption.InnerString) lowLevelRequest.getToolChoice()).value();
    assertThat(choice).isEqualTo("auto");
  }

  @Test
  void withToolChoiceRequired() {
    OpenAiChatCompletionRequest request =
        new OpenAiChatCompletionRequest("message").withToolChoice(OpenAiToolChoice.REQUIRED);

    var lowLevelRequest = request.createCreateChatCompletionRequest();
    var choice =
        ((ChatCompletionToolChoiceOption.InnerString) lowLevelRequest.getToolChoice()).value();
    assertThat(choice).isEqualTo("required");
  }

  @Test
  void withToolChoiceFunction() {
    OpenAiChatCompletionRequest request =
        new OpenAiChatCompletionRequest("message")
            .withToolChoice(OpenAiToolChoice.function("functionName"));

    var lowLevelRequest = request.createCreateChatCompletionRequest();
    var choice =
        ((ChatCompletionToolChoiceOption.InnerChatCompletionNamedToolChoice)
                lowLevelRequest.getToolChoice())
            .value();
    assertThat(choice.getType().getValue()).isEqualTo("function");
    assertThat(choice.getFunction().getName()).isEqualTo("functionName");
  }

  @Test
  void messageListExternallyUnmodifiable() {
    var originalList = new ArrayList<OpenAiMessage>();
    OpenAiUserMessage user = OpenAiMessage.user("Initial message");
    originalList.add(user);

    var request = new OpenAiChatCompletionRequest(originalList);

    var generatedRequest = request.createCreateChatCompletionRequest();
    assertThat(generatedRequest.getMessages()).hasSize(1);

    originalList.add(OpenAiMessage.user("Another message"));

    var generatedRequestAfter = request.createCreateChatCompletionRequest();
    assertThat(generatedRequestAfter.getMessages())
        .as("Modifying the original list should not affect the messages in the request object.")
        .hasSize(1);
  }

  @Test
  void withOpenAiTools() {
    record DummyRequest(String param1, int param2) {}

    var request =
        new OpenAiChatCompletionRequest(OpenAiMessage.user("Hello, world"))
            .withToolsExecutable(
                List.of(
                    OpenAiTool.<DummyRequest>forFunction(r -> "result")
                        .withArgument(DummyRequest.class)
                        .withName("toolA")
                        .withDescription("descA")
                        .withStrict(true),
                    OpenAiTool.<DummyRequest>forFunction(r -> "result")
                        .withArgument(DummyRequest.class)
                        .withName("toolB")
                        .withDescription("descB")
                        .withStrict(true)));

    var lowLevelRequest = request.createCreateChatCompletionRequest();
    assertThat(lowLevelRequest.getTools()).hasSize(2);

    var toolA = lowLevelRequest.getTools().get(0);
    assertThat(toolA).isInstanceOf(ChatCompletionTool.class);
    assertThat(toolA.getType()).isEqualTo(ChatCompletionTool.TypeEnum.FUNCTION);
    assertThat(toolA.getFunction().getName()).isEqualTo("toolA");
    assertThat(toolA.getFunction().getDescription()).isEqualTo("descA");
    assertThat(toolA.getFunction().isStrict()).isTrue();

    assertThat(toolA.getFunction().getParameters())
        .isEqualTo(
            Map.of(
                "properties",
                Map.of(
                    "param1", Map.of("type", "string"),
                    "param2", Map.of("type", "integer")),
                "type",
                "object"));
  }
}
