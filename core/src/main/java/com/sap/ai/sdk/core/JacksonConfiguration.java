package com.sap.ai.sdk.core;

import static com.fasterxml.jackson.databind.MapperFeature.DEFAULT_VIEW_INCLUSION;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/** Internal utility class for getting a default object mapper with preset configuration. */
@NoArgsConstructor(access = AccessLevel.NONE)
public final class JacksonConfiguration {

  /**
   * Default object mapper used for JSON de-/serialization. <b>Only intended for internal usage
   * within this SDK</b>. Largely follows the defaults set by Spring.
   *
   * @return A new object mapper with the default configuration.
   * @see <a
   *     href="https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/converter/json/Jackson2ObjectMapperBuilder.html">Jackson2ObjectMapperBuilder</a>
   */
  @Nonnull
  public static JsonMapper getDefaultObjectMapper() {
    return JsonMapper.builder()
        .addModule(new JavaTimeModule())
        .addModule(new ParameterNamesModule())
        // Disable automatic detection of getters and setters
        .visibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE)
        .visibility(PropertyAccessor.SETTER, JsonAutoDetect.Visibility.NONE)
        .serializationInclusion(JsonInclude.Include.NON_NULL)
        // for generated code unknown properties should always be stored as custom fields
        // still added for any non-generated code and additional safety
        .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        .disable(DEFAULT_VIEW_INCLUSION)
        .build();
  }
}
