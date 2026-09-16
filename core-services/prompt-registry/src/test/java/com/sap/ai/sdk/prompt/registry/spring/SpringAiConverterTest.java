package com.sap.ai.sdk.prompt.registry.spring;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.prompt.registry.PromptRegistryClient;
import com.sap.ai.sdk.prompt.registry.client.PromptTemplatesApi;
import com.sap.ai.sdk.prompt.registry.model.MultiChatContent;
import com.sap.ai.sdk.prompt.registry.model.MultiChatTemplate;
import com.sap.ai.sdk.prompt.registry.model.PromptTemplate;
import com.sap.ai.sdk.prompt.registry.model.PromptTemplateSubstitutionRequest;
import com.sap.ai.sdk.prompt.registry.model.PromptTemplateSubstitutionResponse;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import java.util.List;
import java.util.Map;
import lombok.val;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;

public class SpringAiConverterTest {
  @RegisterExtension
  private static final WireMockExtension WM =
      WireMockExtension.newInstance().options(wireMockConfig().dynamicPort()).build();

  private static PromptTemplatesApi client;

  @BeforeAll
  static void setup() {
    val destination = DefaultHttpDestination.builder(WM.baseUrl()).build();
    val service = new AiCoreService().withBaseDestination(destination);
    client = new PromptRegistryClient(service).prompt();
  }

  @Test
  void testPromptRegistryToSpringAi() {
    val promptResponse =
        client.parsePromptTemplateByNameVersion(
            "categorization",
            "0.0.1",
            "java-e2e-test",
            "default",
            null,
            false,
            PromptTemplateSubstitutionRequest.create()
                .inputParams(Map.of("inputExample", "I love football")));

    List<Message> messages = SpringAiConverter.promptTemplateToMessages(promptResponse);
    assertThat(messages)
        .isEqualTo(
            List.of(
                new SystemMessage(
                    "You classify input text into the two following categories: Finance, Tech, Sports, Politics"),
                new UserMessage("I love football")));
  }

  @Test
  void testInvalidRoleThrowsException() {
    val errorPrompt =
        client.parsePromptTemplateByNameVersion(
            "categorization",
            "0.0.1",
            "error",
            "default",
            null,
            false,
            PromptTemplateSubstitutionRequest.create()
                .inputParams(Map.of("inputExample", "I love football")));

    assertThatThrownBy(() -> SpringAiConverter.promptTemplateToMessages(errorPrompt))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Unknown role: error");
  }

  @Test
  void testMultiChatTemplateTextContentToSpringAi() {
    val promptResponse =
        client.parsePromptTemplateByNameVersion(
            "categorization",
            "0.0.1",
            "multi-text",
            "default",
            null,
            false,
            PromptTemplateSubstitutionRequest.create()
                .inputParams(Map.of("inputExample", "I love football")));

    List<Message> messages = SpringAiConverter.promptTemplateToMessages(promptResponse);
    assertThat(messages)
        .isEqualTo(List.of(new UserMessage("First content line\nSecond content line")));
  }

  @Test
  void testMultiChatTemplateImageContentThrowsException() {
    val promptResponse =
        client.parsePromptTemplateByNameVersion(
            "categorization",
            "0.0.1",
            "multi-image",
            "default",
            null,
            false,
            PromptTemplateSubstitutionRequest.create()
                .inputParams(Map.of("inputExample", "I love football")));

    assertThatThrownBy(() -> SpringAiConverter.promptTemplateToMessages(promptResponse))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessageContaining("image content is not supported");
  }

  @Test
  void testUnsupportedPromptTemplateTypeThrowsException() {
    final PromptTemplate unsupportedTemplate = new PromptTemplate() {};
    final var promptResponse =
        PromptTemplateSubstitutionResponse.create().parsedPrompt(List.of(unsupportedTemplate));

    assertThatThrownBy(() -> SpringAiConverter.promptTemplateToMessages(promptResponse))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Unsupported PromptTemplate type");
  }

  @Test
  void testUnsupportedMultiChatContentTypeThrowsException() {
    final MultiChatContent unsupportedContent = new MultiChatContent() {};
    final var message = MultiChatTemplate.create().role("user").content(unsupportedContent);
    final var promptResponse =
        PromptTemplateSubstitutionResponse.create().parsedPrompt(List.of(message));

    assertThatThrownBy(() -> SpringAiConverter.promptTemplateToMessages(promptResponse))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessageContaining("Unsupported MultiChatContent type");
  }
}
