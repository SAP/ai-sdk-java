package com.sap.ai.sdk.foundationmodels.openai.realtime;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RealtimeParamTurnDetectionUnitTest {

  private RealtimeParamTurnDetection random;
  private String expectedRandomValue;

  @BeforeEach
  void setUp() {
    expectedRandomValue = UUID.randomUUID().toString().substring(0, 20);
    random = new RealtimeParamTurnDetection(expectedRandomValue);
  }

  @Test
  void getParamName() {
    assertThat(random.getParamName()).isEqualTo(RealtimeParam.ParamName.TURN_DETECTION);
    assertThat(RealtimeParamTurnDetection.BY_MODEL_AUTO.getParamName())
        .isEqualTo(RealtimeParam.ParamName.TURN_DETECTION);
    assertThat(RealtimeParamTurnDetection.EACH_CALL_IS_A_TURN.getParamName())
        .isEqualTo(RealtimeParam.ParamName.TURN_DETECTION);
  }

  @Test
  void getValueAsString() {
    assertThat(random.getValueAsString()).isEqualTo(expectedRandomValue);
    assertThat(RealtimeParamTurnDetection.BY_MODEL_AUTO.getValueAsString())
        .isEqualTo("BY_MODEL_AUTO");
    assertThat(RealtimeParamTurnDetection.EACH_CALL_IS_A_TURN.getValueAsString())
        .isEqualTo("EACH_CALL_IS_A_TURN");
  }

  @Test
  void testEquals() {
    assertThat(random).isEqualTo(random);
    assertThat(RealtimeParamTurnDetection.EACH_CALL_IS_A_TURN)
        .isEqualTo(RealtimeParamTurnDetection.EACH_CALL_IS_A_TURN);
    assertThat(RealtimeParamTurnDetection.BY_MODEL_AUTO)
        .isEqualTo(RealtimeParamTurnDetection.BY_MODEL_AUTO);
    assertThat(random).isNotEqualTo(RealtimeParamTurnDetection.BY_MODEL_AUTO);
    assertThat(random).isNotEqualTo(RealtimeParamTurnDetection.EACH_CALL_IS_A_TURN);
    assertThat(RealtimeParamTurnDetection.EACH_CALL_IS_A_TURN)
        .isNotEqualTo(RealtimeParamTurnDetection.BY_MODEL_AUTO);
  }

  @Test
  void testHashCode() {
    assertThat(random.hashCode()).isEqualTo(random.hashCode());
    assertThat(RealtimeParamTurnDetection.EACH_CALL_IS_A_TURN.hashCode())
        .isEqualTo(RealtimeParamTurnDetection.EACH_CALL_IS_A_TURN.hashCode());
    assertThat(RealtimeParamTurnDetection.BY_MODEL_AUTO.hashCode())
        .isEqualTo(RealtimeParamTurnDetection.BY_MODEL_AUTO.hashCode());
  }
}
