package com.sap.ai.sdk.core;

import java.util.Objects;
import javax.annotation.Nonnull;
import org.jspecify.annotations.NonNull;

public final class RealtimeParamVoice implements RealtimeParam {

  public static final RealtimeParamVoice DEFAULT_1 =
      new RealtimeParamVoice("DEFAULT_1");
  public static final RealtimeParamVoice DEFAULT_2 =
      new RealtimeParamVoice("DEFAULT_1");

  private final String voice;

  private RealtimeParamVoice(@Nonnull String voice) {
    this.voice = voice;
  }

  public static RealtimeParamVoice unsafeWithExplicitVoice(String voiceName) {
    return new RealtimeParamVoice(voiceName);
  }

  @Override
  public @NonNull SpeechOutputParamName getParamName() {
    return SpeechOutputParamName.VOICE;
  }

  @Override
  public @NonNull String getValueAsString() {
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
