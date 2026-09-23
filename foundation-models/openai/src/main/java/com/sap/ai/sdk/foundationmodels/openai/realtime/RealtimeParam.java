package com.sap.ai.sdk.foundationmodels.openai.realtime;

import javax.annotation.Nonnull;

/** Represents possible configuration params of realtime client Internal sdk usage only */
public abstract class RealtimeParam {
  /** Represents configurable options */
  enum ParamName {
    /** Voice name to use to produce sound */
    OUTPUT_VOICE,
    /**
     * How model will recognize that it is its turn to respond (e.g. explicitly asked, automatically
     * detected)
     */
    TURN_DETECTION,
    /** Override or specify system prompt given to a model */
    SYSTEM_PROMPT,
  }

  /** Internal use only. Can only be extended or constructed in the same package. */
  RealtimeParam() {}

  /**
   * Returns param name
   *
   * @return name
   */
  @Nonnull
  abstract ParamName getParamName();

  /**
   * Returns string value representation of the param
   *
   * @return string value
   */
  @Nonnull
  abstract String getValueAsString();
}
