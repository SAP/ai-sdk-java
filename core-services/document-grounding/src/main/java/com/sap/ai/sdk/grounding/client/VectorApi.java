package com.sap.ai.sdk.grounding.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.sap.ai.sdk.grounding.model.Collection;
import com.sap.ai.sdk.grounding.model.CollectionRequest;
import com.sap.ai.sdk.grounding.model.CollectionsListResponse;
import com.sap.ai.sdk.grounding.model.DocumentCreateRequest;
import com.sap.ai.sdk.grounding.model.DocumentResponse;
import com.sap.ai.sdk.grounding.model.DocumentUpdateRequest;
import com.sap.ai.sdk.grounding.model.Documents;
import com.sap.ai.sdk.grounding.model.DocumentsListResponse;
import com.sap.ai.sdk.grounding.model.TextSearchRequest;
import com.sap.ai.sdk.grounding.model.VectorSearchResults;
import com.sap.ai.sdk.grounding.model.VectorV1VectorEndpointsGetCollectionCreationStatus200Response;
import com.sap.ai.sdk.grounding.model.VectorV1VectorEndpointsGetCollectionDeletionStatus200Response;
import com.sap.cloud.sdk.cloudplatform.connectivity.Destination;
import com.sap.cloud.sdk.services.openapi.apache.ApiClient;
import com.sap.cloud.sdk.services.openapi.apache.BaseApi;
import com.sap.cloud.sdk.services.openapi.apache.OpenApiResponse;
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
public class VectorApi extends BaseApi {

  /** Instantiates this API class to invoke operations on the Grounding */
  public VectorApi() {}

  /**
   * Instantiates this API class to invoke operations on the Grounding.
   *
   * @param httpDestination The destination that API should be used with
   */
  public VectorApi(@Nonnull final Destination httpDestination) {
    super(httpDestination);
  }

