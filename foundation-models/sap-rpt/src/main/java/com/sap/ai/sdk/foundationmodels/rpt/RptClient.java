package com.sap.ai.sdk.foundationmodels.rpt;

import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.core.DeploymentResolutionException;
import com.sap.ai.sdk.foundationmodels.rpt.generated.client.PredictApi;
import com.sap.ai.sdk.foundationmodels.rpt.generated.model.TabCompletionPostRequest;
import com.sap.ai.sdk.foundationmodels.rpt.generated.model.TabCompletionPostResponse;
import com.sap.cloud.sdk.cloudplatform.connectivity.Destination;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/** Client for interacting with SAP RPT foundation models. */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class RptClient {
  @Nonnull private final PredictApi api;

  /**
   * Creates a new RptClient for the specified foundation model.
   *
   * @param foundationModel The foundation model to use.
   * @return A new instance of RptClient.
   * @throws DeploymentResolutionException If there is an error resolving the deployment.
   */
  @Nonnull
  public static RptClient forModel(@Nonnull final RptModel foundationModel)
      throws DeploymentResolutionException {
    final var destination = new AiCoreService().getInferenceDestination().forModel(foundationModel);
    return forDestination(destination);
  }

  /**
   * Creates a new RptClient for the specified destination.
   *
   * @param destination The destination to use.
   * @return A new instance of RptClient.
   */
  static RptClient forDestination(@Nonnull final Destination destination) {
    return new RptClient(new PredictApi(destination));
  }

  /**
   * Predict targets using SAP RPT model with structured data.
   *
   * <p>
   *
   * <p><b>200</b> - Successful response with predictive insights.
   *
   * <p><b>400</b> - Bad Request - Invalid input.
   *
   * <p><b>422</b> - Unprocessable Content - Invalid input.
   *
   * <p><b>500</b> - Internal Server Error.
   *
   * @param requestBody The value for the parameter tabCompletionPostRequest
   * @return TabCompletionPostResponse
   */
  @Nonnull
  public TabCompletionPostResponse tabCompletion(
      @Nonnull final TabCompletionPostRequest requestBody) {
    return api.completionsCreate(requestBody);
  }
}
