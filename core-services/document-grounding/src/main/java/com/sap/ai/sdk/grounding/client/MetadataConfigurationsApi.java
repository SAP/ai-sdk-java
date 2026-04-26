package com.sap.ai.sdk.grounding.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.sap.ai.sdk.grounding.model.BatchUpdateDocumentsResponseInner;
import com.sap.ai.sdk.grounding.model.ConfigurationDocument;
import com.sap.ai.sdk.grounding.model.DocumentMetadataBatchRequest;
import com.sap.ai.sdk.grounding.model.ListConfigurationDocuments;
import com.sap.ai.sdk.grounding.model.ListMetadataConfigurations;
import com.sap.ai.sdk.grounding.model.MetadataConfigurationRequest;
import com.sap.ai.sdk.grounding.model.MetadataConfigurationResponse;
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
 * Grounding in version 0.1.0.
 *
 * <p>Grounding is a service designed to handle data-related tasks, such as grounding and retrieval,
 * using vector databases. It provides specialized data retrieval through these databases, grounding
 * the retrieval process with your own external and context-relevant data. Grounding combines
 * generative AI capabilities with the ability to use real-time, precise data to improve
 * decision-making and business operations for specific AI-driven business solutions.
 */
public class MetadataConfigurationsApi extends BaseApi {

  /**
   * Instantiates this API class to invoke operations on the Grounding.
   *
   * @param httpDestination The destination that API should be used with
   */
  public MetadataConfigurationsApi(@Nonnull final Destination httpDestination) {
    super(httpDestination);
  }

  /**
   * Instantiates this API class to invoke operations on the Grounding based on a given {@link
   * ApiClient}.
   *
   * @param apiClient ApiClient to invoke the API on
   */
  public MetadataConfigurationsApi(@Nonnull final ApiClient apiClient) {
    super(apiClient);
  }

  /**
   * Creates a new API instance with additional default headers.
   *
   * @param defaultHeaders Additional headers to include in all requests
   * @return A new API instance with the combined headers
   */
  public MetadataConfigurationsApi withDefaultHeaders(
      @Nonnull final Map<String, String> defaultHeaders) {
    final var api = new MetadataConfigurationsApi(apiClient);
    api.defaultHeaders.putAll(this.defaultHeaders);
    api.defaultHeaders.putAll(defaultHeaders);
    return api;
  }

