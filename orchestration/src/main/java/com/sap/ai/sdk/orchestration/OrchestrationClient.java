package com.sap.ai.sdk.orchestration;

import static com.sap.ai.sdk.orchestration.OrchestrationJacksonConfiguration.getOrchestrationObjectMapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.annotations.Beta;
import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.beta.realtime.sessions.Session;
import com.openai.models.realtime.SessionUpdateEvent;
import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.orchestration.model.CompletionPostRequest;
import com.sap.ai.sdk.orchestration.model.CompletionPostResponse;
import com.sap.ai.sdk.orchestration.model.CompletionRequestConfiguration;
import com.sap.ai.sdk.orchestration.model.EmbeddingsPostRequest;
import com.sap.ai.sdk.orchestration.model.EmbeddingsPostResponse;
import com.sap.ai.sdk.orchestration.model.GlobalStreamOptions;
import com.sap.ai.sdk.orchestration.model.OrchestrationConfig;
import com.sap.cloud.sdk.cloudplatform.connectivity.Header;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;
import io.vavr.collection.Array;
import io.vavr.control.Try;

import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;

/** Client to execute requests to the orchestration service. */
@Slf4j
@RequiredArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class OrchestrationClient {
  private static final String DEFAULT_SCENARIO = "orchestration";
  private static final String COMPLETION_ENDPOINT = "/v2/completion";
  private static final String REALTIME_API_PATH =
          "wss://realtime.ai.intprod-eu12.eu-central-1.aws.ml.hana.ondemand.com/v2/inference/deployments/ddb5a959fdb51ffe/v1/realtime";

  static final ObjectMapper JACKSON = getOrchestrationObjectMapper();


  private final OrchestrationHttpExecutor executor;
  private final List<Header> customHeaders = new ArrayList<>();

  /** Default constructor. */
  public OrchestrationClient() {
    final Supplier<HttpDestination> destinationSupplier =
        () -> new AiCoreService().getInferenceDestination().forScenario(DEFAULT_SCENARIO);
    this.executor = new OrchestrationHttpExecutor(destinationSupplier);
  }

  /**
   * Constructor with a custom destination, allowing for a custom resource group or otherwise custom
   * destination. The destination needs to be configured with a URL pointing to an orchestration
   * service deployment. Typically, such a destination should be obtained using {@link
   * AiCoreService#getInferenceDestination(String)}.
   *
   * <p>Example:
   *
   * <pre>{@code
   * new OrchestrationClient(new AiCoreService().getInferenceDestination("custom-rg").forScenario("orchestration"));
   * }</pre>
   *
   * @param destination The specific {@link HttpDestination} to use.
   * @see AiCoreService#getInferenceDestination(String)
   */
  public OrchestrationClient(@Nonnull final HttpDestination destination) {
    this.executor = new OrchestrationHttpExecutor(() -> destination);
  }

  /**
   * Convert the given prompt and config into a low-level request data object. The data object
   * allows for further customization before sending the request.
   *
   * @param prompt The {@link OrchestrationPrompt} to generate a completion for.
   * @param config The {@link OrchestrationConfig } configuration to use for the completion.
   * @param fallbackConfigs Fallback configurations to use.
   * @return The low-level request data object to send to orchestration.
   */
  @Nonnull
  public static CompletionRequestConfiguration toCompletionPostRequest(
      @Nonnull final OrchestrationPrompt prompt,
      @Nonnull final OrchestrationModuleConfig config,
      @Nonnull final OrchestrationModuleConfig... fallbackConfigs) {
    return ConfigToRequestTransformer.toCompletionPostRequest(prompt, config, fallbackConfigs);
  }

  /**
   * Generate a completion for the given prompt.
   *
   * @param prompt The {@link OrchestrationPrompt} to send to orchestration.
   * @param config the configuration to use
   * @param fallbackConfigs fallback configurations
   * @return the completion output
   * @throws OrchestrationClientException if the request fails.
   */
  @Nonnull
  public OrchestrationChatResponse chatCompletion(
      @Nonnull final OrchestrationPrompt prompt,
      @Nonnull final OrchestrationModuleConfig config,
      @Nonnull final OrchestrationModuleConfig... fallbackConfigs)
      throws OrchestrationClientException {
    val request = toCompletionPostRequest(prompt, config, fallbackConfigs);
    val response = executeRequest(request);
    return new OrchestrationChatResponse(response);
  }

    /**
     * Voices input text into output audio
     *
     * @param input text to voice
     * @return byte chunks output stream in PCM16 format, 16-bit, mono, 24000 Hz, little-endian
     */
    public Stream<byte[]> speech(String input) {

        var deploymentID = "";
        final List<byte[]> result = new ArrayList<>();
        try {
            HttpClient client = HttpClient.newHttpClient(); // TODO: fix local setup and use autocloseable
      WebSocket ws =
          client
              .newWebSocketBuilder()
              .header(
                  "Authorization",
                  "Bearer .......")
              .header("AI-Resource-Group", "default")
              .buildAsync(
                  URI.create(REALTIME_API_PATH + deploymentID),
                  new WebSocket.Listener() {

                    @Override
                    public void onOpen(WebSocket webSocket) {
                      log.warn("Websocket connection opened");
                      String sessionUpdateJson =
                          """
                            {
                              "type": "session.update",
                              "session": {
                                "type": "realtime",
                                "output_modalities": ["audio"],
                                "instructions": "you are a speaker and your role is to read (produce audio) of the user input speech. voice user text input",
                                "audio": {
                                  "input": { "format": { "type": "audio/pcm", "rate": 24000 } },
                                  "output": { "format": { "type": "audio/pcm", "rate": 24000 }, "voice": "alloy" }
                                }
                              }
                            }
                            """;

                      // String sessionUpdateJson = "{\"type\": \"session.update\", \"session\":
                      // {\"modalities\": [\"audio\", \"text\"]}}";
                      webSocket.sendText(sessionUpdateJson, true);
                      webSocket.request(1);

                      String textMessage =
                          """
{
  "type": "conversation.item.create",
  "item": {
    "type": "message",
    "role": "system",
    "content": [
      {
        "type": "input_text",
        "text": "you are a speaker and your role is to read (produce audio) of the user input speech. voice user text input"
      }
    ]
  }
}
""";
                              //.replaceFirst("%VALUE%", input); // TODO: sanitize or/and escape input
                      log.warn("Sending message to websocket: {}", textMessage);
                      webSocket.sendText(textMessage, true);
                      webSocket.request(1);

                      String voiceMessage =
                              """
    {
      "type": "conversation.item.create",
      "item": {
        "type": "message",
        "role": "user",
        "content": [
          {
            "type": "input_text",
            "text": "Eat more of these fresh french baguettes"
          }
        ]
      }
    }
    """;

                      webSocket.sendText(voiceMessage, true);
                      webSocket.request(1);




                      String askForResponse = "{ \"type\": \"response.create\" }";
                      webSocket.sendText(askForResponse, true);

                      webSocket.request(80);
                    }

                    @Override
                    public CompletionStage<?> onText(
                        WebSocket webSocket, CharSequence data, boolean last) {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        log.warn("text received: {}", data);
                      final JsonNode event;
                      try {
                        event = JACKSON.readTree(data.toString());
                      } catch (JsonProcessingException e) {
                        throw new OrchestrationClientException(
                            "Error parsing JSON response from speech API", e);
                      }
                      String type = event.get("type").asText();
                      if ("response.output_audio.delta".equals(type)) {
                        String base64Audio = event.get("delta").asText();
                        byte[] audio = Base64.getDecoder().decode(base64Audio);
                        result.add(audio);
                      } else if ("response.audio_transcript.delta".equals(type)) {
                        log.warn("RECEIVED TRANSCRIPT: {}", event.get("delta").asText());
                      } else if ("response.output_audio.done".equals(type)) {
                        // TODO: we should not wait last ack, we should return stream immediately,
                        // this requires
                        // TODO: thread management, factory and thread safety of all structs
                        webSocket.sendClose(1000, "done");
                        return null;
                      } else {
                        log.warn("Unhandled event type: {}", data);
                      }
                      return CompletableFuture.completedStage(null);
                    }
                  })
              .join();
            Thread.sleep(25000);
            return result.stream();
        } catch (Exception e) {
            //log.error(e.getMessage(), e);
            throw new OrchestrationClientException("Failed to call realtime API", e);
        }
    }

  /**
   * Generate a completion for the given prompt.
   *
   * @param prompt a text message.
   * @param config the configuration to use
   * @param fallbackConfigs fallback configurations
   * @return a stream of message deltas
   * @throws OrchestrationClientException if the request fails or if the finish reason is
   *     content_filter
   * @since 1.1.0
   */
  @Nonnull
  public Stream<String> streamChatCompletion(
      @Nonnull final OrchestrationPrompt prompt,
      @Nonnull final OrchestrationModuleConfig config,
      @Nonnull final OrchestrationModuleConfig... fallbackConfigs)
      throws OrchestrationClientException {

    val request = toCompletionPostRequest(prompt, config, fallbackConfigs);
    return streamChatCompletionDeltas(request)
        .peek(OrchestrationClient::throwOnContentFilter)
        .map(OrchestrationChatCompletionDelta::getDeltaContent);
  }

  private static void throwOnContentFilter(@Nonnull final OrchestrationChatCompletionDelta delta)
      throws OrchestrationFilterException.Output {
    final String finishReason = delta.getFinishReason();
    if (finishReason != null && finishReason.equals("content_filter")) {
      final var filterDetails =
          Try.of(() -> getOutputFilteringChoices(delta)).getOrElseGet(e -> Map.of());
      final var message = "Content filter filtered the output.";
      throw new OrchestrationFilterException.Output(message).setFilterDetails(filterDetails);
    }
  }

  @SuppressWarnings("unchecked")
  private static Map<String, Object> getOutputFilteringChoices(
      @Nonnull final OrchestrationChatCompletionDelta delta) {
    final var f = delta.getIntermediateResults().getOutputFiltering();
    return ((List<Map<String, Object>>) ((Map<String, Object>) f.getData()).get("choices")).get(0);
  }

  /**
   * Serializes the given request, executes it and deserializes the response.
   *
   * <p>Override this method to customize the request execution. For example, to modify the request
   * object before it is sent, use:
   *
   * <pre>{@code
   * @Override
   * protected CompletionPostResponse executeRequest(@Nonnull CompletionPostRequest request) {
   *   request.setCustomField("myField", "myValue");
   *   return super.executeRequest(request);
   * }
   * }</pre>
   *
   * <p>Alternatively, you can call this method directly with a fully custom request object.
   *
   * @param request The request data object to send to orchestration.
   * @return The response data object from orchestration.
   * @throws OrchestrationClientException If the request fails.
   */
  @SuppressWarnings("PMD.PublicApiExposesModelType")
  @Nonnull
  public CompletionPostResponse executeRequest(@Nonnull final CompletionPostRequest request)
      throws OrchestrationClientException {
    return executor.execute(
        COMPLETION_ENDPOINT, request, CompletionPostResponse.class, customHeaders);
  }

  /**
   * Generate a completion using a referenced Orchestration config.
   *
   * @param reference A reference to an Orchestration config stored in prompt registry
   * @return The completion output
   * @since 1.15.0
   */
  @Nonnull
  public OrchestrationChatResponse chatCompletionUsingReference(
      @Nonnull final OrchestrationConfigReference reference) {
    val request = ConfigToRequestTransformer.fromReferenceToCompletionPostRequest(reference);
    val response = executeRequest(request);
    return new OrchestrationChatResponse(response);
  }

  /**
   * Perform a request to the orchestration service using a module configuration provided as JSON
   * string. This can be useful when building a configuration in the AI Launchpad UI and exporting
   * it as JSON. Furthermore, this allows for using features that are not yet supported natively by
   * the API.
   *
   * <p><b>NOTE:</b> This method does not support streaming.
   *
   * @param prompt The input parameters and optionally message history to use for prompt execution.
   * @param moduleConfig The module configuration in JSON format.
   * @return The completion response.
   * @throws OrchestrationClientException If the request fails.
   */
  @Beta
  @Nonnull
  public OrchestrationChatResponse executeRequestFromJsonModuleConfig(
      @Nonnull final OrchestrationPrompt prompt, @Nonnull final String moduleConfig)
      throws OrchestrationClientException {
    if (!prompt.getMessages().isEmpty()) {
      throw new IllegalArgumentException(
          "Prompt must not contain any messages when using a JSON module configuration, as the template is already defined in the JSON.");
    }

    final ObjectNode requestJson = JACKSON.createObjectNode();
    final var chatMessageHistory =
        prompt.getMessagesHistory().stream().map(Message::createChatMessage).toList();
    requestJson.set("messages_history", JACKSON.valueToTree(chatMessageHistory));
    requestJson.set("input_params", JACKSON.valueToTree(prompt.getTemplateParameters()));

    final JsonNode moduleConfigJson;
    try {
      moduleConfigJson = JACKSON.readTree(moduleConfig);
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException(
          "The provided module configuration is not valid JSON: " + moduleConfig, e);
    }
    requestJson.set("orchestration_config", moduleConfigJson);

    return new OrchestrationChatResponse(
        executor.execute(
            COMPLETION_ENDPOINT, requestJson, CompletionPostResponse.class, customHeaders));
  }

  /**
   * Generate a completion for the given prompt.
   *
   * @param request the prompt, including messages and other parameters.
   * @return A stream of chat completion delta elements.
   * @throws OrchestrationClientException if the request fails
   * @since 1.1.0
   */
  @SuppressWarnings("PMD.PublicApiExposesModelType")
  @Nonnull
  public Stream<OrchestrationChatCompletionDelta> streamChatCompletionDeltas(
      @Nonnull final CompletionRequestConfiguration request) throws OrchestrationClientException {
    val config = request.getConfig();
    val stream = config.getStream();
    if (stream == null) {
      config.setStream(GlobalStreamOptions.create().enabled(true).delimiters(null));
    } else {
      stream.enabled(true);
    }

    return executor.stream(COMPLETION_ENDPOINT, request, customHeaders);
  }

  /**
   * Generate embeddings for a {@code OrchestrationEmbeddingRequest} request.
   *
   * @param request the request containing the input text and other parameters.
   * @return the response containing the embeddings.
   * @throws OrchestrationClientException if the request fails
   * @since 1.12.0
   */
  @Nonnull
  public OrchestrationEmbeddingResponse embed(@Nonnull final OrchestrationEmbeddingRequest request)
      throws OrchestrationClientException {
    final var response = embed(request.createEmbeddingsPostRequest());
    return new OrchestrationEmbeddingResponse(response);
  }

  /**
   * Generates embeddings using the low-level API request.
   *
   * <p>This method provides direct access to the underlying API for advanced use cases. For most
   * scenarios, prefer {@link #embed(OrchestrationEmbeddingRequest)}.
   *
   * @param request the low-level API request
   * @return the low level response object
   * @throws OrchestrationClientException if the request fails
   * @since 1.12.0
   * @see #embed(OrchestrationEmbeddingRequest)
   */
  @SuppressWarnings("PMD.PublicApiExposesModelType")
  @Nonnull
  public EmbeddingsPostResponse embed(@Nonnull final EmbeddingsPostRequest request)
      throws OrchestrationClientException {
    return executor.execute("/v2/embeddings", request, EmbeddingsPostResponse.class, customHeaders);
  }

  /**
   * Create a new orchestration client with a custom header added to every call made with this
   * client
   *
   * @param key the key of the custom header to add
   * @param value the value of the custom header to add
   * @return a new client.
   * @since 1.11.0
   */
  @Nonnull
  public OrchestrationClient withHeader(@Nonnull final String key, @Nonnull final String value) {
    final var newClient = new OrchestrationClient(this.executor);
    newClient.customHeaders.addAll(this.customHeaders);
    newClient.customHeaders.add(new Header(key, value));
    return newClient;
  }

  /**
   * Create a new orchestration client for the given resource group and scenario.
   *
   * @param resourceGroup the resource group
   * @param scenario the scenario
   * @return a new client configured with the resolved destination.
   */
  @Nonnull
  public OrchestrationClient withResourceGroup(
      @Nonnull final String resourceGroup, @Nonnull final String scenario) {
    final var destination =
        new AiCoreService().getInferenceDestination(resourceGroup).forScenario(scenario);
    return new OrchestrationClient(destination);
  }
}
