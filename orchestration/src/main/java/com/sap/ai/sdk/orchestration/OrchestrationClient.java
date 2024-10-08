package com.sap.ai.sdk.orchestration;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sap.ai.sdk.core.Core;
import com.sap.ai.sdk.orchestration.client.model.CompletionPostRequest;
import com.sap.ai.sdk.orchestration.client.model.CompletionPostResponse;
import com.sap.ai.sdk.orchestration.client.model.LLMModuleConfig;
import com.sap.ai.sdk.orchestration.client.model.MaskingModuleConfig;
import com.sap.ai.sdk.orchestration.client.model.TemplatingModuleConfig;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Accessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;
import lombok.AllArgsConstructor;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.message.BasicClassicHttpRequest;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import javax.annotation.Nonnull;
import java.util.stream.Stream;

@AllArgsConstructor
public class OrchestrationClient implements OrchestrationConfigBuilder<OrchestrationClient> {
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

  @Nonnull private final OrchestrationConfig config = new OrchestrationConfig();
  @Nonnull private final HttpDestination destination;

  public OrchestrationClient() {
    // TODO: use AiCoreService after refactoring
    this.destination = Core.getDestination().asHttp();
  }

  /**
   * Generate a completion for the given user prompt.
   *
   * @param userPrompt a text message.
   * @return the completion output
   * @throws OrchestrationClientException if the request fails
   */
  @Nonnull
  // TODO: decide if we want to offer this in addition to the already simple `new Prompt("asdf")`
  public String chatCompletion(@Nonnull final String userPrompt)
      throws OrchestrationClientException {
    var response = chatCompletion(new OrchestrationPrompt(userPrompt));
    return response.getOrchestrationResult().getChoices().getFirst().getMessage().getContent();
  }

  /**
   * Generate a completion for the given prompt.
   *
   * @param prompt the prompt, including messages and other parameters.
   * @return the completion output
   * @throws OrchestrationClientException if the request fails
   */
  @Nonnull
  public CompletionPostResponse chatCompletion(@Nonnull final OrchestrationPrompt prompt)
      throws OrchestrationClientException {
    var requestConfig = prompt.getConfig().mergeWithDefaults(config);
    var dto =
        CompletionPostRequest.create()
            .orchestrationConfig(
                com.sap.ai.sdk.orchestration.client.model.OrchestrationConfig.create()
                    .moduleConfigurations(requestConfig.toDTO()))
            .messagesHistory(prompt.getMessages())
            .inputParams(prompt.getTemplateParameters());

    var response = executeRequest(dto);
    return null;
  }

  @Nonnull
  public Stream<String> streamChatCompletion(@Nonnull final String prompt)
      throws OrchestrationClientException {
    throw new RuntimeException("Not implemented");
  }

  @Nonnull
  public Stream<String> streamChatCompletionDelta(@Nonnull final OrchestrationPrompt prompt)
      throws OrchestrationClientException {
    throw new RuntimeException("Not implemented");
  }

  @SuppressWarnings("UnstableApiUsage")
  @Nonnull
  private CompletionPostResponse executeRequest(@Nonnull final CompletionPostRequest request) {
    final var client = ApacheHttpClient5Accessor.getHttpClient(destination);
    final BasicClassicHttpRequest postRequest = new HttpPost("/completion");
    try {
      final var json = JACKSON.writeValueAsString(request);
      postRequest.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));
    } catch (final JsonProcessingException e) {
      throw new OrchestrationClientException("Failed to serialize request parameters", e);
    }

    try {
      return client.execute(postRequest, new OrchestrationResponseHandler<>(CompletionPostResponse.class));
    } catch (Exception e) {
      throw new OrchestrationClientException("Failed to execute request", e);
    }
  }

  @Nonnull
  @Override
  public OrchestrationClient withLlmConfig(LLMModuleConfig llm) {
    config.withLlmConfig(llm);
    return this;
  }

  @Nonnull
  @Override
  public OrchestrationClient withTemplate(TemplatingModuleConfig template) {
    config.withTemplate(template);
    return this;
  }

  @Nonnull
  @Override
  public OrchestrationClient withMaskingConfig(MaskingModuleConfig maskingConfig) {
    config.withMaskingConfig(maskingConfig);
    return this;
  }
}
