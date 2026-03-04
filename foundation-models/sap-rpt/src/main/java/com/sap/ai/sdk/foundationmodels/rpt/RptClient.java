package com.sap.ai.sdk.foundationmodels.rpt;

import static com.sap.ai.sdk.core.JacksonConfiguration.getDefaultObjectMapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.annotations.Beta;
import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.core.DeploymentResolutionException;
import com.sap.ai.sdk.core.JacksonConfiguration;
import com.sap.ai.sdk.foundationmodels.rpt.generated.client.DefaultApi;
import com.sap.ai.sdk.foundationmodels.rpt.generated.model.PredictRequestPayload;
import com.sap.ai.sdk.foundationmodels.rpt.generated.model.PredictResponsePayload;
import com.sap.ai.sdk.foundationmodels.rpt.generated.model.PredictionConfig;
import com.sap.cloud.sdk.cloudplatform.connectivity.Destination;
import com.sap.cloud.sdk.services.openapi.apache.apiclient.ApiClient;
import com.sap.cloud.sdk.services.openapi.apache.apiclient.Pair;
import com.sap.cloud.sdk.services.openapi.core.OpenApiRequestException;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * Client for interacting with SAP RPT foundation models.
 *
 * @since 1.16.0
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class RptClient {
  @Nonnull private final DefaultApi api;
  @Nonnull private final ApiClient apiClient;

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
    return new RptClient(new DefaultApi(apiClient), apiClient);
  }

  /**
   * Predict targets using SAP RPT model with structured data.
   *
   * <p>Note: This method is marked as {@link Beta} because it uses generated API types in its
   * public signature.
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
  @Beta
  @Nonnull
  public PredictResponsePayload tableCompletion(@Nonnull final PredictRequestPayload requestBody)
      throws OpenApiRequestException {

    // create path and map variables
    final String localVarPath = "/predict";

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    final List<Pair> localVarQueryParams = new ArrayList<>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<>();
    final Map<String, String> localVarHeaderParams = Map.of("Content-Encoding", "gzip");
    final Map<String, Object> localVarFormParams = new HashMap<>();

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {"application/json"};
    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<PredictResponsePayload> localVarReturnType = new TypeReference<>() {};

    return apiClient.invokeAPI(
        localVarPath,
        "POST",
        localVarQueryParams,
        localVarCollectionQueryParams,
        localVarQueryStringJoiner.toString(),
        requestBody,
        localVarHeaderParams,
        localVarFormParams,
        localVarAccept,
        localVarContentType,
        localVarReturnType);
  }

  /**
   * Make in-context predictions for specified target columns based on provided table data Parquet
   * file.
   *
   * <p>Note: This method is marked as {@link Beta} because it uses generated API types in its
   * public signature.
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
   * @param parquetFile Parquet file
   * @param predictionConfig The prediction configuration
   * @return prediction response from the RPT model
   * @since 1.16.0
   */
  @Beta
  @Nonnull
  public PredictResponsePayload tableCompletion(
      @Nonnull final File parquetFile, @Nonnull final PredictionConfig predictionConfig) {
    try {
      final var config =
          JacksonConfiguration.getDefaultObjectMapper().writeValueAsString(predictionConfig);
      return api.predictParquet(parquetFile, config);
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException("Failed to serialize PredictionConfig to JSON", e);
    }
  }
}
