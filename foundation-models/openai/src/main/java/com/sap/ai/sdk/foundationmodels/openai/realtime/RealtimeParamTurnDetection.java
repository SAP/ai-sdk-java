package com.sap.ai.sdk.foundationmodels.openai.realtime;

import com.google.common.annotations.Beta;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** Allows to configure turn detection (how model responds). */
@Beta
public final class RealtimeParamTurnDetection implements RealtimeParam {

  /** Model tries to recognize if/when it should respond automatically */
  @Beta
  public static final RealtimeParamTurnDetection BY_MODEL_AUTO =
      new RealtimeParamTurnDetection("BY_MODEL_AUTO");

  /**
   * Each call to the provided realtime client is considered a turn (eager explicit turn detection).
   * Less convenient than the automatic option but may give lower latency in some cases (model does
   * not need to perform additional turn detection analysis).
   */
  @Beta
  public static final RealtimeParamTurnDetection EACH_CALL_IS_A_TURN =
      new RealtimeParamTurnDetection("EACH_CALL_IS_A_TURN");

  private final String turnDetectionKind;

  RealtimeParamTurnDetection(final String turnDetectionKind) {
    this.turnDetectionKind = turnDetectionKind;
  }

  @Override
  @Beta
  public @Nonnull ParamName getParamName() {
    return ParamName.TURN_DETECTION;
  }

  @Override
  @Beta
  public @Nonnull String getValueAsString() {
    return turnDetectionKind;
  }

  @Override
  @Beta
  public boolean equals(@Nullable final Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final RealtimeParamTurnDetection that = (RealtimeParamTurnDetection) o;
    return Objects.equals(turnDetectionKind, that.turnDetectionKind);
  }

  @Override
  @Beta
  public int hashCode() {
    return Objects.hashCode(turnDetectionKind);
  }
}
