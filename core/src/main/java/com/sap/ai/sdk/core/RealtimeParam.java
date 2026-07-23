package com.sap.ai.sdk.core;

import javax.annotation.Nonnull;

/** Represents possible configuration params of realtime client */
public interface RealtimeParam {
  /**
   * Represents configurable options
   */
  enum SpeechOutputParamName {
    /**
     * Voice name to use to produce sound
     */
    VOICE,
    /**
     * How model will recognize that it is its turn to respond (e.g. explicitly asked, automatically detected)
     */
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
