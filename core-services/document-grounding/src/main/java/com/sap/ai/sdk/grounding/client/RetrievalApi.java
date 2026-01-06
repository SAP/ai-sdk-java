package com.sap.ai.sdk.grounding.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.sap.ai.sdk.grounding.model.DataRepositories;
import com.sap.ai.sdk.grounding.model.DataRepository;
import com.sap.ai.sdk.grounding.model.RetrievalSearchInput;
import com.sap.ai.sdk.grounding.model.RetrievalSearchResults;
import com.sap.cloud.sdk.cloudplatform.connectivity.Destination;
import com.sap.cloud.sdk.services.openapi.apache.ApiClient;
import com.sap.cloud.sdk.services.openapi.apache.BaseApi;
import com.sap.cloud.sdk.services.openapi.apache.Pair;
import com.sap.cloud.sdk.services.openapi.core.OpenApiRequestException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.UUID;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Grounding in version 0.1.0.
 *
 * <p>Grounding is a service designed to handle data-related tasks, such as grounding and retrieval,
 * using vector databases. It provides specialized data retrieval through these databases, grounding
 * the retrieval process with your own external and context-relevant data. Grounding combines
 * generative AI capabilities with the ability to use real-time, precise data to improve
 * decision-making and business operations for specific AI-driven business solutions.
 */
public class RetrievalApi extends BaseApi {

  /** Instantiates this API class to invoke operations on the Grounding */
  public RetrievalApi() {}

  /**
   * Instantiates this API class to invoke operations on the Grounding.
   *
   * @param httpDestination The destination that API should be used with
   */
  public RetrievalApi(@Nonnull final Destination httpDestination) {
    super(httpDestination);
  }

  /**
   * Instantiates this API class to invoke operations on the Grounding based on a given {@link
   * ApiClient}.
   *
   * @param apiClient ApiClient to invoke the API on
   */
  public RetrievalApi(@Nonnull final ApiClient apiClient) {
    super(apiClient);
  }

  /**
   * List all DataRepository objects.
   *
   * <p>List all Data Repositories
   *
   * <p><b>200</b> - Successful Response
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
   * @return DataRepositories
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public DataRepositories getDataRepositories(
      @Nonnull final String aiResourceGroup,
      @Nullable final Integer $top,
      @Nullable final Integer $skip,
      @Nullable final Boolean $count)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'aiResourceGroup' when calling getDataRepositories")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath = "/retrieval/dataRepositories";

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    String localVarQueryParameterBaseName;
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>();
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

    final TypeReference<DataRepositories> localVarReturnType =
        new TypeReference<DataRepositories>() {};
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
   * List all DataRepository objects.
   *
   * <p>List all Data Repositories
   *
   * <p><b>200</b> - Successful Response
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * @param aiResourceGroup Resource Group ID
   * @return DataRepositories
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public DataRepositories getDataRepositories(@Nonnull final String aiResourceGroup)
      throws OpenApiRequestException {
    return getDataRepositories(aiResourceGroup, null, null, null);
  }

  /**
   * List single DataRepository object.
   *
   * <p>List data repository by id
   *
   * <p><b>200</b> - Successful Response
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * <p><b>422</b> - There are validation issues with the data.
   *
   * <p><b>404</b> - The specification of the resource was incorrect
   *
   * @param aiResourceGroup Resource Group ID
   * @param repositoryId Repository ID
   * @return DataRepository
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public DataRepository getDataRepositoryById(
      @Nonnull final String aiResourceGroup, @Nonnull final UUID repositoryId)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'aiResourceGroup' when calling getDataRepositoryById")
          .statusCode(400);
    }

    // verify the required parameter 'repositoryId' is set
    if (repositoryId == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'repositoryId' when calling getDataRepositoryById")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/retrieval/dataRepositories/{repositoryId}"
            .replaceAll(
                "\\{" + "repositoryId" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(repositoryId)));

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

    final String[] localVarContentTypes = {};

    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<DataRepository> localVarReturnType = new TypeReference<DataRepository>() {};
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
   * Retrieve relevant content
   *
   * <p>Retrieve relevant content given a query string.
   *
   * <p><b>200</b> - Successful Response
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * <p><b>422</b> - There are validation issues with the data.
   *
   * @param aiResourceGroup Resource Group ID
   * @param retrievalSearchInput The value for the parameter retrievalSearchInput
   * @return RetrievalSearchResults
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public RetrievalSearchResults search(
      @Nonnull final String aiResourceGroup,
      @Nonnull final RetrievalSearchInput retrievalSearchInput)
      throws OpenApiRequestException {
    final Object localVarPostBody = retrievalSearchInput;

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'aiResourceGroup' when calling search")
          .statusCode(400);
    }

    // verify the required parameter 'retrievalSearchInput' is set
    if (retrievalSearchInput == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'retrievalSearchInput' when calling retrievalV1RetrievalEndpointsSearch")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath = "/retrieval/search";

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

    final TypeReference<RetrievalSearchResults> localVarReturnType =
        new TypeReference<RetrievalSearchResults>() {};
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
}
