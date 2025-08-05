package com.sap.ai.sdk.grounding.client;

import com.google.common.annotations.Beta;
import com.sap.ai.sdk.grounding.model.DataRepositories;
import com.sap.ai.sdk.grounding.model.DataRepository;
import com.sap.ai.sdk.grounding.model.SearchInput;
import com.sap.ai.sdk.grounding.model.SearchResults;
import com.sap.cloud.sdk.cloudplatform.connectivity.Destination;
import com.sap.cloud.sdk.services.openapi.apiclient.ApiClient;
import com.sap.cloud.sdk.services.openapi.core.AbstractOpenApiService;
import com.sap.cloud.sdk.services.openapi.core.OpenApiRequestException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
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
 * Grounding in version 0.1.0.
 *
 * <p>Grounding is a service designed to handle data-related tasks, such as grounding and retrieval,
 * using vector databases. It provides specialized data retrieval through these databases, grounding
 * the retrieval process with your own external and context-relevant data. Grounding combines
 * generative AI capabilities with the ability to use real-time, precise data to improve
 * decision-making and business operations for specific AI-driven business solutions.
 */
public class RetrievalApi extends AbstractOpenApiService {
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
  @Beta
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
  public DataRepositories retrievalV1RetrievalEndpointsGetDataRepositories(
      @Nonnull final String aiResourceGroup,
      @Nullable final Integer $top,
      @Nullable final Integer $skip,
      @Nullable final Boolean $count)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'aiResourceGroup' when calling retrievalV1RetrievalEndpointsGetDataRepositories");
    }

    final String localVarPath =
        UriComponentsBuilder.fromPath("/retrieval/dataRepositories").build().toUriString();

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

    final ParameterizedTypeReference<DataRepositories> localVarReturnType =
        new ParameterizedTypeReference<DataRepositories>() {};
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
  public DataRepositories retrievalV1RetrievalEndpointsGetDataRepositories(
      @Nonnull final String aiResourceGroup) throws OpenApiRequestException {
    return retrievalV1RetrievalEndpointsGetDataRepositories(aiResourceGroup, null, null, null);
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
  public DataRepository retrievalV1RetrievalEndpointsGetDataRepository(
      @Nonnull final String aiResourceGroup, @Nonnull final UUID repositoryId)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'aiResourceGroup' when calling retrievalV1RetrievalEndpointsGetDataRepository");
    }

    // verify the required parameter 'repositoryId' is set
    if (repositoryId == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'repositoryId' when calling retrievalV1RetrievalEndpointsGetDataRepository");
    }

    // create path and map variables
    final Map<String, Object> localVarPathParams = new HashMap<String, Object>();
    localVarPathParams.put("repositoryId", repositoryId);
    final String localVarPath =
        UriComponentsBuilder.fromPath("/retrieval/dataRepositories/{repositoryId}")
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

    final ParameterizedTypeReference<DataRepository> localVarReturnType =
        new ParameterizedTypeReference<DataRepository>() {};
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
   * @param searchInput The value for the parameter searchInput
   * @return SearchResults
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public SearchResults search(
      @Nonnull final String aiResourceGroup, @Nonnull final SearchInput searchInput)
      throws OpenApiRequestException {
    final Object localVarPostBody = searchInput;

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'aiResourceGroup' when calling search");
    }

    // verify the required parameter 'searchInput' is set
    if (searchInput == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'searchInput' when calling retrievalV1RetrievalEndpointsSearch");
    }

    final String localVarPath =
        UriComponentsBuilder.fromPath("/retrieval/search").build().toUriString();

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

    final ParameterizedTypeReference<SearchResults> localVarReturnType =
        new ParameterizedTypeReference<SearchResults>() {};
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
}
