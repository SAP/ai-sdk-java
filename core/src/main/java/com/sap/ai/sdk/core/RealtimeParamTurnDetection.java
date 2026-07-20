package com.sap.ai.sdk.core;

import java.util.Objects;
import javax.annotation.Nonnull;

/**
 * Allows to configure turn detection (how model responds).
 */
public final class RealtimeParamTurnDetection implements RealtimeParam {

  /**
   * Model tries to recognize if/when it should respond automatically
   */
  public static final RealtimeParamTurnDetection BY_MODEL_AUTO =
      new RealtimeParamTurnDetection("BY_MODEL_AUTO");

  /**
   * Each call to the provided realtime client is considered a turn (eager explicit turn detection).
   * Less convenient than the automatic option but may give lower latency in some cases (model does not need
   * to perform additional turn detection analysis).
   */
  public static final RealtimeParamTurnDetection EACH_CALL_IS_A_TURN =
      new RealtimeParamTurnDetection("EACH_CALL_IS_A_TURN");

  private final String turnDetectionKind;

  private RealtimeParamTurnDetection(String turnDetectionKind) {
    this.turnDetectionKind = turnDetectionKind;
  }

  @Override
  public @Nonnull SpeechOutputParamName getParamName() {
    return SpeechOutputParamName.TURN_DETECTION;
  }

  @Override
  public @Nonnull String getValueAsString() {
    return turnDetectionKind;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    RealtimeParamTurnDetection that = (RealtimeParamTurnDetection) o;
    return Objects.equals(turnDetectionKind, that.turnDetectionKind);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(turnDetectionKind);
  }
}
