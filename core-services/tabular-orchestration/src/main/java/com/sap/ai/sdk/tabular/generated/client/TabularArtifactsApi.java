package com.sap.ai.sdk.tabular.generated.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.sap.ai.sdk.tabular.generated.model.ControllersTabularArtifactV1EndpointsCreateTabularArtifact202Response;
import com.sap.ai.sdk.tabular.generated.model.CreateTARequest;
import com.sap.ai.sdk.tabular.generated.model.TabularArtifactDataPreview;
import com.sap.ai.sdk.tabular.generated.model.TabularArtifactDetails;
import com.sap.ai.sdk.tabular.generated.model.TabularArtifactListResponse;
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
public class TabularArtifactsApi extends BaseApi {

  /**
   * Instantiates this API class to invoke operations on the Context Registry.
   *
   * @param httpDestination The destination that API should be used with
   */
  public TabularArtifactsApi(@Nonnull final Destination httpDestination) {
    super(httpDestination);
  }

  /**
   * Instantiates this API class to invoke operations on the Context Registry based on a given
   * {@link ApiClient}.
   *
   * @param apiClient ApiClient to invoke the API on
   */
  public TabularArtifactsApi(@Nonnull final ApiClient apiClient) {
    super(apiClient);
  }

  /**
   * Creates a new API instance with additional default headers.
   *
   * @param defaultHeaders Additional headers to include in all requests
   * @return A new API instance with the combined headers
   */
  public TabularArtifactsApi withDefaultHeaders(@Nonnull final Map<String, String> defaultHeaders) {
    final var api = new TabularArtifactsApi(apiClient);
    api.defaultHeaders.putAll(this.defaultHeaders);
    api.defaultHeaders.putAll(defaultHeaders);
    return api;
  }

