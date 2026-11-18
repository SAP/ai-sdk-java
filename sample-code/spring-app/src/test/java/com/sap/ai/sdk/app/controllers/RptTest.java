package com.sap.ai.sdk.app.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import com.sap.ai.sdk.app.services.RptService;
import com.sap.ai.sdk.foundationmodels.rpt.decisions.generated.model.ChoiceAnswer;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class RptTest {
  final RptService service = new RptService();

  @Test
  void testDecision() {
    final var response = service.decision();

    assertThat(response).isNotNull();
    assertThat(response.getAnswers()).containsOnlyKeys("COSTCENTER");
    assertThat(response.getAnswers().get("COSTCENTER"))
        .isInstanceOfSatisfying(
            ChoiceAnswer.class,
            answer -> {
              assertThat(answer.getType()).isEqualTo(ChoiceAnswer.TypeEnum.CHOICE);
              assertThat(answer.getChoice())
                  .isIn(
                      "Office Furniture",
                      "Data Infrastructure"); // Ideally a couch is only "Office Furniture"...
              assertThat(answer.getProbabilities())
                  .containsOnlyKeys("Office Furniture", "Data Infrastructure")
                  .allSatisfy((option, probability) ->
                      assertThat(probability).isBetween(BigDecimal.ZERO, BigDecimal.ONE));
              assertThat(answer.getConfidence()).isBetween(BigDecimal.ZERO, BigDecimal.ONE);
            });
  }

  @Test
  void testSimplePrediction() {
    final var response = service.predict();

    assertThat(response).isNotNull();
    assertThat(response.getMetadata().getNumRows()).isEqualTo(3);
    assertThat(response.getMetadata().getNumColumns()).isEqualTo(5);
    assertThat(response.getMetadata().getNumQueryRows()).isEqualTo(1);
    assertThat(response.getMetadata().getNumPredictions()).isEqualTo(1);
    assertThat(response.getPredictions().get(0)).containsKey("COSTCENTER");
    assertThat(response.getExplanations()).isNotNull();
  }

  @Test
  void testPredictWithParquet() {
    final var response = service.predictParquet();

    assertThat(response).isNotNull();
    assertThat(response.getMetadata().getNumRows()).isEqualTo(3);
    assertThat(response.getMetadata().getNumColumns()).isEqualTo(5);
    assertThat(response.getMetadata().getNumQueryRows()).isEqualTo(1);
    assertThat(response.getMetadata().getNumPredictions()).isEqualTo(1);
    assertThat(response.getPredictions().get(0)).containsKey("COSTCENTER");
    assertThat(response.getExplanations()).isNotNull();
  }
}
