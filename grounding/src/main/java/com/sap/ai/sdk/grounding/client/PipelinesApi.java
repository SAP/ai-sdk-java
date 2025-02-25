package com.sap.ai.sdk.grounding.client;

import com.google.common.annotations.Beta;
import com.sap.ai.sdk.grounding.model.Pipeline;
import com.sap.ai.sdk.grounding.model.PipelineId;
import com.sap.ai.sdk.grounding.model.PipelinePostRequst;
import com.sap.ai.sdk.grounding.model.PipelineStatus;
import com.sap.ai.sdk.grounding.model.Pipelines;
import com.sap.cloud.sdk.cloudplatform.connectivity.Destination;
import com.sap.cloud.sdk.services.openapi.apiclient.ApiClient;
import com.sap.cloud.sdk.services.openapi.core.AbstractOpenApiService;
import com.sap.cloud.sdk.services.openapi.core.OpenApiRequestException;
import com.sap.cloud.sdk.services.openapi.core.OpenApiResponse;
import java.util.HashMap;
import java.util.List;
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
 * Document Grounding Pipeline API in version 0.1.0.
 *
 * <p>SAP AI Core - API Specification AI Data Management api's
 */
@Beta
public class PipelinesApi extends AbstractOpenApiService {
  /**
   * Instantiates this API class to invoke operations on the Document Grounding Pipeline API.
   *
   * @param httpDestination The destination that API should be used with
   */
  public PipelinesApi(@Nonnull final Destination httpDestination) {
    super(httpDestination);
  }

  /**
   * Instantiates this API class to invoke operations on the Document Grounding Pipeline API based
   * on a given {@link ApiClient}.
   *
   * @param apiClient ApiClient to invoke the API on
   */
  @Beta
  public PipelinesApi(@Nonnull final ApiClient apiClient) {
    super(apiClient);
  }

