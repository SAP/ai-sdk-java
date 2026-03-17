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
import com.google.common.collect.Iterables;
import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.prompt.registry.client.PromptTemplatesApi;
import com.sap.ai.sdk.prompt.registry.model.MultiChatContent;
import com.sap.ai.sdk.prompt.registry.model.MultiChatTemplate;
import com.sap.ai.sdk.prompt.registry.model.PromptTemplate;
import com.sap.ai.sdk.prompt.registry.model.PromptTemplateSpecResponseFormat;
import com.sap.ai.sdk.prompt.registry.model.ResponseFormatJsonObject;
import com.sap.ai.sdk.prompt.registry.model.ResponseFormatJsonSchema;
import com.sap.ai.sdk.prompt.registry.model.ResponseFormatText;
import com.sap.ai.sdk.prompt.registry.model.SingleChatTemplate;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Accessor;
import com.sap.cloud.sdk.services.openapi.apiclient.ApiClient;
import java.io.IOException;
import java.util.ArrayList;
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
