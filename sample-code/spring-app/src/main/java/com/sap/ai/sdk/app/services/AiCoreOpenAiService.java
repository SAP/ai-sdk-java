package com.sap.ai.sdk.app.services;

import static com.sap.ai.sdk.foundationmodels.openai.OpenAiModel.GPT_5;

import com.openai.core.http.StreamResponse;
import com.openai.models.ChatModel;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;
import com.openai.models.responses.ResponseStreamEvent;
import com.openai.services.blocking.ResponseService;
import com.sap.ai.sdk.foundationmodels.openai.AiCoreOpenAiClient;
import javax.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

/** Service class for the OpenAI Responses API */
@Service
@Slf4j
public class AiCoreOpenAiService {

  private static final ResponseService RESPONSE_CLIENT =
      AiCoreOpenAiClient.responses(GPT_5, "ai-sdk-java-e2e");

  /**
   * Create a simple response using the Responses API
   *
   * @param input the input text to send to the model
   * @return the response object from the Responses API
   */
  @Nonnull
  public Response createResponse(@Nonnull final String input) {
    val params = ResponseCreateParams.builder().input(input).store(false).build();
    return RESPONSE_CLIENT.create(params);
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
    val params = ResponseCreateParams.builder().input(input).build();
    val createResponse = RESPONSE_CLIENT.create(params);
    return RESPONSE_CLIENT.retrieve(createResponse.id());
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
    return RESPONSE_CLIENT.createStreaming(params);
  }
}
