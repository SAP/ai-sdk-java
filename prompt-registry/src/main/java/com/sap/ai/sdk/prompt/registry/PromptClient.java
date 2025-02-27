package com.sap.ai.sdk.prompt.registry;

import static com.sap.ai.sdk.core.JacksonConfiguration.getDefaultObjectMapper;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.collect.Iterables;
import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.prompt.registry.client.DefaultApi;
import com.sap.ai.sdk.prompt.registry.model.PromptTemplateSpecResponseFormat;
import com.sap.ai.sdk.prompt.registry.model.ResponseFormatText;
import com.sap.ai.sdk.prompt.registry.model.SingleChatTemplate;
import com.sap.ai.sdk.prompt.registry.model.Template;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Accessor;
import com.sap.cloud.sdk.services.openapi.apiclient.ApiClient;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/** Client for the Prompt Registry service. */
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class PromptClient {

  @Nonnull
  @Getter(AccessLevel.PROTECTED)
  private final AiCoreService service;

  /** Create a new client for the Prompt Registry service. */
  public PromptClient() {
    this(new AiCoreService());
  }

  /**
   * Get the API client for the Prompt Registry service.
   *
   * @return The API client.
   */
  @Nonnull
  public DefaultApi api() {
    return new DefaultApi(addMixin(getService()));
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
                        .addMixIn(Template.class, JacksonMixin.TemplateMixIn.class)
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
