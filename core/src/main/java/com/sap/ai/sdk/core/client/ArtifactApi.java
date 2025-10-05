package com.sap.ai.sdk.core.client;

import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.core.model.AiArtifact;
import com.sap.ai.sdk.core.model.AiArtifactCreationResponse;
import com.sap.ai.sdk.core.model.AiArtifactList;
import com.sap.ai.sdk.core.model.AiArtifactPostData;
import com.sap.cloud.sdk.services.openapi.apiclient.ApiClient;
import com.sap.cloud.sdk.services.openapi.core.AbstractOpenApiService;
import com.sap.cloud.sdk.services.openapi.core.OpenApiRequestException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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
public class ArtifactApi extends AbstractOpenApiService {

  /** Instantiates this API class to invoke operations on the AI Core */
  public ArtifactApi() {
    super(new AiCoreService().getApiClient());
  }

  /**
   * Instantiates this API class to invoke operations on the AI Core
   *
   * @param aiCoreService The configured connectivity instance to AI Core
   */
  public ArtifactApi(@Nonnull final AiCoreService aiCoreService) {
    super(aiCoreService.getApiClient());
  }

  /**
   * Get number of artifacts
   *
   * <p>Retrieve the number of available artifacts that match the specified filter criteria. Filter
   * criteria include a scenarioId, executionId, an artifact name, artifact kind, or artifact
   * labels. Search by substring of artifact name or description is also possible.
   *
   * <p><b>200</b> - Number of artifacts
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * @param aiResourceGroup (required) Specify a resource group id
   * @param scenarioId (optional) Scenario identifier
   * @param executionId (optional) Execution identifier
   * @param name (optional) Artifact name
   * @param kind (optional) Kind of the artifact
   * @param $search (optional) Generic search term to be looked for in various attributes
   * @param searchCaseInsensitive (optional, default to false) indicates whether the search should
   *     be case insensitive
   * @param artifactLabelSelector (optional Filter artifact by labels. Pass in expressions in the
   *     form of \&quot;key&#x3D;value\&quot; or \&quot;key!&#x3D;value\&quot; separated by commas
   *     and the result will be filtered to only those resources that have labels that match all
   *     provided expressions (i.e. logical AND). The maximum number of labels permitted for
   *     filtering is 10. Special handling is required when using comma in the value field. This is
   *     because commas are also used as a separator for multiple \&quot;key&#x3D;value\&quot;
   *     entries. Therefore, if the value contains a comma, then replace this with the string
   *     \&quot;{comma}\&quot; in the value field.
   * @return Integer
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public Integer count(
      @Nonnull final String aiResourceGroup,
      @Nullable final String scenarioId,
      @Nullable final String executionId,
      @Nullable final String name,
      @Nullable final String kind,
      @Nullable final String $search,
      @Nullable final Boolean searchCaseInsensitive,
      @Nullable final List<String> artifactLabelSelector)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'aiResourceGroup' when calling count");
    }

    final String localVarPath =
        UriComponentsBuilder.fromPath("/lm/artifacts/$count").build().toUriString();

    final MultiValueMap<String, String> localVarQueryParams =
        new LinkedMultiValueMap<String, String>();
    final HttpHeaders localVarHeaderParams = new HttpHeaders();
    final MultiValueMap<String, Object> localVarFormParams =
        new LinkedMultiValueMap<String, Object>();

    localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "scenarioId", scenarioId));
    localVarQueryParams.putAll(
        apiClient.parameterToMultiValueMap(null, "executionId", executionId));
    localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "name", name));
    localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "kind", kind));
    localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "$search", $search));
    localVarQueryParams.putAll(
        apiClient.parameterToMultiValueMap(null, "searchCaseInsensitive", searchCaseInsensitive));
    localVarQueryParams.putAll(
        apiClient.parameterToMultiValueMap(
            ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)),
            "artifactLabelSelector",
            artifactLabelSelector));

    if (aiResourceGroup != null)
      localVarHeaderParams.add("AI-Resource-Group", apiClient.parameterToString(aiResourceGroup));

    final String[] localVarAccepts = {"text/plain", "application/json"};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    final String[] localVarAuthNames = new String[] {"Oauth2"};

    final ParameterizedTypeReference<Integer> localVarReturnType =
        new ParameterizedTypeReference<Integer>() {};
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
   * Get number of artifacts
   *
   * <p>Retrieve the number of available artifacts that match the specified filter criteria. Filter
   * criteria include a scenarioId, executionId, an artifact name, artifact kind, or artifact
   * labels. Search by substring of artifact name or description is also possible.
   *
   * <p><b>200</b> - Number of artifacts
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * @param aiResourceGroup Specify a resource group id
   * @return Integer
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public Integer count(@Nonnull final String aiResourceGroup) throws OpenApiRequestException {
    return count(aiResourceGroup, null, null, null, null, null, null, null);
  }

  /**
   * Register artifact
   *
   * <p>Register an artifact for use in a configuration, for example a model or a dataset.
   *
   * <p><b>201</b> - The artifact has been registered successfully
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * @param aiResourceGroup Specify a resource group id
   * @param aiArtifactPostData The value for the parameter aiArtifactPostData
   * @return AiArtifactCreationResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public AiArtifactCreationResponse create(
      @Nonnull final String aiResourceGroup, @Nonnull final AiArtifactPostData aiArtifactPostData)
      throws OpenApiRequestException {
    final Object localVarPostBody = aiArtifactPostData;

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'aiResourceGroup' when calling create");
    }

    // verify the required parameter 'aiArtifactPostData' is set
    if (aiArtifactPostData == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'aiArtifactPostData' when calling create");
    }

    final String localVarPath =
        UriComponentsBuilder.fromPath("/lm/artifacts").build().toUriString();

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

    final String[] localVarAuthNames = new String[] {"Oauth2"};

    final ParameterizedTypeReference<AiArtifactCreationResponse> localVarReturnType =
        new ParameterizedTypeReference<AiArtifactCreationResponse>() {};
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
   * Get artifact by ID
   *
   * <p>Retrieve details for artifact with artifactId.
   *
   * <p><b>200</b> - An artifact
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * @param aiResourceGroup (required) Specify a resource group id
   * @param artifactId (required) Artifact identifier
   * @param $expand (optional) expand detailed information on scenario
   * @return AiArtifact
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public AiArtifact get(
      @Nonnull final String aiResourceGroup,
      @Nonnull final String artifactId,
      @Nullable final String $expand)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'aiResourceGroup' when calling get");
    }

    // verify the required parameter 'artifactId' is set
    if (artifactId == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'artifactId' when calling get");
    }

    // create path and map variables
    final Map<String, Object> localVarPathParams = new HashMap<String, Object>();
    localVarPathParams.put("artifactId", artifactId);
    final String localVarPath =
        UriComponentsBuilder.fromPath("/lm/artifacts/{artifactId}")
            .buildAndExpand(localVarPathParams)
            .toUriString();

    final MultiValueMap<String, String> localVarQueryParams =
        new LinkedMultiValueMap<String, String>();
    final HttpHeaders localVarHeaderParams = new HttpHeaders();
    final MultiValueMap<String, Object> localVarFormParams =
        new LinkedMultiValueMap<String, Object>();

    localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "$expand", $expand));

    if (aiResourceGroup != null)
      localVarHeaderParams.add("AI-Resource-Group", apiClient.parameterToString(aiResourceGroup));

    final String[] localVarAccepts = {"application/json"};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    final String[] localVarAuthNames = new String[] {"Oauth2"};

    final ParameterizedTypeReference<AiArtifact> localVarReturnType =
        new ParameterizedTypeReference<AiArtifact>() {};
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
   * Get artifact by ID
   *
   * <p>Retrieve details for artifact with artifactId.
   *
   * <p><b>200</b> - An artifact
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * @param aiResourceGroup Specify a resource group id
   * @param artifactId Artifact identifier
   * @return AiArtifact
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public AiArtifact get(@Nonnull final String aiResourceGroup, @Nonnull final String artifactId)
      throws OpenApiRequestException {
    return get(aiResourceGroup, artifactId, null);
  }

  /**
   * Get list of artifacts
   *
   * <p>Retrieve a list of artifacts that matches the specified filter criteria. Filter criteria
   * include scenario ID, execution ID, an artifact name, artifact kind, or artifact labels. Use
   * top/skip parameters to paginate the result list. Search by substring of artifact name or
   * description, if required.
   *
   * <p><b>200</b> - A list of artifacts
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * @param aiResourceGroup (required) Specify a resource group id
   * @param scenarioId (optional) Scenario identifier
   * @param executionId (optional) Execution identifier
   * @param name (optional) Artifact name
   * @param kind (optional) Kind of the artifact
   * @param artifactLabelSelector (optional Filter artifact by labels. Pass in expressions in the
   *     form of \&quot;key&#x3D;value\&quot; or \&quot;key!&#x3D;value\&quot; separated by commas
   *     and the result will be filtered to only those resources that have labels that match all
   *     provided expressions (i.e. logical AND). The maximum number of labels permitted for
   *     filtering is 10. Special handling is required when using comma in the value field. This is
   *     because commas are also used as a separator for multiple \&quot;key&#x3D;value\&quot;
   *     entries. Therefore, if the value contains a comma, then replace this with the string
   *     \&quot;{comma}\&quot; in the value field.
   * @param $top (optional, default to 10000) Number of results to display
   * @param $skip (optional) Number of results to be skipped from the ordered list of results
   * @param $search (optional) Generic search term to be looked for in various attributes
   * @param searchCaseInsensitive (optional, default to false) indicates whether the search should
   *     be case insensitive
   * @param $expand (optional) expand detailed information on scenario
   * @return AiArtifactList
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public AiArtifactList query(
      @Nonnull final String aiResourceGroup,
      @Nullable final String scenarioId,
      @Nullable final String executionId,
      @Nullable final String name,
      @Nullable final String kind,
      @Nullable final List<String> artifactLabelSelector,
      @Nullable final Integer $top,
      @Nullable final Integer $skip,
      @Nullable final String $search,
      @Nullable final Boolean searchCaseInsensitive,
      @Nullable final String $expand)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'aiResourceGroup' when calling query");
    }

    final String localVarPath =
        UriComponentsBuilder.fromPath("/lm/artifacts").build().toUriString();

    final MultiValueMap<String, String> localVarQueryParams =
        new LinkedMultiValueMap<String, String>();
    final HttpHeaders localVarHeaderParams = new HttpHeaders();
    final MultiValueMap<String, Object> localVarFormParams =
        new LinkedMultiValueMap<String, Object>();

    localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "scenarioId", scenarioId));
    localVarQueryParams.putAll(
        apiClient.parameterToMultiValueMap(null, "executionId", executionId));
    localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "name", name));
    localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "kind", kind));
    localVarQueryParams.putAll(
        apiClient.parameterToMultiValueMap(
            ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)),
            "artifactLabelSelector",
            artifactLabelSelector));
    localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "$top", $top));
    localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "$skip", $skip));
    localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "$search", $search));
    localVarQueryParams.putAll(
        apiClient.parameterToMultiValueMap(null, "searchCaseInsensitive", searchCaseInsensitive));
    localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "$expand", $expand));

    if (aiResourceGroup != null)
      localVarHeaderParams.add("AI-Resource-Group", apiClient.parameterToString(aiResourceGroup));

    final String[] localVarAccepts = {"application/json"};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    final String[] localVarAuthNames = new String[] {"Oauth2"};

    final ParameterizedTypeReference<AiArtifactList> localVarReturnType =
        new ParameterizedTypeReference<AiArtifactList>() {};
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
   * Get list of artifacts
   *
   * <p>Retrieve a list of artifacts that matches the specified filter criteria. Filter criteria
   * include scenario ID, execution ID, an artifact name, artifact kind, or artifact labels. Use
   * top/skip parameters to paginate the result list. Search by substring of artifact name or
   * description, if required.
   *
   * <p><b>200</b> - A list of artifacts
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * @param aiResourceGroup Specify a resource group id
   * @return AiArtifactList
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public AiArtifactList query(@Nonnull final String aiResourceGroup)
      throws OpenApiRequestException {
    return query(aiResourceGroup, null, null, null, null, null, null, null, null, null, null);
  }
}
