package com.sap.ai.sdk.app.services;

import static com.sap.ai.sdk.foundationmodels.openai.OpenAiModel.GPT_5;

import com.openai.core.http.StreamResponse;
import com.openai.models.ChatModel;
import com.openai.models.realtime.calls.CallAcceptParams;
import com.openai.models.realtime.calls.CallReferParams;
import com.openai.models.realtime.clientsecrets.ClientSecretCreateResponse;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;
import com.openai.models.responses.ResponseStreamEvent;
import com.openai.services.blocking.RealtimeService;
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

  private static final RealtimeService REALTIME_CLIENT =
      AiCoreOpenAiClient.forModel(GPT_5, "ai-sdk-java-e2e").realtime();

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
   * Create a short-lived client secret for a Realtime API session.
   *
   * @return the client secret response containing the ephemeral token
   */
  @Nonnull
  public ClientSecretCreateResponse createRealtimeClientSecret() {
    return REALTIME_CLIENT.clientSecrets().create();
  }

  /**
   * Accept an incoming SIP call and configure the Realtime API session that will handle it.
   *
   * @param callId the ID of the incoming call to accept
   * @param params the parameters to configure the Realtime session
   */
  public void acceptCall(@Nonnull final String callId, @Nonnull final CallAcceptParams params) {
    REALTIME_CLIENT.calls().accept(callId, params);
  }

  /**
   * End an active Realtime API call.
   *
   * @param callId the ID of the active call to hang up
   */
  public void hangupCall(@Nonnull final String callId) {
    REALTIME_CLIENT.calls().hangup(callId);
  }

  /**
   * Transfer an active SIP call to a new destination.
   *
   * @param callId the ID of the active call to transfer
   * @param params the refer parameters including the transfer target
   */
  public void referCall(@Nonnull final String callId, @Nonnull final CallReferParams params) {
    REALTIME_CLIENT.calls().refer(callId, params);
  }

  /**
   * Decline an incoming SIP call.
   *
   * @param callId the ID of the incoming call to reject
   */
  public void rejectCall(@Nonnull final String callId) {
    REALTIME_CLIENT.calls().reject(callId);
  }
}
