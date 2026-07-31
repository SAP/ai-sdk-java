package com.sap.ai.sdk.foundationmodels.openai.realtime;

import com.google.common.annotations.Beta;
import javax.annotation.Nonnull;

/** Represents possible configuration params of realtime client */
@Beta
public interface RealtimeParam {
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

  /**
   * Returns param name
   *
   * @return name
   */
  @Nonnull
  ParamName getParamName();

  /**
   * Returns string value representation of the param
   *
   * @return string value
   */
  @Nonnull
  String getValueAsString();
}
