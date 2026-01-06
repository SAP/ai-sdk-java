package com.sap.ai.sdk.prompt.registry;

import static com.sap.ai.sdk.core.JacksonConfiguration.getDefaultObjectMapper;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.prompt.registry.client.PromptTemplatesApi;
import com.sap.ai.sdk.prompt.registry.model.PromptTemplate;
import com.sap.ai.sdk.prompt.registry.model.PromptTemplateSpecResponseFormat;
import com.sap.ai.sdk.prompt.registry.model.ResponseFormatJsonObject;
import com.sap.ai.sdk.prompt.registry.model.ResponseFormatJsonSchema;
import com.sap.ai.sdk.prompt.registry.model.ResponseFormatText;
import com.sap.ai.sdk.prompt.registry.model.SingleChatTemplate;
import com.sap.cloud.sdk.services.openapi.apache.ApiClient;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

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
    // Create the new Apache-based ApiClient with destination
    final var apiClient = ApiClient.create(destination);

    // Configure Jackson ObjectMapper directly on the ApiClient
    final var customObjectMapper =
        getDefaultObjectMapper()
            .addMixIn(PromptTemplate.class, JacksonMixin.TemplateMixIn.class)
            .addMixIn(PromptTemplateSpecResponseFormat.class, JacksonMixin.ResponseFormat.class);

    return apiClient.withObjectMapper(customObjectMapper);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  private static class JacksonMixin {
    @JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
    @JsonDeserialize(as = SingleChatTemplate.class)
    interface TemplateMixIn {}

    @JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type",
        visible = true)
    @JsonSubTypes({
      @JsonSubTypes.Type(value = ResponseFormatJsonSchema.class, name = "json_schema"),
      @JsonSubTypes.Type(value = ResponseFormatJsonObject.class, name = "json_object"),
      @JsonSubTypes.Type(value = ResponseFormatText.class, name = "text")
    })
    interface ResponseFormat {}
  }
}