  /**
   * Create a pipeline
   *
   * <p><b>201</b> - Returns pipelineId on successful creation.
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * @param aiResourceGroup The value for the parameter aiResourceGroup
   * @param pipelinePostRequst The value for the parameter pipelinePostRequst
   * @return PipelineId
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public PipelineId createPipeline(
      @Nonnull final String aiResourceGroup, @Nonnull final PipelinePostRequst pipelinePostRequst)
      throws OpenApiRequestException {
    final Object localVarPostBody = pipelinePostRequst;

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'aiResourceGroup' when calling createPipeline");
    }

    // verify the required parameter 'pipelinePostRequst' is set
    if (pipelinePostRequst == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'pipelinePostRequst' when calling pipelineV1PipelineEndpointsCreatePipeline");
    }

    final String localVarPath = UriComponentsBuilder.fromPath("/pipelines").build().toUriString();

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

    final String[] localVarAuthNames = new String[] {};

    final ParameterizedTypeReference<PipelineId> localVarReturnType =
        new ParameterizedTypeReference<PipelineId>() {};
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
   * Delete a pipeline by pipeline id
   *
   * <p><b>204</b> - No Content
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * @param aiResourceGroup The value for the parameter aiResourceGroup
   * @param pipelineId The ID of the pipeline to delete.
   * @return An OpenApiResponse containing the status code of the HttpResponse.
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public OpenApiResponse deletePipelineById(
      @Nonnull final String aiResourceGroup, @Nonnull final String pipelineId)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'aiResourceGroup' when calling deletePipelineById");
    }

    // verify the required parameter 'pipelineId' is set
    if (pipelineId == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'pipelineId' when calling deletePipelineById");
    }

    // create path and map variables
    final Map<String, Object> localVarPathParams = new HashMap<String, Object>();
    localVarPathParams.put("pipelineId", pipelineId);
    final String localVarPath =
        UriComponentsBuilder.fromPath("/pipelines/{pipelineId}")
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

    final String[] localVarAuthNames = new String[] {};

    final ParameterizedTypeReference<Void> localVarReturnType =
        new ParameterizedTypeReference<Void>() {};
    apiClient.invokeAPI(
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
    return new OpenApiResponse(apiClient);
  }

  /**
   * Get all pipelines
   *
   * <p><b>200</b> - Returns all pipelines for the tenant.
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * @param aiResourceGroup (required) The value for the parameter aiResourceGroup
   * @param $top (optional) Number of results to display
   * @param $skip (optional) Number of results to be skipped from the ordered list of results
   * @param $count (optional) When the $count field is set to false, the response contains a count
   *     of the items present in the response. When the $count field is set to true, the response
   *     contains a count of all the items present on the server, and not just the ones in the
   *     response. When the $count field is not passed, it is false by default.
   * @return Pipelines
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public Pipelines getAllPipelines(
      @Nonnull final String aiResourceGroup,
      @Nullable final Integer $top,
      @Nullable final Integer $skip,
      @Nullable final Boolean $count)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'aiResourceGroup' when calling getAllPipelines");
    }

    final String localVarPath = UriComponentsBuilder.fromPath("/pipelines").build().toUriString();

    final MultiValueMap<String, String> localVarQueryParams =
        new LinkedMultiValueMap<String, String>();
    final HttpHeaders localVarHeaderParams = new HttpHeaders();
    final MultiValueMap<String, Object> localVarFormParams =
        new LinkedMultiValueMap<String, Object>();

    localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "$top", $top));
    localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "$skip", $skip));
    localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "$count", $count));

    if (aiResourceGroup != null)
      localVarHeaderParams.add("AI-Resource-Group", apiClient.parameterToString(aiResourceGroup));

    final String[] localVarAccepts = {"application/json"};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    final String[] localVarAuthNames = new String[] {};

    final ParameterizedTypeReference<Pipelines> localVarReturnType =
        new ParameterizedTypeReference<Pipelines>() {};
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
   * Get all pipelines
   *
   * <p><b>200</b> - Returns all pipelines for the tenant.
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * @param aiResourceGroup The value for the parameter aiResourceGroup
   * @return Pipelines
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public Pipelines getAllPipelines(@Nonnull final String aiResourceGroup)
      throws OpenApiRequestException {
    return getAllPipelines(aiResourceGroup, null, null, null);
  }

  /**
   * Get details of a pipeline by pipeline id
   *
   * <p><b>200</b> - Returns the pipeline for an pipelineId
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * @param aiResourceGroup The value for the parameter aiResourceGroup
   * @param pipelineId The ID of the pipeline to get.
   * @return Pipeline
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public Pipeline getPipelineById(
      @Nonnull final String aiResourceGroup, @Nonnull final String pipelineId)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'aiResourceGroup' when calling getPipelineById");
    }

    // verify the required parameter 'pipelineId' is set
    if (pipelineId == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'pipelineId' when calling getPipelineById");
    }

    // create path and map variables
    final Map<String, Object> localVarPathParams = new HashMap<String, Object>();
    localVarPathParams.put("pipelineId", pipelineId);
    final String localVarPath =
        UriComponentsBuilder.fromPath("/pipelines/{pipelineId}")
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

    final String[] localVarAuthNames = new String[] {};

    final ParameterizedTypeReference<Pipeline> localVarReturnType =
        new ParameterizedTypeReference<Pipeline>() {};
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
   * Get pipeline status by pipeline id
   *
   * <p><b>200</b> - Returns the pipeline status for an pipelineId.
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * @param aiResourceGroup The value for the parameter aiResourceGroup
   * @param pipelineId The ID of the pipeline to get status.
   * @return PipelineStatus
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public PipelineStatus getPipelineStatus(
      @Nonnull final String aiResourceGroup, @Nonnull final String pipelineId)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'aiResourceGroup' when calling getPipelineStatus");
    }

    // verify the required parameter 'pipelineId' is set
    if (pipelineId == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'pipelineId' when calling getPipelineStatus");
    }

    // create path and map variables
    final Map<String, Object> localVarPathParams = new HashMap<String, Object>();
    localVarPathParams.put("pipelineId", pipelineId);
    final String localVarPath =
        UriComponentsBuilder.fromPath("/pipelines/{pipelineId}/status")
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

    final String[] localVarAuthNames = new String[] {};

    final ParameterizedTypeReference<PipelineStatus> localVarReturnType =
        new ParameterizedTypeReference<PipelineStatus>() {};
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
}
