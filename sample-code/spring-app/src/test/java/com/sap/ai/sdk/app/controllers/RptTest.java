package com.sap.ai.sdk.app.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import com.sap.ai.sdk.app.services.RptService;
import org.junit.jupiter.api.Test;

class RptTest {
  @Test
  void testRpt() {
    final var service = new RptService();
    final var response = service.predict();

    assertThat(response).isNotNull();
    assertThat(response.getMetadata().getNumRows()).isEqualTo(2);
    assertThat(response.getMetadata().getNumColumns()).isEqualTo(5);
    assertThat(response.getMetadata().getNumQueryRows()).isEqualTo(1);
    assertThat(response.getMetadata().getNumPredictions()).isEqualTo(1);
    assertThat(response.getPredictions().get(0)).containsKey("COSTCENTER");
  }
}
