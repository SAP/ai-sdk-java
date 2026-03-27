package com.sap.ai.sdk.core.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.core.model.AiExecutionSchedule;
import com.sap.ai.sdk.core.model.AiExecutionScheduleCreationData;
import com.sap.ai.sdk.core.model.AiExecutionScheduleCreationResponse;
import com.sap.ai.sdk.core.model.AiExecutionScheduleDeletionResponse;
import com.sap.ai.sdk.core.model.AiExecutionScheduleList;
import com.sap.ai.sdk.core.model.AiExecutionScheduleModificationRequest;
import com.sap.ai.sdk.core.model.AiExecutionScheduleModificationResponse;
import com.sap.cloud.sdk.services.openapi.apache.apiclient.ApiClient;
import com.sap.cloud.sdk.services.openapi.apache.apiclient.BaseApi;
import com.sap.cloud.sdk.services.openapi.apache.apiclient.Pair;
import com.sap.cloud.sdk.services.openapi.apache.core.OpenApiRequestException;
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
public class ExecutionScheduleApi extends BaseApi {

  /** Instantiates this API class to invoke operations on the AI Core */
  public ExecutionScheduleApi() {
    super(new AiCoreService().getApiClient());
  }

  /**
   * Instantiates this API class to invoke operations on the AI Core
   *
   * @param aiCoreService The configured connectivity instance to AI Core
   */
  public ExecutionScheduleApi(@Nonnull final AiCoreService aiCoreService) {
    super(aiCoreService.getApiClient());
  }

