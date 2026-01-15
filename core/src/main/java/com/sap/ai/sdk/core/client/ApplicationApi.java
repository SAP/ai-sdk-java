package com.sap.ai.sdk.core.client;

import com.fasterxml.jackson.core.type.TypeReference;
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
import com.sap.cloud.sdk.services.openapi.apache.ApiClient;
import com.sap.cloud.sdk.services.openapi.apache.BaseApi;
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
public class ApplicationApi extends BaseApi {

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

    // verify the required parameter 'kubesubmitV4ApplicationsCreateRequest' is set
    if (kubesubmitV4ApplicationsCreateRequest == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'kubesubmitV4ApplicationsCreateRequest' when calling create")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath = "/admin/applications";

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    if (authorization != null)
      localVarHeaderParams.put("Authorization", ApiClient.parameterToString(authorization));

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {"application/json"};
    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<BckndArgoCDApplicationCreationResponse> localVarReturnType =
        new TypeReference<BckndArgoCDApplicationCreationResponse>() {};

    return apiClient.invokeAPI(
        localVarPath,
        "POST",
        localVarQueryParams,
        localVarCollectionQueryParams,
        localVarQueryStringJoiner.toString(),
        kubesubmitV4ApplicationsCreateRequest,
        localVarHeaderParams,
        localVarFormParams,
        localVarAccept,
        localVarContentType,
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

    // verify the required parameter 'applicationName' is set
    if (applicationName == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'applicationName' when calling delete")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/admin/applications/{applicationName}"
            .replaceAll(
                "\\{" + "applicationName" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(applicationName)));

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    if (authorization != null)
      localVarHeaderParams.put("Authorization", ApiClient.parameterToString(authorization));

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};

    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<BckndArgoCDApplicationDeletionResponse> localVarReturnType =
        new TypeReference<BckndArgoCDApplicationDeletionResponse>() {};

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

    // verify the required parameter 'applicationName' is set
    if (applicationName == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'applicationName' when calling get")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/admin/applications/{applicationName}"
            .replaceAll(
                "\\{" + "applicationName" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(applicationName)));

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    if (authorization != null)
      localVarHeaderParams.put("Authorization", ApiClient.parameterToString(authorization));

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};

    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<BckndArgoCDApplicationData> localVarReturnType =
        new TypeReference<BckndArgoCDApplicationData>() {};

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

    // create path and map variables
    final String localVarPath = "/admin/applications";

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(ApiClient.parameterToPair("$top", $top));
    localVarQueryParams.addAll(ApiClient.parameterToPair("$skip", $skip));
    localVarQueryParams.addAll(ApiClient.parameterToPair("$count", $count));
    if (authorization != null)
      localVarHeaderParams.put("Authorization", ApiClient.parameterToString(authorization));

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};

    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<BckndAllArgoCDApplicationData> localVarReturnType =
        new TypeReference<BckndAllArgoCDApplicationData>() {};

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

    // verify the required parameter 'applicationName' is set
    if (applicationName == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'applicationName' when calling getStatus")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/admin/applications/{applicationName}/status"
            .replaceAll(
                "\\{" + "applicationName" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(applicationName)));

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    if (authorization != null)
      localVarHeaderParams.put("Authorization", ApiClient.parameterToString(authorization));

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};

    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<BckndArgoCDApplicationStatus> localVarReturnType =
        new TypeReference<BckndArgoCDApplicationStatus>() {};

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

    // verify the required parameter 'applicationName' is set
    if (applicationName == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'applicationName' when calling refresh")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/admin/applications/{applicationName}/refresh"
            .replaceAll(
                "\\{" + "applicationName" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(applicationName)));

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    if (authorization != null)
      localVarHeaderParams.put("Authorization", ApiClient.parameterToString(authorization));

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};

    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<BckndArgoCDApplicationRefreshResponse> localVarReturnType =
        new TypeReference<BckndArgoCDApplicationRefreshResponse>() {};

    return apiClient.invokeAPI(
        localVarPath,
        "POST",
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

    // verify the required parameter 'applicationName' is set
    if (applicationName == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'applicationName' when calling update")
          .statusCode(400);
    }

    // verify the required parameter 'bckndArgoCDApplicationBaseData' is set
    if (bckndArgoCDApplicationBaseData == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'bckndArgoCDApplicationBaseData' when calling update")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/admin/applications/{applicationName}"
            .replaceAll(
                "\\{" + "applicationName" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(applicationName)));

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    if (authorization != null)
      localVarHeaderParams.put("Authorization", ApiClient.parameterToString(authorization));

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {"application/json"};
    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<BckndArgoCDApplicationModificationResponse> localVarReturnType =
        new TypeReference<BckndArgoCDApplicationModificationResponse>() {};

    return apiClient.invokeAPI(
        localVarPath,
        "PATCH",
        localVarQueryParams,
        localVarCollectionQueryParams,
        localVarQueryStringJoiner.toString(),
        bckndArgoCDApplicationBaseData,
        localVarHeaderParams,
        localVarFormParams,
        localVarAccept,
        localVarContentType,
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
