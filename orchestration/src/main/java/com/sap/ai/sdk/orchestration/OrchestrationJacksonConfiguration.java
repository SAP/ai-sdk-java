package com.sap.ai.sdk.orchestration;

import static com.sap.ai.sdk.core.JacksonConfiguration.getDefaultObjectMapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.common.annotations.Beta;
import com.sap.ai.sdk.orchestration.model.ChatMessage;
import com.sap.ai.sdk.orchestration.model.TemplateResponseFormat;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.val;

/**
 * Internal utility class for getting a default object mapper with preset configuration.
 *
 * @since 1.2.0
 */
@Beta
@NoArgsConstructor(access = AccessLevel.NONE)
public class OrchestrationJacksonConfiguration {

  /**
   * Default object mapper used for JSON de-/serialization. <b>Only intended for internal usage
   * within this SDK</b>. Largely follows the defaults set by Spring.
   *
   * @return A new object mapper with the default configuration.
   * @see <a
   *     href="https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/converter/json/Jackson2ObjectMapperBuilder.html">Jackson2ObjectMapperBuilder</a>
   */
  @Nonnull
  @Beta
  public static ObjectMapper getOrchestrationObjectMapper() {

    val jackson = getDefaultObjectMapper();

    jackson.addMixIn(ChatMessage.class, JacksonMixins.ChatMessageMixin.class);

    final var module =
        new SimpleModule()
            .addDeserializer(
                TemplateResponseFormat.class,
                PolymorphicFallbackDeserializer.fromJsonSubTypes(TemplateResponseFormat.class))
            .setMixInAnnotation(
                TemplateResponseFormat.class, JacksonMixins.ResponseFormatSubTypesMixin.class);
    jackson.registerModule(module);
    return jackson;
  }
}
