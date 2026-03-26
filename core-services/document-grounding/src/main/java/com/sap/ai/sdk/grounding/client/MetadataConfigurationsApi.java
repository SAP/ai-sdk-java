package com.sap.ai.sdk.grounding.client;

import com.google.common.annotations.Beta;
import com.sap.ai.sdk.grounding.model.BatchUpdateDocumentsResponse;
import com.sap.ai.sdk.grounding.model.ConfigurationDocument;
import com.sap.ai.sdk.grounding.model.DocumentMetadataBatchRequest;
import com.sap.ai.sdk.grounding.model.ListConfigurationDocuments;
import com.sap.ai.sdk.grounding.model.ListMetadataConfigurations;
import com.sap.ai.sdk.grounding.model.MetadataConfigurationRequest;
import com.sap.ai.sdk.grounding.model.MetadataConfigurationResponse;
import com.sap.cloud.sdk.cloudplatform.connectivity.Destination;
import com.sap.cloud.sdk.services.openapi.apiclient.ApiClient;
import com.sap.cloud.sdk.services.openapi.core.AbstractOpenApiService;
import com.sap.cloud.sdk.services.openapi.core.OpenApiRequestException;
import com.sap.cloud.sdk.services.openapi.core.OpenApiResponse;
import java.util.HashMap;
import java.util.List;
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
 * Grounding in version 0.1.0.
 *
 * <p>Grounding is a service designed to handle data-related tasks, such as grounding and retrieval,
 * using vector databases. It provides specialized data retrieval through these databases, grounding
 * the retrieval process with your own external and context-relevant data. Grounding combines
 * generative AI capabilities with the ability to use real-time, precise data to improve
 * decision-making and business operations for specific AI-driven business solutions.
 */
