package com.sap.ai.sdk.core.client;

import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.core.model.BckndAllArgoCDApplicationData;
import com.sap.ai.sdk.core.model.BckndArgoCDApplicationBaseData;
import com.sap.ai.sdk.core.model.BckndArgoCDApplicationCreationResponse;
import com.sap.ai.sdk.core.model.BckndArgoCDApplicationData;
import com.sap.ai.sdk.core.model.BckndArgoCDApplicationDeletionResponse;
import com.sap.ai.sdk.core.model.BckndArgoCDApplicationModificationResponse;
import com.sap.ai.sdk.core.model.BckndArgoCDApplicationRefreshResponse;
import com.sap.ai.sdk.core.model.BckndArgoCDApplicationStatus;
import com.sap.ai.sdk.core.model.KubesubmitV4ApplicationsCreateRequest;
import com.sap.cloud.sdk.services.openapi.core.AbstractOpenApiService;
import com.sap.cloud.sdk.services.openapi.core.OpenApiRequestException;
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
 * AI Core in version 2.41.0.
 *
 * <p>Provides tools to manage your scenarios and workflows in SAP AI Core. Execute pipelines as a
 * batch job, for example to pre-process or train your models, or perform batch inference. Serve
 * inference requests of trained models. Deploy а trained machine learning model as a web service to
 * serve inference requests with high performance. Register your own Docker registry, synchronize
 * your AI content from your own git repository, and register your own object store for training
 * data and trained models.
 */
public class ApplicationApi extends AbstractOpenApiService {

  /** Instantiates this API class to invoke operations on the AI Core */
  public ApplicationApi() {
    super(new AiCoreService().getApiClient());
  }

  /**
   * Instantiates this API class to invoke operations on the AI Core
   *
   * @param aiCoreService The configured connectivity instance to AI Core
   */
  public ApplicationApi(@Nonnull final AiCoreService aiCoreService) {
    super(aiCoreService.getApiClient());
  }

