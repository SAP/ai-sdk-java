package com.sap.ai.sdk.app.services;

import static com.sap.ai.sdk.foundationmodels.openai.OpenAiModel.GPT_5;

import com.openai.core.http.StreamResponse;
import com.openai.models.ChatModel;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCancelParams;
import com.openai.models.responses.ResponseCreateParams;
import com.openai.models.responses.ResponseDeleteParams;
import com.openai.models.responses.ResponseRetrieveParams;
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
      AiCoreOpenAiClient.forModel(GPT_5, "ai-sdk-java-e2e").responses();

  /**
   * Create a simple non-persistent response using the Responses API
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
   * Create a non-persistent streaming response using the Responses API
   *
   * @param input the input text to send to the model
   * @return the streaming response object from the Responses API
   */
  @Nonnull
  public StreamResponse<ResponseStreamEvent> createStreamingResponse(@Nonnull final String input) {
    val params =
        ResponseCreateParams.builder().input(input).model(ChatModel.GPT_5).store(false).build();
    return RESPONSE_CLIENT.createStreaming(params);
  }

  /**
   * Create a persistent response so it can be retrieved, cancelled, or deleted later.
   *
   * @param input the input text to send to the model
   * @param background if true, the response runs asynchronously
   * @return the response object from the Responses API
   */
  @Nonnull
  public Response createPersistentResponse(@Nonnull final String input, final boolean background) {
    val params =
        ResponseCreateParams.builder().input(input).store(true).background(background).build();
    return RESPONSE_CLIENT.create(params);
  }

  /**
   * Retrieve a previously created response by its id.
   *
   * @param responseId the id returned by the created persistent response call
   * @return the stored response
   */
  @Nonnull
  public Response retrieveResponse(@Nonnull final String responseId) {
    val params = ResponseRetrieveParams.builder().responseId(responseId).build();
    return RESPONSE_CLIENT.retrieve(params);
  }

  /**
   * Cancel a background response by its id.
   *
   * @param responseId the id of the background response to cancel
   * @return the response with the new status.
   */
  @Nonnull
  public Response cancelResponse(@Nonnull final String responseId) {
    val params = ResponseCancelParams.builder().responseId(responseId).build();
    return RESPONSE_CLIENT.cancel(params);
  }

  /**
   * Delete a stored response by its id.
   *
   * @param responseId the id of the response to delete
   */
  public void deleteResponse(@Nonnull final String responseId) {
    val params = ResponseDeleteParams.builder().responseId(responseId).build();
    RESPONSE_CLIENT.delete(params);
  }
}
