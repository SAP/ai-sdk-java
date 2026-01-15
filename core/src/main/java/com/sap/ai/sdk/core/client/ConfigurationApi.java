package com.sap.ai.sdk.core.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.core.model.AiConfiguration;
import com.sap.ai.sdk.core.model.AiConfigurationBaseData;
import com.sap.ai.sdk.core.model.AiConfigurationCreationResponse;
import com.sap.ai.sdk.core.model.AiConfigurationList;
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
public class ConfigurationApi extends BaseApi {

  /** Instantiates this API class to invoke operations on the AI Core */
  public ConfigurationApi() {
    super(new AiCoreService().getApiClient());
  }

  /**
   * Instantiates this API class to invoke operations on the AI Core
   *
   * @param aiCoreService The configured connectivity instance to AI Core
   */
  public ConfigurationApi(@Nonnull final AiCoreService aiCoreService) {
    super(aiCoreService.getApiClient());
  }

  /**
   * Get number of configurations
   *
   * <p>Retrieve the number of available configurations that match the specified filter criteria.
   * Filter criteria include a scenarioId or executableIdsList. Search by substring of configuration
   * name is also possible.
   *
   * <p><b>200</b> - Number of configurations
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * @param aiResourceGroup (required) Specify a resource group id
   * @param scenarioId (optional) Scenario identifier
   * @param $search (optional) Generic search term to be looked for in various attributes
   * @param searchCaseInsensitive (optional, default to false) indicates whether the search should
   *     be case insensitive
   * @param executableIds (optional) Limit query to only these executable IDs
   * @return Integer
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public Integer count(
      @Nonnull final String aiResourceGroup,
      @Nullable final String scenarioId,
      @Nullable final String $search,
      @Nullable final Boolean searchCaseInsensitive,
      @Nullable final List<String> executableIds)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'aiResourceGroup' when calling count")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath = "/lm/configurations/$count";

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    String localVarQueryParameterBaseName;
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(ApiClient.parameterToPair("scenarioId", scenarioId));
    localVarQueryParams.addAll(ApiClient.parameterToPair("$search", $search));
    localVarQueryParams.addAll(
        ApiClient.parameterToPair("searchCaseInsensitive", searchCaseInsensitive));
    localVarCollectionQueryParams.addAll(
        ApiClient.parameterToPairs("csv", "executableIds", executableIds));
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
        localVarPostBody,
        localVarHeaderParams,
        localVarFormParams,
        localVarAccept,
        localVarContentType,
        localVarReturnType);
  }

  /**
   * Get number of configurations
   *
   * <p>Retrieve the number of available configurations that match the specified filter criteria.
   * Filter criteria include a scenarioId or executableIdsList. Search by substring of configuration
   * name is also possible.
   *
   * <p><b>200</b> - Number of configurations
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * @param aiResourceGroup Specify a resource group id
   * @return Integer
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public Integer count(@Nonnull final String aiResourceGroup) throws OpenApiRequestException {
    return count(aiResourceGroup, null, null, null, null);
  }

  /**
   * Create configuration
   *
   * <p>Create a new configuration linked to a specific scenario and executable for use in an
   * execution or deployment.
   *
   * <p><b>201</b> - The created configuration
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * @param aiResourceGroup Specify a resource group id
   * @param aiConfigurationBaseData The value for the parameter aiConfigurationBaseData
   * @return AiConfigurationCreationResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public AiConfigurationCreationResponse create(
      @Nonnull final String aiResourceGroup,
      @Nonnull final AiConfigurationBaseData aiConfigurationBaseData)
      throws OpenApiRequestException {
    final Object localVarPostBody = aiConfigurationBaseData;

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'aiResourceGroup' when calling create")
          .statusCode(400);
    }

    // verify the required parameter 'aiConfigurationBaseData' is set
    if (aiConfigurationBaseData == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'aiConfigurationBaseData' when calling create")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath = "/lm/configurations";

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    String localVarQueryParameterBaseName;
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

    final TypeReference<AiConfigurationCreationResponse> localVarReturnType =
        new TypeReference<AiConfigurationCreationResponse>() {};

    return apiClient.invokeAPI(
        localVarPath,
        "POST",
        localVarQueryParams,
        localVarCollectionQueryParams,
        localVarQueryStringJoiner.toString(),
        localVarPostBody,
        localVarHeaderParams,
        localVarFormParams,
        localVarAccept,
        localVarContentType,
        localVarReturnType);
  }

  /**
   * Get configuration by ID
   *
   * <p>Retrieve details for configuration with configurationId.
   *
   * <p><b>200</b> - A configuration
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * @param aiResourceGroup (required) Specify a resource group id
   * @param configurationId (required) Configuration identifier
   * @param $expand (optional) expand detailed information on scenario
   * @return AiConfiguration
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public AiConfiguration get(
      @Nonnull final String aiResourceGroup,
      @Nonnull final String configurationId,
      @Nullable final String $expand)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'aiResourceGroup' when calling get")
          .statusCode(400);
    }

    // verify the required parameter 'configurationId' is set
    if (configurationId == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'configurationId' when calling get")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/lm/configurations/{configurationId}"
            .replaceAll(
                "\\{" + "configurationId" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(configurationId)));

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    String localVarQueryParameterBaseName;
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(ApiClient.parameterToPair("$expand", $expand));
    if (aiResourceGroup != null)
      localVarHeaderParams.put("AI-Resource-Group", ApiClient.parameterToString(aiResourceGroup));

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};

    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<AiConfiguration> localVarReturnType =
        new TypeReference<AiConfiguration>() {};

    return apiClient.invokeAPI(
        localVarPath,
        "GET",
        localVarQueryParams,
        localVarCollectionQueryParams,
        localVarQueryStringJoiner.toString(),
        localVarPostBody,
        localVarHeaderParams,
        localVarFormParams,
        localVarAccept,
        localVarContentType,
        localVarReturnType);
  }

  /**
   * Get configuration by ID
   *
   * <p>Retrieve details for configuration with configurationId.
   *
   * <p><b>200</b> - A configuration
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * @param aiResourceGroup Specify a resource group id
   * @param configurationId Configuration identifier
   * @return AiConfiguration
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public AiConfiguration get(
      @Nonnull final String aiResourceGroup, @Nonnull final String configurationId)
      throws OpenApiRequestException {
    return get(aiResourceGroup, configurationId, null);
  }

  /**
   * Get list of configurations
   *
   * <p>Retrieve a list of configurations. Filter results by scenario ID or a list of executable
   * IDs. Search for configurations containing the search string as substring in the configuration
   * name.
   *
   * <p><b>200</b> - A list of configurations
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * @param aiResourceGroup (required) Specify a resource group id
   * @param scenarioId (optional) Scenario identifier
   * @param $top (optional, default to 10000) Number of results to display
   * @param $skip (optional) Number of results to be skipped from the ordered list of results
   * @param executableIds (optional) Limit query to only these executable IDs
   * @param $search (optional) Generic search term to be looked for in various attributes
   * @param searchCaseInsensitive (optional, default to false) indicates whether the search should
   *     be case insensitive
   * @param $expand (optional) expand detailed information on scenario
   * @return AiConfigurationList
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public AiConfigurationList query(
      @Nonnull final String aiResourceGroup,
      @Nullable final String scenarioId,
      @Nullable final Integer $top,
      @Nullable final Integer $skip,
      @Nullable final List<String> executableIds,
      @Nullable final String $search,
      @Nullable final Boolean searchCaseInsensitive,
      @Nullable final String $expand)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'aiResourceGroup' when calling query")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath = "/lm/configurations";

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    String localVarQueryParameterBaseName;
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(ApiClient.parameterToPair("scenarioId", scenarioId));
    localVarQueryParams.addAll(ApiClient.parameterToPair("$top", $top));
    localVarQueryParams.addAll(ApiClient.parameterToPair("$skip", $skip));
    localVarCollectionQueryParams.addAll(
        ApiClient.parameterToPairs("csv", "executableIds", executableIds));
    localVarQueryParams.addAll(ApiClient.parameterToPair("$search", $search));
    localVarQueryParams.addAll(
        ApiClient.parameterToPair("searchCaseInsensitive", searchCaseInsensitive));
    localVarQueryParams.addAll(ApiClient.parameterToPair("$expand", $expand));
    if (aiResourceGroup != null)
      localVarHeaderParams.put("AI-Resource-Group", ApiClient.parameterToString(aiResourceGroup));

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};

    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<AiConfigurationList> localVarReturnType =
        new TypeReference<AiConfigurationList>() {};

    return apiClient.invokeAPI(
        localVarPath,
        "GET",
        localVarQueryParams,
        localVarCollectionQueryParams,
        localVarQueryStringJoiner.toString(),
        localVarPostBody,
        localVarHeaderParams,
        localVarFormParams,
        localVarAccept,
        localVarContentType,
        localVarReturnType);
  }

  /**
   * Get list of configurations
   *
   * <p>Retrieve a list of configurations. Filter results by scenario ID or a list of executable
   * IDs. Search for configurations containing the search string as substring in the configuration
   * name.
   *
   * <p><b>200</b> - A list of configurations
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * @param aiResourceGroup Specify a resource group id
   * @return AiConfigurationList
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public AiConfigurationList query(@Nonnull final String aiResourceGroup)
      throws OpenApiRequestException {
    return query(aiResourceGroup, null, null, null, null, null, null, null);
  }
}