  /**
   * Batch update metadata for multiple documents
   *
   * <p>Patch the documents of a configuration in batch
   *
   * <p><b>200</b> - Batch patch applied successfully
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * @param metadataConfigId Metadata Configuration ID
   * @param documentMetadataBatchRequest The value for the parameter documentMetadataBatchRequest
   * @return List&lt;BatchUpdateDocumentsResponseInner&gt;
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public List<BatchUpdateDocumentsResponseInner> batchUpdateDocumentsMetadata(
      @Nonnull final String metadataConfigId,
      @Nonnull final DocumentMetadataBatchRequest documentMetadataBatchRequest)
      throws OpenApiRequestException {

    // verify the required parameter 'metadataConfigId' is set
    if (metadataConfigId == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'metadataConfigId' when calling batchUpdateDocumentsMetadata")
          .statusCode(400);
    }

    // verify the required parameter 'documentMetadataBatchRequest' is set
    if (documentMetadataBatchRequest == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'documentMetadataBatchRequest' when calling metadataManagementV1MetadataEndpointsBatchUpdateDocumentsMetadata")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/pipelines/metadata/configurations/{metadataConfigId}/documents"
            .replaceAll(
                "\\{" + "metadataConfigId" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(metadataConfigId)));

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>(defaultHeaders);
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {"application/merge-patch+json"};
    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<List<BatchUpdateDocumentsResponseInner>> localVarReturnType =
        new TypeReference<List<BatchUpdateDocumentsResponseInner>>() {};

    return apiClient.invokeAPI(
        localVarPath,
        "PATCH",
        localVarQueryParams,
        localVarCollectionQueryParams,
        localVarQueryStringJoiner.toString(),
        documentMetadataBatchRequest,
        localVarHeaderParams,
        localVarFormParams,
        localVarAccept,
        localVarContentType,
        localVarReturnType);
  }

  /**
   * Create a metadata configuration
   *
   * <p>Creates a new metadata configuration.
   *
   * <p><b>202</b> - Accepted - Document enumeration in progress
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * @param metadataConfigurationRequest The value for the parameter metadataConfigurationRequest
   * @return An OpenApiResponse containing the status code of the HttpResponse.
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public OpenApiResponse createMetadataConfiguration(
      @Nonnull final MetadataConfigurationRequest metadataConfigurationRequest)
      throws OpenApiRequestException {

    // verify the required parameter 'metadataConfigurationRequest' is set
    if (metadataConfigurationRequest == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'metadataConfigurationRequest' when calling metadataManagementV1MetadataEndpointsCreateMetadataConfiguration")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath = "/pipelines/metadata/configurations";

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>(defaultHeaders);
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

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
        metadataConfigurationRequest,
        localVarHeaderParams,
        localVarFormParams,
        localVarAccept,
        localVarContentType,
        localVarReturnType);
  }

  /**
   * Delete a configuration by ID
   *
   * <p>Delete a metadata configuration by ID
   *
   * <p><b>202</b> - Configuration deletion initiated
   *
   * <p><b>404</b> - Configuration not found
   *
   * <p><b>500</b> - Internal server error during deletion of configuration
   *
   * <p><b>409</b> - Pipelines exists for this configuration. Delete the pipelines before deleting
   * the configuration.
   *
   * @param metadataConfigId Metadata Configuration ID
   * @return An OpenApiResponse containing the status code of the HttpResponse.
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public OpenApiResponse deleteMetadataConfigurationById(@Nonnull final String metadataConfigId)
      throws OpenApiRequestException {

    // verify the required parameter 'metadataConfigId' is set
    if (metadataConfigId == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'metadataConfigId' when calling deleteMetadataConfigurationById")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/pipelines/metadata/configurations/{metadataConfigId}"
            .replaceAll(
                "\\{" + "metadataConfigId" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(metadataConfigId)));

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>(defaultHeaders);
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

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
   * Get document details
   *
   * <p>Get the details of a document by document id
   *
   * <p><b>200</b> - Document details
   *
   * <p><b>404</b> - Document or configuration not found
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * @param metadataConfigId Metadata Configuration ID
   * @param documentId Document ID
   * @return ConfigurationDocument
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public ConfigurationDocument getMetadataDocumentDetails(
      @Nonnull final String metadataConfigId, @Nonnull final String documentId)
      throws OpenApiRequestException {

    // verify the required parameter 'metadataConfigId' is set
    if (metadataConfigId == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'metadataConfigId' when calling getMetadataDocumentDetails")
          .statusCode(400);
    }

    // verify the required parameter 'documentId' is set
    if (documentId == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'documentId' when calling getMetadataDocumentDetails")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/pipelines/metadata/configurations/{metadataConfigId}/documents/{documentId}"
            .replaceAll(
                "\\{" + "metadataConfigId" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(metadataConfigId)))
            .replaceAll(
                "\\{" + "documentId" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(documentId)));

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>(defaultHeaders);
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};

    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<ConfigurationDocument> localVarReturnType =
        new TypeReference<ConfigurationDocument>() {};

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
   * Get a configuration by ID
   *
   * <p>Get the details of a configuration by ID
   *
   * <p><b>200</b> - Configuration found
   *
   * <p><b>404</b> - Configuration not found
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * @param metadataConfigId Metadata Configuration ID
   * @return MetadataConfigurationResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public MetadataConfigurationResponse getMetadataConfigurationById(
      @Nonnull final String metadataConfigId) throws OpenApiRequestException {

    // verify the required parameter 'metadataConfigId' is set
    if (metadataConfigId == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'metadataConfigId' when calling getMetadataConfigurationById")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/pipelines/metadata/configurations/{metadataConfigId}"
            .replaceAll(
                "\\{" + "metadataConfigId" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(metadataConfigId)));

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>(defaultHeaders);
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};

    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<MetadataConfigurationResponse> localVarReturnType =
        new TypeReference<MetadataConfigurationResponse>() {};

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
   * List documents of a configuration
   *
   * <p>List the documents for a configuration
   *
   * <p><b>200</b> - List of documents
   *
   * @param metadataConfigId (required) Metadata Configuration ID
   * @param absolutePath (optional) Absolute path of the resource. Supports wildcard values (e.g.,
   *     &#x60;/folder/_*&#x60;).
   * @param $top (optional) Number of results to display
   * @param $skip (optional) Number of results to be skipped from the ordered list of results
   * @param $count (optional) When the $count field is set to false, the response contains a count
   *     of the items present in the response. When the $count field is set to true, the response
   *     contains a count of all the items present on the server, and not just the ones in the
   *     response. When the $count field is not passed, it is false by default.
   * @return ListConfigurationDocuments
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public ListConfigurationDocuments listMetadataConfigurationDocuments(
      @Nonnull final String metadataConfigId,
      @Nullable final String absolutePath,
      @Nullable final Integer $top,
      @Nullable final Integer $skip,
      @Nullable final Boolean $count)
      throws OpenApiRequestException {

    // verify the required parameter 'metadataConfigId' is set
    if (metadataConfigId == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'metadataConfigId' when calling listMetadataConfigurationDocuments")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/pipelines/metadata/configurations/{metadataConfigId}/documents"
            .replaceAll(
                "\\{" + "metadataConfigId" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(metadataConfigId)));

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>(defaultHeaders);
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(ApiClient.parameterToPair("absolutePath", absolutePath));
    localVarQueryParams.addAll(ApiClient.parameterToPair("$top", $top));
    localVarQueryParams.addAll(ApiClient.parameterToPair("$skip", $skip));
    localVarQueryParams.addAll(ApiClient.parameterToPair("$count", $count));

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};

    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<ListConfigurationDocuments> localVarReturnType =
        new TypeReference<ListConfigurationDocuments>() {};

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
   * List documents of a configuration
   *
   * <p>List the documents for a configuration
   *
   * <p><b>200</b> - List of documents
   *
   * @param metadataConfigId Metadata Configuration ID
   * @return ListConfigurationDocuments
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public ListConfigurationDocuments listMetadataConfigurationDocuments(
      @Nonnull final String metadataConfigId) throws OpenApiRequestException {
    return listMetadataConfigurationDocuments(metadataConfigId, null, null, null, null);
  }

  /**
   * List metadata configurations
   *
   * <p>List all metadata configurations
   *
   * <p><b>200</b> - Paginated list of configurations
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * @param $top (optional) Number of results to display
   * @param $skip (optional) Number of results to be skipped from the ordered list of results
   * @param $count (optional) When the $count field is set to false, the response contains a count
   *     of the items present in the response. When the $count field is set to true, the response
   *     contains a count of all the items present on the server, and not just the ones in the
   *     response. When the $count field is not passed, it is false by default.
   * @return ListMetadataConfigurations
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public ListMetadataConfigurations listMetadataConfigurations(
      @Nullable final Integer $top, @Nullable final Integer $skip, @Nullable final Boolean $count)
      throws OpenApiRequestException {

    // create path and map variables
    final String localVarPath = "/pipelines/metadata/configurations";

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>(defaultHeaders);
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(ApiClient.parameterToPair("$top", $top));
    localVarQueryParams.addAll(ApiClient.parameterToPair("$skip", $skip));
    localVarQueryParams.addAll(ApiClient.parameterToPair("$count", $count));

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};

    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<ListMetadataConfigurations> localVarReturnType =
        new TypeReference<ListMetadataConfigurations>() {};

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
   * List metadata configurations
   *
   * <p>List all metadata configurations
   *
   * <p><b>200</b> - Paginated list of configurations
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * @return ListMetadataConfigurations
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public ListMetadataConfigurations listMetadataConfigurations() throws OpenApiRequestException {
    return listMetadataConfigurations(null, null, null);
  }
}
