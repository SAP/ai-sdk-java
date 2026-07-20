package com.sap.ai.sdk.core;

import javax.annotation.Nonnull;
import java.util.Objects;

public final class RealtimeParamTurnDetection implements RealtimeParam {

  public static final RealtimeParamTurnDetection BY_MODEL_AUTO =
      new RealtimeParamTurnDetection("BY_MODEL_AUTO");
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
