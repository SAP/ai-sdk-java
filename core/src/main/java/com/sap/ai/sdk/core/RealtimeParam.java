package com.sap.ai.sdk.core;

import javax.annotation.Nonnull;

public interface RealtimeParam {
  enum SpeechOutputParamName {
    VOICE,
    TURN_DETECTION,
  }

  @Nonnull
  SpeechOutputParamName getParamName();

  @Nonnull
  String getValueAsString();
}
