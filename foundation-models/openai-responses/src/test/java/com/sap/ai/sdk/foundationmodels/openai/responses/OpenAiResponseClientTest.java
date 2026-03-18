package com.sap.ai.sdk.foundationmodels.openai.responses;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openai.core.ObjectMappers;
import com.openai.models.ChatModel;
import com.openai.models.responses.ResponseCreateParams;
import jdk.jfr.Description;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class OpenAiResponseClientTest {

  @Test
  @Disabled("Integration test - requires actual API credentials")
  void createResponse() {

    final ResponseCreateParams request =
        ResponseCreateParams.builder()
            .model(ChatModel.GPT_5)
            .input("List 5 famous late twentieth century novels.")
            .build();

    final OpenAiResponsesClient client = new OpenAiResponsesClient();
    final var response = client.createResponse(request);

    assertThat(response).isNotNull();
    assertThat(response.output()).isNotEmpty();
  }

  @Test
  @Description("Demo OpenAI DTOs are jackson incompatible")
  void testResponseCreateParamsSerializationIssue() throws Exception {
    // Build a ResponseCreateParams object
    final ResponseCreateParams params =
        ResponseCreateParams.builder()
            .model(ChatModel.GPT_4O)
            .input("What is the capital of France?")
            .temperature(0.7)
            .build();

    // Attempt to serialize with Jackson
    final ObjectMapper mapper = new ObjectMapper();
    final String json = mapper.writeValueAsString(params);

    // Assert that the serialization produces empty or nearly empty JSON
    // The issue is that Jackson doesn't serialize the private 'body' field
    // It only sees public methods that return Optional<T>, which Jackson doesn't serialize by
    // default
    assertThat(json)
        .describedAs("ResponseCreateParams should serialize to empty JSON")
        .isEqualTo("{}");
  }

  @Test
  @Description("Demo OpenAI DTOs are jackson compatible with body attribute")
  void testResponseCreateParamsSerialization() throws Exception {
    // Build a ResponseCreateParams object
    final ResponseCreateParams params =
        ResponseCreateParams.builder()
            .model(ChatModel.GPT_4O)
            .input("What is the capital of France?")
            .temperature(0.7)
            .build();

    // Attempt to serialize with Jackson
    final ObjectMapper mapper = new ObjectMapper();
    final String json = mapper.writeValueAsString(params._body());

    assertThat(json)
        .describedAs("ResponseCreateParams should serialize to empty JSON")
        .isEqualTo(
            "{\"input\":\"What is the capital of France?\",\"model\":\"gpt-4o\",\"temperature\":0.7,\"valid\":true}");
  }

  @Test
  void testResponseCreateParamsSerializationWithOpenAiMapper() throws Exception {
    final ResponseCreateParams params =
        ResponseCreateParams.builder()
            .model(ChatModel.GPT_4O)
            .input("What is the capital of France?")
            .temperature(0.7)
            .build();

    // Use OpenAI SDK's mapper
    final ObjectMapper mapper = ObjectMappers.jsonMapper();
    final String json = mapper.writeValueAsString(params._body());

    System.out.println("Serialized JSON: " + json);

    // Now it should NOT be empty!
    assertThat(json).isNotEqualTo("{}");
    assertThat(json).contains("gpt-4o");
    assertThat(json).contains("What is the capital of France?");
  }
}
