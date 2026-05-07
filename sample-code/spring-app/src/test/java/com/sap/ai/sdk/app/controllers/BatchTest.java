package com.sap.ai.sdk.app.controllers;

import static com.sap.ai.sdk.app.controllers.BatchController.FILE_CLIENT;
import static com.sap.ai.sdk.app.controllers.BatchController.OUTPUT_JSONL;
import static com.sap.ai.sdk.app.controllers.BatchController.RESOURCE_GROUP;
import static com.sap.ai.sdk.app.controllers.BatchController.S3_DIRECTORY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.sap.cloud.sdk.services.openapi.apache.core.OpenApiRequestException;
import lombok.val;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class BatchTest {

  static BatchController controller = new BatchController();

  @BeforeAll
  public static void yesterdaysCleanup() {
    for (val batchJob : controller.list().getResources()) {
      if (batchJob.getCreatedAt().isBefore(java.time.OffsetDateTime.now().minusDays(1))
          && (batchJob.getStatus().equals("CANCELLED")
              || batchJob.getStatus().equals("COMPLETED")
              || batchJob.getStatus().equals("FAILED"))) {
        String id = batchJob.getId().toString();
        val response = controller.delete(id);
        System.out.println("Delete batch: " + response.getMessage());

        // Clean up old batch output files from object store.
        // Object store content can be accessed from an S3 bucket reader with the following
        // credentials
        // BTP Cockpit -> Instances -> s3 -> credentials
        String filePath = S3_DIRECTORY + id + OUTPUT_JSONL;
        try {
          FILE_CLIENT.delete(filePath, RESOURCE_GROUP);
          System.out.println("Deleted file: " + filePath);
        } catch (OpenApiRequestException e) {
          System.err.println("Error deleting file " + filePath + ": " + e.getMessage());
        }
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
    assertThat(getResponse.getStatus().getCurrentStatus()).isEqualTo("PENDING");
  }
}
