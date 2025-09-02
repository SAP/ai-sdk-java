package com.sap.ai.sdk.prompt.registry;

import static com.sap.ai.sdk.core.JacksonConfiguration.getDefaultObjectMapper;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.collect.Iterables;
import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.prompt.registry.client.PromptTemplatesApi;
import com.sap.ai.sdk.prompt.registry.model.PromptTemplateSpecResponseFormat;
import com.sap.ai.sdk.prompt.registry.model.ResponseFormatText;
import com.sap.ai.sdk.prompt.registry.model.SingleChatTemplate;
import com.sap.ai.sdk.prompt.registry.model.PromptTemplate;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Accessor;
import com.sap.cloud.sdk.services.openapi.apiclient.ApiClient;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Client for the Prompt Registry service.
 *
 * @since 1.6.0
 */
public class PromptClient extends PromptTemplatesApi {

  /**
   * Instantiates this a client to invoke operations on the Prompt Registry service.
   *
   * @since 1.6.0
   */
  public PromptClient() {
    this(new AiCoreService());
  }

  /**
   * Instantiates this a client to invoke operations on the Prompt Registry service.
   *
   * @param aiCoreService The configured connectivity instance to AI Core
   * @since 1.6.0
   */
  public PromptClient(@Nonnull final AiCoreService aiCoreService) {
    super(addMixin(aiCoreService));
  }

  @Nonnull
  private static ApiClient addMixin(@Nonnull final AiCoreService service) {
    final var destination = service.getBaseDestination();
    final var httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
    httpRequestFactory.setHttpClient(ApacheHttpClient5Accessor.getHttpClient(destination));

    final var rt = new RestTemplate();
    Iterables.filter(rt.getMessageConverters(), MappingJackson2HttpMessageConverter.class)
        .forEach(
            converter ->
                converter.setObjectMapper(
                    getDefaultObjectMapper()
                        .addMixIn(PromptTemplate.class, JacksonMixin.TemplateMixIn.class)
                        .addMixIn(
                            PromptTemplateSpecResponseFormat.class,
                            JacksonMixin.ResponseFormat.class)));

    rt.setRequestFactory(new BufferingClientHttpRequestFactory(httpRequestFactory));

    return new ApiClient(rt).setBasePath(destination.asHttp().getUri().toString());
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  private static class JacksonMixin {
    @JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
    @JsonDeserialize(as = SingleChatTemplate.class)
    interface TemplateMixIn {}

    @JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
    @JsonDeserialize(as = ResponseFormatText.class)
    interface ResponseFormat {}
  }
}
