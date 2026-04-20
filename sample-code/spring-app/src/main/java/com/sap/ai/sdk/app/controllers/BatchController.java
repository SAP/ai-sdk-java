package com.sap.ai.sdk.app.controllers;

import com.sap.ai.sdk.batch.generated.client.BatchesApi;
import com.sap.ai.sdk.batch.generated.model.BatchCreateRequest;
import com.sap.ai.sdk.batch.generated.model.BatchCreateRequest.TypeEnum;
import com.sap.ai.sdk.batch.generated.model.BatchCreateRequestInput;
import com.sap.ai.sdk.batch.generated.model.BatchCreateRequestOutput;
import com.sap.ai.sdk.batch.generated.model.BatchCreateRequestSpec;
import com.sap.ai.sdk.batch.generated.model.BatchListResponse;
import com.sap.ai.sdk.core.AiCoreService;
import lombok.val;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SuppressWarnings("unused")
@RequestMapping("/batch")
public class BatchController {

  private static final BatchesApi CLIENT = new BatchesApi(new AiCoreService().getApiClient());

  @GetMapping("/test")
  public BatchListResponse test() {
    val a =
        CLIENT.createBatch(
            BatchCreateRequest.create()
                .type(TypeEnum.LLM_NATIVE)
                .input(BatchCreateRequestInput.create().uri(""))
                .output(BatchCreateRequestOutput.create().uri(""))
                .spec(BatchCreateRequestSpec.create().provider("").model("")));
    return CLIENT.listBatches();
  }
}
