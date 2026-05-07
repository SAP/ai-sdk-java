package com.sap.ai.sdk.app.controllers;

import com.sap.ai.sdk.batch.generated.client.BatchesApi;
import com.sap.ai.sdk.batch.generated.model.BatchCreateRequest;
import com.sap.ai.sdk.batch.generated.model.BatchCreateRequest.TypeEnum;
import com.sap.ai.sdk.batch.generated.model.BatchCreateRequestInput;
import com.sap.ai.sdk.batch.generated.model.BatchCreateRequestOutput;
import com.sap.ai.sdk.batch.generated.model.BatchCreateRequestSpec;
import com.sap.ai.sdk.batch.generated.model.BatchCreateResponse;
import com.sap.ai.sdk.batch.generated.model.BatchDeleteResponse;
import com.sap.ai.sdk.batch.generated.model.BatchDetailResponse;
import com.sap.ai.sdk.batch.generated.model.BatchListResponse;
import com.sap.ai.sdk.core.client.FileApi;
import com.sap.cloud.sdk.services.openapi.apache.core.OpenApiRequestException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.UUID;
import javax.annotation.Nonnull;
import lombok.val;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** LLM Batch Service API for managing LLM batch processing jobs */
@RestController
@SuppressWarnings("unused")
@RequestMapping("/batch")
public class BatchController {

  private static final BatchesApi CLIENT = new BatchesApi();

  /** For reading S3 bucket file contents */
  public static FileApi FILE_CLIENT = new FileApi();

  /** Resource group that the S3 Bucket Object store is on */
  public static final String RESOURCE_GROUP = "ai-sdk-java-e2e";

  /** Directory path for batch output files in the object store. */
  public static final String S3_DIRECTORY = "s3secret//";

  /** Batch output file name */
  public static final String OUTPUT_JSONL = "/output.jsonl";

  /**
   * Create a new batch job
   *
   * @return response object
   */
  @GetMapping("/create")
  @Nonnull
  public BatchCreateResponse create() {
    // The S3 Bucket was created in AI Launchpad > AI Core Administration > Object store secrets
    // The credentials can be accessed from the BTP Cockpit > Instances > s3 Object Store
    return CLIENT.createBatch(
        RESOURCE_GROUP,
        BatchCreateRequest.create()
            .type(TypeEnum.LLM_NATIVE)
            .input(BatchCreateRequestInput.create().uri("ai://s3secret/input-batch.jsonl"))
            .output(BatchCreateRequestOutput.create().uri("ai://s3secret/"))
            .spec(BatchCreateRequestSpec.create().provider("azure-openai").model("gpt-4.1")));
  }

  /**
   * List all batch jobs
   *
   * @return response object
   */
  @GetMapping("list")
  @Nonnull
  public BatchListResponse list() {
    return CLIENT.listBatches(RESOURCE_GROUP);
  }

  /**
   * Get batch job for an id
   *
   * @param id the id of the batch job
   * @return the response object
   */
  @GetMapping("/get/{id}")
  @Nonnull
  public BatchDetailResponse get(@Nonnull @PathVariable("id") final String id) {
    return CLIENT.getBatchById(RESOURCE_GROUP, UUID.fromString(id));
  }

  /**
   * Delete batch job for an id
   *
   * @param id the id of the batch job
   * @return the response object
   */
  @GetMapping("delete/{id}")
  @Nonnull
  public BatchDeleteResponse delete(@Nonnull @PathVariable("id") final String id) {
    return CLIENT.deleteBatch(RESOURCE_GROUP, UUID.fromString(id));
  }

  /**
   * Read the content of a batch output in the S3 bucket
   *
   * @param id the id of the batch job
   * @return the content of the batch output file
   */
  @GetMapping("/read/{id}")
  @Nonnull
  public String read(@Nonnull @PathVariable("id") final String id) {
    final UUID validatedId = UUID.fromString(id);
    val filePath = S3_DIRECTORY + validatedId + OUTPUT_JSONL;
    try {
      val downloadedFile = FILE_CLIENT.download(filePath, RESOURCE_GROUP);
      val content = Files.readAllBytes(downloadedFile.toPath());
      // Clean up temporary file
      val ignore = downloadedFile.delete();

      if (content.length == 0) {
        return "No file found";
      } else {
        return new String(content, StandardCharsets.UTF_8);
      }

    } catch (OpenApiRequestException e) {
      return "Error downloading file";
    } catch (Exception e) {
      return "Error reading file";
    }
  }
}