  /**
   * Create an application
   *
   * <p>Create an ArgoCD application to synchronise a repository.
   *
   * <p><b>200</b> - The ArgoCD application has been created and will be eventually synchronised
   * with the repository.
   *
   * <p><b>400</b> - The request was malformed and could thus not be processed.
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @param kubesubmitV4ApplicationsCreateRequest (required) The value for the parameter
   *     kubesubmitV4ApplicationsCreateRequest
   * @param authorization (optional) Authorization bearer token containing a JWT token.
   * @return BckndArgoCDApplicationCreationResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BckndArgoCDApplicationCreationResponse create(
      @Nonnull final KubesubmitV4ApplicationsCreateRequest kubesubmitV4ApplicationsCreateRequest,
      @Nullable final String authorization)
      throws OpenApiRequestException {
    final Object localVarPostBody = kubesubmitV4ApplicationsCreateRequest;

    // verify the required parameter 'kubesubmitV4ApplicationsCreateRequest' is set
    if (kubesubmitV4ApplicationsCreateRequest == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'kubesubmitV4ApplicationsCreateRequest' when calling create");
    }

    final String localVarPath =
        UriComponentsBuilder.fromPath("/admin/applications").build().toUriString();

    final MultiValueMap<String, String> localVarQueryParams =
        new LinkedMultiValueMap<String, String>();
    final HttpHeaders localVarHeaderParams = new HttpHeaders();
    final MultiValueMap<String, Object> localVarFormParams =
        new LinkedMultiValueMap<String, Object>();

    if (authorization != null)
      localVarHeaderParams.add("Authorization", apiClient.parameterToString(authorization));

    final String[] localVarAccepts = {"application/json"};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {"application/json"};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    final String[] localVarAuthNames = new String[] {"Oauth2"};

    final ParameterizedTypeReference<BckndArgoCDApplicationCreationResponse> localVarReturnType =
        new ParameterizedTypeReference<BckndArgoCDApplicationCreationResponse>() {};
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
   * Create an application
   *
   * <p>Create an ArgoCD application to synchronise a repository.
   *
   * <p><b>200</b> - The ArgoCD application has been created and will be eventually synchronised
   * with the repository.
   *
   * <p><b>400</b> - The request was malformed and could thus not be processed.
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @param kubesubmitV4ApplicationsCreateRequest The value for the parameter
   *     kubesubmitV4ApplicationsCreateRequest
   * @return BckndArgoCDApplicationCreationResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BckndArgoCDApplicationCreationResponse create(
      @Nonnull final KubesubmitV4ApplicationsCreateRequest kubesubmitV4ApplicationsCreateRequest)
      throws OpenApiRequestException {
    return create(kubesubmitV4ApplicationsCreateRequest, null);
  }

  /**
   * Delete application
   *
   * <p>Delete an ArgoCD application
   *
   * <p><b>200</b> - The argoCD application has been deleted successfully.
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * <p><b>400</b> - The request was malformed and could thus not be processed.
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @param applicationName (required) Name of the ArgoCD application
   * @param authorization (optional) Authorization bearer token containing a JWT token.
   * @return BckndArgoCDApplicationDeletionResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BckndArgoCDApplicationDeletionResponse delete(
      @Nonnull final String applicationName, @Nullable final String authorization)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    // verify the required parameter 'applicationName' is set
    if (applicationName == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'applicationName' when calling delete");
    }

    // create path and map variables
    final Map<String, Object> localVarPathParams = new HashMap<String, Object>();
    localVarPathParams.put("applicationName", applicationName);
    final String localVarPath =
        UriComponentsBuilder.fromPath("/admin/applications/{applicationName}")
            .buildAndExpand(localVarPathParams)
            .toUriString();

    final MultiValueMap<String, String> localVarQueryParams =
        new LinkedMultiValueMap<String, String>();
    final HttpHeaders localVarHeaderParams = new HttpHeaders();
    final MultiValueMap<String, Object> localVarFormParams =
        new LinkedMultiValueMap<String, Object>();

    if (authorization != null)
      localVarHeaderParams.add("Authorization", apiClient.parameterToString(authorization));

    final String[] localVarAccepts = {"application/json"};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    final String[] localVarAuthNames = new String[] {"Oauth2"};

    final ParameterizedTypeReference<BckndArgoCDApplicationDeletionResponse> localVarReturnType =
        new ParameterizedTypeReference<BckndArgoCDApplicationDeletionResponse>() {};
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
   * Delete application
   *
   * <p>Delete an ArgoCD application
   *
   * <p><b>200</b> - The argoCD application has been deleted successfully.
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * <p><b>400</b> - The request was malformed and could thus not be processed.
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @param applicationName Name of the ArgoCD application
   * @return BckndArgoCDApplicationDeletionResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BckndArgoCDApplicationDeletionResponse delete(@Nonnull final String applicationName)
      throws OpenApiRequestException {
    return delete(applicationName, null);
  }

  /**
   * Get ArgoCD application
   *
   * <p>Retrieve the ArgoCD application details.
   *
   * <p><b>200</b> - The application has been found and returned.
   *
   * <p><b>400</b> - The request was malformed and could thus not be processed.
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @param applicationName (required) Name of the ArgoCD application
   * @param authorization (optional) Authorization bearer token containing a JWT token.
   * @return BckndArgoCDApplicationData
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BckndArgoCDApplicationData get(
      @Nonnull final String applicationName, @Nullable final String authorization)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    // verify the required parameter 'applicationName' is set
    if (applicationName == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'applicationName' when calling get");
    }

    // create path and map variables
    final Map<String, Object> localVarPathParams = new HashMap<String, Object>();
    localVarPathParams.put("applicationName", applicationName);
    final String localVarPath =
        UriComponentsBuilder.fromPath("/admin/applications/{applicationName}")
            .buildAndExpand(localVarPathParams)
            .toUriString();

    final MultiValueMap<String, String> localVarQueryParams =
        new LinkedMultiValueMap<String, String>();
    final HttpHeaders localVarHeaderParams = new HttpHeaders();
    final MultiValueMap<String, Object> localVarFormParams =
        new LinkedMultiValueMap<String, Object>();

    if (authorization != null)
      localVarHeaderParams.add("Authorization", apiClient.parameterToString(authorization));

    final String[] localVarAccepts = {"application/json"};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    final String[] localVarAuthNames = new String[] {"Oauth2"};

    final ParameterizedTypeReference<BckndArgoCDApplicationData> localVarReturnType =
        new ParameterizedTypeReference<BckndArgoCDApplicationData>() {};
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
   * Get ArgoCD application
   *
   * <p>Retrieve the ArgoCD application details.
   *
   * <p><b>200</b> - The application has been found and returned.
   *
   * <p><b>400</b> - The request was malformed and could thus not be processed.
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @param applicationName Name of the ArgoCD application
   * @return BckndArgoCDApplicationData
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BckndArgoCDApplicationData get(@Nonnull final String applicationName)
      throws OpenApiRequestException {
    return get(applicationName, null);
  }

  /**
   * Return all applications
   *
   * <p>Return all Argo CD application data objects.
   *
   * <p><b>200</b> - All applications have been found and returned.
   *
   * <p><b>400</b> - The request was malformed and could thus not be processed.
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @param authorization (optional) Authorization bearer token containing a JWT token.
   * @param $top (optional) Number of results to display
   * @param $skip (optional) Number of results to be skipped from the ordered list of results
   * @param $count (optional) When the $count field is set to false, the response contains a count
   *     of the items present in the response. When the $count field is set to true, the response
   *     contains a count of all the items present on the server, and not just the ones in the
   *     response. When the $count field is not passed, it is false by default.
   * @return BckndAllArgoCDApplicationData
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BckndAllArgoCDApplicationData getAll(
      @Nullable final String authorization,
      @Nullable final Integer $top,
      @Nullable final Integer $skip,
      @Nullable final Boolean $count)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    final String localVarPath =
        UriComponentsBuilder.fromPath("/admin/applications").build().toUriString();

    final MultiValueMap<String, String> localVarQueryParams =
        new LinkedMultiValueMap<String, String>();
    final HttpHeaders localVarHeaderParams = new HttpHeaders();
    final MultiValueMap<String, Object> localVarFormParams =
        new LinkedMultiValueMap<String, Object>();

    localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "$top", $top));
    localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "$skip", $skip));
    localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "$count", $count));

    if (authorization != null)
      localVarHeaderParams.add("Authorization", apiClient.parameterToString(authorization));

    final String[] localVarAccepts = {"application/json"};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    final String[] localVarAuthNames = new String[] {"Oauth2"};

    final ParameterizedTypeReference<BckndAllArgoCDApplicationData> localVarReturnType =
        new ParameterizedTypeReference<BckndAllArgoCDApplicationData>() {};
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
   * Return all applications
   *
   * <p>Return all Argo CD application data objects.
   *
   * <p><b>200</b> - All applications have been found and returned.
   *
   * <p><b>400</b> - The request was malformed and could thus not be processed.
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @return BckndAllArgoCDApplicationData
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BckndAllArgoCDApplicationData getAll() throws OpenApiRequestException {
    return getAll(null, null, null, null);
  }

  /**
   * Returns the ArgoCD application status
   *
   * <p>Returns the ArgoCD application health and sync status.
   *
   * <p><b>200</b> - The application status has been found and returned.
   *
   * <p><b>400</b> - The request was malformed and could thus not be processed.
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @param applicationName (required) Name of the ArgoCD application
   * @param authorization (optional) Authorization bearer token containing a JWT token.
   * @return BckndArgoCDApplicationStatus
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BckndArgoCDApplicationStatus getStatus(
      @Nonnull final String applicationName, @Nullable final String authorization)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    // verify the required parameter 'applicationName' is set
    if (applicationName == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'applicationName' when calling getStatus");
    }

    // create path and map variables
    final Map<String, Object> localVarPathParams = new HashMap<String, Object>();
    localVarPathParams.put("applicationName", applicationName);
    final String localVarPath =
        UriComponentsBuilder.fromPath("/admin/applications/{applicationName}/status")
            .buildAndExpand(localVarPathParams)
            .toUriString();

    final MultiValueMap<String, String> localVarQueryParams =
        new LinkedMultiValueMap<String, String>();
    final HttpHeaders localVarHeaderParams = new HttpHeaders();
    final MultiValueMap<String, Object> localVarFormParams =
        new LinkedMultiValueMap<String, Object>();

    if (authorization != null)
      localVarHeaderParams.add("Authorization", apiClient.parameterToString(authorization));

    final String[] localVarAccepts = {"application/json"};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    final String[] localVarAuthNames = new String[] {"Oauth2"};

    final ParameterizedTypeReference<BckndArgoCDApplicationStatus> localVarReturnType =
        new ParameterizedTypeReference<BckndArgoCDApplicationStatus>() {};
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
   * Returns the ArgoCD application status
   *
   * <p>Returns the ArgoCD application health and sync status.
   *
   * <p><b>200</b> - The application status has been found and returned.
   *
   * <p><b>400</b> - The request was malformed and could thus not be processed.
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @param applicationName Name of the ArgoCD application
   * @return BckndArgoCDApplicationStatus
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BckndArgoCDApplicationStatus getStatus(@Nonnull final String applicationName)
      throws OpenApiRequestException {
    return getStatus(applicationName, null);
  }

  /**
   * Makes ArgoDC refresh the specified application
   *
   * <p>Schedules a refresh of the specified application that will be picked up by ArgoCD
   * asynchronously
   *
   * <p><b>202</b> - A refresh of the application has been scheduled
   *
   * <p><b>400</b> - The request was malformed and could thus not be processed.
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @param applicationName (required) Name of the ArgoCD application
   * @param authorization (optional) Authorization bearer token containing a JWT token.
   * @return BckndArgoCDApplicationRefreshResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BckndArgoCDApplicationRefreshResponse refresh(
      @Nonnull final String applicationName, @Nullable final String authorization)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    // verify the required parameter 'applicationName' is set
    if (applicationName == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'applicationName' when calling refresh");
    }

    // create path and map variables
    final Map<String, Object> localVarPathParams = new HashMap<String, Object>();
    localVarPathParams.put("applicationName", applicationName);
    final String localVarPath =
        UriComponentsBuilder.fromPath("/admin/applications/{applicationName}/refresh")
            .buildAndExpand(localVarPathParams)
            .toUriString();

    final MultiValueMap<String, String> localVarQueryParams =
        new LinkedMultiValueMap<String, String>();
    final HttpHeaders localVarHeaderParams = new HttpHeaders();
    final MultiValueMap<String, Object> localVarFormParams =
        new LinkedMultiValueMap<String, Object>();

    if (authorization != null)
      localVarHeaderParams.add("Authorization", apiClient.parameterToString(authorization));

    final String[] localVarAccepts = {"application/json"};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    final String[] localVarAuthNames = new String[] {"Oauth2"};

    final ParameterizedTypeReference<BckndArgoCDApplicationRefreshResponse> localVarReturnType =
        new ParameterizedTypeReference<BckndArgoCDApplicationRefreshResponse>() {};
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
   * Makes ArgoDC refresh the specified application
   *
   * <p>Schedules a refresh of the specified application that will be picked up by ArgoCD
   * asynchronously
   *
   * <p><b>202</b> - A refresh of the application has been scheduled
   *
   * <p><b>400</b> - The request was malformed and could thus not be processed.
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @param applicationName Name of the ArgoCD application
   * @return BckndArgoCDApplicationRefreshResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BckndArgoCDApplicationRefreshResponse refresh(@Nonnull final String applicationName)
      throws OpenApiRequestException {
    return refresh(applicationName, null);
  }

  /**
   * Update the ArgoCD application.
   *
   * <p>Update the referenced ArgoCD application to synchronize the repository.
   *
   * <p><b>200</b> - The ArgoCD application has been created and will be eventually synchronised
   * with the repository.
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * <p><b>400</b> - The request was malformed and could thus not be processed.
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @param applicationName (required) Name of the ArgoCD application
   * @param bckndArgoCDApplicationBaseData (required) The value for the parameter
   *     bckndArgoCDApplicationBaseData
   * @param authorization (optional) Authorization bearer token containing a JWT token.
   * @return BckndArgoCDApplicationModificationResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BckndArgoCDApplicationModificationResponse update(
      @Nonnull final String applicationName,
      @Nonnull final BckndArgoCDApplicationBaseData bckndArgoCDApplicationBaseData,
      @Nullable final String authorization)
      throws OpenApiRequestException {
    final Object localVarPostBody = bckndArgoCDApplicationBaseData;

    // verify the required parameter 'applicationName' is set
    if (applicationName == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'applicationName' when calling update");
    }

    // verify the required parameter 'bckndArgoCDApplicationBaseData' is set
    if (bckndArgoCDApplicationBaseData == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'bckndArgoCDApplicationBaseData' when calling update");
    }

    // create path and map variables
    final Map<String, Object> localVarPathParams = new HashMap<String, Object>();
    localVarPathParams.put("applicationName", applicationName);
    final String localVarPath =
        UriComponentsBuilder.fromPath("/admin/applications/{applicationName}")
            .buildAndExpand(localVarPathParams)
            .toUriString();

    final MultiValueMap<String, String> localVarQueryParams =
        new LinkedMultiValueMap<String, String>();
    final HttpHeaders localVarHeaderParams = new HttpHeaders();
    final MultiValueMap<String, Object> localVarFormParams =
        new LinkedMultiValueMap<String, Object>();

    if (authorization != null)
      localVarHeaderParams.add("Authorization", apiClient.parameterToString(authorization));

    final String[] localVarAccepts = {"application/json"};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {"application/json"};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    final String[] localVarAuthNames = new String[] {"Oauth2"};

    final ParameterizedTypeReference<BckndArgoCDApplicationModificationResponse>
        localVarReturnType =
            new ParameterizedTypeReference<BckndArgoCDApplicationModificationResponse>() {};
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
   * Update the ArgoCD application.
   *
   * <p>Update the referenced ArgoCD application to synchronize the repository.
   *
   * <p><b>200</b> - The ArgoCD application has been created and will be eventually synchronised
   * with the repository.
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * <p><b>400</b> - The request was malformed and could thus not be processed.
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @param applicationName Name of the ArgoCD application
   * @param bckndArgoCDApplicationBaseData The value for the parameter
   *     bckndArgoCDApplicationBaseData
   * @return BckndArgoCDApplicationModificationResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BckndArgoCDApplicationModificationResponse update(
      @Nonnull final String applicationName,
      @Nonnull final BckndArgoCDApplicationBaseData bckndArgoCDApplicationBaseData)
      throws OpenApiRequestException {
    return update(applicationName, bckndArgoCDApplicationBaseData, null);
  }
}
