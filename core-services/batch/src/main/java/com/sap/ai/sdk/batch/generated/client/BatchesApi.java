package com.sap.ai.sdk.batch.generated.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.sap.ai.sdk.batch.generated.model.BatchCancelResponse;
import com.sap.ai.sdk.batch.generated.model.BatchCreateRequest;
import com.sap.ai.sdk.batch.generated.model.BatchCreateResponse;
import com.sap.ai.sdk.batch.generated.model.BatchDeleteResponse;
import com.sap.ai.sdk.batch.generated.model.BatchDetailResponse;
import com.sap.ai.sdk.batch.generated.model.BatchListResponse;
import com.sap.ai.sdk.batch.generated.model.BatchStatusResponse;
import com.sap.cloud.sdk.cloudplatform.connectivity.Destination;
import com.sap.cloud.sdk.services.openapi.apache.apiclient.ApiClient;
import com.sap.cloud.sdk.services.openapi.apache.apiclient.BaseApi;
import com.sap.cloud.sdk.services.openapi.apache.apiclient.Pair;
import com.sap.cloud.sdk.services.openapi.apache.core.OpenApiRequestException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.UUID;
import javax.annotation.Nonnull;

/**
 * LLM Batch Service API in version 0.0.1.
 *
 * <p>API for managing LLM batch processing jobs
 */
public class BatchesApi extends BaseApi {

  /**
   * Instantiates this API class to invoke operations on the LLM Batch Service API.
   *
   * @param httpDestination The destination that API should be used with
   */
  public BatchesApi(@Nonnull final Destination httpDestination) {
    super(httpDestination);
  }

  /**
   * Instantiates this API class to invoke operations on the LLM Batch Service API based on a given
   * {@link ApiClient}.
   *
   * @param apiClient ApiClient to invoke the API on
   */
  public BatchesApi(@Nonnull final ApiClient apiClient) {
    super(apiClient);
  }

  /**
   * Creates a new API instance with additional default headers.
   *
   * @param defaultHeaders Additional headers to include in all requests
   * @return A new API instance with the combined headers
   */
  public BatchesApi withDefaultHeaders(@Nonnull final Map<String, String> defaultHeaders) {
    final var api = new BatchesApi(apiClient);
    api.defaultHeaders.putAll(this.defaultHeaders);
    api.defaultHeaders.putAll(defaultHeaders);
    return api;
  }

