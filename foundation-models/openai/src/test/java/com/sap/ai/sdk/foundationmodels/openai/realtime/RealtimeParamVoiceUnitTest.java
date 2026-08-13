package com.sap.ai.sdk.foundationmodels.openai.realtime;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RealtimeParamVoiceUnitTest {

  private RealtimeParamVoice random;
  private String expectedRandomValue;

  @BeforeEach
  void setUp() {
    expectedRandomValue = UUID.randomUUID().toString().substring(0, 20);
    random = new RealtimeParamVoice(expectedRandomValue);
  }

  @Test
  void withExplicitVoice() {
    assertThat(random).isEqualTo(RealtimeParamVoice.withExplicitVoice(expectedRandomValue));
  }

  @Test
  void getParamName() {
    assertThat(random.getParamName()).isEqualTo(RealtimeParam.ParamName.OUTPUT_VOICE);
    assertThat(RealtimeParamVoice.DEFAULT_1.getParamName())
        .isEqualTo(RealtimeParam.ParamName.OUTPUT_VOICE);
    assertThat(RealtimeParamVoice.DEFAULT_1.getParamName())
        .isEqualTo(RealtimeParam.ParamName.OUTPUT_VOICE);
  }

  @Test
  void getValueAsString() {
    assertThat(random.getValueAsString()).isEqualTo(expectedRandomValue);
    assertThat(RealtimeParamVoice.DEFAULT_1.getValueAsString()).isEqualTo("DEFAULT_1");
    assertThat(RealtimeParamVoice.DEFAULT_2.getValueAsString()).isEqualTo("DEFAULT_2");
  }

  @Test
  void testEquals() {
    assertThat(random).isEqualTo(random);
    assertThat(RealtimeParamVoice.DEFAULT_1).isEqualTo(RealtimeParamVoice.DEFAULT_1);
    assertThat(RealtimeParamVoice.DEFAULT_2).isEqualTo(RealtimeParamVoice.DEFAULT_2);
    assertThat(random).isNotEqualTo(RealtimeParamVoice.DEFAULT_1);
    assertThat(random).isNotEqualTo(RealtimeParamVoice.DEFAULT_2);
    assertThat(RealtimeParamVoice.DEFAULT_1).isNotEqualTo(RealtimeParamVoice.DEFAULT_2);
  }

  @Test
  void testHashCode() {
    assertThat(random.hashCode()).isEqualTo(random.hashCode());
    assertThat(RealtimeParamVoice.DEFAULT_1.hashCode())
        .isEqualTo(RealtimeParamVoice.DEFAULT_1.hashCode());
    assertThat(RealtimeParamVoice.DEFAULT_2.hashCode())
        .isEqualTo(RealtimeParamVoice.DEFAULT_2.hashCode());
  }
}