  /**
   * Async Tabular Artifact Creation
   *
   * <p>Create a Tabular Artifact asynchronously. Schema validation happens synchronously; resource
   * creation runs in a background task. Poll GET /tabularArtifacts/{name} to track progress via
   * status and errorMessage. Returns 202 (not exists or ERROR retry), 409 (ACTIVE or DELETING), 422
   * (retry exhausted).
   *
   * <p><b>202</b> - Accepted - Tabular Artifact creation request accepted
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
   * @param tabularArtifactName Unique name of the Tabular Artifact
   * @param createTARequest The value for the parameter createTARequest
   * @return ControllersTabularArtifactV1EndpointsCreateTabularArtifact202Response
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public ControllersTabularArtifactV1EndpointsCreateTabularArtifact202Response
      createTabularArtifact(
          @Nonnull final String aiResourceGroup,
          @Nonnull final String tabularArtifactName,
          @Nonnull final CreateTARequest createTARequest)
          throws OpenApiRequestException {

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'aiResourceGroup' when calling createTabularArtifact")
          .statusCode(400);
    }

    // verify the required parameter 'tabularArtifactName' is set
    if (tabularArtifactName == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'tabularArtifactName' when calling createTabularArtifact")
          .statusCode(400);
    }

    // verify the required parameter 'createTARequest' is set
    if (createTARequest == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'createTARequest' when calling createTabularArtifact")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/tabularArtifacts/{tabularArtifactName}"
            .replaceAll(
                "\\{" + "tabularArtifactName" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(tabularArtifactName)));

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

    final TypeReference<ControllersTabularArtifactV1EndpointsCreateTabularArtifact202Response>
        localVarReturnType =
            new TypeReference<
                ControllersTabularArtifactV1EndpointsCreateTabularArtifact202Response>() {};

    return apiClient.invokeAPI(
        localVarPath,
        "PUT",
        localVarQueryParams,
        localVarCollectionQueryParams,
        localVarQueryStringJoiner.toString(),
        createTARequest,
        localVarHeaderParams,
        localVarFormParams,
        localVarAccept,
        localVarContentType,
        localVarReturnType);
  }

  /**
   * Delete Tabular Artifact
   *
   * <p>Delete a specific Tabular Artifact by name
   *
   * <p><b>202</b> - Accepted — the Tabular Artifact has been marked for deletion and will be
   * removed asynchronously
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * <p><b>409</b> - The resource conflicts with existing resources or has dependencies
   *
   * <p><b>500</b> - Server error
   *
   * @param aiResourceGroup Resource Group ID
   * @param tabularArtifactName Unique name of the Tabular Artifact. Must match allowed pattern and
   *     length constraints.
   * @return An OpenApiResponse containing the status code of the HttpResponse.
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public OpenApiResponse deleteTabularArtifact(
      @Nonnull final String aiResourceGroup, @Nonnull final String tabularArtifactName)
      throws OpenApiRequestException {

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'aiResourceGroup' when calling deleteTabularArtifact")
          .statusCode(400);
    }

    // verify the required parameter 'tabularArtifactName' is set
    if (tabularArtifactName == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'tabularArtifactName' when calling deleteTabularArtifact")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/tabularArtifacts/{tabularArtifactName}"
            .replaceAll(
                "\\{" + "tabularArtifactName" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(tabularArtifactName)));

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
   * Get all Tabular Artifacts
   *
   * <p>Retrieve list of Tabular Artifacts with pagination
   *
   * <p><b>200</b> - Tabular Artifacts retrieved successfully
   *
   * <p><b>500</b> - Server error
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * @param aiResourceGroup (required) Resource Group ID
   * @param $top (optional) Number of results to display
   * @param $skip (optional) Number of results to be skipped from the ordered list of results
   * @param $count (optional) When the $count field is set to false, the response contains a count
   *     of the items present in the response. When the $count field is set to true, the response
   *     contains a count of all the items present on the server, and not just the ones in the
   *     response. When the $count field is not passed, it is false by default.
   * @return TabularArtifactListResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public TabularArtifactListResponse getAllTabularArtifacts(
      @Nonnull final String aiResourceGroup,
      @Nullable final Integer $top,
      @Nullable final Integer $skip,
      @Nullable final Boolean $count)
      throws OpenApiRequestException {

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'aiResourceGroup' when calling getAllTabularArtifacts")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath = "/tabularArtifacts";

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

    final TypeReference<TabularArtifactListResponse> localVarReturnType =
        new TypeReference<TabularArtifactListResponse>() {};

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
   * Get all Tabular Artifacts
   *
   * <p>Retrieve list of Tabular Artifacts with pagination
   *
   * <p><b>200</b> - Tabular Artifacts retrieved successfully
   *
   * <p><b>500</b> - Server error
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * @param aiResourceGroup Resource Group ID
   * @return TabularArtifactListResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public TabularArtifactListResponse getAllTabularArtifacts(@Nonnull final String aiResourceGroup)
      throws OpenApiRequestException {
    return getAllTabularArtifacts(aiResourceGroup, null, null, null);
  }

  /**
   * Get Tabular Artifact Details with Metadata
   *
   * <p>Retrieve details of a specific Tabular Artifact by name including schema metadata.
   *
   * <p><b>200</b> - Tabular Artifact details retrieved successfully
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * <p><b>500</b> - Server error
   *
   * @param aiResourceGroup Resource Group ID
   * @param tabularArtifactName Unique name of the Tabular Artifact. Must match allowed pattern and
   *     length constraints.
   * @return TabularArtifactDetails
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public TabularArtifactDetails getTabularArtifactByName(
      @Nonnull final String aiResourceGroup, @Nonnull final String tabularArtifactName)
      throws OpenApiRequestException {

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'aiResourceGroup' when calling getTabularArtifactByName")
          .statusCode(400);
    }

    // verify the required parameter 'tabularArtifactName' is set
    if (tabularArtifactName == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'tabularArtifactName' when calling getTabularArtifactByName")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/tabularArtifacts/{tabularArtifactName}"
            .replaceAll(
                "\\{" + "tabularArtifactName" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(tabularArtifactName)));

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

    final TypeReference<TabularArtifactDetails> localVarReturnType =
        new TypeReference<TabularArtifactDetails>() {};

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
   * Get Tabular Artifact Data Preview
   *
   * <p>Retrieve a preview of data rows from the Virtual Table (first 10 records)
   *
   * <p><b>200</b> - Tabular Artifact data preview retrieved successfully
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * <p><b>500</b> - Server error
   *
   * @param aiResourceGroup Resource Group ID
   * @param tabularArtifactName Unique name of the Tabular Artifact. Must match allowed pattern and
   *     length constraints.
   * @return TabularArtifactDataPreview
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public TabularArtifactDataPreview getTabularArtifactData(
      @Nonnull final String aiResourceGroup, @Nonnull final String tabularArtifactName)
      throws OpenApiRequestException {

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'aiResourceGroup' when calling getTabularArtifactData")
          .statusCode(400);
    }

    // verify the required parameter 'tabularArtifactName' is set
    if (tabularArtifactName == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'tabularArtifactName' when calling getTabularArtifactData")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/tabularArtifacts/{tabularArtifactName}/data"
            .replaceAll(
                "\\{" + "tabularArtifactName" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(tabularArtifactName)));

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

    final TypeReference<TabularArtifactDataPreview> localVarReturnType =
        new TypeReference<TabularArtifactDataPreview>() {};

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
