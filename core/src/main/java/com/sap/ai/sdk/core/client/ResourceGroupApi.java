package com.sap.ai.sdk.core.client;

import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.core.model.BckndResourceGroup;
import com.sap.ai.sdk.core.model.BckndResourceGroupBase;
import com.sap.ai.sdk.core.model.BckndResourceGroupDeletionResponse;
import com.sap.ai.sdk.core.model.BckndResourceGroupList;
import com.sap.ai.sdk.core.model.BckndResourceGroupPatchRequest;
import com.sap.ai.sdk.core.model.BckndResourceGroupsPostRequest;
import com.sap.cloud.sdk.services.openapi.apiclient.ApiClient;
import com.sap.cloud.sdk.services.openapi.core.AbstractOpenApiService;
import com.sap.cloud.sdk.services.openapi.core.OpenApiRequestException;
import com.sap.cloud.sdk.services.openapi.core.OpenApiResponse;
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
public class ResourceGroupApi extends AbstractOpenApiService {

  /** Instantiates this API class to invoke operations on the AI Core */
  public ResourceGroupApi() {
    super(new AiCoreService().getApiClient());
  }

  /**
   * Instantiates this API class to invoke operations on the AI Core
   *
   * @param aiCoreService The configured connectivity instance to AI Core
   */
  public ResourceGroupApi(@Nonnull final AiCoreService aiCoreService) {
    super(aiCoreService.getApiClient());
  }

