package com.sap.ai.sdk.app.services;

import static com.sap.ai.sdk.foundationmodels.openai.OpenAiModel.GPT_5;

import com.openai.client.OpenAIClient;
import com.openai.core.http.QueryParams;
import com.openai.core.http.StreamResponse;
import com.openai.models.ChatModel;
import com.openai.models.chat.completions.ChatCompletionChunk;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;
import com.openai.models.responses.ResponseStreamEvent;
import com.sap.ai.sdk.foundationmodels.openai.v1.AiCoreOpenAiClient;
import javax.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

/** Service class for the OpenAI Responses API */
@Service
@Slf4j
public class OpenAiV1Service {

  private static final OpenAIClient client = AiCoreOpenAiClient.forModel(GPT_5, "ai-sdk-java-e2e");

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
    return client.responses().create(params);
  }

  /**
   * Create a streaming response using the Responses API
   *
   * @param input the input text to send to the model
   * @return the streaming response object from the Responses API
   */
  @Nonnull
  public StreamResponse<ResponseStreamEvent> createStreamingResponse(@Nonnull final String input) {
    val params =
        ResponseCreateParams.builder().input(input).model(ChatModel.GPT_5).store(false).build();
    return client.responses().createStreaming(params);
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
            .addUserMessage("Say this is a test")
            .model(ChatModel.GPT_5)
            .additionalQueryParams(QueryParams.builder().put("api-version", "2023-05-15").build())
            .build();
    return client.chat().completions().createStreaming(params);
  }
}
