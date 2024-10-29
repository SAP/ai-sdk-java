package com.sap.ai.sdk.orchestration;

import static com.sap.ai.sdk.orchestration.OrchestrationResponse.FinishReason.CONTENT_FILTER;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.orchestration.client.model.CompletionPostRequest;
import com.sap.ai.sdk.orchestration.client.model.CompletionPostResponse;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Accessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.exception.DestinationAccessException;
import com.sap.cloud.sdk.cloudplatform.connectivity.exception.DestinationNotFoundException;
import com.sap.cloud.sdk.cloudplatform.connectivity.exception.HttpClientInstantiationException;
import io.vavr.NotImplementedError;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.message.BasicClassicHttpRequest;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * Client to execute requests to the orchestration service. Can be configured to hold a default
 * {@link OrchestrationConfig} to be used for all requests. Configuration set on an {@link
 * OrchestrationPrompt} will take precedence upon execution.
 */
@Slf4j
@RequiredArgsConstructor
public class OrchestrationClient implements OrchestrationConfig<OrchestrationClient> {
  static final ObjectMapper JACKSON;

  static {
    JACKSON =
        new Jackson2ObjectMapperBuilder()
            .modules(new JavaTimeModule())
            .visibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE)
            .visibility(PropertyAccessor.SETTER, JsonAutoDetect.Visibility.NONE)
            .serializationInclusion(JsonInclude.Include.NON_NULL)
            .build();
  }

  @Delegate(types = IDelegate.class)
  @Nonnull
  private final DefaultOrchestrationConfig<OrchestrationClient> clientConfig =
      DefaultOrchestrationConfig.asDelegateFor(this);

  @Nonnull private final AiCoreService service;

  private interface IDelegate extends OrchestrationConfig<OrchestrationClient> {}

  /** Default constructor. */
  public OrchestrationClient() {
    service = new AiCoreService();
  }

  /**
   * Generate a completion for the given user prompt.
   *
   * @param userPrompt a text message.
   * @return the completion output
   * @throws OrchestrationClientException if the request fails
   */
  @Nonnull
  public String chatCompletion(@Nonnull final String userPrompt)
      throws OrchestrationClientException {
    val response = chatCompletion(new OrchestrationPrompt(userPrompt));

    if (response.finishReason() == CONTENT_FILTER) {
      log.error(
          "Output content filter triggered. Full response details: {}",
          response.originalResponseDto());
      throw new OrchestrationClientException("Output content filter triggered");
    }
    return response.assistantMessage().content();
  }

  /**
   * Generate a completion for the given prompt.
   *
   * @param prompt the prompt, including messages and other parameters.
   * @return the completion output
   * @throws OrchestrationClientException if the request fails
   */
  @Nonnull
  public OrchestrationResponse chatCompletion(@Nonnull final OrchestrationPrompt prompt)
      throws OrchestrationClientException {
    log.debug(
        """
            Performing request to orchestration service.
            Prompt: {}
            Defaults: {}
            """,
        prompt,
        clientConfig);
    val dto = prompt.toCompletionPostRequestDto(clientConfig);
    log.debug("Assembled data transfer object for request: {}", dto);
    val result = executeRequest(dto);
    return OrchestrationResponse.fromCompletionPostResponseDto(result);
  }

  /**
   * Stream a completion for the given user message.
   *
   * @param prompt The user message.
   * @return A stream of message chunks.
   * @throws OrchestrationClientException If the request fails.
   */
  @Nonnull
  public Stream<String> streamChatCompletion(@Nonnull final String prompt)
      throws OrchestrationClientException {
    throw new NotImplementedError();
  }

  /**
   * Stream a completion for the given prompt.
   *
   * @param prompt The prompt, including messages and other configuration.
   * @return A stream of message chunks.
   * @throws OrchestrationClientException If the request fails.
   */
  @Nonnull
  public Stream<String> streamChatCompletionDelta(@Nonnull final OrchestrationPrompt prompt)
      throws OrchestrationClientException {
    throw new NotImplementedError();
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
   * @param request The request DTO to send to orchestration.
   * @return The response DTO from orchestration.
   * @throws OrchestrationClientException If the request fails.
   */
  @Nonnull
  protected CompletionPostResponse executeRequest(@Nonnull final CompletionPostRequest request)
      throws OrchestrationClientException {
    final BasicClassicHttpRequest postRequest = new HttpPost("/completion");
    try {
      val json = JACKSON.writeValueAsString(request);
      log.debug("Serialized request into JSON payload: {}", json);
      postRequest.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));
    } catch (final JsonProcessingException e) {
      throw new OrchestrationClientException("Failed to serialize request parameters", e);
    }

    return executeRequest(postRequest);
  }

  @SuppressWarnings("UnstableApiUsage")
  @Nonnull
  CompletionPostResponse executeRequest(@Nonnull final BasicClassicHttpRequest request) {
    try {
      val destination = service.forDeploymentByScenario("orchestration").destination();
      log.debug("Using destination {} to connect to orchestration service", destination);
      val client = ApacheHttpClient5Accessor.getHttpClient(destination);
      return client.execute(
          request, new OrchestrationResponseHandler<>(CompletionPostResponse.class));
    } catch (NoSuchElementException
        | DestinationAccessException
        | DestinationNotFoundException
        | HttpClientInstantiationException
        | IOException e) {
      throw new OrchestrationClientException("Failed to execute request", e);
    }
  }
}
