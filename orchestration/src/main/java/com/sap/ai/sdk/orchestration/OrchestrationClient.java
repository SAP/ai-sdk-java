package com.sap.ai.sdk.orchestration;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.annotations.Beta;
import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.orchestration.client.model.CompletionPostRequest;
import com.sap.ai.sdk.orchestration.client.model.CompletionPostResponse;
import com.sap.ai.sdk.orchestration.client.model.FilterConfig;
import com.sap.ai.sdk.orchestration.client.model.LLMModuleResult;
import com.sap.ai.sdk.orchestration.client.model.MaskingProviderConfig;
import com.sap.ai.sdk.orchestration.client.model.ModuleConfigs;
import com.sap.ai.sdk.orchestration.client.model.ModuleResultsOutputUnmaskingInner;
import com.sap.ai.sdk.orchestration.client.model.OrchestrationConfig;
import com.sap.ai.sdk.orchestration.client.model.TemplatingModuleConfig;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Accessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;
import com.sap.cloud.sdk.cloudplatform.connectivity.exception.DestinationAccessException;
import com.sap.cloud.sdk.cloudplatform.connectivity.exception.DestinationNotFoundException;
import com.sap.cloud.sdk.cloudplatform.connectivity.exception.HttpClientInstantiationException;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.function.Supplier;
import javax.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/** Client to execute requests to the orchestration service. */
@Slf4j
public class OrchestrationClient {
  private static final String DEFAULT_SCENARIO = "orchestration";

  static final ObjectMapper JACKSON;

  static {
    JACKSON =
        new Jackson2ObjectMapperBuilder()
            .modules(new JavaTimeModule())
            .visibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE)
            .visibility(PropertyAccessor.SETTER, JsonAutoDetect.Visibility.NONE)
            .serializationInclusion(JsonInclude.Include.NON_NULL)
            .mixIn(LLMModuleResult.class, JacksonMixins.LLMModuleResultMixIn.class)
            .mixIn(
                ModuleResultsOutputUnmaskingInner.class,
                JacksonMixins.ModuleResultsOutputUnmaskingInnerMixIn.class)
            .mixIn(FilterConfig.class, JacksonMixins.NoTypeInfoMixin.class)
            .mixIn(MaskingProviderConfig.class, JacksonMixins.NoTypeInfoMixin.class)
            .mixIn(TemplatingModuleConfig.class, JacksonMixins.NoTypeInfoMixin.class)
            .build();
  }

  @Nonnull private final Supplier<HttpDestination> destinationSupplier;

  /** Default constructor. */
  public OrchestrationClient() {
    destinationSupplier = () -> new AiCoreService().getDestinationForDeploymentByScenario(AiCoreService.DEFAULT_RESOURCE_GROUP, DEFAULT_SCENARIO);
  }

  /**
   * Constructor with a custom destination, allowing for a custom resource group or otherwise
   * custom destination.
   *
   * <p>Example:
   *
   * <pre>{@code
   * new OrchestrationClient(new AiCoreService().getDestinationForDeploymentByScenario("custom-rg", "orchestration"));
   * }</pre>
   *
   * @param destination The specific {@link HttpDestination} to use.
   * @see AiCoreService#getDestinationForDeploymentByScenario(String, String)
   */
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
    return new OrchestrationChatResponse(executeRequest(request));
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
    requestJson.set("messages_history", JACKSON.valueToTree(prompt.getMessagesHistory()));
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
  CompletionPostResponse executeRequest(@Nonnull final String request) {
    val postRequest = new HttpPost("/completion");
    postRequest.setEntity(new StringEntity(request, ContentType.APPLICATION_JSON));

    try {
      val destination = destinationSupplier.get();
      log.debug("Using destination {} to connect to orchestration service", destination);
      val client = ApacheHttpClient5Accessor.getHttpClient(destination);
      return client.execute(
          postRequest, new OrchestrationResponseHandler<>(CompletionPostResponse.class));
    } catch (NoSuchElementException
        | DestinationAccessException
        | DestinationNotFoundException
        | HttpClientInstantiationException
        | IOException e) {
      throw new OrchestrationClientException("Failed to execute request", e);
    }
  }
}
