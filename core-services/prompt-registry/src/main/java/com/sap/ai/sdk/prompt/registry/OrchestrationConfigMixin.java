package com.sap.ai.sdk.prompt.registry;

import static com.sap.ai.sdk.core.JacksonConfiguration.getDefaultObjectMapper;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.prompt.registry.model.AzureContentSafetyInputFilterConfig;
import com.sap.ai.sdk.prompt.registry.model.AzureContentSafetyOutputFilterConfig;
import com.sap.ai.sdk.prompt.registry.model.InputFilterConfig;
import com.sap.ai.sdk.prompt.registry.model.LlamaGuard38bFilterConfig;
import com.sap.ai.sdk.prompt.registry.model.OutputFilterConfig;
import com.sap.cloud.sdk.services.openapi.apache.apiclient.ApiClient;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.val;

class OrchestrationConfigMixin {

  @Nonnull
  static ApiClient addMixin(@Nonnull final AiCoreService service) {
    final var destination = service.getBaseDestination();

    val objectMapper =
        getDefaultObjectMapper()
            .addMixIn(OutputFilterConfig.class, JacksonMixin.OutputFilter.class)
            .addMixIn(InputFilterConfig.class, JacksonMixin.InputFilter.class);

    return ApiClient.create(destination).withObjectMapper(objectMapper);
  }

  @NoArgsConstructor(access = AccessLevel.PACKAGE)
  static class JacksonMixin {

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
