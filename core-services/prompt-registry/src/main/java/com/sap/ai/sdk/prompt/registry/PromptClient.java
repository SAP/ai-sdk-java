package com.sap.ai.sdk.prompt.registry;

import static com.sap.ai.sdk.core.JacksonConfiguration.getDefaultObjectMapper;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.annotations.Beta;
import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.prompt.registry.client.PromptTemplatesApi;
import com.sap.ai.sdk.prompt.registry.model.MultiChatContent;
import com.sap.ai.sdk.prompt.registry.model.MultiChatTemplate;
import com.sap.ai.sdk.prompt.registry.model.PromptTemplate;
import com.sap.ai.sdk.prompt.registry.model.PromptTemplateDeleteResponse;
import com.sap.ai.sdk.prompt.registry.model.PromptTemplateGetResponse;
import com.sap.ai.sdk.prompt.registry.model.PromptTemplateListResponse;
import com.sap.ai.sdk.prompt.registry.model.PromptTemplatePostRequest;
import com.sap.ai.sdk.prompt.registry.model.PromptTemplatePostResponse;
import com.sap.ai.sdk.prompt.registry.model.PromptTemplateSpecResponseFormat;
import com.sap.ai.sdk.prompt.registry.model.PromptTemplateSubstitutionRequest;
import com.sap.ai.sdk.prompt.registry.model.PromptTemplateSubstitutionResponse;
import com.sap.ai.sdk.prompt.registry.model.ResponseFormatJsonObject;
import com.sap.ai.sdk.prompt.registry.model.ResponseFormatJsonSchema;
import com.sap.ai.sdk.prompt.registry.model.ResponseFormatText;
import com.sap.ai.sdk.prompt.registry.model.SingleChatTemplate;
import com.sap.cloud.sdk.services.openapi.apache.apiclient.ApiClient;
import com.sap.cloud.sdk.services.openapi.apache.core.OpenApiRequestException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.Tolerate;
import lombok.val;

