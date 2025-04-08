package com.sap.ai.sdk.foundationmodels.openai;

import static com.sap.ai.sdk.foundationmodels.openai.OpenAiUtils.getOpenAiObjectMapper;

import com.fasterxml.jackson.core.JsonProcessingException;
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
   * Parses the arguments, encoded as a JSON string, into a {@code Map<String, Object>}.
   *
   * @return a map of the arguments
   * @throws IllegalArgumentException if parsing fails
   * @since 1.7.0
   */
  @Nonnull
  public Map<String, Object> getArgumentsAsMap() throws IllegalArgumentException {
    return getArgumentsAsObject(Map.class);
  }

  /**
   * Parses the arguments, encoded as a JSON string, into an object of the specified type.
   *
   * @param clazz the class to convert the arguments to
   * @param <T> the type of the class
   * @return the parsed arguments as an object
   * @throws IllegalArgumentException if parsing fails
   * @since 1.7.0
   */
  @Nonnull
  public <T> T getArgumentsAsObject(@Nonnull final Class<T> clazz) throws IllegalArgumentException {
    try {
      return getOpenAiObjectMapper().readValue(getArguments(), clazz);
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException(
          "Failed to parse JSON string to class " + clazz.getTypeName(), e);
    }
  }
}