  /**
   * Instantiates this API class to invoke operations on the Grounding based on a given {@link
   * ApiClient}.
   *
   * @param apiClient ApiClient to invoke the API on
   */
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
   * @param aiResourceGroup Resource Group ID
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
              "Missing the required parameter 'aiResourceGroup' when calling createCollection")
          .statusCode(400);
    }

    // verify the required parameter 'collectionRequest' is set
    if (collectionRequest == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'collectionRequest' when calling vectorV1VectorEndpointsCreateCollection")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath = "/vector/collections";

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

    final TypeReference<OpenApiResponse> localVarReturnType =
        new TypeReference<OpenApiResponse>() {};
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
   * @param aiResourceGroup Resource Group ID
   * @param collectionId Collection ID
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
              "Missing the required parameter 'aiResourceGroup' when calling createDocuments")
          .statusCode(400);
    }

    // verify the required parameter 'collectionId' is set
    if (collectionId == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'collectionId' when calling createDocuments")
          .statusCode(400);
    }

    // verify the required parameter 'documentCreateRequest' is set
    if (documentCreateRequest == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'documentCreateRequest' when calling vectorV1VectorEndpointsCreateDocuments")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/vector/collections/{collectionId}/documents"
            .replaceAll(
                "\\{" + "collectionId" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(collectionId)));

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

    final TypeReference<DocumentsListResponse> localVarReturnType =
        new TypeReference<DocumentsListResponse>() {};
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
   * @param aiResourceGroup Resource Group ID
   * @param collectionId Collection ID
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
              "Missing the required parameter 'aiResourceGroup' when calling deleteCollectionById")
          .statusCode(400);
    }

    // verify the required parameter 'collectionId' is set
    if (collectionId == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'collectionId' when calling deleteCollectionById")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/vector/collections/{collectionId}"
            .replaceAll(
                "\\{" + "collectionId" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(collectionId)));

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

    final TypeReference<OpenApiResponse> localVarReturnType =
        new TypeReference<OpenApiResponse>() {};
    return apiClient.invokeAPI(
        localVarPath,
        "DELETE",
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
   * @param aiResourceGroup Resource Group ID
   * @param collectionId Collection ID
   * @param documentId Document ID
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
              "Missing the required parameter 'aiResourceGroup' when calling deleteDocumentById")
          .statusCode(400);
    }

    // verify the required parameter 'collectionId' is set
    if (collectionId == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'collectionId' when calling deleteDocumentById")
          .statusCode(400);
    }

    // verify the required parameter 'documentId' is set
    if (documentId == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'documentId' when calling deleteDocumentById")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/vector/collections/{collectionId}/documents/{documentId}"
            .replaceAll(
                "\\{" + "collectionId" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(collectionId)))
            .replaceAll(
                "\\{" + "documentId" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(documentId)));

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

    final TypeReference<OpenApiResponse> localVarReturnType =
        new TypeReference<OpenApiResponse>() {};
    return apiClient.invokeAPI(
        localVarPath,
        "DELETE",
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
   * Get collections
   *
   * <p>Gets a list of collections.
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
              "Missing the required parameter 'aiResourceGroup' when calling getAllCollections")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath = "/vector/collections";

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

    final TypeReference<CollectionsListResponse> localVarReturnType =
        new TypeReference<CollectionsListResponse>() {};
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
   * Get collections
   *
   * <p>Gets a list of collections.
   *
   * <p><b>200</b> - Successful Response
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * @param aiResourceGroup Resource Group ID
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
   * @param aiResourceGroup (required) Resource Group ID
   * @param collectionId (required) Collection ID
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
              "Missing the required parameter 'aiResourceGroup' when calling getAllDocuments")
          .statusCode(400);
    }

    // verify the required parameter 'collectionId' is set
    if (collectionId == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'collectionId' when calling getAllDocuments")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/vector/collections/{collectionId}/documents"
            .replaceAll(
                "\\{" + "collectionId" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(collectionId)));

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

    final TypeReference<Documents> localVarReturnType = new TypeReference<Documents>() {};
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
   * @param aiResourceGroup Resource Group ID
   * @param collectionId Collection ID
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
   * @param aiResourceGroup Resource Group ID
   * @param collectionId Collection ID
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
              "Missing the required parameter 'aiResourceGroup' when calling getCollectionById")
          .statusCode(400);
    }

    // verify the required parameter 'collectionId' is set
    if (collectionId == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'collectionId' when calling getCollectionById")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/vector/collections/{collectionId}"
            .replaceAll(
                "\\{" + "collectionId" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(collectionId)));

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

    final TypeReference<Collection> localVarReturnType = new TypeReference<Collection>() {};
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
   * @param aiResourceGroup Resource Group ID
   * @param id Collection ID
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
              "Missing the required parameter 'aiResourceGroup' when calling getCollectionCreationStatus")
          .statusCode(400);
    }

    // verify the required parameter 'id' is set
    if (id == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'id' when calling getCollectionCreationStatus")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/vector/collections/{id}/creationStatus"
            .replaceAll(
                "\\{" + "id" + "\\}", ApiClient.escapeString(ApiClient.parameterToString(id)));

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

    final TypeReference<VectorV1VectorEndpointsGetCollectionCreationStatus200Response>
        localVarReturnType =
            new TypeReference<VectorV1VectorEndpointsGetCollectionCreationStatus200Response>() {};
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
   * @param aiResourceGroup Resource Group ID
   * @param id Collection ID
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
              "Missing the required parameter 'aiResourceGroup' when calling getCollectionDeletionStatus")
          .statusCode(400);
    }

    // verify the required parameter 'id' is set
    if (id == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'id' when calling getCollectionDeletionStatus")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/vector/collections/{id}/deletionStatus"
            .replaceAll(
                "\\{" + "id" + "\\}", ApiClient.escapeString(ApiClient.parameterToString(id)));

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

    final TypeReference<VectorV1VectorEndpointsGetCollectionDeletionStatus200Response>
        localVarReturnType =
            new TypeReference<VectorV1VectorEndpointsGetCollectionDeletionStatus200Response>() {};
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
   * @param aiResourceGroup Resource Group ID
   * @param collectionId Collection ID
   * @param documentId Document ID
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
              "Missing the required parameter 'aiResourceGroup' when calling getDocumentById")
          .statusCode(400);
    }

    // verify the required parameter 'collectionId' is set
    if (collectionId == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'collectionId' when calling getDocumentById")
          .statusCode(400);
    }

    // verify the required parameter 'documentId' is set
    if (documentId == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'documentId' when calling getDocumentById")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/vector/collections/{collectionId}/documents/{documentId}"
            .replaceAll(
                "\\{" + "collectionId" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(collectionId)))
            .replaceAll(
                "\\{" + "documentId" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(documentId)));

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

    final TypeReference<DocumentResponse> localVarReturnType =
        new TypeReference<DocumentResponse>() {};
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
   * Search chunk by vector
   *
   * <p>Search chunks
   *
   * <p><b>200</b> - Successful Response
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * <p><b>404</b> - The specification of the resource was incorrect
   *
   * <p><b>422</b> - There are validation issues with the data.
   *
   * @param aiResourceGroup Resource Group ID
   * @param textSearchRequest The value for the parameter textSearchRequest
   * @return VectorSearchResults
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public VectorSearchResults search(
      @Nonnull final String aiResourceGroup, @Nonnull final TextSearchRequest textSearchRequest)
      throws OpenApiRequestException {
    final Object localVarPostBody = textSearchRequest;

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'aiResourceGroup' when calling search")
          .statusCode(400);
    }

    // verify the required parameter 'textSearchRequest' is set
    if (textSearchRequest == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'textSearchRequest' when calling vectorV1VectorEndpointsSearchChunk")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath = "/vector/search";

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

    final TypeReference<VectorSearchResults> localVarReturnType =
        new TypeReference<VectorSearchResults>() {};
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
   * @param aiResourceGroup Resource Group ID
   * @param collectionId Collection ID
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
              "Missing the required parameter 'aiResourceGroup' when calling updateDocuments")
          .statusCode(400);
    }

    // verify the required parameter 'collectionId' is set
    if (collectionId == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'collectionId' when calling updateDocuments")
          .statusCode(400);
    }

    // verify the required parameter 'documentUpdateRequest' is set
    if (documentUpdateRequest == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'documentUpdateRequest' when calling vectorV1VectorEndpointsUpdateDocuments")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/vector/collections/{collectionId}/documents"
            .replaceAll(
                "\\{" + "collectionId" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(collectionId)));

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

    final TypeReference<DocumentsListResponse> localVarReturnType =
        new TypeReference<DocumentsListResponse>() {};
    return apiClient.invokeAPI(
        localVarPath,
        "PATCH",
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
