package com.sap.ai.sdk.app.controllers;

import static com.sap.ai.sdk.core.Core.getClient;
import static com.sap.ai.sdk.core.Core.getDestination;

import com.sap.ai.sdk.core.client.ModelApi;
import com.sap.ai.sdk.core.client.model.AiModelList;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import javax.annotation.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/** Endpoint for Available Models */
@RestController
@SuppressWarnings("unused")
public class ModelsController {

  private static final ModelApi API =
      new ModelApi(
          getClient(
              // TODO: Remove this destination header once the spec is fixed
              DefaultHttpDestination.fromDestination(getDestination())
                  .header("AI-Resource-Group", "default")
                  .build()));

  /**
   * Get the list of available models
   *
   * @return the list of available models
   */
  @GetMapping("/models")
  @Nullable
  public AiModelList getModels() {
    return API.modelsGet("foundation-models");
  }
}
