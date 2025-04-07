package com.sap.ai.sdk.foundationmodels.openai;

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
   * @return the parsed arguments
   * @throws IllegalArgumentException if parsing fails
   * @since 1.7.0
   */
  @Nonnull
  public Map<String, Object> getArgumentsAsMap() throws IllegalArgumentException {
    return OpenAiUtils.parseJson(getArguments(), new TypeReference<>() {});
  }

  /**
   * Returns the arguments as an object of the specified class.
   *
   * @param clazz the class to convert the arguments to
   * @param <T> the type of the class
   * @return the arguments as an object of the specified class
   * @throws IllegalArgumentException if parsing fails
   * @since 1.7.0
   */
  @Nonnull
  public <T> T getArgumentsAsObject(@Nonnull final Class<T> clazz) throws IllegalArgumentException {
    return OpenAiUtils.parseJson(getArguments(), clazz);
  }
}
