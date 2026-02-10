package com.sap.ai.sdk.foundationmodels.rpt;

import static com.sap.ai.sdk.core.JacksonConfiguration.getDefaultObjectMapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.annotations.Beta;
import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.core.DeploymentResolutionException;
import com.sap.ai.sdk.core.JacksonConfiguration;
import com.sap.ai.sdk.foundationmodels.rpt.generated.client.DefaultApi;
import com.sap.ai.sdk.foundationmodels.rpt.generated.model.PredictRequestPayload;
import com.sap.ai.sdk.foundationmodels.rpt.generated.model.PredictResponsePayload;
import com.sap.ai.sdk.foundationmodels.rpt.generated.model.PredictionConfig;
import com.sap.cloud.sdk.cloudplatform.connectivity.Destination;
import com.sap.cloud.sdk.services.openapi.apache.ApiClient;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * Client for interacting with SAP RPT foundation models.
 *
 * @since 1.16.0
 */
@Beta
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class RptClient {
  @Nonnull private final DefaultApi api;

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
    final var apiClient = ApiClient.create(destination).withObjectMapper(getDefaultObjectMapper());
    return new RptClient(new DefaultApi(apiClient));
  }

  /**
   * Predict targets using SAP RPT model with structured data. *
   *
   * <p><b>200</b> - Successful Prediction
   *
   * <p><b>400</b> - Bad Request - Invalid input data
   *
   * <p><b>413</b> - Payload Too Large
   *
   * <p><b>422</b> - Validation Error
   *
   * <p><b>500</b> - Internal Server Error
   *
   * @param requestBody The prediction request
   * @return prediction response from the RPT model
   */
  @Nonnull
  public PredictResponsePayload tableCompletion(@Nonnull final PredictRequestPayload requestBody) {
    return api.predict(requestBody);
  }

  /**
   * Make in-context predictions for specified target columns based on provided table data Parquet
   * file.
   *
   * <p><b>200</b> - Successful Prediction
   *
   * <p><b>400</b> - Bad Request - Invalid input data
   *
   * <p><b>413</b> - Payload Too Large
   *
   * <p><b>422</b> - Validation Error
   *
   * <p><b>500</b> - Internal Server Error
   *
   * @param parquetData Binary Parquet file data
   * @param predictionConfig The prediction configuration
   * @return prediction response from the RPT model
   */
  @Nonnull
  public PredictResponsePayload tableCompletion(
      @Nonnull final byte[] parquetData, @Nonnull final PredictionConfig predictionConfig) {
    try {
      final var config =
          JacksonConfiguration.getDefaultObjectMapper().writeValueAsString(predictionConfig);
      return api.predictParquet(parquetData, config);
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException("Failed to serialize PredictionConfig to JSON", e);
    }
  }
}