public class MetadataConfigurationsApi extends AbstractOpenApiService {
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
  @Beta
  public MetadataConfigurationsApi(@Nonnull final ApiClient apiClient) {
    super(apiClient);
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
   * @return BatchUpdateDocumentsResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public BatchUpdateDocumentsResponse batchUpdateDocumentsMetadata(
      @Nonnull final String metadataConfigId,
      @Nonnull final DocumentMetadataBatchRequest documentMetadataBatchRequest)
      throws OpenApiRequestException {
    final Object localVarPostBody = documentMetadataBatchRequest;

    // verify the required parameter 'metadataConfigId' is set
    if (metadataConfigId == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'metadataConfigId' when calling batchUpdateDocumentsMetadata");
    }

    // verify the required parameter 'documentMetadataBatchRequest' is set
    if (documentMetadataBatchRequest == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'documentMetadataBatchRequest' when calling metadataManagementV1MetadataEndpointsBatchUpdateDocumentsMetadata");
    }

    // create path and map variables
    final Map<String, Object> localVarPathParams = new HashMap<String, Object>();
    localVarPathParams.put("metadataConfigId", metadataConfigId);
    final String localVarPath =
        UriComponentsBuilder.fromPath(
                "/pipelines/metadata/configurations/{metadataConfigId}/documents")
            .buildAndExpand(localVarPathParams)
            .toUriString();

    final MultiValueMap<String, String> localVarQueryParams =
        new LinkedMultiValueMap<String, String>();
    final HttpHeaders localVarHeaderParams = new HttpHeaders();
    final MultiValueMap<String, Object> localVarFormParams =
        new LinkedMultiValueMap<String, Object>();

    final String[] localVarAccepts = {"application/json"};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {"application/merge-patch+json"};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    final String[] localVarAuthNames = new String[] {};

    final ParameterizedTypeReference<BatchUpdateDocumentsResponse> localVarReturnType =
        new ParameterizedTypeReference<BatchUpdateDocumentsResponse>() {};
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
    final Object localVarPostBody = metadataConfigurationRequest;

    // verify the required parameter 'metadataConfigurationRequest' is set
    if (metadataConfigurationRequest == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'metadataConfigurationRequest' when calling metadataManagementV1MetadataEndpointsCreateMetadataConfiguration");
    }

    final String localVarPath =
        UriComponentsBuilder.fromPath("/pipelines/metadata/configurations").build().toUriString();

    final MultiValueMap<String, String> localVarQueryParams =
        new LinkedMultiValueMap<String, String>();
    final HttpHeaders localVarHeaderParams = new HttpHeaders();
    final MultiValueMap<String, Object> localVarFormParams =
        new LinkedMultiValueMap<String, Object>();

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
    final Object localVarPostBody = null;

    // verify the required parameter 'metadataConfigId' is set
    if (metadataConfigId == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'metadataConfigId' when calling deleteMetadataConfigurationById");
    }

    // create path and map variables
    final Map<String, Object> localVarPathParams = new HashMap<String, Object>();
    localVarPathParams.put("metadataConfigId", metadataConfigId);
    final String localVarPath =
        UriComponentsBuilder.fromPath("/pipelines/metadata/configurations/{metadataConfigId}")
            .buildAndExpand(localVarPathParams)
            .toUriString();

    final MultiValueMap<String, String> localVarQueryParams =
        new LinkedMultiValueMap<String, String>();
    final HttpHeaders localVarHeaderParams = new HttpHeaders();
    final MultiValueMap<String, Object> localVarFormParams =
        new LinkedMultiValueMap<String, Object>();

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
  public ConfigurationDocument getDocumentDetailsById(
      @Nonnull final String metadataConfigId, @Nonnull final String documentId)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    // verify the required parameter 'metadataConfigId' is set
    if (metadataConfigId == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'metadataConfigId' when calling getDocumentDetailsById");
    }

    // verify the required parameter 'documentId' is set
    if (documentId == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'documentId' when calling getDocumentDetailsById");
    }

    // create path and map variables
    final Map<String, Object> localVarPathParams = new HashMap<String, Object>();
    localVarPathParams.put("metadataConfigId", metadataConfigId);
    localVarPathParams.put("documentId", documentId);
    final String localVarPath =
        UriComponentsBuilder.fromPath(
                "/pipelines/metadata/configurations/{metadataConfigId}/documents/{documentId}")
            .buildAndExpand(localVarPathParams)
            .toUriString();

    final MultiValueMap<String, String> localVarQueryParams =
        new LinkedMultiValueMap<String, String>();
    final HttpHeaders localVarHeaderParams = new HttpHeaders();
    final MultiValueMap<String, Object> localVarFormParams =
        new LinkedMultiValueMap<String, Object>();

    final String[] localVarAccepts = {"application/json"};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    final String[] localVarAuthNames = new String[] {};

    final ParameterizedTypeReference<ConfigurationDocument> localVarReturnType =
        new ParameterizedTypeReference<ConfigurationDocument>() {};
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
    final Object localVarPostBody = null;

    // verify the required parameter 'metadataConfigId' is set
    if (metadataConfigId == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'metadataConfigId' when calling getMetadataConfigurationById");
    }

    // create path and map variables
    final Map<String, Object> localVarPathParams = new HashMap<String, Object>();
    localVarPathParams.put("metadataConfigId", metadataConfigId);
    final String localVarPath =
        UriComponentsBuilder.fromPath("/pipelines/metadata/configurations/{metadataConfigId}")
            .buildAndExpand(localVarPathParams)
            .toUriString();

    final MultiValueMap<String, String> localVarQueryParams =
        new LinkedMultiValueMap<String, String>();
    final HttpHeaders localVarHeaderParams = new HttpHeaders();
    final MultiValueMap<String, Object> localVarFormParams =
        new LinkedMultiValueMap<String, Object>();

    final String[] localVarAccepts = {"application/json"};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    final String[] localVarAuthNames = new String[] {};

    final ParameterizedTypeReference<MetadataConfigurationResponse> localVarReturnType =
        new ParameterizedTypeReference<MetadataConfigurationResponse>() {};
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
    final Object localVarPostBody = null;

    // verify the required parameter 'metadataConfigId' is set
    if (metadataConfigId == null) {
      throw new OpenApiRequestException(
          "Missing the required parameter 'metadataConfigId' when calling listMetadataConfigurationDocuments");
    }

    // create path and map variables
    final Map<String, Object> localVarPathParams = new HashMap<String, Object>();
    localVarPathParams.put("metadataConfigId", metadataConfigId);
    final String localVarPath =
        UriComponentsBuilder.fromPath(
                "/pipelines/metadata/configurations/{metadataConfigId}/documents")
            .buildAndExpand(localVarPathParams)
            .toUriString();

    final MultiValueMap<String, String> localVarQueryParams =
        new LinkedMultiValueMap<String, String>();
    final HttpHeaders localVarHeaderParams = new HttpHeaders();
    final MultiValueMap<String, Object> localVarFormParams =
        new LinkedMultiValueMap<String, Object>();

    localVarQueryParams.putAll(
        apiClient.parameterToMultiValueMap(null, "absolutePath", absolutePath));
    localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "$top", $top));
    localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "$skip", $skip));
    localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "$count", $count));

    final String[] localVarAccepts = {"application/json"};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    final String[] localVarAuthNames = new String[] {};

    final ParameterizedTypeReference<ListConfigurationDocuments> localVarReturnType =
        new ParameterizedTypeReference<ListConfigurationDocuments>() {};
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
    final Object localVarPostBody = null;

    final String localVarPath =
        UriComponentsBuilder.fromPath("/pipelines/metadata/configurations").build().toUriString();

    final MultiValueMap<String, String> localVarQueryParams =
        new LinkedMultiValueMap<String, String>();
    final HttpHeaders localVarHeaderParams = new HttpHeaders();
    final MultiValueMap<String, Object> localVarFormParams =
        new LinkedMultiValueMap<String, Object>();

    localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "$top", $top));
    localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "$skip", $skip));
    localVarQueryParams.putAll(apiClient.parameterToMultiValueMap(null, "$count", $count));

    final String[] localVarAccepts = {"application/json"};
    final List<MediaType> localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};
    final MediaType localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    final String[] localVarAuthNames = new String[] {};

    final ParameterizedTypeReference<ListMetadataConfigurations> localVarReturnType =
        new ParameterizedTypeReference<ListMetadataConfigurations>() {};
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
