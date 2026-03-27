package com.sap.ai.sdk.core.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.core.model.AiEnactmentCreationRequest;
import com.sap.ai.sdk.core.model.AiExecutionBulkModificationRequest;
import com.sap.ai.sdk.core.model.AiExecutionBulkModificationResponse;
import com.sap.ai.sdk.core.model.AiExecutionCreationResponse;
import com.sap.ai.sdk.core.model.AiExecutionDeletionResponse;
import com.sap.ai.sdk.core.model.AiExecutionList;
import com.sap.ai.sdk.core.model.AiExecutionModificationRequest;
import com.sap.ai.sdk.core.model.AiExecutionModificationResponse;
import com.sap.ai.sdk.core.model.AiExecutionResponseWithDetails;
import com.sap.ai.sdk.core.model.RTALogCommonResponse;
import com.sap.cloud.sdk.services.openapi.apache.apiclient.ApiClient;
import com.sap.cloud.sdk.services.openapi.apache.apiclient.BaseApi;
import com.sap.cloud.sdk.services.openapi.apache.apiclient.Pair;
import com.sap.cloud.sdk.services.openapi.apache.core.OpenApiRequestException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * AI Core in version 2.42.0.
 *
 * <p>Provides tools to manage your scenarios and workflows in SAP AI Core. Execute pipelines as a
 * batch job, for example to pre-process or train your models, or perform batch inference. Serve
 * inference requests of trained models. Deploy а trained machine learning model as a web service to
 * serve inference requests with high performance. Register your own Docker registry, synchronize
 * your AI content from your own git repository, and register your own object store for training
 * data and trained models.
 */
public class ExecutionApi extends BaseApi {

  /** Instantiates this API class to invoke operations on the AI Core */
  public ExecutionApi() {
    super(new AiCoreService().getApiClient());
  }

  /**
   * Instantiates this API class to invoke operations on the AI Core
   *
   * @param aiCoreService The configured connectivity instance to AI Core
   */
  public ExecutionApi(@Nonnull final AiCoreService aiCoreService) {
    super(aiCoreService.getApiClient());
  }

