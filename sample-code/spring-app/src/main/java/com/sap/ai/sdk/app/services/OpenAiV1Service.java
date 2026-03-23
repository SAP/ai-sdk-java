package com.sap.ai.sdk.app.services;

import static com.sap.ai.sdk.foundationmodels.openai.OpenAiModel.GPT_5;

import com.openai.client.OpenAIClient;
import com.openai.models.ChatModel;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;
import com.sap.ai.sdk.foundationmodels.openai.responses.AiCoreOpenAiClient;
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
}
