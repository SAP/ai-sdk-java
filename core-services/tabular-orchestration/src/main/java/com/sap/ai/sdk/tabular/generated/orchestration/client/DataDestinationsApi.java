package com.sap.ai.sdk.tabular.generated.orchestration.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.sap.ai.sdk.tabular.generated.orchestration.model.AsyncCreateDataDestinationResponse;
import com.sap.ai.sdk.tabular.generated.orchestration.model.ControllersDataDestinationV1EndpointsSearchDataDestinationsRequest;
import com.sap.ai.sdk.tabular.generated.orchestration.model.CreateDataDestination;
import com.sap.ai.sdk.tabular.generated.orchestration.model.GetDataDestination;
import com.sap.ai.sdk.tabular.generated.orchestration.model.GetDataDestinations;
import com.sap.ai.sdk.tabular.generated.orchestration.model.PatchDataDestination;
import com.sap.ai.sdk.tabular.generated.orchestration.model.ValidateDataDestinationRequest;
import com.sap.ai.sdk.tabular.generated.orchestration.model.ValidateDataDestinationResponse;
import com.sap.cloud.sdk.cloudplatform.connectivity.Destination;
import com.sap.cloud.sdk.services.openapi.apache.apiclient.ApiClient;
import com.sap.cloud.sdk.services.openapi.apache.apiclient.BaseApi;
import com.sap.cloud.sdk.services.openapi.apache.apiclient.Pair;
import com.sap.cloud.sdk.services.openapi.apache.core.OpenApiRequestException;
import com.sap.cloud.sdk.services.openapi.apache.core.OpenApiResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Context Registry in version 1.0.0.
 *
 * <p>Context Registry is a service for managing tabular data contexts for AI applications. It
 * provides capabilities to register data destinations (object stores and data sharing platforms),
 * create tabular artifacts from various file formats (CSV, Parquet, Delta), and configure scenarios
 * for context selection in AI-driven business solutions.
 */
public class DataDestinationsApi extends BaseApi {

  /**
   * Instantiates this API class to invoke operations on the Context Registry.
   *
   * @param httpDestination The destination that API should be used with
   */
  public DataDestinationsApi(@Nonnull final Destination httpDestination) {
    super(httpDestination);
  }

  /**
   * Instantiates this API class to invoke operations on the Context Registry based on a given
   * {@link ApiClient}.
   *
   * @param apiClient ApiClient to invoke the API on
   */
  public DataDestinationsApi(@Nonnull final ApiClient apiClient) {
    super(apiClient);
  }

  /**
   * Creates a new API instance with additional default headers.
   *
   * @param defaultHeaders Additional headers to include in all requests
   * @return A new API instance with the combined headers
   */
  public DataDestinationsApi withDefaultHeaders(@Nonnull final Map<String, String> defaultHeaders) {
    final var api = new DataDestinationsApi(apiClient);
    api.defaultHeaders.putAll(this.defaultHeaders);
    api.defaultHeaders.putAll(defaultHeaders);
    return api;
  }

