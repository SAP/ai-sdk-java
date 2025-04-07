package com.sap.ai.sdk.foundationmodels.openai;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.annotations.Beta;
import java.util.Map;
import javax.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * Represents a function type tool called by an OpenAI model.
 *
 * @since 1.6.0
 */
@Beta
@Value
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
public class OpenAiFunctionCall implements OpenAiToolCall {
  /** The unique identifier for the function call. */
  @Nonnull String id;

  /** The name of the function to be called. */
  @Nonnull String name;

  /** The arguments for the function call, encoded as a JSON string. */
  @Nonnull String arguments;

  /**
   * Returns the arguments as a {@code Map<String, Object>}.
   *
   * @return the arguments as a map
   * @throws IllegalArgumentException if parsing fails
   */
  public Map<String, Object> getArgumentsAsMap() throws IllegalArgumentException {
    try {
      return OpenAiUtils.getOpenAiObjectMapper()
          .readValue(getArguments(), new TypeReference<Map<String, Object>>() {});
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException(
          "Failed to parse given JSON string to Map<String, Object>", e);
    }
  }

  /**
   * Returns the arguments as an object of the specified class.
   *
   * @param clazz the class to convert the arguments to
   * @param <T> the type of the class
   * @return the arguments as an object of the specified class
   * @throws IllegalArgumentException if parsing fails
   */
  public <T> T getArgumentsAsObject(Class<T> clazz) throws IllegalArgumentException {
    try {
      return OpenAiUtils.getOpenAiObjectMapper().readValue(getArguments(), clazz);
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException(
          "Failed to parse given JSON string to " + clazz.getTypeName(), e);
    }
  }
}
