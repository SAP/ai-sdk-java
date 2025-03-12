package com.sap.ai.sdk.core.client;

import com.google.common.annotations.Beta;
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
import com.sap.cloud.sdk.services.openapi.apiclient.ApiClient;
import com.sap.cloud.sdk.services.openapi.core.AbstractOpenApiService;
import com.sap.cloud.sdk.services.openapi.core.OpenApiRequestException;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * AI Core in version 2.38.0.
 *
 * <p>Provides tools to manage your scenarios and workflows in SAP AI Core. Execute pipelines as a
 * batch job, for example to pre-process or train your models, or perform batch inference. Serve
 * inference requests of trained models. Deploy а trained machine learning model as a web service to
 * serve inference requests with high performance. Register your own Docker registry, synchronize
 * your AI content from your own git repository, and register your own object store for training
 * data and trained models.
 */
@Beta
public class ExecutionApi extends AbstractOpenApiService {

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
    final Object localVarPostBody = aiExecutionBulkModificationRequest;

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'aiResourceGroup' when calling batchModify");
    }

    // verify the required parameter 'aiExecutionBulkModificationRequest' is set
    if (aiExecutionBulkModificationRequest == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'aiExecutionBulkModificationRequest' when calling batchModify");
    }

    final String localVarPath =
        UriComponentsBuilder.fromPath("/lm/executions").build().toUriString();

    final MultiValueMap<String, String> localVarQueryParams =
        new LinkedMultiValueMap<String, String>();
    final HttpHeaders localVarHeaderParams = new HttpHeaders();
    final MultiValueMap<String, Object> localVarFormParams =
        new LinkedMultiValueMap<String, Object>();

    if (aiResourceGroup != null)
      localVarHeaderParams.add("AI-Resource-Group", apiClient.parameterToString(aiResourceGroup));

    final String[] localVarAccepts = {"application/json"};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {"application/merge-patch+json"};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    final String[] localVarAuthNames = new String[] {"Oauth2"};

    final ParameterizedTypeReference<AiExecutionBulkModificationResponse> localVarReturnType =
        new ParameterizedTypeReference<AiExecutionBulkModificationResponse>() {};
    return apiClient.invokeAPI(
        localVarPath,
        HttpMethod.PATCH,
        localVarQueryParams,
        localVarPostBody,
        localVarHeaderParams,
        localVarFormParams,
        localVarAccept,
        localVarContentType,
        localVarAuthNames,
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
   * @param executableIds (optional Limit query to only these executable IDs
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
    final Object localVarPostBody = null;

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'aiResourceGroup' when calling count");
    }

    final String localVarPath =
        UriComponentsBuilder.fromPath("/lm/executions/$count").build().toUriString();

    final MultiValueMap<String, String> localVarQueryParams =
        new LinkedMultiValueMap<String, String>();
    final HttpHeaders localVarHeaderParams = new HttpHeaders();
    final MultiValueMap<String, Object> localVarFormParams =
        new LinkedMultiValueMap<String, Object>();

    localVarQueryParams.putAll(
        apiClient.parameterToMultiValueMap(
            ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)),
            "executableIds",
            executableIds));
    localVarQueryParams.putAll(
        apiClient.parameterToMultiValueMap(null, "configurationId", configurationId));
    localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "scenarioId", scenarioId));
    localVarQueryParams.putAll(
        apiClient.parameterToMultiValueMap(null, "executionScheduleId", executionScheduleId));
    localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "status", status));

    if (aiResourceGroup != null)
      localVarHeaderParams.add("AI-Resource-Group", apiClient.parameterToString(aiResourceGroup));

    final String[] localVarAccepts = {"text/plain", "application/json"};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    final String[] localVarAuthNames = new String[] {"Oauth2"};

    final ParameterizedTypeReference<Integer> localVarReturnType =
        new ParameterizedTypeReference<Integer>() {};
    return apiClient.invokeAPI(
        localVarPath,
        HttpMethod.GET,
        localVarQueryParams,
        localVarPostBody,
        localVarHeaderParams,
        localVarFormParams,
        localVarAccept,
        localVarContentType,
        localVarAuthNames,
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
    final Object localVarPostBody = aiEnactmentCreationRequest;

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'aiResourceGroup' when calling create");
    }

    // verify the required parameter 'aiEnactmentCreationRequest' is set
    if (aiEnactmentCreationRequest == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'aiEnactmentCreationRequest' when calling create");
    }

    final String localVarPath =
        UriComponentsBuilder.fromPath("/lm/executions").build().toUriString();

    final MultiValueMap<String, String> localVarQueryParams =
        new LinkedMultiValueMap<String, String>();
    final HttpHeaders localVarHeaderParams = new HttpHeaders();
    final MultiValueMap<String, Object> localVarFormParams =
        new LinkedMultiValueMap<String, Object>();

    if (aiResourceGroup != null)
      localVarHeaderParams.add("AI-Resource-Group", apiClient.parameterToString(aiResourceGroup));

    final String[] localVarAccepts = {"application/json"};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {"application/json"};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    final String[] localVarAuthNames = new String[] {"Oauth2"};

    final ParameterizedTypeReference<AiExecutionCreationResponse> localVarReturnType =
        new ParameterizedTypeReference<AiExecutionCreationResponse>() {};
    return apiClient.invokeAPI(
        localVarPath,
        HttpMethod.POST,
        localVarQueryParams,
        localVarPostBody,
        localVarHeaderParams,
        localVarFormParams,
        localVarAccept,
        localVarContentType,
        localVarAuthNames,
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
    final Object localVarPostBody = null;

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'aiResourceGroup' when calling delete");
    }

    // verify the required parameter 'executionId' is set
    if (executionId == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'executionId' when calling delete");
    }

    // create path and map variables
    final Map<String, Object> localVarPathParams = new HashMap<String, Object>();
    localVarPathParams.put("executionId", executionId);
    final String localVarPath =
        UriComponentsBuilder.fromPath("/lm/executions/{executionId}")
            .buildAndExpand(localVarPathParams)
            .toUriString();

    final MultiValueMap<String, String> localVarQueryParams =
        new LinkedMultiValueMap<String, String>();
    final HttpHeaders localVarHeaderParams = new HttpHeaders();
    final MultiValueMap<String, Object> localVarFormParams =
        new LinkedMultiValueMap<String, Object>();

    if (aiResourceGroup != null)
      localVarHeaderParams.add("AI-Resource-Group", apiClient.parameterToString(aiResourceGroup));

    final String[] localVarAccepts = {"application/json"};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    final String[] localVarAuthNames = new String[] {"Oauth2"};

    final ParameterizedTypeReference<AiExecutionDeletionResponse> localVarReturnType =
        new ParameterizedTypeReference<AiExecutionDeletionResponse>() {};
    return apiClient.invokeAPI(
        localVarPath,
        HttpMethod.DELETE,
        localVarQueryParams,
        localVarPostBody,
        localVarHeaderParams,
        localVarFormParams,
        localVarAccept,
        localVarContentType,
        localVarAuthNames,
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
    final Object localVarPostBody = null;

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'aiResourceGroup' when calling get");
    }

    // verify the required parameter 'executionId' is set
    if (executionId == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'executionId' when calling get");
    }

    // create path and map variables
    final Map<String, Object> localVarPathParams = new HashMap<String, Object>();
    localVarPathParams.put("executionId", executionId);
    final String localVarPath =
        UriComponentsBuilder.fromPath("/lm/executions/{executionId}")
            .buildAndExpand(localVarPathParams)
            .toUriString();

    final MultiValueMap<String, String> localVarQueryParams =
        new LinkedMultiValueMap<String, String>();
    final HttpHeaders localVarHeaderParams = new HttpHeaders();
    final MultiValueMap<String, Object> localVarFormParams =
        new LinkedMultiValueMap<String, Object>();

    localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "$select", $select));

    if (aiResourceGroup != null)
      localVarHeaderParams.add("AI-Resource-Group", apiClient.parameterToString(aiResourceGroup));

    final String[] localVarAccepts = {"application/json"};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    final String[] localVarAuthNames = new String[] {"Oauth2"};

    final ParameterizedTypeReference<AiExecutionResponseWithDetails> localVarReturnType =
        new ParameterizedTypeReference<AiExecutionResponseWithDetails>() {};
    return apiClient.invokeAPI(
        localVarPath,
        HttpMethod.GET,
        localVarQueryParams,
        localVarPostBody,
        localVarHeaderParams,
        localVarFormParams,
        localVarAccept,
        localVarContentType,
        localVarAuthNames,
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
    final Object localVarPostBody = null;

    // verify the required parameter 'executionId' is set
    if (executionId == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'executionId' when calling getLogs");
    }

    // create path and map variables
    final Map<String, Object> localVarPathParams = new HashMap<String, Object>();
    localVarPathParams.put("executionId", executionId);
    final String localVarPath =
        UriComponentsBuilder.fromPath("/lm/executions/{executionId}/logs")
            .buildAndExpand(localVarPathParams)
            .toUriString();

    final MultiValueMap<String, String> localVarQueryParams =
        new LinkedMultiValueMap<String, String>();
    final HttpHeaders localVarHeaderParams = new HttpHeaders();
    final MultiValueMap<String, Object> localVarFormParams =
        new LinkedMultiValueMap<String, Object>();

    localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "$top", $top));
    localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "start", start));
    localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "end", end));
    localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "$order", $order));

    if (authorization != null)
      localVarHeaderParams.add("Authorization", apiClient.parameterToString(authorization));

    final String[] localVarAccepts = {"application/json"};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    final String[] localVarAuthNames = new String[] {"Oauth2"};

    final ParameterizedTypeReference<RTALogCommonResponse> localVarReturnType =
        new ParameterizedTypeReference<RTALogCommonResponse>() {};
    return apiClient.invokeAPI(
        localVarPath,
        HttpMethod.GET,
        localVarQueryParams,
        localVarPostBody,
        localVarHeaderParams,
        localVarFormParams,
        localVarAccept,
        localVarContentType,
        localVarAuthNames,
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
    final Object localVarPostBody = aiExecutionModificationRequest;

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'aiResourceGroup' when calling modify");
    }

    // verify the required parameter 'executionId' is set
    if (executionId == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'executionId' when calling modify");
    }

    // verify the required parameter 'aiExecutionModificationRequest' is set
    if (aiExecutionModificationRequest == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'aiExecutionModificationRequest' when calling modify");
    }

    // create path and map variables
    final Map<String, Object> localVarPathParams = new HashMap<String, Object>();
    localVarPathParams.put("executionId", executionId);
    final String localVarPath =
        UriComponentsBuilder.fromPath("/lm/executions/{executionId}")
            .buildAndExpand(localVarPathParams)
            .toUriString();

    final MultiValueMap<String, String> localVarQueryParams =
        new LinkedMultiValueMap<String, String>();
    final HttpHeaders localVarHeaderParams = new HttpHeaders();
    final MultiValueMap<String, Object> localVarFormParams =
        new LinkedMultiValueMap<String, Object>();

    if (aiResourceGroup != null)
      localVarHeaderParams.add("AI-Resource-Group", apiClient.parameterToString(aiResourceGroup));

    final String[] localVarAccepts = {"application/json"};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {"application/json"};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    final String[] localVarAuthNames = new String[] {"Oauth2"};

    final ParameterizedTypeReference<AiExecutionModificationResponse> localVarReturnType =
        new ParameterizedTypeReference<AiExecutionModificationResponse>() {};
    return apiClient.invokeAPI(
        localVarPath,
        HttpMethod.PATCH,
        localVarQueryParams,
        localVarPostBody,
        localVarHeaderParams,
        localVarFormParams,
        localVarAccept,
        localVarContentType,
        localVarAuthNames,
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
   * @param executableIds (optional Limit query to only these executable IDs
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
    final Object localVarPostBody = null;

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'aiResourceGroup' when calling query");
    }

    final String localVarPath =
        UriComponentsBuilder.fromPath("/lm/executions").build().toUriString();

    final MultiValueMap<String, String> localVarQueryParams =
        new LinkedMultiValueMap<String, String>();
    final HttpHeaders localVarHeaderParams = new HttpHeaders();
    final MultiValueMap<String, Object> localVarFormParams =
        new LinkedMultiValueMap<String, Object>();

    localVarQueryParams.putAll(
        apiClient.parameterToMultiValueMap(
            ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)),
            "executableIds",
            executableIds));
    localVarQueryParams.putAll(
        apiClient.parameterToMultiValueMap(null, "configurationId", configurationId));
    localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "scenarioId", scenarioId));
    localVarQueryParams.putAll(
        apiClient.parameterToMultiValueMap(null, "executionScheduleId", executionScheduleId));
    localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "status", status));
    localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "$top", $top));
    localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "$skip", $skip));
    localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "$select", $select));

    if (aiResourceGroup != null)
      localVarHeaderParams.add("AI-Resource-Group", apiClient.parameterToString(aiResourceGroup));

    final String[] localVarAccepts = {"application/json"};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    final String[] localVarAuthNames = new String[] {"Oauth2"};

    final ParameterizedTypeReference<AiExecutionList> localVarReturnType =
        new ParameterizedTypeReference<AiExecutionList>() {};
    return apiClient.invokeAPI(
        localVarPath,
        HttpMethod.GET,
        localVarQueryParams,
        localVarPostBody,
        localVarHeaderParams,
        localVarFormParams,
        localVarAccept,
        localVarContentType,
        localVarAuthNames,
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