  /**
   * Creates a resource group
   *
   * <p>Create resource group to a given main tenant. The length of resource group id must be
   * between 3 and 253.
   *
   * <p><b>202</b> - A resource group base object
   *
   * <p><b>400</b> - The request was malformed and could thus not be processed.
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @param bckndResourceGroupsPostRequest (required) The value for the parameter
   *     bckndResourceGroupsPostRequest
   * @param authorization (optional) Authorization bearer token containing a JWT token.
   * @return BckndResourceGroupBase
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BckndResourceGroupBase create(
      @Nonnull final BckndResourceGroupsPostRequest bckndResourceGroupsPostRequest,
      @Nullable final String authorization)
      throws OpenApiRequestException {
    final Object localVarPostBody = bckndResourceGroupsPostRequest;

    // verify the required parameter 'bckndResourceGroupsPostRequest' is set
    if (bckndResourceGroupsPostRequest == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'bckndResourceGroupsPostRequest' when calling create");
    }

    final String localVarPath =
        UriComponentsBuilder.fromPath("/admin/resourceGroups").build().toUriString();

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

    final ParameterizedTypeReference<BckndResourceGroupBase> localVarReturnType =
        new ParameterizedTypeReference<BckndResourceGroupBase>() {};
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
   * Creates a resource group
   *
   * <p>Create resource group to a given main tenant. The length of resource group id must be
   * between 3 and 253.
   *
   * <p><b>202</b> - A resource group base object
   *
   * <p><b>400</b> - The request was malformed and could thus not be processed.
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @param bckndResourceGroupsPostRequest The value for the parameter
   *     bckndResourceGroupsPostRequest
   * @return BckndResourceGroupBase
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BckndResourceGroupBase create(
      @Nonnull final BckndResourceGroupsPostRequest bckndResourceGroupsPostRequest)
      throws OpenApiRequestException {
    return create(bckndResourceGroupsPostRequest, null);
  }

  /**
   * Delete a resource group
   *
   * <p>Delete a resource group of a given main tenant.
   *
   * <p><b>202</b> - The deletion of the resource group has been scheduled successfully
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * <p><b>400</b> - The request was malformed and could thus not be processed.
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @param resourceGroupId (required) Resource group identifier
   * @param authorization (optional) Authorization bearer token containing a JWT token.
   * @return BckndResourceGroupDeletionResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BckndResourceGroupDeletionResponse delete(
      @Nonnull final String resourceGroupId, @Nullable final String authorization)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    // verify the required parameter 'resourceGroupId' is set
    if (resourceGroupId == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'resourceGroupId' when calling delete");
    }

    // create path and map variables
    final Map<String, Object> localVarPathParams = new HashMap<String, Object>();
    localVarPathParams.put("resourceGroupId", resourceGroupId);
    final String localVarPath =
        UriComponentsBuilder.fromPath("/admin/resourceGroups/{resourceGroupId}")
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

    final ParameterizedTypeReference<BckndResourceGroupDeletionResponse> localVarReturnType =
        new ParameterizedTypeReference<BckndResourceGroupDeletionResponse>() {};
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
   * Delete a resource group
   *
   * <p>Delete a resource group of a given main tenant.
   *
   * <p><b>202</b> - The deletion of the resource group has been scheduled successfully
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * <p><b>400</b> - The request was malformed and could thus not be processed.
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @param resourceGroupId Resource group identifier
   * @return BckndResourceGroupDeletionResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BckndResourceGroupDeletionResponse delete(@Nonnull final String resourceGroupId)
      throws OpenApiRequestException {
    return delete(resourceGroupId, null);
  }

  /**
   * Get a resource group
   *
   * <p>Get a resource group of a given main tenant.
   *
   * <p><b>200</b> - A resource group object
   *
   * <p><b>400</b> - The request was malformed and could thus not be processed.
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @param resourceGroupId (required) Resource group identifier
   * @param authorization (optional) Authorization bearer token containing a JWT token.
   * @return BckndResourceGroup
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BckndResourceGroup get(
      @Nonnull final String resourceGroupId, @Nullable final String authorization)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    // verify the required parameter 'resourceGroupId' is set
    if (resourceGroupId == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'resourceGroupId' when calling get");
    }

    // create path and map variables
    final Map<String, Object> localVarPathParams = new HashMap<String, Object>();
    localVarPathParams.put("resourceGroupId", resourceGroupId);
    final String localVarPath =
        UriComponentsBuilder.fromPath("/admin/resourceGroups/{resourceGroupId}")
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

    final ParameterizedTypeReference<BckndResourceGroup> localVarReturnType =
        new ParameterizedTypeReference<BckndResourceGroup>() {};
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
   * Get a resource group
   *
   * <p>Get a resource group of a given main tenant.
   *
   * <p><b>200</b> - A resource group object
   *
   * <p><b>400</b> - The request was malformed and could thus not be processed.
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @param resourceGroupId Resource group identifier
   * @return BckndResourceGroup
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BckndResourceGroup get(@Nonnull final String resourceGroupId)
      throws OpenApiRequestException {
    return get(resourceGroupId, null);
  }

  /**
   * Gets all resource groups of a given tenant
   *
   * <p>Retrieve a list of resource groups for a given tenant.
   *
   * <p><b>200</b> - A list of resource groups
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
   * @param prefer (optional) &lt;p&gt; One can use the HTTP Prefer header to configure the
   *     \&quot;maxpagesize\&quot; of this endpoint. \&quot;maxpagesize\&quot; is the maximum number
   *     of resourcegroups to return for a request to this endpoint. If more resourcegroups exist,
   *     the response will include a \&quot;Link\&quot; HTTP header. This header will include the
   *     URL for getting the next batch of resourcegroups. &lt;p&gt; The \&quot;maxpagesize\&quot;
   *     preference and the \&quot;continueToken\&quot; parameter together enable splitting a large
   *     list of resourcegroups to small chunks. This feature can help to reduce the response time
   *     of a single list-resourcegroup request when the client has a large number resourcegroups in
   *     the system. &lt;p&gt; By default, the \&quot;maxpagesize\&quot; is not set, which means
   *     retrieving all resourcegroups in a single request. &lt;p&gt; Setting a maxpagesize may
   *     return fewer than the requested amount of resourcegroups (up to zero) in the event all
   *     requested resourcegroups are filtered out. The client should only use the presence of the
   *     Link header in the response to determine whether more resourcegroups are available. If
   *     \&quot;maxpagesize\&quot; is specified and the response does not include a Link header, the
   *     client may assume that no more resourcegroups are available.
   * @param continueToken (optional) &lt;p&gt; A token for getting more resourcegroups from the
   *     service. It is encoded in the URL returned via a \&quot;Link\&quot; HTTP header of a
   *     response of this endpoint if the original request specified \&quot;maxpagesize\&quot; in a
   *     \&quot;Prefer\&quot; HTTP header and the number of available resourcegroups is larger than
   *     the number specified by \&quot;maxpagesize\&quot;. Do &lt;b&gt;NOT&lt;/b&gt; use the
   *     \&quot;continueToken\&quot; parameter in other scenarios. &lt;p&gt; The server may reject a
   *     continue token that it does not recognize. If the specified continue token is no longer
   *     valid whether due to expiration (generally five to fifteen minutes) or a configuration
   *     change on the server, the server will respond with a 410 ResourceExpired error. In this
   *     case, the client must restart their list request without the continue token.
   * @param labelSelector (optional filter by labels. Pass in pairs in the form of
   *     &#39;key&#x3D;value&#39; and / or &#39;key!&#x3D;value&#39; separated by commas and the
   *     result will be filtered to only those scenarios containing and / or not containing all
   *     given key/values
   * @return BckndResourceGroupList
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BckndResourceGroupList getAll(
      @Nullable final String authorization,
      @Nullable final Integer $top,
      @Nullable final Integer $skip,
      @Nullable final Boolean $count,
      @Nullable final String prefer,
      @Nullable final String continueToken,
      @Nullable final List<String> labelSelector)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    final String localVarPath =
        UriComponentsBuilder.fromPath("/admin/resourceGroups").build().toUriString();

    final MultiValueMap<String, String> localVarQueryParams =
        new LinkedMultiValueMap<String, String>();
    final HttpHeaders localVarHeaderParams = new HttpHeaders();
    final MultiValueMap<String, Object> localVarFormParams =
        new LinkedMultiValueMap<String, Object>();

    localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "$top", $top));
    localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "$skip", $skip));
    localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "$count", $count));
    localVarQueryParams.putAll(
        apiClient.parameterToMultiValueMap(null, "continueToken", continueToken));
    localVarQueryParams.putAll(
        apiClient.parameterToMultiValueMap(
            ApiClient.CollectionFormat.valueOf("csv".toUpperCase(Locale.ROOT)),
            "labelSelector",
            labelSelector));

    if (authorization != null)
      localVarHeaderParams.add("Authorization", apiClient.parameterToString(authorization));
    if (prefer != null) localVarHeaderParams.add("Prefer", apiClient.parameterToString(prefer));

    final String[] localVarAccepts = {"application/json"};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    final String[] localVarAuthNames = new String[] {"Oauth2"};

    final ParameterizedTypeReference<BckndResourceGroupList> localVarReturnType =
        new ParameterizedTypeReference<BckndResourceGroupList>() {};
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
   * Gets all resource groups of a given tenant
   *
   * <p>Retrieve a list of resource groups for a given tenant.
   *
   * <p><b>200</b> - A list of resource groups
   *
   * <p><b>400</b> - The request was malformed and could thus not be processed.
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @return BckndResourceGroupList
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BckndResourceGroupList getAll() throws OpenApiRequestException {
    return getAll(null, null, null, null, null, null, null);
  }

  /**
   * Change some characteristics of the resource group
   *
   * <p>Replace some characteristics of the resource group, for instance labels.
   *
   * <p><b>202</b> - Resource group changes accepted.
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @param resourceGroupId (required) Resource group identifier
   * @param bckndResourceGroupPatchRequest (required) The value for the parameter
   *     bckndResourceGroupPatchRequest
   * @param authorization (optional) Authorization bearer token containing a JWT token.
   * @return An OpenApiResponse containing the status code of the HttpResponse.
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public OpenApiResponse patch(
      @Nonnull final String resourceGroupId,
      @Nonnull final BckndResourceGroupPatchRequest bckndResourceGroupPatchRequest,
      @Nullable final String authorization)
      throws OpenApiRequestException {
    final Object localVarPostBody = bckndResourceGroupPatchRequest;

    // verify the required parameter 'resourceGroupId' is set
    if (resourceGroupId == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'resourceGroupId' when calling patch");
    }

    // verify the required parameter 'bckndResourceGroupPatchRequest' is set
    if (bckndResourceGroupPatchRequest == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'bckndResourceGroupPatchRequest' when calling patch");
    }

    // create path and map variables
    final Map<String, Object> localVarPathParams = new HashMap<String, Object>();
    localVarPathParams.put("resourceGroupId", resourceGroupId);
    final String localVarPath =
        UriComponentsBuilder.fromPath("/admin/resourceGroups/{resourceGroupId}")
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

    final ParameterizedTypeReference<Void> localVarReturnType =
        new ParameterizedTypeReference<Void>() {};
    apiClient.invokeAPI(
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
    return new OpenApiResponse(apiClient);
  }

  /**
   * Change some characteristics of the resource group
   *
   * <p>Replace some characteristics of the resource group, for instance labels.
   *
   * <p><b>202</b> - Resource group changes accepted.
   *
   * <p><b>404</b> - The specified resource was not found
   *
   * <p><b>0</b> - HTTP status codes 401, 403 or 500. Response body contains further details.
   *
   * @param resourceGroupId Resource group identifier
   * @param bckndResourceGroupPatchRequest The value for the parameter
   *     bckndResourceGroupPatchRequest
   * @return An OpenApiResponse containing the status code of the HttpResponse.
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public OpenApiResponse patch(
      @Nonnull final String resourceGroupId,
      @Nonnull final BckndResourceGroupPatchRequest bckndResourceGroupPatchRequest)
      throws OpenApiRequestException {
    return patch(resourceGroupId, bckndResourceGroupPatchRequest, null);
  }
}
