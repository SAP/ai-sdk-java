package com.sap.ai.sdk.orchestration;

import java.util.Map;
import java.util.Objects;
import javax.annotation.Nonnull;

/**
 * A template variable that can be used within orchestration prompts.
 *
 * @param name The name of the variable.
 */
public record TemplateVariable(@Nonnull String name) {

  @Override
  @Nonnull
  public String toString() {
    return "{{?%s}}".formatted(name);
  }

  /**
   * Convert the given template into an {@link Map.Entry} containing variable name and value.
   *
   * @param value The value to assign to the variable.
   * @return An {@link Map.Entry} containing the variable name and value.
   * @see OrchestrationPrompt#OrchestrationPrompt(Map)
   */
  @Nonnull
  public Map.Entry<String, String> apply(@Nonnull final Object value) {
    return Map.entry(name, Objects.toString(value));
  }
}
