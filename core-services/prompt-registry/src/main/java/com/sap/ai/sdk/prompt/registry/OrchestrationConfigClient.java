package com.sap.ai.sdk.prompt.registry;

import static com.sap.ai.sdk.core.JacksonConfiguration.getDefaultObjectMapper;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.common.annotations.Beta;
import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.prompt.registry.client.OrchestrationConfigsApi;
import com.sap.ai.sdk.prompt.registry.model.AzureContentSafetyInputFilterConfig;
import com.sap.ai.sdk.prompt.registry.model.AzureContentSafetyOutputFilterConfig;
import com.sap.ai.sdk.prompt.registry.model.InputFilterConfig;
import com.sap.ai.sdk.prompt.registry.model.LlamaGuard38bFilterConfig;
import com.sap.ai.sdk.prompt.registry.model.OrchestrationConfigDeleteResponse;
import com.sap.ai.sdk.prompt.registry.model.OrchestrationConfigListResponse;
import com.sap.ai.sdk.prompt.registry.model.OrchestrationConfigPostRequest;
import com.sap.ai.sdk.prompt.registry.model.OrchestrationConfigPostResponse;
import com.sap.ai.sdk.prompt.registry.model.OutputFilterConfig;
import com.sap.cloud.sdk.services.openapi.apache.apiclient.ApiClient;
import com.sap.cloud.sdk.services.openapi.apache.core.OpenApiRequestException;
import java.util.UUID;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.Tolerate;
import lombok.val;

/**
 * Client for managing Orchestration Configurations in the Prompt Registry service.
 *
 * @since 1.15.0
 */
@Beta
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class OrchestrationConfigClient {

  private final OrchestrationConfigsApi configsApi;

  /**
   * Instantiates a client to manage Orchestration Configurations on the Prompt Registry service.
   */
  @Tolerate
  public OrchestrationConfigClient() {
    this(new AiCoreService());
  }

  /**
   * Instantiates a client to manage Orchestration Configurations on the Prompt Registry service.
   *
   * @param aiCoreService The configured connectivity instance to AI Core
   */
  @Tolerate
  public OrchestrationConfigClient(@Nonnull final AiCoreService aiCoreService) {
    this.configsApi = new OrchestrationConfigsApi(addMixin(aiCoreService));
  }

  /**
   * Create or update an orchestration config
   *
   * <p><b>200</b> - Successful response
   *
   * <p><b>400</b> - Bad Request
   *
   * <p><b>403</b> - Forbidden Error
   *
   * <p><b>0</b> - Common Error
   *
   * @param request The value for the parameter orchestrationConfigPostRequest
   * @return OrchestrationConfigPostResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public OrchestrationConfigPostResponse createUpdateOrchestrationConfig(
      @Nonnull final OrchestrationConfigPostRequest request) throws OpenApiRequestException {
    return this.configsApi.createUpdateOrchestrationConfig(request);
  }

  /**
   * List orchestration configs
   *
   * <p><b>200</b> - Successful response
   *
   * <p><b>400</b> - Bad Request
   *
   * <p><b>403</b> - Forbidden Error
   *
   * <p><b>413</b> - Payload Too Large — result set exceeds maximum allowed rows; use $top and $skip
   * to paginate
   *
   * <p><b>0</b> - Common Error
   *
   * @return OrchestrationConfigListResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public OrchestrationConfigListResponse listOrchestrationConfigs() throws OpenApiRequestException {
    return configsApi.listOrchestrationConfigs();
  }

  /**
   * Delete orchestration config
   *
   * <p><b>200</b> - Successful response
   *
   * <p><b>403</b> - Forbidden Error
   *
   * <p><b>404</b> - Bad Request
   *
   * <p><b>0</b> - Common Error
   *
   * @param id The value for the parameter orchestrationConfigId
   * @return OrchestrationConfigDeleteResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public OrchestrationConfigDeleteResponse deleteOrchestrationConfig(@Nonnull final UUID id)
      throws OpenApiRequestException {
    return configsApi.deleteOrchestrationConfig(id);
  }

  @Nonnull
  private static ApiClient addMixin(@Nonnull final AiCoreService service) {
    final var destination = service.getBaseDestination();

    val objectMapper =
        getDefaultObjectMapper()
            .addMixIn(OutputFilterConfig.class, JacksonMixin.OutputFilter.class)
            .addMixIn(InputFilterConfig.class, JacksonMixin.InputFilter.class);

    return ApiClient.create(destination).withObjectMapper(objectMapper);
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
