package com.sap.ai.sdk.foundationmodels.openai.realtime;

import com.google.common.annotations.Beta;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** Allows to configure model system prompt */
@Beta
public final class RealtimeParamSystemPrompt implements RealtimeParam {

  private final String systemPrompt;

  /**
   * Constructs RealtimeParamSystemPrompt object
   *
   * @param systemPrompt system prompt to use
   */
  @Beta
  public RealtimeParamSystemPrompt(@Nonnull final String systemPrompt) {
    this.systemPrompt = systemPrompt;
  }

  @Override
  @Beta
  public @Nonnull ParamName getParamName() {
    return ParamName.SYSTEM_PROMPT;
  }

  @Override
  @Beta
  public @Nonnull String getValueAsString() {
    return systemPrompt;
  }

  @Override
  @Beta
  public boolean equals(@Nullable final Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final RealtimeParamSystemPrompt that = (RealtimeParamSystemPrompt) o;
    return Objects.equals(systemPrompt, that.systemPrompt);
  }

  @Override
  @Beta
  public int hashCode() {
    return Objects.hashCode(systemPrompt);
  }
}
