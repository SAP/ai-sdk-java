package com.sap.ai.sdk.orchestration;

import static com.sap.ai.sdk.orchestration.OrchestrationJacksonConfiguration.getOrchestrationObjectMapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.annotations.Beta;
import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.core.DeploymentResolutionException;
import com.sap.ai.sdk.core.common.ClientResponseHandler;
import com.sap.ai.sdk.core.common.ClientStreamingHandler;
import com.sap.ai.sdk.core.common.StreamedDelta;
import com.sap.ai.sdk.orchestration.model.CompletionPostRequest;
import com.sap.ai.sdk.orchestration.model.CompletionPostResponseSynchronous;
import com.sap.ai.sdk.orchestration.model.ErrorResponseModuleResultsAllOfLlm;
import com.sap.ai.sdk.orchestration.model.ModuleConfigs;
import com.sap.ai.sdk.orchestration.model.OrchestrationConfig;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Accessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;
import com.sap.cloud.sdk.cloudplatform.connectivity.exception.DestinationAccessException;
import com.sap.cloud.sdk.cloudplatform.connectivity.exception.DestinationNotFoundException;
import com.sap.cloud.sdk.cloudplatform.connectivity.exception.HttpClientInstantiationException;
import java.io.IOException;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.message.BasicClassicHttpRequest;

/** Client to execute requests to the orchestration service. */
@Slf4j
public class OrchestrationClient {
  private static final String DEFAULT_SCENARIO = "orchestration";

  static final ObjectMapper JACKSON = getOrchestrationObjectMapper();

  @Nonnull private final Supplier<HttpDestination> destinationSupplier;

  /** Default constructor. */
  public OrchestrationClient() {
    destinationSupplier =
        () -> new AiCoreService().getInferenceDestination().forScenario(DEFAULT_SCENARIO);
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
    this.destinationSupplier = () -> destination;
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
  public static CompletionPostRequest toCompletionPostRequest(
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

  private static void throwOnContentFilter(@Nonnull final OrchestrationChatCompletionDelta delta) {
    final String finishReason = delta.getFinishReason();
    if (finishReason != null && finishReason.equals("content_filter")) {
      throw new OrchestrationClientException("Content filter filtered the output.");
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
   * @param request The request data object to send to orchestration.
   * @return The response data object from orchestration.
   * @throws OrchestrationClientException If the request fails.
   */
  @Nonnull
  public CompletionPostResponseSynchronous executeRequest(
      @Nonnull final CompletionPostRequest request) throws OrchestrationClientException {
    final String jsonRequest;
    try {
      jsonRequest = JACKSON.writeValueAsString(request);
      log.debug("Serialized request into JSON payload: {}", jsonRequest);
    } catch (final JsonProcessingException e) {
      throw new OrchestrationClientException("Failed to serialize request parameters", e);
    }

    return executeRequest(jsonRequest);
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

    final String body;
    try {
      body = JACKSON.writeValueAsString(requestJson);
    } catch (JsonProcessingException e) {
      throw new OrchestrationClientException("Failed to serialize request to JSON", e);
    }
    return new OrchestrationChatResponse(executeRequest(body));
  }

  @Nonnull
  CompletionPostResponseSynchronous executeRequest(@Nonnull final String request) {
    val postRequest = new HttpPost("/completion");
    postRequest.setEntity(new StringEntity(request, ContentType.APPLICATION_JSON));
    JACKSON.addMixIn(
        ErrorResponseModuleResultsAllOfLlm.class, JacksonMixins.DefaultToSynchronousMixin.class);

    try {
      val destination = destinationSupplier.get();
      log.debug("Using destination {} to connect to orchestration service", destination);
      val client = ApacheHttpClient5Accessor.getHttpClient(destination);
      val handler =
          new ClientResponseHandler<>(
                  CompletionPostResponseSynchronous.class,
                  OrchestrationError.class,
                  OrchestrationClientException::new)
              .objectMapper(JACKSON);
      return client.execute(postRequest, handler);
    } catch (DeploymentResolutionException
        | DestinationAccessException
        | DestinationNotFoundException
        | HttpClientInstantiationException
        | IOException e) {
      throw new OrchestrationClientException("Failed to execute request", e);
    }
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
      @Nonnull final CompletionPostRequest request) throws OrchestrationClientException {
    request.getOrchestrationConfig().setStream(true);
    return executeStream("/completion", request, OrchestrationChatCompletionDelta.class);
  }

  @Nonnull
  private <D extends StreamedDelta> Stream<D> executeStream(
      @Nonnull final String path,
      @Nonnull final Object payload,
      @Nonnull final Class<D> deltaType) {
    final var request = new HttpPost(path);
    serializeAndSetHttpEntity(request, payload);
    return streamRequest(request, deltaType);
  }

  private static void serializeAndSetHttpEntity(
      @Nonnull final BasicClassicHttpRequest request, @Nonnull final Object payload) {
    try {
      final var json = JACKSON.writeValueAsString(payload);
      request.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));
    } catch (final JsonProcessingException e) {
      throw new OrchestrationClientException("Failed to serialize request parameters", e);
    }
  }

  @Nonnull
  private <D extends StreamedDelta> Stream<D> streamRequest(
      final BasicClassicHttpRequest request, @Nonnull final Class<D> deltaType) {
    try {
      val destination = destinationSupplier.get();
      log.debug("Using destination {} to connect to orchestration service", destination);
      val client = ApacheHttpClient5Accessor.getHttpClient(destination);
      JACKSON.addMixIn(
          ErrorResponseModuleResultsAllOfLlm.class, JacksonMixins.DefaultToStreamingMixin.class);
      return new ClientStreamingHandler<>(
              deltaType, OrchestrationError.class, OrchestrationClientException::new)
          .objectMapper(JACKSON)
          .handleStreamingResponse(client.executeOpen(null, request, null));
    } catch (final IOException e) {
      throw new OrchestrationClientException("Request to the Orchestration service failed", e);
    }
  }
}