  /**
   * Get number of execution schedules
   *
   * <p>Retrieve the number of scheduled executions. The number can be filtered by configurationId
   * or executionScheduleStatus.
   *
   * <p><b>200</b> - Number of execution schedules
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * @param aiResourceGroup (required) Specify a resource group id
   * @param configurationId (optional) Configuration identifier
   * @param status (optional) Execution Schedule status
   * @return Integer
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public Integer count(
      @Nonnull final String aiResourceGroup,
      @Nullable final String configurationId,
      @Nullable final String status)
      throws OpenApiRequestException {

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'aiResourceGroup' when calling count")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath = "/lm/executionSchedules/$count";

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(ApiClient.parameterToPair("configurationId", configurationId));
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
   * Get number of execution schedules
   *
   * <p>Retrieve the number of scheduled executions. The number can be filtered by configurationId
   * or executionScheduleStatus.
   *
   * <p><b>200</b> - Number of execution schedules
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * @param aiResourceGroup Specify a resource group id
   * @return Integer
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public Integer count(@Nonnull final String aiResourceGroup) throws OpenApiRequestException {
    return count(aiResourceGroup, null, null);
  }

  /**
   * Create execution schedule
   *
   * <p>Create an execution schedule using the configuration specified by configurationId, and
   * schedule.
   *
   * <p><b>202</b> - The execution schedule has been created successfully
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * @param aiResourceGroup Specify a resource group id
   * @param aiExecutionScheduleCreationData The value for the parameter
   *     aiExecutionScheduleCreationData
   * @return AiExecutionScheduleCreationResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public AiExecutionScheduleCreationResponse create(
      @Nonnull final String aiResourceGroup,
      @Nonnull final AiExecutionScheduleCreationData aiExecutionScheduleCreationData)
      throws OpenApiRequestException {

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'aiResourceGroup' when calling create")
          .statusCode(400);
    }

    // verify the required parameter 'aiExecutionScheduleCreationData' is set
    if (aiExecutionScheduleCreationData == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'aiExecutionScheduleCreationData' when calling create")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath = "/lm/executionSchedules";

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

    final TypeReference<AiExecutionScheduleCreationResponse> localVarReturnType =
        new TypeReference<AiExecutionScheduleCreationResponse>() {};

    return apiClient.invokeAPI(
        localVarPath,
        "POST",
        localVarQueryParams,
        localVarCollectionQueryParams,
        localVarQueryStringJoiner.toString(),
        aiExecutionScheduleCreationData,
        localVarHeaderParams,
        localVarFormParams,
        localVarAccept,
        localVarContentType,
        localVarReturnType);
  }

  /**
   * Delete execution schedule
   *
   * <p>Delete the execution schedule with executionScheduleId.
   *
   * <p><b>202</b> - The execution schedule has been deleted successfully
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * @param aiResourceGroup Specify a resource group id
   * @param executionScheduleId Execution Schedule identifier
   * @return AiExecutionScheduleDeletionResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public AiExecutionScheduleDeletionResponse delete(
      @Nonnull final String aiResourceGroup, @Nonnull final String executionScheduleId)
      throws OpenApiRequestException {

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'aiResourceGroup' when calling delete")
          .statusCode(400);
    }

    // verify the required parameter 'executionScheduleId' is set
    if (executionScheduleId == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'executionScheduleId' when calling delete")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/lm/executionSchedules/{executionScheduleId}"
            .replaceAll(
                "\\{" + "executionScheduleId" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(executionScheduleId)));

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

    final TypeReference<AiExecutionScheduleDeletionResponse> localVarReturnType =
        new TypeReference<AiExecutionScheduleDeletionResponse>() {};

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
   * Get information about an execution schedule
   *
   * <p>Retrieve details for execution schedule with executionScheduleId.
   *
   * <p><b>200</b> - Information about the execution schedule
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * @param aiResourceGroup Specify a resource group id
   * @param executionScheduleId Execution Schedule identifier
   * @return AiExecutionSchedule
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public AiExecutionSchedule get(
      @Nonnull final String aiResourceGroup, @Nonnull final String executionScheduleId)
      throws OpenApiRequestException {

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'aiResourceGroup' when calling get")
          .statusCode(400);
    }

    // verify the required parameter 'executionScheduleId' is set
    if (executionScheduleId == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'executionScheduleId' when calling get")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/lm/executionSchedules/{executionScheduleId}"
            .replaceAll(
                "\\{" + "executionScheduleId" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(executionScheduleId)));

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

    final TypeReference<AiExecutionSchedule> localVarReturnType =
        new TypeReference<AiExecutionSchedule>() {};

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
   * Update an execution schedule
   *
   * <p>Update details of an execution schedule
   *
   * <p><b>202</b> - The execution schedule has been modified successfully
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * @param aiResourceGroup Specify a resource group id
   * @param executionScheduleId Execution Schedule identifier
   * @param aiExecutionScheduleModificationRequest The value for the parameter
   *     aiExecutionScheduleModificationRequest
   * @return AiExecutionScheduleModificationResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public AiExecutionScheduleModificationResponse modify(
      @Nonnull final String aiResourceGroup,
      @Nonnull final String executionScheduleId,
      @Nonnull final AiExecutionScheduleModificationRequest aiExecutionScheduleModificationRequest)
      throws OpenApiRequestException {

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'aiResourceGroup' when calling modify")
          .statusCode(400);
    }

    // verify the required parameter 'executionScheduleId' is set
    if (executionScheduleId == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'executionScheduleId' when calling modify")
          .statusCode(400);
    }

    // verify the required parameter 'aiExecutionScheduleModificationRequest' is set
    if (aiExecutionScheduleModificationRequest == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'aiExecutionScheduleModificationRequest' when calling modify")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/lm/executionSchedules/{executionScheduleId}"
            .replaceAll(
                "\\{" + "executionScheduleId" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(executionScheduleId)));

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

    final TypeReference<AiExecutionScheduleModificationResponse> localVarReturnType =
        new TypeReference<AiExecutionScheduleModificationResponse>() {};

    return apiClient.invokeAPI(
        localVarPath,
        "PATCH",
        localVarQueryParams,
        localVarCollectionQueryParams,
        localVarQueryStringJoiner.toString(),
        aiExecutionScheduleModificationRequest,
        localVarHeaderParams,
        localVarFormParams,
        localVarAccept,
        localVarContentType,
        localVarReturnType);
  }

  /**
   * Get list of execution schedules
   *
   * <p>Retrieve a list of execution schedules that match the specified filter criteria. Filter
   * criteria include executionScheduleStatus or a configurationId. With top/skip parameters it is
   * possible to paginate the result list.
   *
   * <p><b>200</b> - A list of execution schedules
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * @param aiResourceGroup (required) Specify a resource group id
   * @param configurationId (optional) Configuration identifier
   * @param status (optional) Execution Schedule status
   * @param $top (optional, default to 10000) Number of results to display
   * @param $skip (optional) Number of results to be skipped from the ordered list of results
   * @return AiExecutionScheduleList
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public AiExecutionScheduleList query(
      @Nonnull final String aiResourceGroup,
      @Nullable final String configurationId,
      @Nullable final String status,
      @Nullable final Integer $top,
      @Nullable final Integer $skip)
      throws OpenApiRequestException {

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'aiResourceGroup' when calling query")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath = "/lm/executionSchedules";

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(ApiClient.parameterToPair("configurationId", configurationId));
    localVarQueryParams.addAll(ApiClient.parameterToPair("status", status));
    localVarQueryParams.addAll(ApiClient.parameterToPair("$top", $top));
    localVarQueryParams.addAll(ApiClient.parameterToPair("$skip", $skip));
    if (aiResourceGroup != null)
      localVarHeaderParams.put("AI-Resource-Group", ApiClient.parameterToString(aiResourceGroup));

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};

    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<AiExecutionScheduleList> localVarReturnType =
        new TypeReference<AiExecutionScheduleList>() {};

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
   * Get list of execution schedules
   *
   * <p>Retrieve a list of execution schedules that match the specified filter criteria. Filter
   * criteria include executionScheduleStatus or a configurationId. With top/skip parameters it is
   * possible to paginate the result list.
   *
   * <p><b>200</b> - A list of execution schedules
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * @param aiResourceGroup Specify a resource group id
   * @return AiExecutionScheduleList
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public AiExecutionScheduleList query(@Nonnull final String aiResourceGroup)
      throws OpenApiRequestException {
    return query(aiResourceGroup, null, null, null, null);
  }
}