  /**
   * Cancel a batch
   *
   * <p>
   *
   * <p><b>202</b> - Batch job scheduled for cancellation
   *
   * <p><b>404</b> - Batch job not found
   *
   * <p><b>500</b> - Internal server error
   *
   * @param batchId The value for the parameter batchId
   * @return BatchCancelResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BatchCancelResponse cancelBatch(@Nonnull final UUID batchId)
      throws OpenApiRequestException {

    // verify the required parameter 'batchId' is set
    if (batchId == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'batchId' when calling cancelBatch")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/batches/{batch_id}/cancel"
            .replaceAll(
                "\\{" + "batch_id" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(batchId)));

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>(defaultHeaders);
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};

    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<BatchCancelResponse> localVarReturnType =
        new TypeReference<BatchCancelResponse>() {};

    return apiClient.invokeAPI(
        localVarPath,
        "PATCH",
        localVarQueryParams,
        localVarCollectionQueryParams,
        localVarQueryStringJoiner.toString(),
        null,
        localVarHeaderParams,
        localVarFormParams,
        localVarAccept,
        localVarContentType,
        localVarReturnType);
  }

  /**
   * Create a new batch job
   *
   * <p>
   *
   * <p><b>202</b> - Batch job scheduled
   *
   * <p><b>400</b> - Bad request
   *
   * <p><b>500</b> - Internal server error
   *
   * @param batchCreateRequest The value for the parameter batchCreateRequest
   * @return BatchCreateResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BatchCreateResponse createBatch(@Nonnull final BatchCreateRequest batchCreateRequest)
      throws OpenApiRequestException {

    // verify the required parameter 'batchCreateRequest' is set
    if (batchCreateRequest == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'batchCreateRequest' when calling createBatch")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath = "/batches";

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>(defaultHeaders);
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {"application/json"};
    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<BatchCreateResponse> localVarReturnType =
        new TypeReference<BatchCreateResponse>() {};

    return apiClient.invokeAPI(
        localVarPath,
        "POST",
        localVarQueryParams,
        localVarCollectionQueryParams,
        localVarQueryStringJoiner.toString(),
        batchCreateRequest,
        localVarHeaderParams,
        localVarFormParams,
        localVarAccept,
        localVarContentType,
        localVarReturnType);
  }

  /**
   * Delete a Batch job (only for cancelled, completed, or failed batches)
   *
   * <p>
   *
   * <p><b>200</b> - Batch job deleted
   *
   * <p><b>400</b> - Bad request - Batch job not in deletable state
   *
   * <p><b>404</b> - Batch job not found
   *
   * <p><b>500</b> - Internal server error
   *
   * @param batchId The value for the parameter batchId
   * @return BatchDeleteResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BatchDeleteResponse deleteBatch(@Nonnull final UUID batchId)
      throws OpenApiRequestException {

    // verify the required parameter 'batchId' is set
    if (batchId == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'batchId' when calling deleteBatch")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/batches/{batch_id}"
            .replaceAll(
                "\\{" + "batch_id" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(batchId)));

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>(defaultHeaders);
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};

    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<BatchDeleteResponse> localVarReturnType =
        new TypeReference<BatchDeleteResponse>() {};

    return apiClient.invokeAPI(
        localVarPath,
        "DELETE",
        localVarQueryParams,
        localVarCollectionQueryParams,
        localVarQueryStringJoiner.toString(),
        null,
        localVarHeaderParams,
        localVarFormParams,
        localVarAccept,
        localVarContentType,
        localVarReturnType);
  }

  /**
   * Get Batch job details
   *
   * <p>
   *
   * <p><b>200</b> - Batch job details
   *
   * <p><b>404</b> - Batch job not found
   *
   * <p><b>500</b> - Internal server error
   *
   * @param batchId The value for the parameter batchId
   * @return BatchDetailResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BatchDetailResponse getBatchById(@Nonnull final UUID batchId)
      throws OpenApiRequestException {

    // verify the required parameter 'batchId' is set
    if (batchId == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'batchId' when calling getBatchById")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/batches/{batch_id}"
            .replaceAll(
                "\\{" + "batch_id" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(batchId)));

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>(defaultHeaders);
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};

    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<BatchDetailResponse> localVarReturnType =
        new TypeReference<BatchDetailResponse>() {};

    return apiClient.invokeAPI(
        localVarPath,
        "GET",
        localVarQueryParams,
        localVarCollectionQueryParams,
        localVarQueryStringJoiner.toString(),
        null,
        localVarHeaderParams,
        localVarFormParams,
        localVarAccept,
        localVarContentType,
        localVarReturnType);
  }

  /**
   * Get Batch job status
   *
   * <p>
   *
   * <p><b>200</b> - Batch job status
   *
   * <p><b>404</b> - Batch job not found
   *
   * <p><b>500</b> - Internal server error
   *
   * @param batchId The value for the parameter batchId
   * @return BatchStatusResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BatchStatusResponse getBatchStatus(@Nonnull final UUID batchId)
      throws OpenApiRequestException {

    // verify the required parameter 'batchId' is set
    if (batchId == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'batchId' when calling getBatchStatus")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/batches/{batch_id}/status"
            .replaceAll(
                "\\{" + "batch_id" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(batchId)));

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>(defaultHeaders);
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};

    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<BatchStatusResponse> localVarReturnType =
        new TypeReference<BatchStatusResponse>() {};

    return apiClient.invokeAPI(
        localVarPath,
        "GET",
        localVarQueryParams,
        localVarCollectionQueryParams,
        localVarQueryStringJoiner.toString(),
        null,
        localVarHeaderParams,
        localVarFormParams,
        localVarAccept,
        localVarContentType,
        localVarReturnType);
  }

  /**
   * List all batches
   *
   * <p>
   *
   * <p><b>200</b> - List of batches
   *
   * <p><b>500</b> - Internal server error
   *
   * @return BatchListResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BatchListResponse listBatches() throws OpenApiRequestException {

    // create path and map variables
    final String localVarPath = "/batches";

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>(defaultHeaders);
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};

    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<BatchListResponse> localVarReturnType =
        new TypeReference<BatchListResponse>() {};

    return apiClient.invokeAPI(
        localVarPath,
        "GET",
        localVarQueryParams,
        localVarCollectionQueryParams,
        localVarQueryStringJoiner.toString(),
        null,
        localVarHeaderParams,
        localVarFormParams,
        localVarAccept,
        localVarContentType,
        localVarReturnType);
  }
}
