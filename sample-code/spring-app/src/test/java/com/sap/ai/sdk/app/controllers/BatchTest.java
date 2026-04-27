package com.sap.ai.sdk.app.controllers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import lombok.val;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class BatchTest {
  static BatchController controller = new BatchController();

  @BeforeAll
  public static void yesterdaysCleanup() {
    for (val listResponse : controller.list().getResources()) {
      if (listResponse.getCreatedAt().isBefore(java.time.OffsetDateTime.now().minusDays(2))
          && (listResponse.getStatus().equals("CANCELLED")
              || listResponse.getStatus().equals("COMPLETED")
              || listResponse.getStatus().equals("FAILED"))) {
        controller.delete(listResponse.getId().toString());
      }
    }
  }

  @Test
  public void testBatch() {
    val createResponse = controller.create();
    assertThat(createResponse.getMessage()).contains("Batch job scheduled");
    assertThat(createResponse.getStatus()).isEqualTo("PENDING");
    val id = createResponse.getId().toString();

    val getResponse = controller.get(id);
    assertThat(getResponse.getStatus()).isEqualTo("IN_PROGRESS");
  }
}
