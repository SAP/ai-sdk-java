package com.sap.ai.sdk.orchestration;

import static com.sap.ai.sdk.orchestration.OrchestrationJacksonConfiguration.getOrchestrationObjectMapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.annotations.Beta;
import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.orchestration.model.CompletionPostRequest;
import com.sap.ai.sdk.orchestration.model.CompletionPostResponse;
import com.sap.ai.sdk.orchestration.model.CompletionRequestConfiguration;
import com.sap.ai.sdk.orchestration.model.EmbeddingsPostRequest;
import com.sap.ai.sdk.orchestration.model.EmbeddingsPostResponse;
import com.sap.ai.sdk.orchestration.model.GlobalStreamOptions;
import com.sap.ai.sdk.orchestration.model.ModuleConfigs;
import com.sap.ai.sdk.orchestration.model.OrchestrationConfig;
import com.sap.cloud.sdk.cloudplatform.connectivity.Header;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;
import io.vavr.control.Try;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

/** Client to execute requests to the orchestration service. */
@Slf4j
@RequiredArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class OrchestrationClient {
  private static final String DEFAULT_SCENARIO = "orchestration";
  private static final String COMPLETION_ENDPOINT = "/v2/completion";

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
  @Beta
  public OrchestrationClient(@Nonnull final HttpDestination destination) {
    this.executor = new OrchestrationHttpExecutor(() -> destination);
  }

  /**
   * Convert the given prompt and config into a low-level request data object. The data object
   * allows for further customization before sending the request.
   *
   * @param prompt The {@link OrchestrationPrompt} to generate a completion for.
   * @param config The {@link OrchestrationConfig } configuration to use for the completion.
   * @return The low-level request data object to send to orchestration.
   */
  @Nonnull
  public static CompletionRequestConfiguration toCompletionPostRequest(
      @Nonnull final OrchestrationPrompt prompt, @Nonnull final OrchestrationModuleConfig config) {
    return ConfigToRequestTransformer.toCompletionPostRequest(prompt, config);
  }

  /**
   * Generate a completion for the given prompt.
   *
   * @param prompt The {@link OrchestrationPrompt} to send to orchestration.
   * @param config The {@link ModuleConfigs} configuration to use for the completion.
   * @return the completion output
   * @throws OrchestrationClientException if the request fails.
   */
  @Nonnull
  public OrchestrationChatResponse chatCompletion(
      @Nonnull final OrchestrationPrompt prompt, @Nonnull final OrchestrationModuleConfig config)
      throws OrchestrationClientException {

    val request = toCompletionPostRequest(prompt, config);
    val response = executeRequest(request);
    return new OrchestrationChatResponse(response);
  }

  /**
   * Generate a completion for the given prompt.
   *
   * @param prompt a text message.
   * @param config the configuration to use
   * @return a stream of message deltas
   * @throws OrchestrationClientException if the request fails or if the finish reason is
   *     content_filter
   * @since 1.1.0
   */
  @Nonnull
  public Stream<String> streamChatCompletion(
      @Nonnull final OrchestrationPrompt prompt, @Nonnull final OrchestrationModuleConfig config)
      throws OrchestrationClientException {

    val request = toCompletionPostRequest(prompt, config);
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
  @Nonnull
  public CompletionPostResponse executeRequest(@Nonnull final CompletionPostRequest request)
      throws OrchestrationClientException {
    return executor.execute(
        COMPLETION_ENDPOINT, request, CompletionPostResponse.class, customHeaders);
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
  @Beta
  @Nonnull
  public OrchestrationClient withHeader(@Nonnull final String key, @Nonnull final String value) {
    final var newClient = new OrchestrationClient(this.executor);
    newClient.customHeaders.addAll(this.customHeaders);
    newClient.customHeaders.add(new Header(key, value));
    return newClient;
  }
}
