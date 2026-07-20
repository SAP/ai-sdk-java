package com.sap.ai.sdk.core;

import java.util.Objects;
import javax.annotation.Nonnull;

/** Allows to configure model output voice */
public final class RealtimeParamVoice implements RealtimeParam {

  /** Standard voice 1 */
  public static final RealtimeParamVoice DEFAULT_1 = new RealtimeParamVoice("DEFAULT_1");

  /** Standard voice 2 */
  public static final RealtimeParamVoice DEFAULT_2 = new RealtimeParamVoice("DEFAULT_2");

  private final String voice;

  private RealtimeParamVoice(@Nonnull String voice) {
    this.voice = voice;
  }

  /**
   * Allows to configure raw voice name as named by model provider. Unsafe because SDK cannot verify
   * in advance if the provided voice name is correct and supported by the chosen model and use case
   *
   * @param voiceName as named by model provider
   * @return typed voice client configuration param
   */
  public static RealtimeParamVoice unsafeWithExplicitVoice(String voiceName) {
    return new RealtimeParamVoice(voiceName);
  }

  @Override
  public @Nonnull SpeechOutputParamName getParamName() {
    return SpeechOutputParamName.VOICE;
  }

  @Override
  public @Nonnull String getValueAsString() {
    return voice;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    RealtimeParamVoice that = (RealtimeParamVoice) o;
    return Objects.equals(voice, that.voice);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(voice);
  }
}
