/*
 * Copyright (c) 2025 SAP SE or an SAP affiliate company. All rights reserved.
 */

package openapi.grounding.api;

import com.google.common.annotations.Beta;
import com.sap.cloud.sdk.cloudplatform.connectivity.Destination;
import com.sap.cloud.sdk.datamodel.openapi.grounding.model.Collection;
import com.sap.cloud.sdk.datamodel.openapi.grounding.model.CollectionRequest;
import com.sap.cloud.sdk.datamodel.openapi.grounding.model.CollectionsListResponse;
import com.sap.cloud.sdk.datamodel.openapi.grounding.model.DocumentCreateRequest;
import com.sap.cloud.sdk.datamodel.openapi.grounding.model.DocumentResponse;
import com.sap.cloud.sdk.datamodel.openapi.grounding.model.DocumentUpdateRequest;
import com.sap.cloud.sdk.datamodel.openapi.grounding.model.Documents;
import com.sap.cloud.sdk.datamodel.openapi.grounding.model.DocumentsListResponse;
import com.sap.cloud.sdk.datamodel.openapi.grounding.model.SearchResults;
import com.sap.cloud.sdk.datamodel.openapi.grounding.model.TextSearchRequest;
import com.sap.cloud.sdk.datamodel.openapi.grounding.model.VectorV1VectorEndpointsGetCollectionCreationStatus200Response;
import com.sap.cloud.sdk.datamodel.openapi.grounding.model.VectorV1VectorEndpointsGetCollectionDeletionStatus200Response;
import com.sap.cloud.sdk.services.openapi.apiclient.ApiClient;
import com.sap.cloud.sdk.services.openapi.core.AbstractOpenApiService;
import com.sap.cloud.sdk.services.openapi.core.OpenApiRequestException;
import com.sap.cloud.sdk.services.openapi.core.OpenApiResponse;
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
 * Document Grounding Pipeline API in version 0.1.0.
 *
 * <p>SAP AI Core - API Specification AI Data Management api's
 */
public class VectorApi extends AbstractOpenApiService {
  /**
   * Instantiates this API class to invoke operations on the Document Grounding Pipeline API.
   *
   * @param httpDestination The destination that API should be used with
   */
  public VectorApi(@Nonnull final Destination httpDestination) {
    super(httpDestination);
  }

  /**
   * Instantiates this API class to invoke operations on the Document Grounding Pipeline API based
   * on a given {@link ApiClient}.
   *
   * @param apiClient ApiClient to invoke the API on
   */
  @Beta
  public VectorApi(@Nonnull final ApiClient apiClient) {
    super(apiClient);
  }

