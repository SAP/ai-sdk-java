package com.sap.ai.sdk.orchestration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sap.ai.sdk.orchestration.model.ChatCompletionTool;
import com.sap.ai.sdk.orchestration.model.ChatMessage;
import com.sap.ai.sdk.orchestration.model.PromptTemplatingModuleConfigPrompt;
import com.sap.ai.sdk.orchestration.model.ResponseFormatJsonObject;
import com.sap.ai.sdk.orchestration.model.ResponseFormatJsonSchema;
import com.sap.ai.sdk.orchestration.model.ResponseFormatJsonSchemaJsonSchema;
import com.sap.ai.sdk.orchestration.model.Template;
import com.sap.ai.sdk.orchestration.model.TemplateResponseFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.With;
import lombok.val;

/**
 * A template to use in {@link OrchestrationModuleConfig}.
 *
 * @since 1.4.0
 */
@EqualsAndHashCode(callSuper = true)
@Value
@With
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class LocalPromptTemplate extends PromptTemplate
{

  /** Please use {@link #withMessages(Message...)} instead. */
  @JsonProperty("template")
  @Nullable
  @With(AccessLevel.PRIVATE)
  List<ChatMessage> chatMessages;

  @Nonnull List<Message> messageHistory;

  @Nonnull Map<String, String> inputParameters;

  @JsonProperty("defaults")
  @Nullable
  Map<String, String> defaults;

  @JsonProperty("response_format")
  @With(AccessLevel.PRIVATE)
  @Nullable
  @SuppressWarnings("PMD.LombokGetterSetterExposesModelType")
  TemplateResponseFormat responseFormat;

  @JsonProperty("tools")
  @Nullable
  List<ChatCompletionTool> tools;

  /**
   * Create a new template with the given messages.
   *
   * @param messages the messages to use in the template.
   * @return the updated template.
   */
  @Nonnull
  public LocalPromptTemplate withMessages(@Nonnull final Message... messages) {
    return this.withChatMessages(Stream.of(messages).map(Message::createChatMessage).toList());
  }

  /**
   * Create a low-level representation of the template.
   *
   * @return the low-level representation of the template.
   */
  @Override
  @Nonnull
  protected PromptTemplatingModuleConfigPrompt toLowLevel() {
    final List<ChatMessage> template = this.chatMessages != null ? this.chatMessages : List.of();
    final Map<String, String> defaults = this.defaults != null ? this.defaults : new HashMap<>();
    final List<ChatCompletionTool> tools = this.tools != null ? this.tools : new ArrayList<>();
    return Template.create()
        .template(template)
        .defaults(defaults)
        .responseFormat(responseFormat)
        .tools(tools);
  }

  /**
   * Set the response format to the given JSON schema.
   *
   * @param schema the JSON schema to use.
   * @return the updated template.
   */
  @Nonnull
  public LocalPromptTemplate withJsonSchemaResponse(@Nonnull final ResponseJsonSchema schema) {
    val responseFormatJsonSchema =
        ResponseFormatJsonSchema.create()
            .type(ResponseFormatJsonSchema.TypeEnum.JSON_SCHEMA)
            .jsonSchema(
                ResponseFormatJsonSchemaJsonSchema.create()
                    .name(schema.getName())
                    .schema(schema.getSchemaMap())
                    .strict(schema.getStrict())
                    .description(schema.getDescription()));
    return this.withResponseFormat(responseFormatJsonSchema);
  }

  /**
   * Set the response format to JSON object.
   *
   * @return the updated template.
   */
  @Nonnull
  public LocalPromptTemplate withJsonResponse() {
    val responseFormatJsonObject =
        ResponseFormatJsonObject.create().type(ResponseFormatJsonObject.TypeEnum.JSON_OBJECT);
    return this.withResponseFormat(responseFormatJsonObject);
  }
}
