package com.sap.ai.sdk.orchestration;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sap.ai.sdk.core.AiCoreDeployment;
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
import org.apache.hc.core5.http.message.BasicClassicHttpRequest;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/** Client to execute requests to the orchestration service. */
@Slf4j
public class OrchestrationClient {
  static final ObjectMapper JACKSON;

  static {
    JACKSON =
        new Jackson2ObjectMapperBuilder()
            .modules(new JavaTimeModule())
            .visibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE)
            .visibility(PropertyAccessor.SETTER, JsonAutoDetect.Visibility.NONE)
            .serializationInclusion(JsonInclude.Include.NON_NULL)
            .mixIn(LLMModuleResult.class, LLMModuleResultMixIn.class)
            .mixIn(ModuleResultsOutputUnmaskingInner.class, NoTypeInfoMixin.class)
            .mixIn(FilterConfig.class, NoTypeInfoMixin.class)
            .mixIn(MaskingProviderConfig.class, NoTypeInfoMixin.class)
            .mixIn(TemplatingModuleConfig.class, NoTypeInfoMixin.class)
            .build();
  }

  @Nonnull private final Supplier<AiCoreDeployment> deployment;

  /** Default constructor. */
  public OrchestrationClient() {
    deployment = () -> new AiCoreService().forDeploymentByScenario("orchestration");
  }

  /**
   * Constructor with a custom deployment, allowing for a custom resource group or otherwise
   * specific deployment ID.
   *
   * <p>Example:
   *
   * <pre>{@code
   * new OrchestrationClient(new AiCoreService().forDeploymentByScenario("orchestration"));
   * }</pre>
   *
   * @param deployment The specific {@link AiCoreDeployment} to use.
   */
  public OrchestrationClient(@Nonnull final AiCoreDeployment deployment) {
    this.deployment = () -> deployment;
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
  public OrchestrationResponse chatCompletion(
      @Nonnull final OrchestrationPrompt prompt, @Nonnull final OrchestrationModuleConfig config)
      throws OrchestrationClientException {

    val request = toCompletionPostRequest(prompt, config);
    return new OrchestrationResponse(executeRequest(request));
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

  @Nonnull
  CompletionPostResponse executeRequest(@Nonnull final BasicClassicHttpRequest request) {
    try {
      val destination = deployment.get().destination();
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
