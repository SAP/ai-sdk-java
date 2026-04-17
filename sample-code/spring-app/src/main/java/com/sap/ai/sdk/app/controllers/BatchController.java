package com.sap.ai.sdk.app.controllers;

import com.sap.ai.sdk.batch.generated.client.BatchesApi;
import com.sap.ai.sdk.batch.generated.model.BatchListResponse;
import com.sap.ai.sdk.core.AiCoreService;
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
    return CLIENT.listBatches();
  }
}