  /**
   * Async Data Destination Creation
   *
   * <p>Create a data destination asynchronously. Schema validation happens synchronously;
   * credential validation and secret storage run in a background task. Poll GET
   * /dataDestinations/{name} to track progress via status and errorMessage. Returns 202 (not exists
   * or ERROR retry), 409 (ACTIVE or DELETING), 422 (retry exhausted).
   *
   * <p><b>202</b> - Accepted - background task launched
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * <p><b>409</b> - The resource conflicts with existing resources or has dependencies
   *
   * <p><b>422</b> - The request is valid but cannot be processed due to semantic errors
   *
   * <p><b>500</b> - Server error
   *
   * @param aiResourceGroup Resource Group ID
   * @param dataDestinationName Unique identifier for the data destination
   * @param createDataDestination The value for the parameter createDataDestination
   * @return AsyncCreateDataDestinationResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public AsyncCreateDataDestinationResponse createUpdateDataDestination(
      @Nonnull final String aiResourceGroup,
      @Nonnull final String dataDestinationName,
      @Nonnull final CreateDataDestination createDataDestination)
      throws OpenApiRequestException {

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'aiResourceGroup' when calling createUpdateDataDestination")
          .statusCode(400);
    }

    // verify the required parameter 'dataDestinationName' is set
    if (dataDestinationName == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'dataDestinationName' when calling createUpdateDataDestination")
          .statusCode(400);
    }

    // verify the required parameter 'createDataDestination' is set
    if (createDataDestination == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'createDataDestination' when calling createUpdateDataDestination")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/dataDestinations/{dataDestinationName}"
            .replaceAll(
                "\\{" + "dataDestinationName" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(dataDestinationName)));

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>(defaultHeaders);
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    if (aiResourceGroup != null)
      localVarHeaderParams.put("AI-Resource-Group", ApiClient.parameterToString(aiResourceGroup));

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {"application/json"};
    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<AsyncCreateDataDestinationResponse> localVarReturnType =
        new TypeReference<AsyncCreateDataDestinationResponse>() {};

    return apiClient.invokeAPI(
        localVarPath,
        "PUT",
        localVarQueryParams,
        localVarCollectionQueryParams,
        localVarQueryStringJoiner.toString(),
        createDataDestination,
        localVarHeaderParams,
        localVarFormParams,
        localVarAccept,
        localVarContentType,
        localVarReturnType);
  }

  /**
   * Delete Data Destination
   *
   * <p>Mark the data destination for deletion. Synchronously checks for dependent TabularArtifacts;
   * returns 202 immediately after marking DELETING. The cron reaper handles secret deletion and
   * hard delete.
   *
   * <p><b>202</b> - Accepted — data destination marked DELETING; cron handles cleanup
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * <p><b>409</b> - The resource conflicts with existing resources or has dependencies
   *
   * <p><b>500</b> - Server error
   *
   * @param aiResourceGroup Resource Group ID
   * @param dataDestinationName The ID of the data destination to delete
   * @return An OpenApiResponse containing the status code of the HttpResponse.
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public OpenApiResponse deleteDataDestinationByName(
      @Nonnull final String aiResourceGroup, @Nonnull final String dataDestinationName)
      throws OpenApiRequestException {

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'aiResourceGroup' when calling deleteDataDestinationByName")
          .statusCode(400);
    }

    // verify the required parameter 'dataDestinationName' is set
    if (dataDestinationName == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'dataDestinationName' when calling deleteDataDestinationByName")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/dataDestinations/{dataDestinationName}"
            .replaceAll(
                "\\{" + "dataDestinationName" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(dataDestinationName)));

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>(defaultHeaders);
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    if (aiResourceGroup != null)
      localVarHeaderParams.put("AI-Resource-Group", ApiClient.parameterToString(aiResourceGroup));

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};

    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<OpenApiResponse> localVarReturnType =
        new TypeReference<OpenApiResponse>() {};

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
   * Get Data Destination Details
   *
   * <p>Get metadata of a specific data destination (excluding credentials)
   *
   * <p><b>200</b> - Returns the data destination for the given ID
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * <p><b>500</b> - Server error
   *
   * @param aiResourceGroup Resource Group ID
   * @param dataDestinationName The ID of the data destination to get
   * @return GetDataDestination
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public GetDataDestination getDataDestinationByName(
      @Nonnull final String aiResourceGroup, @Nonnull final String dataDestinationName)
      throws OpenApiRequestException {

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'aiResourceGroup' when calling getDataDestinationByName")
          .statusCode(400);
    }

    // verify the required parameter 'dataDestinationName' is set
    if (dataDestinationName == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'dataDestinationName' when calling getDataDestinationByName")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/dataDestinations/{dataDestinationName}"
            .replaceAll(
                "\\{" + "dataDestinationName" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(dataDestinationName)));

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>(defaultHeaders);
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    if (aiResourceGroup != null)
      localVarHeaderParams.put("AI-Resource-Group", ApiClient.parameterToString(aiResourceGroup));

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};

    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<GetDataDestination> localVarReturnType =
        new TypeReference<GetDataDestination>() {};

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
   * Get Data Destinations
   *
   * <p>Get all data destinations in the tenant (resource group)
   *
   * <p><b>200</b> - Returns all data destinations for the tenant
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * <p><b>500</b> - Server error
   *
   * @param aiResourceGroup (required) Resource Group ID
   * @param $top (optional) Number of results to display
   * @param $skip (optional) Number of results to be skipped from the ordered list of results
   * @param $count (optional) When the $count field is set to false, the response contains a count
   *     of the items present in the response. When the $count field is set to true, the response
   *     contains a count of all the items present on the server, and not just the ones in the
   *     response. When the $count field is not passed, it is false by default.
   * @return GetDataDestinations
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public GetDataDestinations getAllDataDestinations(
      @Nonnull final String aiResourceGroup,
      @Nullable final Integer $top,
      @Nullable final Integer $skip,
      @Nullable final Boolean $count)
      throws OpenApiRequestException {

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'aiResourceGroup' when calling getAllDataDestinations")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath = "/dataDestinations";

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>(defaultHeaders);
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(ApiClient.parameterToPair("$top", $top));
    localVarQueryParams.addAll(ApiClient.parameterToPair("$skip", $skip));
    localVarQueryParams.addAll(ApiClient.parameterToPair("$count", $count));
    if (aiResourceGroup != null)
      localVarHeaderParams.put("AI-Resource-Group", ApiClient.parameterToString(aiResourceGroup));

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};

    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<GetDataDestinations> localVarReturnType =
        new TypeReference<GetDataDestinations>() {};

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
   * Get Data Destinations
   *
   * <p>Get all data destinations in the tenant (resource group)
   *
   * <p><b>200</b> - Returns all data destinations for the tenant
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * <p><b>500</b> - Server error
   *
   * @param aiResourceGroup Resource Group ID
   * @return GetDataDestinations
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public GetDataDestinations getAllDataDestinations(@Nonnull final String aiResourceGroup)
      throws OpenApiRequestException {
    return getAllDataDestinations(aiResourceGroup, null, null, null);
  }

  /**
   * Update Data Destination
   *
   * <p>Update a data destination (excluding name and type)
   *
   * <p><b>204</b> - No Content
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * <p><b>409</b> - The resource conflicts with existing resources or has dependencies
   *
   * <p><b>500</b> - Server error
   *
   * @param aiResourceGroup Resource Group ID
   * @param dataDestinationName The ID of the data destination to update
   * @param patchDataDestination The value for the parameter patchDataDestination
   * @return An OpenApiResponse containing the status code of the HttpResponse.
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public OpenApiResponse patchDataDestinationByName(
      @Nonnull final String aiResourceGroup,
      @Nonnull final String dataDestinationName,
      @Nonnull final PatchDataDestination patchDataDestination)
      throws OpenApiRequestException {

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'aiResourceGroup' when calling patchDataDestinationByName")
          .statusCode(400);
    }

    // verify the required parameter 'dataDestinationName' is set
    if (dataDestinationName == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'dataDestinationName' when calling patchDataDestinationByName")
          .statusCode(400);
    }

    // verify the required parameter 'patchDataDestination' is set
    if (patchDataDestination == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'patchDataDestination' when calling patchDataDestinationByName")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/dataDestinations/{dataDestinationName}"
            .replaceAll(
                "\\{" + "dataDestinationName" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(dataDestinationName)));

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>(defaultHeaders);
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    if (aiResourceGroup != null)
      localVarHeaderParams.put("AI-Resource-Group", ApiClient.parameterToString(aiResourceGroup));

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {"application/json"};
    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<OpenApiResponse> localVarReturnType =
        new TypeReference<OpenApiResponse>() {};

    return apiClient.invokeAPI(
        localVarPath,
        "PATCH",
        localVarQueryParams,
        localVarCollectionQueryParams,
        localVarQueryStringJoiner.toString(),
        patchDataDestination,
        localVarHeaderParams,
        localVarFormParams,
        localVarAccept,
        localVarContentType,
        localVarReturnType);
  }

  /**
   * Search Data Destinations
   *
   * <p>Search data destinations by label key-value pairs
   *
   * <p><b>200</b> - Returns data destinations matching the label selector
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * <p><b>500</b> - Server error
   *
   * @param aiResourceGroup (required) Resource Group ID
   * @param controllersDataDestinationV1EndpointsSearchDataDestinationsRequest (required) The value
   *     for the parameter controllersDataDestinationV1EndpointsSearchDataDestinationsRequest
   * @param $top (optional) Number of results to display
   * @param $skip (optional) Number of results to be skipped from the ordered list of results
   * @param $count (optional) When the $count field is set to false, the response contains a count
   *     of the items present in the response. When the $count field is set to true, the response
   *     contains a count of all the items present on the server, and not just the ones in the
   *     response. When the $count field is not passed, it is false by default.
   * @return GetDataDestinations
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public GetDataDestinations searchDestinations(
      @Nonnull final String aiResourceGroup,
      @Nonnull
          final ControllersDataDestinationV1EndpointsSearchDataDestinationsRequest
              controllersDataDestinationV1EndpointsSearchDataDestinationsRequest,
      @Nullable final Integer $top,
      @Nullable final Integer $skip,
      @Nullable final Boolean $count)
      throws OpenApiRequestException {

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'aiResourceGroup' when calling searchDestinations")
          .statusCode(400);
    }

    // verify the required parameter
    // 'controllersDataDestinationV1EndpointsSearchDataDestinationsRequest' is set
    if (controllersDataDestinationV1EndpointsSearchDataDestinationsRequest == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'controllersDataDestinationV1EndpointsSearchDataDestinationsRequest' when calling searchDataDestinations")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath = "/dataDestinations/search";

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>(defaultHeaders);
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(ApiClient.parameterToPair("$top", $top));
    localVarQueryParams.addAll(ApiClient.parameterToPair("$skip", $skip));
    localVarQueryParams.addAll(ApiClient.parameterToPair("$count", $count));
    if (aiResourceGroup != null)
      localVarHeaderParams.put("AI-Resource-Group", ApiClient.parameterToString(aiResourceGroup));

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {"application/json"};
    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<GetDataDestinations> localVarReturnType =
        new TypeReference<GetDataDestinations>() {};

    return apiClient.invokeAPI(
        localVarPath,
        "POST",
        localVarQueryParams,
        localVarCollectionQueryParams,
        localVarQueryStringJoiner.toString(),
        controllersDataDestinationV1EndpointsSearchDataDestinationsRequest,
        localVarHeaderParams,
        localVarFormParams,
        localVarAccept,
        localVarContentType,
        localVarReturnType);
  }

  /**
   * Search Data Destinations
   *
   * <p>Search data destinations by label key-value pairs
   *
   * <p><b>200</b> - Returns data destinations matching the label selector
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * <p><b>500</b> - Server error
   *
   * @param aiResourceGroup Resource Group ID
   * @param controllersDataDestinationV1EndpointsSearchDataDestinationsRequest The value for the
   *     parameter controllersDataDestinationV1EndpointsSearchDataDestinationsRequest
   * @return GetDataDestinations
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public GetDataDestinations searchDestinations(
      @Nonnull final String aiResourceGroup,
      @Nonnull
          final ControllersDataDestinationV1EndpointsSearchDataDestinationsRequest
              controllersDataDestinationV1EndpointsSearchDataDestinationsRequest)
      throws OpenApiRequestException {
    return searchDestinations(
        aiResourceGroup,
        controllersDataDestinationV1EndpointsSearchDataDestinationsRequest,
        null,
        null,
        null);
  }

  /**
   * Validate Data Destination (pre-creation)
   *
   * <p>Test provider connectivity before saving. Nothing is persisted.
   *
   * <p><b>200</b> - Validation result
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * <p><b>500</b> - Server error
   *
   * @param aiResourceGroup Resource Group ID
   * @param validateDataDestinationRequest The value for the parameter
   *     validateDataDestinationRequest
   * @return ValidateDataDestinationResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public ValidateDataDestinationResponse validateDataDestination(
      @Nonnull final String aiResourceGroup,
      @Nonnull final ValidateDataDestinationRequest validateDataDestinationRequest)
      throws OpenApiRequestException {

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'aiResourceGroup' when calling validateDataDestination")
          .statusCode(400);
    }

    // verify the required parameter 'validateDataDestinationRequest' is set
    if (validateDataDestinationRequest == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'validateDataDestinationRequest' when calling validateDataDestination")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath = "/dataDestinations/validate";

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>(defaultHeaders);
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    if (aiResourceGroup != null)
      localVarHeaderParams.put("AI-Resource-Group", ApiClient.parameterToString(aiResourceGroup));

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {"application/json"};
    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<ValidateDataDestinationResponse> localVarReturnType =
        new TypeReference<ValidateDataDestinationResponse>() {};

    return apiClient.invokeAPI(
        localVarPath,
        "POST",
        localVarQueryParams,
        localVarCollectionQueryParams,
        localVarQueryStringJoiner.toString(),
        validateDataDestinationRequest,
        localVarHeaderParams,
        localVarFormParams,
        localVarAccept,
        localVarContentType,
        localVarReturnType);
  }

  /**
   * Validate Data Destination (post-creation)
   *
   * <p>Re-verify an already-saved destination using stored credentials.
   *
   * <p><b>200</b> - Validation result
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * <p><b>500</b> - Server error
   *
   * @param aiResourceGroup Resource Group ID
   * @param dataDestinationName The name of the data destination to validate
   * @return ValidateDataDestinationResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public ValidateDataDestinationResponse validateDataDestinationByName(
      @Nonnull final String aiResourceGroup, @Nonnull final String dataDestinationName)
      throws OpenApiRequestException {

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'aiResourceGroup' when calling validateDataDestinationByName")
          .statusCode(400);
    }

    // verify the required parameter 'dataDestinationName' is set
    if (dataDestinationName == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'dataDestinationName' when calling validateDataDestinationByName")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/dataDestinations/{dataDestinationName}/validate"
            .replaceAll(
                "\\{" + "dataDestinationName" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(dataDestinationName)));

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>(defaultHeaders);
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    if (aiResourceGroup != null)
      localVarHeaderParams.put("AI-Resource-Group", ApiClient.parameterToString(aiResourceGroup));

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};

    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<ValidateDataDestinationResponse> localVarReturnType =
        new TypeReference<ValidateDataDestinationResponse>() {};

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
}