  /**
   * Patch multiple executions
   *
   * <p>Patch multiple executions&#39; status to stopped or deleted.
   *
   * <p><b>202</b> - The modification of the executions have been scheduled successfully
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * @param aiResourceGroup Specify a resource group id
   * @param aiExecutionBulkModificationRequest The value for the parameter
   *     aiExecutionBulkModificationRequest
   * @return AiExecutionBulkModificationResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public AiExecutionBulkModificationResponse batchModify(
      @Nonnull final String aiResourceGroup,
      @Nonnull final AiExecutionBulkModificationRequest aiExecutionBulkModificationRequest)
      throws OpenApiRequestException {

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'aiResourceGroup' when calling batchModify")
          .statusCode(400);
    }

    // verify the required parameter 'aiExecutionBulkModificationRequest' is set
    if (aiExecutionBulkModificationRequest == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'aiExecutionBulkModificationRequest' when calling batchModify")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath = "/lm/executions";

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    if (aiResourceGroup != null)
      localVarHeaderParams.put("AI-Resource-Group", ApiClient.parameterToString(aiResourceGroup));

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {"application/merge-patch+json"};
    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<AiExecutionBulkModificationResponse> localVarReturnType =
        new TypeReference<AiExecutionBulkModificationResponse>() {};

    return apiClient.invokeAPI(
        localVarPath,
        "PATCH",
        localVarQueryParams,
        localVarCollectionQueryParams,
        localVarQueryStringJoiner.toString(),
        aiExecutionBulkModificationRequest,
        localVarHeaderParams,
        localVarFormParams,
        localVarAccept,
        localVarContentType,
        localVarReturnType);
  }

  /**
   * Get number of executions
   *
   * <p>Retrieve the number of available executions. The number can be filtered by scenarioId,
   * configurationId, executableIdsList or by execution status.
   *
   * <p><b>200</b> - Number of executions
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * @param aiResourceGroup (required) Specify a resource group id
   * @param executableIds (optional) Limit query to only these executable IDs
   * @param configurationId (optional) Configuration identifier
   * @param scenarioId (optional) Scenario identifier
   * @param executionScheduleId (optional) Execution Schedule identifier
   * @param status (optional) Filter by status
   * @return Integer
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public Integer count(
      @Nonnull final String aiResourceGroup,
      @Nullable final List<String> executableIds,
      @Nullable final String configurationId,
      @Nullable final String scenarioId,
      @Nullable final String executionScheduleId,
      @Nullable final String status)
      throws OpenApiRequestException {

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'aiResourceGroup' when calling count")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath = "/lm/executions/$count";

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarCollectionQueryParams.addAll(
        ApiClient.parameterToPairs("csv", "executableIds", executableIds));
    localVarQueryParams.addAll(ApiClient.parameterToPair("configurationId", configurationId));
    localVarQueryParams.addAll(ApiClient.parameterToPair("scenarioId", scenarioId));
    localVarQueryParams.addAll(
        ApiClient.parameterToPair("executionScheduleId", executionScheduleId));
    localVarQueryParams.addAll(ApiClient.parameterToPair("status", status));
    if (aiResourceGroup != null)
      localVarHeaderParams.put("AI-Resource-Group", ApiClient.parameterToString(aiResourceGroup));

    final String[] localVarAccepts = {"text/plain", "application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};

    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<Integer> localVarReturnType = new TypeReference<Integer>() {};

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
   * Get number of executions
   *
   * <p>Retrieve the number of available executions. The number can be filtered by scenarioId,
   * configurationId, executableIdsList or by execution status.
   *
   * <p><b>200</b> - Number of executions
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * @param aiResourceGroup Specify a resource group id
   * @return Integer
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public Integer count(@Nonnull final String aiResourceGroup) throws OpenApiRequestException {
    return count(aiResourceGroup, null, null, null, null, null);
  }

  /**
   * Create execution
   *
   * <p>Create an execution using the configuration specified by configurationId.
   *
   * <p><b>202</b> - The execution has been scheduled successfully
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * @param aiResourceGroup Specify a resource group id
   * @param aiEnactmentCreationRequest The value for the parameter aiEnactmentCreationRequest
   * @return AiExecutionCreationResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public AiExecutionCreationResponse create(
      @Nonnull final String aiResourceGroup,
      @Nonnull final AiEnactmentCreationRequest aiEnactmentCreationRequest)
      throws OpenApiRequestException {

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'aiResourceGroup' when calling create")
          .statusCode(400);
    }

    // verify the required parameter 'aiEnactmentCreationRequest' is set
    if (aiEnactmentCreationRequest == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'aiEnactmentCreationRequest' when calling create")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath = "/lm/executions";

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    if (aiResourceGroup != null)
      localVarHeaderParams.put("AI-Resource-Group", ApiClient.parameterToString(aiResourceGroup));

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {"application/json"};
    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<AiExecutionCreationResponse> localVarReturnType =
        new TypeReference<AiExecutionCreationResponse>() {};

    return apiClient.invokeAPI(
        localVarPath,
        "POST",
        localVarQueryParams,
        localVarCollectionQueryParams,
        localVarQueryStringJoiner.toString(),
        aiEnactmentCreationRequest,
        localVarHeaderParams,
        localVarFormParams,
        localVarAccept,
        localVarContentType,
        localVarReturnType);
  }

  /**
   * Mark execution as deleted
   *
   * <p>Mark the execution with executionId as deleted.
   *
   * <p><b>202</b> - The deletion of the execution has been scheduled successfully
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * <p><b>412</b> - The service didn&#39;t meet the precondition needed to execute this operation
   *
   * @param aiResourceGroup Specify a resource group id
   * @param executionId Execution identifier
   * @return AiExecutionDeletionResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public AiExecutionDeletionResponse delete(
      @Nonnull final String aiResourceGroup, @Nonnull final String executionId)
      throws OpenApiRequestException {

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'aiResourceGroup' when calling delete")
          .statusCode(400);
    }

    // verify the required parameter 'executionId' is set
    if (executionId == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'executionId' when calling delete")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/lm/executions/{executionId}"
            .replaceAll(
                "\\{" + "executionId" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(executionId)));

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    if (aiResourceGroup != null)
      localVarHeaderParams.put("AI-Resource-Group", ApiClient.parameterToString(aiResourceGroup));

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};

    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<AiExecutionDeletionResponse> localVarReturnType =
        new TypeReference<AiExecutionDeletionResponse>() {};

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
   * Get information about a specific execution
   *
   * <p>Retrieve details for execution with executionId.
   *
   * <p><b>200</b> - Information about the execution
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * @param aiResourceGroup (required) Specify a resource group id
   * @param executionId (required) Execution identifier
   * @param $select (optional) Allows to request a specified set of properties for each entity
   * @return AiExecutionResponseWithDetails
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public AiExecutionResponseWithDetails get(
      @Nonnull final String aiResourceGroup,
      @Nonnull final String executionId,
      @Nullable final String $select)
      throws OpenApiRequestException {

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'aiResourceGroup' when calling get")
          .statusCode(400);
    }

    // verify the required parameter 'executionId' is set
    if (executionId == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'executionId' when calling get")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/lm/executions/{executionId}"
            .replaceAll(
                "\\{" + "executionId" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(executionId)));

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(ApiClient.parameterToPair("$select", $select));
    if (aiResourceGroup != null)
      localVarHeaderParams.put("AI-Resource-Group", ApiClient.parameterToString(aiResourceGroup));

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};

    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<AiExecutionResponseWithDetails> localVarReturnType =
        new TypeReference<AiExecutionResponseWithDetails>() {};

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
   * Get information about a specific execution
   *
   * <p>Retrieve details for execution with executionId.
   *
   * <p><b>200</b> - Information about the execution
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * @param aiResourceGroup Specify a resource group id
   * @param executionId Execution identifier
   * @return AiExecutionResponseWithDetails
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public AiExecutionResponseWithDetails get(
      @Nonnull final String aiResourceGroup, @Nonnull final String executionId)
      throws OpenApiRequestException {
    return get(aiResourceGroup, executionId, null);
  }

  /**
   * Get logs of specific execution
   *
   * <p>Retrieve logs of an execution for getting insight into the execution results or failures.
   *
   * <p><b>200</b> - The query was processed successfully and logs of the requested execution will
   * be returned.
   *
   * <p><b>400</b> - The request was malformed and could thus not be processed.
   *
   * <p><b>401</b> - Lacks valid authentication credentials for the target resource.
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @param executionId (required) Execution identifier
   * @param authorization (optional) Authorization bearer token containing a JWT token.
   * @param $top (optional, default to 1000) The max number of entries to return. Defaults to 1000.
   *     Limited to 5000 max.
   * @param start (optional) The start time for the query as a RFC 3339 datetime format. Defaults to
   *     one hour ago. + in timezone need to be encoded to %2B.
   * @param end (optional) The end time for the query as a RFC 3339 datetime format. Defaults to
   *     now. + in timezone need to be encoded to %2B.
   * @param $order (optional) Determines the sort order. Supported values are asc or desc. Defaults
   *     to asc. Sort order: * &#x60;asc&#x60; - Ascending, earliest in the order will appear at the
   *     top of the list * &#x60;desc&#x60; - Descending, last in the order will appear at the top
   *     of the list
   * @return RTALogCommonResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public RTALogCommonResponse getLogs(
      @Nonnull final String executionId,
      @Nullable final String authorization,
      @Nullable final Integer $top,
      @Nullable final OffsetDateTime start,
      @Nullable final OffsetDateTime end,
      @Nullable final String $order)
      throws OpenApiRequestException {

    // verify the required parameter 'executionId' is set
    if (executionId == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'executionId' when calling getLogs")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/lm/executions/{executionId}/logs"
            .replaceAll(
                "\\{" + "executionId" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(executionId)));

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(ApiClient.parameterToPair("$top", $top));
    localVarQueryParams.addAll(ApiClient.parameterToPair("start", start));
    localVarQueryParams.addAll(ApiClient.parameterToPair("end", end));
    localVarQueryParams.addAll(ApiClient.parameterToPair("$order", $order));
    if (authorization != null)
      localVarHeaderParams.put("Authorization", ApiClient.parameterToString(authorization));

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};

    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<RTALogCommonResponse> localVarReturnType =
        new TypeReference<RTALogCommonResponse>() {};

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
   * Get logs of specific execution
   *
   * <p>Retrieve logs of an execution for getting insight into the execution results or failures.
   *
   * <p><b>200</b> - The query was processed successfully and logs of the requested execution will
   * be returned.
   *
   * <p><b>400</b> - The request was malformed and could thus not be processed.
   *
   * <p><b>401</b> - Lacks valid authentication credentials for the target resource.
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @param executionId Execution identifier
   * @return RTALogCommonResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public RTALogCommonResponse getLogs(@Nonnull final String executionId)
      throws OpenApiRequestException {
    return getLogs(executionId, null, null, null, null, null);
  }

  /**
   * Update target status of an execution
   *
   * <p>Update target status of the execution to stop an execution.
   *
   * <p><b>202</b> - The modification of the execution has been scheduled successfully
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * <p><b>412</b> - The service didn&#39;t meet the precondition needed to execute this operation
   *
   * @param aiResourceGroup Specify a resource group id
   * @param executionId Execution identifier
   * @param aiExecutionModificationRequest The value for the parameter
   *     aiExecutionModificationRequest
   * @return AiExecutionModificationResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public AiExecutionModificationResponse modify(
      @Nonnull final String aiResourceGroup,
      @Nonnull final String executionId,
      @Nonnull final AiExecutionModificationRequest aiExecutionModificationRequest)
      throws OpenApiRequestException {

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'aiResourceGroup' when calling modify")
          .statusCode(400);
    }

    // verify the required parameter 'executionId' is set
    if (executionId == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'executionId' when calling modify")
          .statusCode(400);
    }

    // verify the required parameter 'aiExecutionModificationRequest' is set
    if (aiExecutionModificationRequest == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'aiExecutionModificationRequest' when calling modify")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/lm/executions/{executionId}"
            .replaceAll(
                "\\{" + "executionId" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(executionId)));

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    if (aiResourceGroup != null)
      localVarHeaderParams.put("AI-Resource-Group", ApiClient.parameterToString(aiResourceGroup));

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {"application/json"};
    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<AiExecutionModificationResponse> localVarReturnType =
        new TypeReference<AiExecutionModificationResponse>() {};

    return apiClient.invokeAPI(
        localVarPath,
        "PATCH",
        localVarQueryParams,
        localVarCollectionQueryParams,
        localVarQueryStringJoiner.toString(),
        aiExecutionModificationRequest,
        localVarHeaderParams,
        localVarFormParams,
        localVarAccept,
        localVarContentType,
        localVarReturnType);
  }

  /**
   * Get list of executions
   *
   * <p>Retrieve a list of executions that match the specified filter criteria. Filter criteria
   * include a list of executableIds, a scenarioId, a configurationId, or a execution status. With
   * top/skip parameters it is possible to paginate the result list. With select parameter it is
   * possible to select only status.
   *
   * <p><b>200</b> - A list of executions
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * @param aiResourceGroup (required) Specify a resource group id
   * @param executableIds (optional) Limit query to only these executable IDs
   * @param configurationId (optional) Configuration identifier
   * @param scenarioId (optional) Scenario identifier
   * @param executionScheduleId (optional) Execution Schedule identifier
   * @param status (optional) Filter by status
   * @param $top (optional, default to 10000) Number of results to display
   * @param $skip (optional) Number of results to be skipped from the ordered list of results
   * @param $select (optional) Allows to request a specified set of properties for each entity
   * @return AiExecutionList
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public AiExecutionList query(
      @Nonnull final String aiResourceGroup,
      @Nullable final List<String> executableIds,
      @Nullable final String configurationId,
      @Nullable final String scenarioId,
      @Nullable final String executionScheduleId,
      @Nullable final String status,
      @Nullable final Integer $top,
      @Nullable final Integer $skip,
      @Nullable final String $select)
      throws OpenApiRequestException {

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'aiResourceGroup' when calling query")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath = "/lm/executions";

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarCollectionQueryParams.addAll(
        ApiClient.parameterToPairs("csv", "executableIds", executableIds));
    localVarQueryParams.addAll(ApiClient.parameterToPair("configurationId", configurationId));
    localVarQueryParams.addAll(ApiClient.parameterToPair("scenarioId", scenarioId));
    localVarQueryParams.addAll(
        ApiClient.parameterToPair("executionScheduleId", executionScheduleId));
    localVarQueryParams.addAll(ApiClient.parameterToPair("status", status));
    localVarQueryParams.addAll(ApiClient.parameterToPair("$top", $top));
    localVarQueryParams.addAll(ApiClient.parameterToPair("$skip", $skip));
    localVarQueryParams.addAll(ApiClient.parameterToPair("$select", $select));
    if (aiResourceGroup != null)
      localVarHeaderParams.put("AI-Resource-Group", ApiClient.parameterToString(aiResourceGroup));

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};

    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<AiExecutionList> localVarReturnType =
        new TypeReference<AiExecutionList>() {};

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
   * Get list of executions
   *
   * <p>Retrieve a list of executions that match the specified filter criteria. Filter criteria
   * include a list of executableIds, a scenarioId, a configurationId, or a execution status. With
   * top/skip parameters it is possible to paginate the result list. With select parameter it is
   * possible to select only status.
   *
   * <p><b>200</b> - A list of executions
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * @param aiResourceGroup Specify a resource group id
   * @return AiExecutionList
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public AiExecutionList query(@Nonnull final String aiResourceGroup)
      throws OpenApiRequestException {
    return query(aiResourceGroup, null, null, null, null, null, null, null, null);
  }
}
