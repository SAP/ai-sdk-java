package com.sap.ai.sdk.prompt.registry;

import static com.sap.ai.sdk.core.JacksonConfiguration.getDefaultObjectMapper;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.common.annotations.Beta;
import com.google.common.collect.Iterables;
import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.prompt.registry.client.OrchestrationConfigsApi;
import com.sap.ai.sdk.prompt.registry.model.AzureContentSafetyInputFilterConfig;
import com.sap.ai.sdk.prompt.registry.model.AzureContentSafetyOutputFilterConfig;
import com.sap.ai.sdk.prompt.registry.model.InputFilterConfig;
import com.sap.ai.sdk.prompt.registry.model.LlamaGuard38bFilterConfig;
import com.sap.ai.sdk.prompt.registry.model.OutputFilterConfig;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Accessor;
import com.sap.cloud.sdk.services.openapi.apiclient.ApiClient;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.yaml.MappingJackson2YamlHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Client for managing Orchestration Configurations in the Prompt Registry service.
 *
 * @since 1.14.0
 */
@Beta
public class OrchestrationConfigClient extends OrchestrationConfigsApi {

  /**
   * Instantiates a client to manage Orchestration Configurations on the Prompt Registry service.
   *
   * @since 1.14.0
   */
  public OrchestrationConfigClient() {
    this(new AiCoreService());
  }

  /**
   * Instantiates a client to manage Orchestration Configurations on the Prompt Registry service.
   *
   * @param aiCoreService The configured connectivity instance to AI Core
   * @since 1.14.0
   */
  public OrchestrationConfigClient(@Nonnull final AiCoreService aiCoreService) {
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
                        .addMixIn(OutputFilterConfig.class, JacksonMixin.OutputFilter.class)
                        .addMixIn(InputFilterConfig.class, JacksonMixin.InputFilter.class)));
    final var yamlMapper = new ObjectMapper(new YAMLFactory());
    yamlMapper
        .addMixIn(OutputFilterConfig.class, JacksonMixin.OutputFilter.class)
        .addMixIn(InputFilterConfig.class, JacksonMixin.InputFilter.class);
    Iterables.filter(rt.getMessageConverters(), MappingJackson2YamlHttpMessageConverter.class)
        .forEach(converter -> converter.setObjectMapper(yamlMapper));

    rt.setRequestFactory(new BufferingClientHttpRequestFactory(httpRequestFactory));

    return new ApiClient(rt).setBasePath(destination.asHttp().getUri().toString());
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  private static class JacksonMixin {

    @JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type",
        visible = true)
    @JsonSubTypes({
      @JsonSubTypes.Type(value = LlamaGuard38bFilterConfig.class, name = "llama_guard_3_8b"),
      @JsonSubTypes.Type(
          value = AzureContentSafetyOutputFilterConfig.class,
          name = "azure_content_safety")
    })
    interface OutputFilter {}

    @JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type",
        visible = true)
    @JsonSubTypes({
      @JsonSubTypes.Type(value = LlamaGuard38bFilterConfig.class, name = "llama_guard_3_8b"),
      @JsonSubTypes.Type(
          value = AzureContentSafetyInputFilterConfig.class,
          name = "azure_content_safety")
    })
    interface InputFilter {}
  }
}