/**
 * Client for the Prompt Registry service.
 *
 * @since 1.6.0
 */
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class PromptClient {

  private final PromptTemplatesApi apiClient;

  /**
   * Instantiates this a client to invoke operations on the Prompt Registry service.
   *
   * @since 1.6.0
   */
  @Tolerate
  public PromptClient() {
    this(new AiCoreService());
  }

  /**
   * Instantiates this a client to invoke operations on the Prompt Registry service.
   *
   * @param aiCoreService The configured connectivity instance to AI Core
   * @since 1.6.0
   */
  @Tolerate
  public PromptClient(@Nonnull final AiCoreService aiCoreService) {
    this.apiClient = new PromptTemplatesApi(addMixin(aiCoreService));
  }

  /**
   * Parse prompt template by name and version
   *
   * <p><b>200</b> - Successful response
   *
   * <p><b>400</b> - Bad Request
   *
   * <p><b>403</b> - Forbidden Error
   *
   * <p><b>0</b> - Common Error
   *
   * @param scenario (required) The value for the parameter scenario
   * @param version (required) The value for the parameter version
   * @param name (required) The value for the parameter name
   * @param aiResourceGroup (optional) Specify a resource group id to use
   * @param aiResourceGroupScope (optional) Specify whether the resource group scope is to be used
   * @param metadata (optional, default to false) The value for the parameter metadata
   * @param request (optional) The value for the parameter promptTemplateSubstitutionRequest
   * @return PromptTemplateSubstitutionResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  @Beta
  public PromptTemplateSubstitutionResponse parsePromptTemplateByNameVersion(
      @Nonnull final String scenario,
      @Nonnull final String version,
      @Nonnull final String name,
      @Nullable final String aiResourceGroup,
      @Nullable final String aiResourceGroupScope,
      @Nullable final Boolean metadata,
      @Nullable final PromptTemplateSubstitutionRequest request)
      throws OpenApiRequestException {
    return apiClient.parsePromptTemplateByNameVersion(
        scenario, version, name, aiResourceGroup, aiResourceGroupScope, metadata, request);
  }

  /**
   * List prompt templates
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
   * @return PromptTemplateListResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public PromptTemplateListResponse listPromptTemplates() throws OpenApiRequestException {
    return apiClient.listPromptTemplates();
  }

  /**
   * Create or update a prompt template
   *
   * <p><b>200</b> - Successful response
   *
   * <p><b>400</b> - Bad Request
   *
   * <p><b>403</b> - Forbidden Error
   *
   * <p><b>0</b> - Common Error
   *
   * @param request The value for the parameter promptTemplatePostRequest
   * @return PromptTemplatePostResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Beta
  @Nonnull
  public PromptTemplatePostResponse createUpdatePromptTemplate(
      @Nonnull final PromptTemplatePostRequest request) throws OpenApiRequestException {
    return apiClient.createUpdatePromptTemplate(request);
  }

  /**
   * Get prompt template by UUID
   *
   * <p><b>200</b> - Successful response
   *
   * <p><b>400</b> - Bad Request
   *
   * <p><b>403</b> - Forbidden Error
   *
   * <p><b>0</b> - Common Error
   *
   * @param uuid The value for the parameter promptTemplateId
   * @return PromptTemplateGetResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public PromptTemplateGetResponse getPromptTemplateByUuid(@Nonnull final UUID uuid) {
    return apiClient.getPromptTemplateByUuid(uuid);
  }

  /**
   * List prompt template history
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
   * @param scenario The value for the parameter scenario
   * @param version The value for the parameter version
   * @param name The value for the parameter name
   * @return PromptTemplateListResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public PromptTemplateListResponse listPromptTemplateHistory(
      @Nonnull final String scenario, @Nonnull final String version, @Nonnull final String name)
      throws OpenApiRequestException {
    return apiClient.listPromptTemplateHistory(scenario, version, name);
  }

  /**
   * Import prompt template
   *
   * <p><b>200</b> - Successful response
   *
   * <p><b>400</b> - Bad Request
   *
   * <p><b>0</b> - Common Error
   *
   * @param aiResourceGroup (optional) Specify a resource group id to use
   * @param aiResourceGroupScope (optional) Specify whether the resource group scope is to be used
   * @param file (optional) The value for the parameter _file
   * @return PromptTemplatePostResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public PromptTemplatePostResponse importPromptTemplate(
      @Nullable final String aiResourceGroup,
      @Nullable final String aiResourceGroupScope,
      @Nullable final File file)
      throws OpenApiRequestException {
    return apiClient.importPromptTemplate(aiResourceGroup, aiResourceGroupScope, file);
  }

  /**
   * Export prompt template
   *
   * <p><b>200</b> - Successful response
   *
   * <p><b>400</b> - Bad Request
   *
   * <p><b>0</b> - Common Error
   *
   * @param promptTemplateId The value for the parameter promptTemplateId
   * @return byte[]
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public byte[] exportPromptTemplate(@Nonnull final UUID promptTemplateId)
      throws OpenApiRequestException {
    return apiClient.exportPromptTemplate(promptTemplateId);
  }

  /**
   * Delete prompt template
   *
   * <p><b>200</b> - Successful response
   *
   * <p><b>403</b> - Forbidden Error
   *
   * <p><b>404</b> - Bad Request
   *
   * <p><b>0</b> - Common Error
   *
   * @param promptTemplateId The value for the parameter promptTemplateId
   * @return PromptTemplateDeleteResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public PromptTemplateDeleteResponse deletePromptTemplate(@Nonnull final UUID promptTemplateId)
      throws OpenApiRequestException {
    return apiClient.deletePromptTemplate(promptTemplateId);
  }

  /**
   * Parse prompt template by ID
   *
   * <p><b>200</b> - Successful response
   *
   * <p><b>400</b> - Bad Request
   *
   * <p><b>403</b> - Forbidden Error
   *
   * <p><b>0</b> - Common Error
   *
   * @param promptTemplateId (required) The value for the parameter promptTemplateId
   * @param aiResourceGroup (optional) Specify a resource group id to use
   * @param aiResourceGroupScope (optional) Specify whether the resource group scope is to be used
   * @param metadata (optional, default to false) The value for the parameter metadata
   * @param promptTemplateSubstitutionRequest (optional) The value for the parameter
   *     promptTemplateSubstitutionRequest
   * @return PromptTemplateSubstitutionResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  @Beta
  public PromptTemplateSubstitutionResponse parsePromptTemplateById(
      @Nonnull final UUID promptTemplateId,
      @Nullable final String aiResourceGroup,
      @Nullable final String aiResourceGroupScope,
      @Nullable final Boolean metadata,
      @Nullable final PromptTemplateSubstitutionRequest promptTemplateSubstitutionRequest)
      throws OpenApiRequestException {
    return apiClient.parsePromptTemplateById(
        promptTemplateId,
        aiResourceGroup,
        aiResourceGroupScope,
        metadata,
        promptTemplateSubstitutionRequest);
  }

  @Nonnull
  private static ApiClient addMixin(@Nonnull final AiCoreService service) {
    final var destination = service.getBaseDestination();

    val objectMapper =
        getDefaultObjectMapper()
            .addMixIn(PromptTemplate.class, JacksonMixin.TemplateMixIn.class)
            .addMixIn(PromptTemplateSpecResponseFormat.class, JacksonMixin.ResponseFormat.class);

    return ApiClient.create(destination).withObjectMapper(objectMapper);
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  private static class JacksonMixin {
    @JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
    @JsonDeserialize(using = PromptTemplateDeserializer.class)
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

  private static class PromptTemplateDeserializer extends JsonDeserializer<PromptTemplate> {

    @Override
    public PromptTemplate deserialize(
        @Nonnull final JsonParser jsonParser,
        @Nonnull final DeserializationContext deserializationContext)
        throws IOException {

      final JsonNode root = jsonParser.readValueAsTree();
      final JsonNode roleNode = root.path("role");
      final String role = roleNode.asText();
      final JsonNode content = root.path("content");

      if (!roleNode.isTextual()) {
        throw JsonMappingException.from(
            jsonParser, "PromptTemplate requires textual 'role' property.");
      }

      if (content.isTextual()) {
        return SingleChatTemplate.create().role(role).content(content.asText());
      }
      if (content.isArray()) {
        final var contentList = new ArrayList<MultiChatContent>();
        for (final JsonNode item : content) {
          contentList.add(jsonParser.getCodec().treeToValue(item, MultiChatContent.class));
        }
        return MultiChatTemplate.create().role(role).content(contentList);
      }

      throw JsonMappingException.from(
          jsonParser,
          "PromptTemplate content must be either a string or an array, but found: "
              + content.getNodeType());
    }
  }
}