  /**
   * Create collection
   *
   * <p>Creates a collection. This operation is asynchronous. Poll the collection resource and check
   * the status field to understand creation status.
   *
   * <p><b>202</b> - Successful Response
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * <p><b>422</b> - There are validation issues with the data.
   *
   * @param aiResourceGroup The value for the parameter aiResourceGroup
   * @param collectionRequest The value for the parameter collectionRequest
   * @return An OpenApiResponse containing the status code of the HttpResponse.
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public OpenApiResponse createCollection(
      @Nonnull final String aiResourceGroup, @Nonnull final CollectionRequest collectionRequest)
      throws OpenApiRequestException {
    final Object localVarPostBody = collectionRequest;

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'aiResourceGroup' when calling createCollection");
    }

    // verify the required parameter 'collectionRequest' is set
    if (collectionRequest == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'collectionRequest' when calling vectorV1VectorEndpointsCreateCollection");
    }

    final String localVarPath =
        UriComponentsBuilder.fromPath("/vector/collections").build().toUriString();

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

    final ParameterizedTypeReference<Void> localVarReturnType =
        new ParameterizedTypeReference<Void>() {};
    apiClient.invokeAPI(
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
    return new OpenApiResponse(apiClient);
  }

  /**
   * Create documents in collection
   *
   * <p>Create and stores one or multiple documents into a collection. If omitted, &#39;id&#39; will
   * be auto-generated.
   *
   * <p><b>201</b> - Successful Response
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * <p><b>404</b> - The specification of the resource was incorrect
   *
   * <p><b>422</b> - There are validation issues with the data.
   *
   * @param aiResourceGroup The value for the parameter aiResourceGroup
   * @param collectionId The value for the parameter collectionId
   * @param documentCreateRequest The value for the parameter documentCreateRequest
   * @return DocumentsListResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public DocumentsListResponse createDocuments(
      @Nonnull final String aiResourceGroup,
      @Nonnull final UUID collectionId,
      @Nonnull final DocumentCreateRequest documentCreateRequest)
      throws OpenApiRequestException {
    final Object localVarPostBody = documentCreateRequest;

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'aiResourceGroup' when calling createDocuments");
    }

    // verify the required parameter 'collectionId' is set
    if (collectionId == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'collectionId' when calling createDocuments");
    }

    // verify the required parameter 'documentCreateRequest' is set
    if (documentCreateRequest == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'documentCreateRequest' when calling vectorV1VectorEndpointsCreateDocuments");
    }

    // create path and map variables
    final Map<String, Object> localVarPathParams = new HashMap<String, Object>();
    localVarPathParams.put("collectionId", collectionId);
    final String localVarPath =
        UriComponentsBuilder.fromPath("/vector/collections/{collectionId}/documents")
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
    final String[] localVarContentTypes = {"application/json"};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    final String[] localVarAuthNames = new String[] {};

    final ParameterizedTypeReference<DocumentsListResponse> localVarReturnType =
        new ParameterizedTypeReference<DocumentsListResponse>() {};
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
   * Delete collection by ID
   *
   * <p>Deletes a specific collection by ID. This operation is asynchronous. Poll the collection for
   * a 404 status code.
   *
   * <p><b>202</b> - Successful Response
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * <p><b>404</b> - The specification of the resource was incorrect
   *
   * <p><b>422</b> - There are validation issues with the data.
   *
   * @param aiResourceGroup The value for the parameter aiResourceGroup
   * @param collectionId The value for the parameter collectionId
   * @return An OpenApiResponse containing the status code of the HttpResponse.
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public OpenApiResponse deleteCollectionById(
      @Nonnull final String aiResourceGroup, @Nonnull final String collectionId)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'aiResourceGroup' when calling deleteCollectionById");
    }

    // verify the required parameter 'collectionId' is set
    if (collectionId == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'collectionId' when calling deleteCollectionById");
    }

    // create path and map variables
    final Map<String, Object> localVarPathParams = new HashMap<String, Object>();
    localVarPathParams.put("collectionId", collectionId);
    final String localVarPath =
        UriComponentsBuilder.fromPath("/vector/collections/{collectionId}")
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

    final ParameterizedTypeReference<Void> localVarReturnType =
        new ParameterizedTypeReference<Void>() {};
    apiClient.invokeAPI(
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
    return new OpenApiResponse(apiClient);
  }

  /**
   * Delete a document
   *
   * <p>Deletes a specific document of a collection.
   *
   * <p><b>204</b> - Successful Response
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * <p><b>404</b> - The specification of the resource was incorrect
   *
   * <p><b>422</b> - There are validation issues with the data.
   *
   * @param aiResourceGroup The value for the parameter aiResourceGroup
   * @param collectionId The value for the parameter collectionId
   * @param documentId The value for the parameter documentId
   * @return An OpenApiResponse containing the status code of the HttpResponse.
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public OpenApiResponse deleteDocumentById(
      @Nonnull final String aiResourceGroup,
      @Nonnull final UUID collectionId,
      @Nonnull final UUID documentId)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'aiResourceGroup' when calling deleteDocumentById");
    }

    // verify the required parameter 'collectionId' is set
    if (collectionId == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'collectionId' when calling deleteDocumentById");
    }

    // verify the required parameter 'documentId' is set
    if (documentId == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'documentId' when calling deleteDocumentById");
    }

    // create path and map variables
    final Map<String, Object> localVarPathParams = new HashMap<String, Object>();
    localVarPathParams.put("collectionId", collectionId);
    localVarPathParams.put("documentId", documentId);
    final String localVarPath =
        UriComponentsBuilder.fromPath("/vector/collections/{collectionId}/documents/{documentId}")
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

    final ParameterizedTypeReference<Void> localVarReturnType =
        new ParameterizedTypeReference<Void>() {};
    apiClient.invokeAPI(
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
    return new OpenApiResponse(apiClient);
  }

  /**
   * Get collections
   *
   * <p>Gets a list of collections.
   *
   * <p><b>200</b> - Successful Response
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * @param aiResourceGroup (required) The value for the parameter aiResourceGroup
   * @param $top (optional) Number of results to display
   * @param $skip (optional) Number of results to be skipped from the ordered list of results
   * @param $count (optional) When the $count field is set to false, the response contains a count
   *     of the items present in the response. When the $count field is set to true, the response
   *     contains a count of all the items present on the server, and not just the ones in the
   *     response. When the $count field is not passed, it is false by default.
   * @return CollectionsListResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public CollectionsListResponse getAllCollections(
      @Nonnull final String aiResourceGroup,
      @Nullable final Integer $top,
      @Nullable final Integer $skip,
      @Nullable final Boolean $count)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'aiResourceGroup' when calling getAllCollections");
    }

    final String localVarPath =
        UriComponentsBuilder.fromPath("/vector/collections").build().toUriString();

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

    final ParameterizedTypeReference<CollectionsListResponse> localVarReturnType =
        new ParameterizedTypeReference<CollectionsListResponse>() {};
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
   * Get collections
   *
   * <p>Gets a list of collections.
   *
   * <p><b>200</b> - Successful Response
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * @param aiResourceGroup The value for the parameter aiResourceGroup
   * @return CollectionsListResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public CollectionsListResponse getAllCollections(@Nonnull final String aiResourceGroup)
      throws OpenApiRequestException {
    return getAllCollections(aiResourceGroup, null, null, null);
  }

  /**
   * Get documents
   *
   * <p>Gets a list of documents of a collection.
   *
   * <p><b>200</b> - Successful Response
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * <p><b>404</b> - The specification of the resource was incorrect
   *
   * <p><b>422</b> - There are validation issues with the data.
   *
   * @param aiResourceGroup (required) The value for the parameter aiResourceGroup
   * @param collectionId (required) The value for the parameter collectionId
   * @param $top (optional) Number of results to display
   * @param $skip (optional) Number of results to be skipped from the ordered list of results
   * @param $count (optional) When the $count field is set to false, the response contains a count
   *     of the items present in the response. When the $count field is set to true, the response
   *     contains a count of all the items present on the server, and not just the ones in the
   *     response. When the $count field is not passed, it is false by default.
   * @return Documents
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public Documents getAllDocuments(
      @Nonnull final String aiResourceGroup,
      @Nonnull final UUID collectionId,
      @Nullable final Integer $top,
      @Nullable final Integer $skip,
      @Nullable final Boolean $count)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'aiResourceGroup' when calling getAllDocuments");
    }

    // verify the required parameter 'collectionId' is set
    if (collectionId == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'collectionId' when calling getAllDocuments");
    }

    // create path and map variables
    final Map<String, Object> localVarPathParams = new HashMap<String, Object>();
    localVarPathParams.put("collectionId", collectionId);
    final String localVarPath =
        UriComponentsBuilder.fromPath("/vector/collections/{collectionId}/documents")
            .buildAndExpand(localVarPathParams)
            .toUriString();

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

    final ParameterizedTypeReference<Documents> localVarReturnType =
        new ParameterizedTypeReference<Documents>() {};
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
   * Get documents
   *
   * <p>Gets a list of documents of a collection.
   *
   * <p><b>200</b> - Successful Response
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * <p><b>404</b> - The specification of the resource was incorrect
   *
   * <p><b>422</b> - There are validation issues with the data.
   *
   * @param aiResourceGroup The value for the parameter aiResourceGroup
   * @param collectionId The value for the parameter collectionId
   * @return Documents
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public Documents getAllDocuments(
      @Nonnull final String aiResourceGroup, @Nonnull final UUID collectionId)
      throws OpenApiRequestException {
    return getAllDocuments(aiResourceGroup, collectionId, null, null, null);
  }

  /**
   * Get collection by ID
   *
   * <p>Gets a specific collection by ID.
   *
   * <p><b>200</b> - Successful Response
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * <p><b>404</b> - The specification of the resource was incorrect
   *
   * <p><b>422</b> - There are validation issues with the data.
   *
   * @param aiResourceGroup The value for the parameter aiResourceGroup
   * @param collectionId The value for the parameter collectionId
   * @return Collection
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public Collection getCollectionById(
      @Nonnull final String aiResourceGroup, @Nonnull final UUID collectionId)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'aiResourceGroup' when calling getCollectionById");
    }

    // verify the required parameter 'collectionId' is set
    if (collectionId == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'collectionId' when calling getCollectionById");
    }

    // create path and map variables
    final Map<String, Object> localVarPathParams = new HashMap<String, Object>();
    localVarPathParams.put("collectionId", collectionId);
    final String localVarPath =
        UriComponentsBuilder.fromPath("/vector/collections/{collectionId}")
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

    final ParameterizedTypeReference<Collection> localVarReturnType =
        new ParameterizedTypeReference<Collection>() {};
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
   * Get collection status by ID
   *
   * <p>Gets a specific collection status from monitor by ID.
   *
   * <p><b>200</b> - Successful Response
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * <p><b>404</b> - The specification of the resource was incorrect
   *
   * <p><b>422</b> - There are validation issues with the data.
   *
   * @param aiResourceGroup The value for the parameter aiResourceGroup
   * @param id The value for the parameter id
   * @return VectorV1VectorEndpointsGetCollectionCreationStatus200Response
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public VectorV1VectorEndpointsGetCollectionCreationStatus200Response getCollectionCreationStatus(
      @Nonnull final String aiResourceGroup, @Nonnull final UUID id)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'aiResourceGroup' when calling getCollectionCreationStatus");
    }

    // verify the required parameter 'id' is set
    if (id == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'id' when calling getCollectionCreationStatus");
    }

    // create path and map variables
    final Map<String, Object> localVarPathParams = new HashMap<String, Object>();
    localVarPathParams.put("id", id);
    final String localVarPath =
        UriComponentsBuilder.fromPath("/vector/collections/{id}/creationStatus")
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

    final ParameterizedTypeReference<VectorV1VectorEndpointsGetCollectionCreationStatus200Response>
        localVarReturnType =
            new ParameterizedTypeReference<
                VectorV1VectorEndpointsGetCollectionCreationStatus200Response>() {};
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
   * Get collection status by ID
   *
   * <p>Gets a specific collection status from monitor by ID.
   *
   * <p><b>200</b> - Successful Response
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * <p><b>404</b> - The specification of the resource was incorrect
   *
   * <p><b>422</b> - There are validation issues with the data.
   *
   * @param aiResourceGroup The value for the parameter aiResourceGroup
   * @param id The value for the parameter id
   * @return VectorV1VectorEndpointsGetCollectionDeletionStatus200Response
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public VectorV1VectorEndpointsGetCollectionDeletionStatus200Response getCollectionDeletionStatus(
      @Nonnull final String aiResourceGroup, @Nonnull final UUID id)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'aiResourceGroup' when calling getCollectionDeletionStatus");
    }

    // verify the required parameter 'id' is set
    if (id == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'id' when calling getCollectionDeletionStatus");
    }

    // create path and map variables
    final Map<String, Object> localVarPathParams = new HashMap<String, Object>();
    localVarPathParams.put("id", id);
    final String localVarPath =
        UriComponentsBuilder.fromPath("/vector/collections/{id}/deletionStatus")
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

    final ParameterizedTypeReference<VectorV1VectorEndpointsGetCollectionDeletionStatus200Response>
        localVarReturnType =
            new ParameterizedTypeReference<
                VectorV1VectorEndpointsGetCollectionDeletionStatus200Response>() {};
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
   * Get document by ID
   *
   * <p>Gets a specific document in a collection by ID.
   *
   * <p><b>200</b> - Successful Response
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * <p><b>404</b> - The specification of the resource was incorrect
   *
   * <p><b>422</b> - There are validation issues with the data.
   *
   * @param aiResourceGroup The value for the parameter aiResourceGroup
   * @param collectionId The value for the parameter collectionId
   * @param documentId The value for the parameter documentId
   * @return DocumentResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public DocumentResponse getDocumentById(
      @Nonnull final String aiResourceGroup,
      @Nonnull final UUID collectionId,
      @Nonnull final UUID documentId)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'aiResourceGroup' when calling getDocumentById");
    }

    // verify the required parameter 'collectionId' is set
    if (collectionId == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'collectionId' when calling getDocumentById");
    }

    // verify the required parameter 'documentId' is set
    if (documentId == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'documentId' when calling getDocumentById");
    }

    // create path and map variables
    final Map<String, Object> localVarPathParams = new HashMap<String, Object>();
    localVarPathParams.put("collectionId", collectionId);
    localVarPathParams.put("documentId", documentId);
    final String localVarPath =
        UriComponentsBuilder.fromPath("/vector/collections/{collectionId}/documents/{documentId}")
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

    final ParameterizedTypeReference<DocumentResponse> localVarReturnType =
        new ParameterizedTypeReference<DocumentResponse>() {};
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
   * Search chunk by vector
   *
   * <p>Search chunk by vector
   *
   * <p><b>200</b> - Successful Response
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * <p><b>404</b> - The specification of the resource was incorrect
   *
   * <p><b>422</b> - There are validation issues with the data.
   *
   * @param aiResourceGroup The value for the parameter aiResourceGroup
   * @param textSearchRequest The value for the parameter textSearchRequest
   * @return SearchResults
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public SearchResults search(
      @Nonnull final String aiResourceGroup, @Nonnull final TextSearchRequest textSearchRequest)
      throws OpenApiRequestException {
    final Object localVarPostBody = textSearchRequest;

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'aiResourceGroup' when calling search");
    }

    // verify the required parameter 'textSearchRequest' is set
    if (textSearchRequest == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'textSearchRequest' when calling vectorV1VectorEndpointsSearch");
    }

    final String localVarPath =
        UriComponentsBuilder.fromPath("/vector/search").build().toUriString();

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

  /**
   * Upsert documents in collection
   *
   * <p>Upserts the data of multiple documents into a collection.
   *
   * <p><b>200</b> - Successful Response
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * <p><b>404</b> - The specification of the resource was incorrect
   *
   * <p><b>422</b> - There are validation issues with the data.
   *
   * @param aiResourceGroup The value for the parameter aiResourceGroup
   * @param collectionId The value for the parameter collectionId
   * @param documentUpdateRequest The value for the parameter documentUpdateRequest
   * @return DocumentsListResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public DocumentsListResponse updateDocuments(
      @Nonnull final String aiResourceGroup,
      @Nonnull final UUID collectionId,
      @Nonnull final DocumentUpdateRequest documentUpdateRequest)
      throws OpenApiRequestException {
    final Object localVarPostBody = documentUpdateRequest;

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'aiResourceGroup' when calling updateDocuments");
    }

    // verify the required parameter 'collectionId' is set
    if (collectionId == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'collectionId' when calling updateDocuments");
    }

    // verify the required parameter 'documentUpdateRequest' is set
    if (documentUpdateRequest == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'documentUpdateRequest' when calling vectorV1VectorEndpointsUpdateDocuments");
    }

    // create path and map variables
    final Map<String, Object> localVarPathParams = new HashMap<String, Object>();
    localVarPathParams.put("collectionId", collectionId);
    final String localVarPath =
        UriComponentsBuilder.fromPath("/vector/collections/{collectionId}/documents")
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
    final String[] localVarContentTypes = {"application/json"};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    final String[] localVarAuthNames = new String[] {};

    final ParameterizedTypeReference<DocumentsListResponse> localVarReturnType =
        new ParameterizedTypeReference<DocumentsListResponse>() {};
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
}
