package com.sap.ai.sdk.app.services;

import static com.sap.ai.sdk.foundationmodels.openai.OpenAiModel.GPT_5;

import com.openai.client.OpenAIClient;
import com.openai.core.http.QueryParams;
import com.openai.core.http.StreamResponse;
import com.openai.models.ChatModel;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionChunk;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;
import com.openai.models.responses.ResponseStreamEvent;
import com.sap.ai.sdk.foundationmodels.openai.AiCoreOpenAiClient;
import javax.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

/** Service class for the OpenAI Responses API */
@Service
@Slf4j
public class AiCoreOpenAiService {

  private static final OpenAIClient CLIENT = AiCoreOpenAiClient.forModel(GPT_5, "ai-sdk-java-e2e");

  /**
   * Create a simple response using the Responses API
   *
   * @param input the input text to send to the model
   * @return the response object from the Responses API
   */
  @Nonnull
  public Response createResponse(@Nonnull final String input) {
    val params =
        ResponseCreateParams.builder().input(input).model(ChatModel.GPT_5).store(false).build();
    return CLIENT.responses().create(params);
  }

  /**
   * Create a response and immediately retrieve it using the Responses API. This demonstrates the
   * two-step process of creating and then fetching a response.
   *
   * @param input the input text to send to the model
   * @return the retrieved response object from the Responses API
   */
  @Nonnull
  public Response retrieveResponse(@Nonnull final String input) {
    // Create a non-persistent response with store=false
    val params = ResponseCreateParams.builder().input(input).model(ChatModel.GPT_5).build();
    val createResponse = CLIENT.responses().create(params);
    return CLIENT.responses().retrieve(createResponse.id());
  }

  /**
   * Create a streaming response using the Responses API
   *
   * @param input the input text to send to the model
   * @return the streaming response object from the Responses API
   */
  @Nonnull
  public StreamResponse<ResponseStreamEvent> createStreamingResponse(@Nonnull final String input) {
    // Create a non-persistent response with store=false
    val params =
        ResponseCreateParams.builder().input(input).model(ChatModel.GPT_5).store(false).build();
    return CLIENT.responses().createStreaming(params);
  }

  /**
   * Create a chat completion using the Chat Completions API. Note: This uses the legacy API version
   * format via query parameters.
   *
   * @param input the input text to send to the model
   * @return the chat completion response from the Chat Completions API
   */
  @Nonnull
  public ChatCompletion createChatCompletion(@Nonnull final String input) {
    val params =
        ChatCompletionCreateParams.builder()
            .addUserMessage(input)
            .model(ChatModel.GPT_5)
            .additionalQueryParams(QueryParams.builder().put("api-version", "2024-02-01").build())
            .build();
    return CLIENT.chat().completions().create(params);
  }

  /**
   * Create a streaming chat completion using the Chat Completions API
   *
   * @param input the input text to send to the model
   * @return the streaming chat completion response from the Chat Completions API
   */
  @Nonnull
  public StreamResponse<ChatCompletionChunk> createStreamingChatCompletion(
      @Nonnull final String input) {
    val params =
        ChatCompletionCreateParams.builder()
            .addUserMessage(input)
            .model(ChatModel.GPT_5)
            .additionalQueryParams(QueryParams.builder().put("api-version", "2024-02-01").build())
            .build();
    return CLIENT.chat().completions().createStreaming(params);
  }
}
