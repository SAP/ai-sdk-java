package com.sap.ai.sdk.orchestration;

import static com.sap.ai.sdk.orchestration.OrchestrationJacksonConfiguration.getOrchestrationObjectMapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.orchestration.model.CompletionPostRequest;
import com.sap.ai.sdk.orchestration.model.CompletionPostResponse;
import com.sap.ai.sdk.orchestration.model.CompletionRequestConfiguration;
import com.sap.ai.sdk.orchestration.model.CompletionRequestConfigurationReferenceById;
import com.sap.ai.sdk.orchestration.model.CompletionRequestConfigurationReferenceByNameScenarioVersion;
import com.sap.ai.sdk.orchestration.model.EmbeddingsPostRequest;
import com.sap.ai.sdk.orchestration.model.EmbeddingsPostResponse;
import com.sap.ai.sdk.orchestration.model.GlobalStreamOptions;
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
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

/**
 * Client to execute requests to the orchestration service.
 *
 * <pre>{@code
 * chatCompletion()
 * │
 * ├── config(config, fallbackConfigs...)
 * │   │
 * │   └── promptTemplate(templateOrReference)
 * │       ├── execute()
 * │       ├── executeStream()
 * │       └── executeStreamDeltas()
 * │
 * ├── configReference(configRef)
 * │   ├── execute()
 * │   ├── executeStream()
 * │   └── executeStreamDeltas()
 * │
 * └── lowLevel()
 *     └── request(completionPostRequest)
 *         ├── execute()
 *         ├── executeStream()
 *         └── executeStreamDeltas()
 *
 * embeddings()
 * │
 * ├── request(orchestrationEmbeddingRequest)
 * │   └── execute()
 * │
 * └── lowLevel()
 *     └── request(embeddingsPostRequest)
 *         └── execute()
 * }</pre>
 *
 * <p>High-level chat execution returns {@link OrchestrationChatResponse}; low-level execution
 * returns {@link CompletionPostResponse}. Embedding execution similarly returns {@link
 * OrchestrationEmbeddingResponse} or {@link EmbeddingsPostResponse}. Use {@code lowLevel()} only
 * when supplying a custom generated request object.
 *
 * <p>Both chat APIs support text streaming and detailed deltas. Text streaming throws on an output
 * content-filter finish reason; detailed streaming exposes that reason in the deltas. Close
 * returned streams with try-with-resources. Streaming does not modify the configured request or
 * shared stream options, so the same execution client can also be used for non-streaming calls.
 */
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
   * @param destination the specific {@link HttpDestination} to use.
   * @see AiCoreService#getInferenceDestination(String)
   */
  public OrchestrationClient(@Nonnull final HttpDestination destination) {
    this.executor = new OrchestrationHttpExecutor(() -> destination);
  }

  /**
   * Convert the given prompt and config into a low-level request data object. The data object
   * allows for further customization before sending the request.
   *
   * @param template the {@link PromptTemplate} to generate a completion for.
   * @param config the {@link OrchestrationConfig } configuration to use for the completion.
   * @param fallbackConfigs fallback configurations to use.
   * @return the low-level request data object to send to orchestration.
   */
  @Nonnull
  public static CompletionRequestConfiguration toCompletionPostRequest(
      @Nonnull final PromptTemplate template,
      @Nonnull final OrchestrationModuleConfig config,
      @Nonnull final OrchestrationModuleConfig... fallbackConfigs) {
    return ConfigToRequestTransformer.toCompletionPostRequest(template, config, fallbackConfigs);
  }

  /**
   * Create a builder to configure and execute a chat completion.
   *
   * @return a builder to configure the chat completion
   */
  @Nonnull
  public ChatCompletionBuilder chatCompletion() {
    return new ChatCompletionBuilder();
  }

  /** Builder to select a local or referenced orchestration configuration for a chat completion. */
  @AllArgsConstructor
  public class ChatCompletionBuilder {

    /**
     * Select the low-level API for a custom completion request.
     *
     * @return a builder to supply the low-level request.
     */
    @Nonnull
    public LowLevelChatCompletionBuilder lowLevel() {
      return new LowLevelChatCompletionBuilder();
    }

    /**
     * Configure the chat completion using a local orchestration configuration.
     *
     * @param config the primary orchestration configuration.
     * @param fallbackConfigs fallback configurations to use.
     * @return a builder to select the prompt template.
     */
    @Nonnull
    public ChatCompletionWithConfig config(
        @Nonnull final OrchestrationModuleConfig config,
        @Nonnull final OrchestrationModuleConfig... fallbackConfigs) {
      return new ChatCompletionWithConfig(config, fallbackConfigs);
    }

    /**
     * Configure the chat completion using a stored orchestration configuration.
     *
     * @param configReference the reference to the stored orchestration configuration.
     * @return a client to execute the configured chat completion.
     */
    @Nonnull
    public ChatCompletionExecutor configReference(
        @Nonnull final OrchestrationConfigReference configReference) {
      val request =
          ConfigToRequestTransformer.fromReferenceToCompletionPostRequest(configReference);
      return new ChatCompletionExecutor(request);
    }
  }

  /** Intermediate builder to select a prompt template for a local orchestration configuration. */
  @AllArgsConstructor
  public class ChatCompletionWithConfig {
    OrchestrationModuleConfig config;
    OrchestrationModuleConfig[] fallbackConfigs;

    /**
     * Set the prompt template for the chat completion.
     *
     * @param template the local prompt template or prompt template reference.
     * @return a client to execute the configured chat completion.
     */
    @Nonnull
    public ChatCompletionExecutor promptTemplate(@Nonnull final PromptTemplate template) {
      val request =
          ConfigToRequestTransformer.toCompletionPostRequest(template, config, fallbackConfigs);
      return new ChatCompletionExecutor(request);
    }
  }

  /** Client to execute a configured chat completion with a regular or streaming response. */
  public class ChatCompletionExecutor {

    private final LowLevelChatCompletionExecutor delegate;

    /**
     * Create a client for a configured chat completion request.
     *
     * @param request the request to execute.
     */
    private ChatCompletionExecutor(@Nonnull final CompletionPostRequest request) {
      this.delegate = new LowLevelChatCompletionExecutor(request);
    }

    /**
     * Generate a completion for the given prompt.
     *
     * @return the completion output
     * @throws OrchestrationClientException if the request fails.
     */
    @Nonnull
    public OrchestrationChatResponse execute() throws OrchestrationClientException {
      return new OrchestrationChatResponse(delegate.execute());
    }

    /**
     * Generate a completion for the given prompt.
     *
     * @return a stream of message deltas
     * @throws OrchestrationClientException if the request fails or if the finish reason is
     *     content_filter
     * @since 1.1.0
     */
    @Nonnull
    public Stream<String> executeStream() throws OrchestrationClientException {
      return delegate.executeStream();
    }

    /**
     * Generate a completion with detailed streaming deltas, including metadata.
     *
     * <p>Streaming is enabled on a copy of the configured request. The caller must close the
     * returned stream. Content-filter finish reasons are available in the deltas rather than
     * converted to exceptions.
     *
     * @return a stream of chat completion deltas.
     * @throws OrchestrationClientException if the request fails.
     */
    @Nonnull
    public Stream<OrchestrationChatCompletionDelta> executeStreamDeltas()
        throws OrchestrationClientException {
      return delegate.executeStreamDeltas();
    }
  }

  /** Builder to supply a custom low-level chat completion request. */
  @AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
  public class LowLevelChatCompletionBuilder {

    /**
     * Set the low-level completion request.
     *
     * @param request the request to send to orchestration.
     * @return a client to execute the request.
     */
    @SuppressWarnings("PMD.PublicApiExposesModelType")
    @Nonnull
    public LowLevelChatCompletionExecutor request(@Nonnull final CompletionPostRequest request) {
      return new LowLevelChatCompletionExecutor(request);
    }
  }

  /** Client to execute a custom low-level chat completion request. */
  @AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
  public class LowLevelChatCompletionExecutor {

    private @Nonnull final CompletionPostRequest request;

    /**
     * Execute the request and return the low-level response.
     *
     * @return the response data object from orchestration.
     * @throws OrchestrationClientException if the request fails.
     */
    @SuppressWarnings("PMD.PublicApiExposesModelType")
    @Nonnull
    public CompletionPostResponse execute() throws OrchestrationClientException {
      return executeRequest(request);
    }

    /**
     * Execute the request with text streaming.
     *
     * <p>The caller must close the returned stream.
     *
     * @return a stream of message text deltas.
     * @throws OrchestrationClientException if the request fails or the output is filtered.
     */
    @Nonnull
    public Stream<String> executeStream() throws OrchestrationClientException {
      return executeStreamDeltas()
          .peek(LowLevelChatCompletionExecutor::throwOnContentFilter)
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
      return ((List<Map<String, Object>>) ((Map<String, Object>) f.getData()).get("choices"))
          .get(0);
    }

    /**
     * Execute the request with detailed streaming deltas.
     *
     * <p>Streaming is enabled on a copy of the supplied request. The caller must close the returned
     * stream. Content-filter finish reasons are available in the deltas rather than converted to
     * exceptions.
     *
     * @return a stream of chat completion deltas.
     * @throws OrchestrationClientException if the request fails.
     */
    @Nonnull
    public Stream<OrchestrationChatCompletionDelta> executeStreamDeltas()
        throws OrchestrationClientException {
      if (!(request instanceof CompletionRequestConfiguration)
          && !(request instanceof CompletionRequestConfigurationReferenceById)
          && !(request instanceof CompletionRequestConfigurationReferenceByNameScenarioVersion)) {
        throw new IllegalStateException(
            "Unsupported request type: " + request.getClass().getName());
      }
      // Copy the serialized payload, not generated models whose polymorphic types can be ambiguous.
      // Only the outgoing copy is changed; caller-owned requests and stream options stay untouched.
      final ObjectNode streamingRequest = JACKSON.valueToTree(request);
      final var config = streamingRequest.withObject("/config");
      if (!config.hasNonNull("stream")) {
        config.set(
            "stream",
            JACKSON.valueToTree(GlobalStreamOptions.create().enabled(true).delimiters(null)));
      } else {
        config.withObject("/stream").put("enabled", true);
      }
      return executor.stream(COMPLETION_ENDPOINT, streamingRequest, customHeaders);
    }
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
   * @param request the request data object to send to orchestration.
   * @return the response data object from orchestration.
   * @throws OrchestrationClientException if the request fails.
   */
  @Nonnull
  protected CompletionPostResponse executeRequest(@Nonnull final CompletionPostRequest request)
      throws OrchestrationClientException {
    return executor.execute(
        COMPLETION_ENDPOINT, request, CompletionPostResponse.class, customHeaders);
  }

  /**
   * Create a builder to configure and execute an embedding request.
   *
   * @return a builder to supply an embedding request or select the low-level API.
   */
  @Nonnull
  public EmbeddingsBuilder embeddings() {
    return new EmbeddingsBuilder();
  }

  /** Builder to select a high-level or low-level embedding request. */
  @AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
  public class EmbeddingsBuilder {

    /**
     * Set the embedding request.
     *
     * @param request the input text, model and optional masking configuration.
     * @return a client to execute the embedding request.
     */
    @Nonnull
    public EmbeddingsExecutor request(@Nonnull final OrchestrationEmbeddingRequest request) {
      return new EmbeddingsExecutor(request);
    }

    /**
     * Select the low-level API for a custom embedding request.
     *
     * @return a builder to supply the low-level request.
     */
    @Nonnull
    public LowLevelEmbeddingsBuilder lowLevel() {
      return new LowLevelEmbeddingsBuilder();
    }
  }

  /** Client to execute a high-level embedding request. */
  @AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
  public class EmbeddingsExecutor {

    private @Nonnull final OrchestrationEmbeddingRequest request;

    /**
     * Generate embeddings for the configured request.
     *
     * @return the response containing the embeddings.
     * @throws OrchestrationClientException if the request fails.
     */
    @Nonnull
    public OrchestrationEmbeddingResponse execute() throws OrchestrationClientException {
      return new OrchestrationEmbeddingResponse(
          new LowLevelEmbeddingsExecutor(request.createEmbeddingsPostRequest()).execute());
    }
  }

  /** Builder to supply a custom low-level embedding request. */
  @AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
  public class LowLevelEmbeddingsBuilder {

    /**
     * Set the low-level embedding request.
     *
     * @param request the request to send to orchestration.
     * @return a client to execute the request.
     */
    @SuppressWarnings("PMD.PublicApiExposesModelType")
    @Nonnull
    public LowLevelEmbeddingsExecutor request(@Nonnull final EmbeddingsPostRequest request) {
      return new LowLevelEmbeddingsExecutor(request);
    }
  }

  /** Client to execute a custom low-level embedding request. */
  @AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
  public class LowLevelEmbeddingsExecutor {

    private @Nonnull final EmbeddingsPostRequest request;

    /**
     * Execute the request and return the low-level response.
     *
     * @return the response data object from orchestration.
     * @throws OrchestrationClientException if the request fails.
     */
    @SuppressWarnings("PMD.PublicApiExposesModelType")
    @Nonnull
    public EmbeddingsPostResponse execute() throws OrchestrationClientException {
      return executor.execute(
          "/v2/embeddings", request, EmbeddingsPostResponse.class, customHeaders);
    }
  }

  /**
   * Create a new orchestration client with a custom header added to every call made with this
   * client.
   *
   * @param key the key of the custom header to add.
   * @param value the value of the custom header to add.
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
   * Create a new orchestration client with multiple custom headers added to every call made with
   * this client.
   *
   * @param headers a map of key value pairs for the custom headers to add.
   * @return a new client.
   * @since 1.22.0
   */
  @Nonnull
  public OrchestrationClient withHeaders(@Nonnull final Map<String, String> headers) {
    final var newClient = new OrchestrationClient(this.executor);
    newClient.customHeaders.addAll(this.customHeaders);
    headers.entrySet().stream()
        .map(e -> new Header(e.getKey(), e.getValue()))
        .forEach(newClient.customHeaders::add);
    return newClient;
  }

  /**
   * Create a new orchestration client for the given resource group and scenario.
   *
   * @param resourceGroup the resource group, usually {@code "default"}.
   * @param scenario the scenario.
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
