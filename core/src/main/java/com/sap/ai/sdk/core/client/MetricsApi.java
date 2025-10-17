package com.sap.ai.sdk.core.client;

import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.core.model.TrckDeleteMetricsResponse;
import com.sap.ai.sdk.core.model.TrckGetMetricResourceList;
import com.sap.ai.sdk.core.model.TrckMetricResource;
import com.sap.cloud.sdk.services.openapi.apiclient.ApiClient;
import com.sap.cloud.sdk.services.openapi.core.AbstractOpenApiService;
import com.sap.cloud.sdk.services.openapi.core.OpenApiRequestException;
import com.sap.cloud.sdk.services.openapi.core.OpenApiResponse;
import java.util.List;
import java.util.Locale;
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
 * AI Core in version 2.41.0.
 *
 * <p>Provides tools to manage your scenarios and workflows in SAP AI Core. Execute pipelines as a
 * batch job, for example to pre-process or train your models, or perform batch inference. Serve
 * inference requests of trained models. Deploy а trained machine learning model as a web service to
 * serve inference requests with high performance. Register your own Docker registry, synchronize
 * your AI content from your own git repository, and register your own object store for training
 * data and trained models.
 */
public class MetricsApi extends AbstractOpenApiService {

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

    final String localVarPath = UriComponentsBuilder.fromPath("/lm/metrics").build().toUriString();

    final MultiValueMap<String, String> localVarQueryParams =
        new LinkedMultiValueMap<String, String>();
    final HttpHeaders localVarHeaderParams = new HttpHeaders();
    final MultiValueMap<String, Object> localVarFormParams =
        new LinkedMultiValueMap<String, Object>();

    localVarQueryParams.putAll(
        apiClient.parameterToMultiValueMap(null, "executionId", executionId));

    if (aiResourceGroup != null)
      localVarHeaderParams.add("AI-Resource-Group", apiClient.parameterToString(aiResourceGroup));

    final String[] localVarAccepts = {"application/json"};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    final String[] localVarAuthNames = new String[] {"Oauth2"};

    final ParameterizedTypeReference<TrckDeleteMetricsResponse> localVarReturnType =
        new ParameterizedTypeReference<TrckDeleteMetricsResponse>() {};
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
   * @param executionIds (optional executionIds parameter allows filtering of metric resource using
   *     single or multiple ExecutionId(s).
   * @param $select (optional returns only the resources that the client explicitly requests. User
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
    final Object localVarPostBody = null;

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'aiResourceGroup' when calling find");
    }

    final String localVarPath = UriComponentsBuilder.fromPath("/lm/metrics").build().toUriString();

    final MultiValueMap<String, String> localVarQueryParams =
        new LinkedMultiValueMap<String, String>();
    final HttpHeaders localVarHeaderParams = new HttpHeaders();
    final MultiValueMap<String, Object> localVarFormParams =
        new LinkedMultiValueMap<String, Object>();

    localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "$filter", $filter));
    localVarQueryParams.putAll(
        apiClient.parameterToMultiValueMap(
            ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)),
            "executionIds",
            executionIds));
    localVarQueryParams.putAll(
        apiClient.parameterToMultiValueMap(
            ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)),
            "$select",
            $select));
    localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "tagFilters", tagFilters));
    localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "$top", $top));
    localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "$skip", $skip));

    if (aiResourceGroup != null)
      localVarHeaderParams.add("AI-Resource-Group", apiClient.parameterToString(aiResourceGroup));

    final String[] localVarAccepts = {"application/json"};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    final String[] localVarAuthNames = new String[] {"Oauth2"};

    final ParameterizedTypeReference<TrckGetMetricResourceList> localVarReturnType =
        new ParameterizedTypeReference<TrckGetMetricResourceList>() {};
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
    final Object localVarPostBody = trckMetricResource;

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'aiResourceGroup' when calling patch");
    }

    // verify the required parameter 'trckMetricResource' is set
    if (trckMetricResource == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'trckMetricResource' when calling patch");
    }

    final String localVarPath = UriComponentsBuilder.fromPath("/lm/metrics").build().toUriString();

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

    final ParameterizedTypeReference<Void> localVarReturnType =
        new ParameterizedTypeReference<Void>() {};
    apiClient.invokeAPI(
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
    return new OpenApiResponse(apiClient);
  }
}
