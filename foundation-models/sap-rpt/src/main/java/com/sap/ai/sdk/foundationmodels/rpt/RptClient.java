package com.sap.ai.sdk.foundationmodels.rpt;

import static com.sap.ai.sdk.core.JacksonConfiguration.getDefaultObjectMapper;
import static com.sap.ai.sdk.foundationmodels.rpt.RptModel.SAP_RPT_1_6_LARGE;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.annotations.Beta;
import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.core.DeploymentResolutionException;
import com.sap.ai.sdk.core.JacksonConfiguration;
import com.sap.ai.sdk.foundationmodels.rpt.generated.client.DefaultApi;
import com.sap.ai.sdk.foundationmodels.rpt.generated.model.PredictRequestPayload;
import com.sap.ai.sdk.foundationmodels.rpt.generated.model.PredictRequestPayloadOneOf;
import com.sap.ai.sdk.foundationmodels.rpt.generated.model.PredictRequestPayloadOneOf1;
import com.sap.ai.sdk.foundationmodels.rpt.generated.model.PredictResponsePayload;
import com.sap.ai.sdk.foundationmodels.rpt.generated.model.PredictionConfig;
import com.sap.cloud.sdk.cloudplatform.connectivity.Destination;
import com.sap.cloud.sdk.services.openapi.apache.apiclient.ApiClient;
import java.io.File;
import java.util.Map;
import java.util.Set;
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
  @Nonnull private final DefaultApi apiWithGzipEncoding;
  private final boolean contextModePossible;

  private static final Set<RptModel> MODELS_WITH_CONTEXT_MODE = Set.of(SAP_RPT_1_6_LARGE);

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
    final var contextModePossible = MODELS_WITH_CONTEXT_MODE.contains(foundationModel);
    final var destination = new AiCoreService().getInferenceDestination().forModel(foundationModel);
    return forDestination(destination, contextModePossible);
  }

  /**
   * Creates a new RptClient for the specified destination.
   *
   * @param destination The destination to use.
   * @return A new instance of RptClient.
   */
  static RptClient forDestination(
      @Nonnull final Destination destination, final boolean contextModePossible) {
    final var apiClient = ApiClient.create(destination).withObjectMapper(getDefaultObjectMapper());
    final var api = new DefaultApi(apiClient);
    return new RptClient(
        api, api.withDefaultHeaders(Map.of("Content-Encoding", "gzip")), contextModePossible);
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
   * @apiNote When used with a model that does not support it, the {@code contextMode} field of the
   *     embedded {@link com.sap.ai.sdk.foundationmodels.rpt.generated.model.PredictionConfig} is
   *     set to {@code null} on the passed-in object as a side effect.
   */
  @Beta
  @Nonnull
  public PredictResponsePayload tableCompletion(@Nonnull final PredictRequestPayload requestBody) {
    if (!contextModePossible) {
      configFrom(requestBody).setContextMode(null);
    }
    return apiWithGzipEncoding.predict(requestBody);
  }

  @Nonnull
  private static PredictionConfig configFrom(@Nonnull final PredictRequestPayload requestBody) {
    if (requestBody instanceof PredictRequestPayloadOneOf rb) {
      return rb.getPredictionConfig();
    } else if (requestBody instanceof PredictRequestPayloadOneOf1 rb1) {
      return rb1.getPredictionConfig();
    }
    throw new IllegalArgumentException(
        "Unsupported PredictRequestPayload type: " + requestBody.getClass().getName());
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
   * @apiNote When used with a model that does not support it, the {@code contextMode} field of the
   *     passed-in {@link com.sap.ai.sdk.foundationmodels.rpt.generated.model.PredictionConfig} is
   *     set to {@code null} as a side effect.
   * @since 1.16.0
   */
  @Beta
  @Nonnull
  public PredictResponsePayload tableCompletion(
      @Nonnull final File parquetFile, @Nonnull final PredictionConfig predictionConfig) {
    if (!contextModePossible) {
      predictionConfig.setContextMode(null);
    }
    try {
      final var config =
          JacksonConfiguration.getDefaultObjectMapper().writeValueAsString(predictionConfig);
      return api.predictParquet(parquetFile, config);
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException("Failed to serialize PredictionConfig to JSON", e);
    }
  }
}
