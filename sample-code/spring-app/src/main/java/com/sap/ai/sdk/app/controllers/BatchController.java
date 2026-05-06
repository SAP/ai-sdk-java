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
import com.sap.ai.sdk.core.AiCoreService;
import java.util.UUID;
import javax.annotation.Nonnull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** LLM Batch Service API for managing LLM batch processing jobs */
@RestController
@SuppressWarnings("unused")
@RequestMapping("/batch")
public class BatchController {

  private static final BatchesApi CLIENT = new BatchesApi(new AiCoreService().getApiClient());
  public static final String RESOURCE_GROUP = "ai-sdk-java-e2e";

  /**
   * Create a new batch job
   *
   * @return response object
   */
  @GetMapping("/create")
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
   * List all batches
   *
   * @return response object
   */
  @GetMapping("list")
  public BatchListResponse list() {
    return CLIENT.listBatches(RESOURCE_GROUP);
  }

  /**
   * Get a batch job for an id
   *
   * @param id the id of the batch job
   * @return the response object
   */
  @GetMapping("/get/{id}")
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
  public BatchDeleteResponse delete(@Nonnull @PathVariable("id") final String id) {
    return CLIENT.deleteBatch(RESOURCE_GROUP, UUID.fromString(id));
  }
}
