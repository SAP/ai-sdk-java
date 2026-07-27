package com.sap.ai.sdk.foundationmodels.openai.realtime;

import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** Allows to configure model system prompt */
public final class RealtimeParamSystemPrompt implements RealtimeParam {

  private final String systemPrompt;

  /**
   * Constructs RealtimeParamSystemPrompt object
   *
   * @param systemPrompt system prompt to use
   */
  public RealtimeParamSystemPrompt(@Nonnull final String systemPrompt) {
    this.systemPrompt = systemPrompt;
  }

  @Override
  public @Nonnull ParamName getParamName() {
    return ParamName.SYSTEM_PROMPT;
  }

  @Override
  public @Nonnull String getValueAsString() {
    return systemPrompt;
  }

  @Override
  public boolean equals(@Nullable final Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final RealtimeParamSystemPrompt that = (RealtimeParamSystemPrompt) o;
    return Objects.equals(systemPrompt, that.systemPrompt);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(systemPrompt);
  }
}
