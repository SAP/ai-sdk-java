package com.sap.ai.sdk.tabular.generated.orchestration.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.sap.ai.sdk.tabular.generated.orchestration.model.CreateScenarioConfiguration;
import com.sap.ai.sdk.tabular.generated.orchestration.model.GetScenarioConfigurations;
import com.sap.ai.sdk.tabular.generated.orchestration.model.PatchScenarioConfiguration;
import com.sap.ai.sdk.tabular.generated.orchestration.model.ScenarioConfigurationNameObject;
import com.sap.ai.sdk.tabular.generated.orchestration.model.ScenarioConfigurationObject;
import com.sap.ai.sdk.tabular.generated.orchestration.model.SearchScenarioConfiguration;
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
public class ScenarioConfigurationManagerApi extends BaseApi {

  /**
   * Instantiates this API class to invoke operations on the Context Registry.
   *
   * @param httpDestination The destination that API should be used with
   */
  public ScenarioConfigurationManagerApi(@Nonnull final Destination httpDestination) {
    super(httpDestination);
  }

  /**
   * Instantiates this API class to invoke operations on the Context Registry based on a given
   * {@link ApiClient}.
   *
   * @param apiClient ApiClient to invoke the API on
   */
  public ScenarioConfigurationManagerApi(@Nonnull final ApiClient apiClient) {
    super(apiClient);
  }

  /**
   * Creates a new API instance with additional default headers.
   *
   * @param defaultHeaders Additional headers to include in all requests
   * @return A new API instance with the combined headers
   */
  public ScenarioConfigurationManagerApi withDefaultHeaders(
      @Nonnull final Map<String, String> defaultHeaders) {
    final var api = new ScenarioConfigurationManagerApi(apiClient);
    api.defaultHeaders.putAll(this.defaultHeaders);
    api.defaultHeaders.putAll(defaultHeaders);
    return api;
  }

