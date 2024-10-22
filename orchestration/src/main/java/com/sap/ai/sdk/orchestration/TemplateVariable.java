package com.sap.ai.sdk.orchestration;

import java.util.Map;
import java.util.Objects;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TemplateVariable {
  @Nonnull String name;

  @Nonnull
  public static TemplateVariable of(@Nonnull final String name) {
    return new TemplateVariable(name);
  }

  @Override
  @Nonnull
  public String toString() {
    return "{{?%s}}".formatted(name);
  }

  @Nonnull
  public String toTemplateString() {
    return toString();
  }

  @Nonnull
  public Map.Entry<String, String> apply(@Nonnull final Object value) {
    return Map.entry(name, Objects.toString(value));
  }
}
