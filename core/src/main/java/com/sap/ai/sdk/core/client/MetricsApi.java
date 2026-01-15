package com.sap.ai.sdk.core.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.core.model.TrckDeleteMetricsResponse;
import com.sap.ai.sdk.core.model.TrckGetMetricResourceList;
import com.sap.ai.sdk.core.model.TrckMetricResource;
import com.sap.cloud.sdk.services.openapi.apache.ApiClient;
import com.sap.cloud.sdk.services.openapi.apache.BaseApi;
import com.sap.cloud.sdk.services.openapi.apache.OpenApiResponse;
import com.sap.cloud.sdk.services.openapi.apache.Pair;
import com.sap.cloud.sdk.services.openapi.core.OpenApiRequestException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * AI Core in version 2.41.0.
 *
 * <p>Provides tools to manage your scenarios and workflows in SAP AI Core. Execute pipelines as a
 * batch job, for example to pre-process or train your models, or perform batch inference. Serve
 * inference requests of trained models. Deploy а trained machine learning model as a web service to
 * serve inference requests with high performance. Register your own Docker registry, synchronize
 * your AI content from your own git repository, and register your own object store for training
 * data and trained models.
 */
public class MetricsApi extends BaseApi {

  /** Instantiates this API class to invoke operations on the AI Core */
  public MetricsApi() {
    super(new AiCoreService().getApiClient());
  }

  /**
   * Instantiates this API class to invoke operations on the AI Core
   *
   * @param aiCoreService The configured connectivity instance to AI Core
   */
  public MetricsApi(@Nonnull final AiCoreService aiCoreService) {
    super(aiCoreService.getApiClient());
  }

  /**
   * Delete metrics, tags, or labels
   *
   * <p>Delete metrics, tags, or labels associated with an execution.
   *
   * <p><b>200</b> - Metric Resource was successfully deleted
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * @param aiResourceGroup Specify a resource group id
   * @param executionId The Id of an execution
   * @return TrckDeleteMetricsResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public TrckDeleteMetricsResponse delete(
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
    final String localVarPath = "/lm/metrics";

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(ApiClient.parameterToPair("executionId", executionId));
    if (aiResourceGroup != null)
      localVarHeaderParams.put("AI-Resource-Group", ApiClient.parameterToString(aiResourceGroup));

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};

    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<TrckDeleteMetricsResponse> localVarReturnType =
        new TypeReference<TrckDeleteMetricsResponse>() {};

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
   * Get metrics according to specified filter conditions.
   *
   * <p>Retrieve metrics, labels, or tags according to filter conditions. One query parameter is
   * mandatory, either execution ID or filter. Use up to 10 execution IDs in a query parameter. With
   * top/skip parameters it is possible to paginate the result list.
   *
   * <p><b>200</b> - List of tracking metadata, where each item includes metrics, labels, tags and
   * customInfo. If $select query parameter is specified, each item will include only the resources
   * specified in $select.
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * <p><b>501</b> - Operation is not Supported.
   *
   * @param aiResourceGroup (required) Specify a resource group id
   * @param $filter (optional) Filter parameter allows filtering of metric resource using
   *     ExecutionId(s). User can only use in, eq operators in filter expression.
   * @param executionIds (optional) executionIds parameter allows filtering of metric resource using
   *     single or multiple ExecutionId(s).
   * @param $select (optional) returns only the resources that the client explicitly requests. User
   *     can also pass * as a value for $select, which will behave same as that of not passing
   *     $select query param.
   * @param tagFilters (optional) Filter metrics using tags by providing expressions in the format
   *     name&#x3D;value or name!&#x3D;value, separated by commas (,). The results will include only
   *     those metrics that match all the specified tag expressions, ensuring a logical AND
   *     operation. The filter supports up to 100 tag expressions, key-only searches (e.g., name)
   *     without a value, and tags containing special characters, which must be properly
   *     percent-encoded for accurate matching.
   * @param $top (optional, default to 1000) Number of results to display
   * @param $skip (optional) Number of results to be skipped from the ordered list of results
   * @return TrckGetMetricResourceList
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public TrckGetMetricResourceList find(
      @Nonnull final String aiResourceGroup,
      @Nullable final String $filter,
      @Nullable final List<String> executionIds,
      @Nullable final List<String> $select,
      @Nullable final String tagFilters,
      @Nullable final Integer $top,
      @Nullable final Integer $skip)
      throws OpenApiRequestException {

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'aiResourceGroup' when calling find")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath = "/lm/metrics";

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(ApiClient.parameterToPair("$filter", $filter));
    localVarCollectionQueryParams.addAll(
        ApiClient.parameterToPairs("csv", "executionIds", executionIds));
    localVarCollectionQueryParams.addAll(ApiClient.parameterToPairs("csv", "$select", $select));
    localVarQueryParams.addAll(ApiClient.parameterToPair("tagFilters", tagFilters));
    localVarQueryParams.addAll(ApiClient.parameterToPair("$top", $top));
    localVarQueryParams.addAll(ApiClient.parameterToPair("$skip", $skip));
    if (aiResourceGroup != null)
      localVarHeaderParams.put("AI-Resource-Group", ApiClient.parameterToString(aiResourceGroup));

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};

    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<TrckGetMetricResourceList> localVarReturnType =
        new TypeReference<TrckGetMetricResourceList>() {};

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
   * Get metrics according to specified filter conditions.
   *
   * <p>Retrieve metrics, labels, or tags according to filter conditions. One query parameter is
   * mandatory, either execution ID or filter. Use up to 10 execution IDs in a query parameter. With
   * top/skip parameters it is possible to paginate the result list.
   *
   * <p><b>200</b> - List of tracking metadata, where each item includes metrics, labels, tags and
   * customInfo. If $select query parameter is specified, each item will include only the resources
   * specified in $select.
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * <p><b>501</b> - Operation is not Supported.
   *
   * @param aiResourceGroup Specify a resource group id
   * @return TrckGetMetricResourceList
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public TrckGetMetricResourceList find(@Nonnull final String aiResourceGroup)
      throws OpenApiRequestException {
    return find(aiResourceGroup, null, null, null, null, null, null);
  }

  /**
   * Create or update metrics, tags, or labels
   *
   * <p>Update or create metrics, tags, or labels associated with an execution.
   *
   * <p><b>204</b> - Metrics was successfully updated/created
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * <p><b>413</b> - request entity is larger than limits defined by server.
   *
   * @param aiResourceGroup Specify a resource group id
   * @param trckMetricResource The value for the parameter trckMetricResource
   * @return An OpenApiResponse containing the status code of the HttpResponse.
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public OpenApiResponse patch(
      @Nonnull final String aiResourceGroup, @Nonnull final TrckMetricResource trckMetricResource)
      throws OpenApiRequestException {

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'aiResourceGroup' when calling patch")
          .statusCode(400);
    }

    // verify the required parameter 'trckMetricResource' is set
    if (trckMetricResource == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'trckMetricResource' when calling patch")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath = "/lm/metrics";

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

    final TypeReference<OpenApiResponse> localVarReturnType =
        new TypeReference<OpenApiResponse>() {};

    return apiClient.invokeAPI(
        localVarPath,
        "PATCH",
        localVarQueryParams,
        localVarCollectionQueryParams,
        localVarQueryStringJoiner.toString(),
        trckMetricResource,
        localVarHeaderParams,
        localVarFormParams,
        localVarAccept,
        localVarContentType,
        localVarReturnType);
  }
}