  /**
   * Async Scenario Configuration Creation
   *
   * <p>Create a scenario configuration asynchronously. Schema validation happens synchronously;
   * background processing runs asynchronously. Poll GET /scenarioConfigurations/{name} to track
   * progress via status and errorMessage. Returns 202 (not exists or ERROR retry), 409 (ACTIVE or
   * DELETING), 422 (retry exhausted).
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
   * @param scenarioConfigurationName Name of the scenario configuration
   * @param createScenarioConfiguration The value for the parameter createScenarioConfiguration
   * @return ScenarioConfigurationNameObject
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public ScenarioConfigurationNameObject createScenarioConfiguration(
      @Nonnull final String aiResourceGroup,
      @Nonnull final String scenarioConfigurationName,
      @Nonnull final CreateScenarioConfiguration createScenarioConfiguration)
      throws OpenApiRequestException {

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'aiResourceGroup' when calling createScenarioConfiguration")
          .statusCode(400);
    }

    // verify the required parameter 'scenarioConfigurationName' is set
    if (scenarioConfigurationName == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'scenarioConfigurationName' when calling createScenarioConfiguration")
          .statusCode(400);
    }

    // verify the required parameter 'createScenarioConfiguration' is set
    if (createScenarioConfiguration == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'createScenarioConfiguration' when calling createScenarioConfiguration")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/scenarioConfigurations/{scenarioConfigurationName}"
            .replaceAll(
                "\\{" + "scenarioConfigurationName" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(scenarioConfigurationName)));

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

    final TypeReference<ScenarioConfigurationNameObject> localVarReturnType =
        new TypeReference<ScenarioConfigurationNameObject>() {};

    return apiClient.invokeAPI(
        localVarPath,
        "PUT",
        localVarQueryParams,
        localVarCollectionQueryParams,
        localVarQueryStringJoiner.toString(),
        createScenarioConfiguration,
        localVarHeaderParams,
        localVarFormParams,
        localVarAccept,
        localVarContentType,
        localVarReturnType);
  }

  /**
   * Delete Scenario Configuration
   *
   * <p>Delete a scenario configuration by name
   *
   * <p><b>204</b> - No Content
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * <p><b>500</b> - Server error
   *
   * @param aiResourceGroup Resource Group ID
   * @param scenarioConfigurationName The name of the scenario configuration to delete
   * @return An OpenApiResponse containing the status code of the HttpResponse.
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public OpenApiResponse deleteScenarioConfigurationByName(
      @Nonnull final String aiResourceGroup, @Nonnull final String scenarioConfigurationName)
      throws OpenApiRequestException {

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'aiResourceGroup' when calling deleteScenarioConfigurationByName")
          .statusCode(400);
    }

    // verify the required parameter 'scenarioConfigurationName' is set
    if (scenarioConfigurationName == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'scenarioConfigurationName' when calling deleteScenarioConfigurationByName")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/scenarioConfigurations/{scenarioConfigurationName}"
            .replaceAll(
                "\\{" + "scenarioConfigurationName" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(scenarioConfigurationName)));

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
   * Get All Scenario Configurations
   *
   * <p>Get all scenario configurations in the tenant (resource group)
   *
   * <p><b>200</b> - Returns all scenario configurations for the tenant
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
   * @return GetScenarioConfigurations
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public GetScenarioConfigurations getAllScenarioConfigurations(
      @Nonnull final String aiResourceGroup,
      @Nullable final Integer $top,
      @Nullable final Integer $skip,
      @Nullable final Boolean $count)
      throws OpenApiRequestException {

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'aiResourceGroup' when calling getAllScenarioConfigurations")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath = "/scenarioConfigurations";

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

    final TypeReference<GetScenarioConfigurations> localVarReturnType =
        new TypeReference<GetScenarioConfigurations>() {};

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
   * Get All Scenario Configurations
   *
   * <p>Get all scenario configurations in the tenant (resource group)
   *
   * <p><b>200</b> - Returns all scenario configurations for the tenant
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * <p><b>500</b> - Server error
   *
   * @param aiResourceGroup Resource Group ID
   * @return GetScenarioConfigurations
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public GetScenarioConfigurations getAllScenarioConfigurations(
      @Nonnull final String aiResourceGroup) throws OpenApiRequestException {
    return getAllScenarioConfigurations(aiResourceGroup, null, null, null);
  }

  /**
   * Get Scenario Configuration By Name
   *
   * <p>Get a scenario configuration by its name
   *
   * <p><b>200</b> - Returns the scenario configuration object
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * <p><b>500</b> - Server error
   *
   * @param aiResourceGroup Resource Group ID
   * @param scenarioConfigurationName The name of the scenario configuration to retrieve
   * @return ScenarioConfigurationObject
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public ScenarioConfigurationObject getScenarioConfigurationByName(
      @Nonnull final String aiResourceGroup, @Nonnull final String scenarioConfigurationName)
      throws OpenApiRequestException {

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'aiResourceGroup' when calling getScenarioConfigurationByName")
          .statusCode(400);
    }

    // verify the required parameter 'scenarioConfigurationName' is set
    if (scenarioConfigurationName == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'scenarioConfigurationName' when calling getScenarioConfigurationByName")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/scenarioConfigurations/{scenarioConfigurationName}"
            .replaceAll(
                "\\{" + "scenarioConfigurationName" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(scenarioConfigurationName)));

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

    final TypeReference<ScenarioConfigurationObject> localVarReturnType =
        new TypeReference<ScenarioConfigurationObject>() {};

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
   * Update Scenario Configuration
   *
   * <p>Update a scenario configuration (excluding name)
   *
   * <p><b>204</b> - No Content
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * <p><b>500</b> - Server error
   *
   * @param aiResourceGroup Resource Group ID
   * @param scenarioConfigurationName The name of the scenario configuration to update
   * @param patchScenarioConfiguration The value for the parameter patchScenarioConfiguration
   * @return An OpenApiResponse containing the status code of the HttpResponse.
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public OpenApiResponse patchScenarioConfigurationByName(
      @Nonnull final String aiResourceGroup,
      @Nonnull final String scenarioConfigurationName,
      @Nonnull final PatchScenarioConfiguration patchScenarioConfiguration)
      throws OpenApiRequestException {

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'aiResourceGroup' when calling patchScenarioConfigurationByName")
          .statusCode(400);
    }

    // verify the required parameter 'scenarioConfigurationName' is set
    if (scenarioConfigurationName == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'scenarioConfigurationName' when calling patchScenarioConfigurationByName")
          .statusCode(400);
    }

    // verify the required parameter 'patchScenarioConfiguration' is set
    if (patchScenarioConfiguration == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'patchScenarioConfiguration' when calling patchScenarioConfigurationByName")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/scenarioConfigurations/{scenarioConfigurationName}"
            .replaceAll(
                "\\{" + "scenarioConfigurationName" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(scenarioConfigurationName)));

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
        patchScenarioConfiguration,
        localVarHeaderParams,
        localVarFormParams,
        localVarAccept,
        localVarContentType,
        localVarReturnType);
  }

  /**
   * Search Scenario Configurations by Labels
   *
   * <p>Search for scenario configurations that match ALL specified labels (AND logic)
   *
   * <p><b>200</b> - Returns scenario configurations matching all specified labels
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * <p><b>500</b> - Server error
   *
   * @param aiResourceGroup (required) Resource Group ID
   * @param searchScenarioConfiguration (required) The value for the parameter
   *     searchScenarioConfiguration
   * @param $top (optional) Number of results to display
   * @param $skip (optional) Number of results to be skipped from the ordered list of results
   * @param $count (optional) When the $count field is set to false, the response contains a count
   *     of the items present in the response. When the $count field is set to true, the response
   *     contains a count of all the items present on the server, and not just the ones in the
   *     response. When the $count field is not passed, it is false by default.
   * @return GetScenarioConfigurations
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public GetScenarioConfigurations searchScenarioConfigurationsByLabel(
      @Nonnull final String aiResourceGroup,
      @Nonnull final SearchScenarioConfiguration searchScenarioConfiguration,
      @Nullable final Integer $top,
      @Nullable final Integer $skip,
      @Nullable final Boolean $count)
      throws OpenApiRequestException {

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'aiResourceGroup' when calling searchScenarioConfigurationsByLabel")
          .statusCode(400);
    }

    // verify the required parameter 'searchScenarioConfiguration' is set
    if (searchScenarioConfiguration == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'searchScenarioConfiguration' when calling searchScenarioConfigurationsByLabel")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath = "/scenarioConfigurations/search";

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

    final TypeReference<GetScenarioConfigurations> localVarReturnType =
        new TypeReference<GetScenarioConfigurations>() {};

    return apiClient.invokeAPI(
        localVarPath,
        "POST",
        localVarQueryParams,
        localVarCollectionQueryParams,
        localVarQueryStringJoiner.toString(),
        searchScenarioConfiguration,
        localVarHeaderParams,
        localVarFormParams,
        localVarAccept,
        localVarContentType,
        localVarReturnType);
  }

  /**
   * Search Scenario Configurations by Labels
   *
   * <p>Search for scenario configurations that match ALL specified labels (AND logic)
   *
   * <p><b>200</b> - Returns scenario configurations matching all specified labels
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * <p><b>500</b> - Server error
   *
   * @param aiResourceGroup Resource Group ID
   * @param searchScenarioConfiguration The value for the parameter searchScenarioConfiguration
   * @return GetScenarioConfigurations
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public GetScenarioConfigurations searchScenarioConfigurationsByLabel(
      @Nonnull final String aiResourceGroup,
      @Nonnull final SearchScenarioConfiguration searchScenarioConfiguration)
      throws OpenApiRequestException {
    return searchScenarioConfigurationsByLabel(
        aiResourceGroup, searchScenarioConfiguration, null, null, null);
  }
}
