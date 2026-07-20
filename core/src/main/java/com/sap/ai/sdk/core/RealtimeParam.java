package com.sap.ai.sdk.core;

import javax.annotation.Nonnull;

/** Represents possible configuration params of realtime client */
public interface RealtimeParam {
  enum SpeechOutputParamName {
    VOICE,
    TURN_DETECTION,
  }

  /**
   * Returns param name
   *
   * @return name
   */
  @Nonnull
  SpeechOutputParamName getParamName();

  /**
   * Returns string value representation of the param
   *
   * @return string value
   */
  @Nonnull
  String getValueAsString();
}
