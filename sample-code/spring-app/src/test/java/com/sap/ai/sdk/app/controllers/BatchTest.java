package com.sap.ai.sdk.app.controllers;

import static com.sap.ai.sdk.app.controllers.BatchController.RESOURCE_GROUP;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.sap.ai.sdk.core.client.FileApi;
import com.sap.cloud.sdk.services.openapi.apache.core.OpenApiRequestException;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import lombok.val;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class BatchTest {

  static BatchController controller = new BatchController();
  static FileApi fileApi = new FileApi();

  /** Directory path for batch output files in the object store. */
  private static final String S3_DIRECTORY = "s3secret//";

  /** Batch output file name */
  public static final String OUTPUT_JSONL = "/output.jsonl";

  @BeforeAll
  public static void yesterdaysCleanup() {
    for (val listResponse : controller.list().getResources()) {
      if (listResponse.getCreatedAt().isBefore(java.time.OffsetDateTime.now().minusSeconds(1))
          && (listResponse.getStatus().equals("CANCELLED")
              || listResponse.getStatus().equals("COMPLETED")
              || listResponse.getStatus().equals("FAILED"))) {
        String id = listResponse.getId().toString();
        val response = controller.delete(id);
        System.out.println("Delete batch: " + response.getMessage());

        // Clean up old batch output files from object store.
        // Object store content can accessed from a S3 bucket reader with the following credentials
        // BTP Cockpit -> Instances -> s3 -> credentials
        String filePath = S3_DIRECTORY + id + OUTPUT_JSONL;
        try {
          fileApi.delete(filePath, RESOURCE_GROUP);
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

  /** Validate batch output by downloading the file from the object store */
  @Disabled("Waiting for completion takes a few minutes, too slow")
  @Test
  public void validateOutput() {
    String batchId = "8594aa58-f275-4b23-aa5f-aa2d7389bd0b";

    String filePath = S3_DIRECTORY + batchId + OUTPUT_JSONL;
    try {
      File downloadedFile = fileApi.download(filePath, RESOURCE_GROUP);
      byte[] content = Files.readAllBytes(downloadedFile.toPath());

      assertThat(new String(content, StandardCharsets.UTF_8)).isNotBlank();
      System.out.println("\n=== " + filePath + " ===");
      if (content.length == 0) {
        System.out.println("(empty file)");
      } else {
        String contentStr = new String(content, StandardCharsets.UTF_8);
        System.out.println(contentStr);
      }

      // Clean up temporary file
      boolean deleted = downloadedFile.delete();
      assertThat(deleted).isTrue();

    } catch (OpenApiRequestException e) {
      System.err.println("Error downloading file " + filePath + ": " + e.getMessage());
    } catch (Exception e) {
      System.err.println("Error reading file " + filePath + ": " + e.getMessage());
    }
  }
}
