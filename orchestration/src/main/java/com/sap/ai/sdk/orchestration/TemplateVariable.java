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
  // TODO: test
  // TODO: discuss: do we really want to offer this?
  @Nonnull String name;

  @Nonnull
  public static TemplateVariable of(@Nonnull String name) {
    return new TemplateVariable(name);
  }

  @Override
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