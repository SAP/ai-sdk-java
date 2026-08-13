package com.sap.ai.sdk.foundationmodels.openai.realtime;

import com.google.common.annotations.Beta;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** Allows to configure model output voice */
@Beta
public final class RealtimeParamVoice implements RealtimeParam {

  /** Standard voice 1 */
  @Beta public static final RealtimeParamVoice DEFAULT_1 = new RealtimeParamVoice("DEFAULT_1");

  /** Standard voice 2 */
  @Beta public static final RealtimeParamVoice DEFAULT_2 = new RealtimeParamVoice("DEFAULT_2");

  private final String voice;

  RealtimeParamVoice(@Nonnull final String voice) {
    this.voice = voice;
  }

  /**
   * Allows to configure raw voice name as named by model provider. Unsafe because SDK cannot verify
   * in advance if the provided voice name is correct and supported by the chosen model and use case
   * NOTE: this method does not check voice name and incorrect input may produce runtime exceptions
   * (unsafe)
   *
   * @param voiceName as named by model provider
   * @return typed voice client configuration param
   */
  @Nonnull
  @Beta
  public static RealtimeParamVoice withExplicitVoice(@Nonnull final String voiceName) {
    return new RealtimeParamVoice(voiceName);
  }

  @Override
  @Beta
  public @Nonnull ParamName getParamName() {
    return ParamName.OUTPUT_VOICE;
  }

  @Override
  @Beta
  public @Nonnull String getValueAsString() {
    return voice;
  }

  @Override
  @Beta
  public boolean equals(@Nullable final Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final RealtimeParamVoice that = (RealtimeParamVoice) o;
    return Objects.equals(voice, that.voice);
  }

  @Override
  @Beta
  public int hashCode() {
    return Objects.hashCode(voice);
  }
}
