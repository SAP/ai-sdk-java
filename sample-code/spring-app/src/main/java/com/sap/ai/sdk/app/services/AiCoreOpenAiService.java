package com.sap.ai.sdk.app.services;

import static com.sap.ai.sdk.foundationmodels.openai.OpenAiModel.GPT_5;

import com.openai.core.JsonValue;
import com.openai.core.http.StreamResponse;
import com.openai.models.ChatModel;
import com.openai.models.Reasoning;
import com.openai.models.ReasoningEffort;
import com.openai.models.responses.FunctionTool;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCancelParams;
import com.openai.models.responses.ResponseCreateParams;
import com.openai.models.responses.ResponseDeleteParams;
import com.openai.models.responses.ResponseFormatTextJsonSchemaConfig;
import com.openai.models.responses.ResponseInputItem;
import com.openai.models.responses.ResponseRetrieveParams;
import com.openai.models.responses.ResponseStatus;
import com.openai.models.responses.ResponseStreamEvent;
import com.openai.models.responses.ResponseTextConfig;
import com.openai.models.responses.ToolChoiceOptions;
import com.openai.services.blocking.ResponseService;
import com.sap.ai.sdk.foundationmodels.openai.AiCoreOpenAiClient;
import java.util.List;
import java.util.Map;
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

  /**
   * Create a background response and poll until it reaches a terminal status.
   *
   * @param input the prompt to run asynchronously
   * @return the completed (or failed) response
   */
  @Nonnull
  public Response createBackgroundResponseAndPoll(@Nonnull final String input)
      throws InterruptedException {
    var response =
        RESPONSE_CLIENT.create(
            ResponseCreateParams.builder().input(input).store(true).background(true).build());
    while (response.status().filter(ResponseStatus.QUEUED::equals).isPresent()
        || response.status().filter(ResponseStatus.IN_PROGRESS::equals).isPresent()) {
      Thread.sleep(2000);
      response = retrieveResponse(response.id());
    }
    return response;
  }

  /**
   * Create a multi-turn follow-up response using a previous response id. *
   *
   * @param input the follow-up question
   * @param previousResponseId the id of the prior response to continue from
   * @return the response object
   */
  @Nonnull
  public Response createMultiTurnResponse(
      @Nonnull final String input, @Nonnull final String previousResponseId) {
    val params =
        ResponseCreateParams.builder()
            .input(input)
            .previousResponseId(previousResponseId)
            .store(true)
            .build();
    return RESPONSE_CLIENT.create(params);
  }

  /**
   * Create a response with tool calling enabled.
   *
   * @param input the user question that may trigger a tool call
   * @return the response object
   */
  @Nonnull
  public Response createResponseWithTools(@Nonnull final String input) {
    val parameters =
        FunctionTool.Parameters.builder()
            .putAdditionalProperty("type", JsonValue.from("object"))
            .putAdditionalProperty(
                "properties",
                JsonValue.from(
                    Map.of(
                        "location",
                        Map.of("type", "string", "description", "City name, e.g. Berlin"),
                        "unit",
                        Map.of(
                            "type",
                            "string",
                            "enum",
                            List.of("celsius", "fahrenheit"),
                            "description",
                            "Temperature unit"))))
            .putAdditionalProperty("required", JsonValue.from(List.of("location", "unit")))
            .putAdditionalProperty("additionalProperties", JsonValue.from(false))
            .build();
    val tool =
        FunctionTool.builder()
            .name("get_weather")
            .description("Retrieves current weather for a given location.")
            .parameters(parameters)
            .strict(true)
            .build();
    val params =
        ResponseCreateParams.builder()
            .input(input)
            .addTool(tool)
            .toolChoice(ToolChoiceOptions.AUTO)
            .store(false)
            .build();
    return RESPONSE_CLIENT.create(params);
  }

  /**
   * Create a response with a specific reasoning effort level.
   *
   * @param input the prompt to send
   * @param effort the reasoning effort level
   * @return the response object
   */
  @Nonnull
  public Response createResponseWithReasoning(
      @Nonnull final String input, @Nonnull final ReasoningEffort effort) {
    val reasoning = Reasoning.builder().effort(effort).summary(Reasoning.Summary.CONCISE).build();

    val params =
        ResponseCreateParams.builder().input(input).reasoning(reasoning).store(false).build();
    return RESPONSE_CLIENT.create(params);
  }

  /**
   * Create a response with structured JSON output conforming to a given schema.
   *
   * @param input the extraction prompt
   * @return the response object whose output text is valid JSON matching the schema
   */
  @Nonnull
  public Response createStructuredResponse(@Nonnull final String input) {
    val schema =
        ResponseFormatTextJsonSchemaConfig.Schema.builder()
            .putAdditionalProperty("type", JsonValue.from("object"))
            .putAdditionalProperty(
                "properties",
                JsonValue.from(
                    Map.of("name", Map.of("type", "string"), "age", Map.of("type", "integer"))))
            .putAdditionalProperty("required", JsonValue.from(List.of("name", "age")))
            .putAdditionalProperty("additionalProperties", JsonValue.from(false))
            .build();
    val format =
        ResponseFormatTextJsonSchemaConfig.builder()
            .name("person")
            .schema(schema)
            .strict(true)
            .build();
    val textConfig = ResponseTextConfig.builder().format(format).build();
    val params = ResponseCreateParams.builder().input(input).text(textConfig).store(false).build();
    return RESPONSE_CLIENT.create(params);
  }

  /**
   * Create a stateless multi-turn response by passing the full conversation history as input.
   *
   * @param messages the full conversation history
   * @return the response object
   */
  @Nonnull
  public Response createStatelessMultiTurnResponse(
      @Nonnull final List<ResponseInputItem> messages) {
    val params = ResponseCreateParams.builder().inputOfResponse(messages).store(false).build();
    return RESPONSE_CLIENT.create(params);
  }

  /**
   * Create a response with truncation enabled so long conversations are automatically trimmed.
   *
   * @param input the follow-up prompt
   * @param previousResponseId the id of the prior response
   * @return the response object
   */
  @SuppressWarnings("deprecation")
  @Nonnull
  public Response createResponseWithTruncation(
      @Nonnull final String input, @Nonnull final String previousResponseId) {
    val truncate = ResponseCreateParams.Truncation.AUTO;
    val params =
        ResponseCreateParams.builder()
            .input(input)
            .previousResponseId(previousResponseId)
            .truncation(truncate)
            .store(true)
            .build();
    return RESPONSE_CLIENT.create(params);
  }

  /**
   * Create a response using a prompt cache key to maximize cache reuse across calls.
   *
   * @param input the prompt
   * @param cacheKey an arbitrary cache key shared across related requests
   * @return the response object
   */
  @Nonnull
  public Response createCachedResponse(
      @Nonnull final String input, @Nonnull final String cacheKey) {
    val params =
        ResponseCreateParams.builder().input(input).promptCacheKey(cacheKey).store(false).build();
    return RESPONSE_CLIENT.create(params);
  }
}
