package com.sap.ai.sdk.grounding.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.sap.ai.sdk.grounding.model.CreatePipeline;
import com.sap.ai.sdk.grounding.model.DocumentsStatusResponse;
import com.sap.ai.sdk.grounding.model.GetPipeline;
import com.sap.ai.sdk.grounding.model.GetPipelineExecutionById;
import com.sap.ai.sdk.grounding.model.GetPipelineExecutions;
import com.sap.ai.sdk.grounding.model.GetPipelineStatus;
import com.sap.ai.sdk.grounding.model.GetPipelines;
import com.sap.ai.sdk.grounding.model.ManualPipelineTrigger;
import com.sap.ai.sdk.grounding.model.PatchPipeline;
import com.sap.ai.sdk.grounding.model.PipelineDocumentResponse;
import com.sap.ai.sdk.grounding.model.PipelineId;
import com.sap.ai.sdk.grounding.model.SearchPipeline;
import com.sap.ai.sdk.grounding.model.SearchPipelinesResponse;
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
public class PipelinesApi extends BaseApi {

  /** Instantiates this API class to invoke operations on the Grounding */
  public PipelinesApi() {}

  /**
   * Instantiates this API class to invoke operations on the Grounding.
   *
   * @param httpDestination The destination that API should be used with
   */
  public PipelinesApi(@Nonnull final Destination httpDestination) {
    super(httpDestination);
  }

  /**
   * Instantiates this API class to invoke operations on the Grounding based on a given {@link
   * ApiClient}.
   *
   * @param apiClient ApiClient to invoke the API on
   */
  public PipelinesApi(@Nonnull final ApiClient apiClient) {
    super(apiClient);
  }

  /**
   * Pipeline Creation
   *
   * <p>Create a pipeline
   *
   * <p><b>201</b> - Returns pipelineId on successful creation.
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * @param aiResourceGroup Resource Group ID
   * @param createPipeline The value for the parameter createPipeline
   * @return PipelineId
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public PipelineId createPipeline(
      @Nonnull final String aiResourceGroup, @Nonnull final CreatePipeline createPipeline)
      throws OpenApiRequestException {
    final Object localVarPostBody = createPipeline;

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'aiResourceGroup' when calling createPipeline")
          .statusCode(400);
    }

    // verify the required parameter 'createPipeline' is set
    if (createPipeline == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'createPipeline' when calling pipelineV1PipelineEndpointsCreatePipeline")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath = "/pipelines";

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

    final TypeReference<PipelineId> localVarReturnType = new TypeReference<PipelineId>() {};

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
   * Delete pipeline
   *
   * <p>Delete a pipeline by pipeline id
   *
   * <p><b>204</b> - No Content
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * @param aiResourceGroup Resource Group ID
   * @param pipelineId The ID of the pipeline to delete.
   * @return An OpenApiResponse containing the status code of the HttpResponse.
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public OpenApiResponse deletePipelineById(
      @Nonnull final String aiResourceGroup, @Nonnull final String pipelineId)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'aiResourceGroup' when calling deletePipelineById")
          .statusCode(400);
    }

    // verify the required parameter 'pipelineId' is set
    if (pipelineId == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'pipelineId' when calling deletePipelineById")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/pipelines/{pipelineId}"
            .replaceAll(
                "\\{" + "pipelineId" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(pipelineId)));

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
   * Get Pipelines
   *
   * <p>Get all pipelines
   *
   * <p><b>200</b> - Returns all pipelines for the tenant.
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
   * @return GetPipelines
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public GetPipelines getAllPipelines(
      @Nonnull final String aiResourceGroup,
      @Nullable final Integer $top,
      @Nullable final Integer $skip,
      @Nullable final Boolean $count)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'aiResourceGroup' when calling getAllPipelines")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath = "/pipelines";

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

    final TypeReference<GetPipelines> localVarReturnType = new TypeReference<GetPipelines>() {};

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
   * Get Pipelines
   *
   * <p>Get all pipelines
   *
   * <p><b>200</b> - Returns all pipelines for the tenant.
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * @param aiResourceGroup Resource Group ID
   * @return GetPipelines
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public GetPipelines getAllPipelines(@Nonnull final String aiResourceGroup)
      throws OpenApiRequestException {
    return getAllPipelines(aiResourceGroup, null, null, null);
  }

  /**
   * Get details of a Pipeline
   *
   * <p>Get details of a pipeline by pipeline id
   *
   * <p><b>200</b> - Returns the pipeline for an pipelineId
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * @param aiResourceGroup Resource Group ID
   * @param pipelineId The ID of the pipeline to get.
   * @return GetPipeline
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public GetPipeline getPipelineById(
      @Nonnull final String aiResourceGroup, @Nonnull final String pipelineId)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'aiResourceGroup' when calling getPipelineById")
          .statusCode(400);
    }

    // verify the required parameter 'pipelineId' is set
    if (pipelineId == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'pipelineId' when calling getPipelineById")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/pipelines/{pipelineId}"
            .replaceAll(
                "\\{" + "pipelineId" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(pipelineId)));

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

    final TypeReference<GetPipeline> localVarReturnType = new TypeReference<GetPipeline>() {};

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
   * Get Document by ID for a Pipeline
   *
   * <p>Retrieve details of a specific document associated with a pipeline.
   *
   * <p><b>200</b> - Returns a response to document id.
   *
   * @param aiResourceGroup Resource Group ID
   * @param pipelineId The ID of the pipeline to get.
   * @param documentId The ID of the document to get.
   * @return PipelineDocumentResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public PipelineDocumentResponse getDocumentByIdForPipeline(
      @Nonnull final String aiResourceGroup,
      @Nonnull final String pipelineId,
      @Nonnull final String documentId)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'aiResourceGroup' when calling getDocumentByIdForPipeline")
          .statusCode(400);
    }

    // verify the required parameter 'pipelineId' is set
    if (pipelineId == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'pipelineId' when calling getDocumentByIdForPipeline")
          .statusCode(400);
    }

    // verify the required parameter 'documentId' is set
    if (documentId == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'documentId' when calling getDocumentByIdForPipeline")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/pipelines/{pipelineId}/documents/{documentId}"
            .replaceAll(
                "\\{" + "pipelineId" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(pipelineId)))
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

    final TypeReference<PipelineDocumentResponse> localVarReturnType =
        new TypeReference<PipelineDocumentResponse>() {};

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
   * Get Documents for a Pipeline
   *
   * <p>Retrieve all documents associated with a specific pipeline. Optionally, filter the results
   * using query parameters.
   *
   * <p><b>200</b> - Returns all documents for a pipeline.
   *
   * @param aiResourceGroup (required) Resource Group ID
   * @param pipelineId (required) The ID of the pipeline to get.
   * @param $top (optional) Number of results to display
   * @param $skip (optional) Number of results to be skipped from the ordered list of results
   * @param $count (optional) When the $count field is set to false, the response contains a count
   *     of the items present in the response. When the $count field is set to true, the response
   *     contains a count of all the items present on the server, and not just the ones in the
   *     response. When the $count field is not passed, it is false by default.
   * @return DocumentsStatusResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public DocumentsStatusResponse getAllDocumentsForPipeline(
      @Nonnull final String aiResourceGroup,
      @Nonnull final String pipelineId,
      @Nullable final Integer $top,
      @Nullable final Integer $skip,
      @Nullable final Boolean $count)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'aiResourceGroup' when calling getAllDocumentsForPipeline")
          .statusCode(400);
    }

    // verify the required parameter 'pipelineId' is set
    if (pipelineId == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'pipelineId' when calling getAllDocumentsForPipeline")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/pipelines/{pipelineId}/documents"
            .replaceAll(
                "\\{" + "pipelineId" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(pipelineId)));

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

    final TypeReference<DocumentsStatusResponse> localVarReturnType =
        new TypeReference<DocumentsStatusResponse>() {};

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
   * Get Documents for a Pipeline
   *
   * <p>Retrieve all documents associated with a specific pipeline. Optionally, filter the results
   * using query parameters.
   *
   * <p><b>200</b> - Returns all documents for a pipeline.
   *
   * @param aiResourceGroup Resource Group ID
   * @param pipelineId The ID of the pipeline to get.
   * @return DocumentsStatusResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public DocumentsStatusResponse getAllDocumentsForPipeline(
      @Nonnull final String aiResourceGroup, @Nonnull final String pipelineId)
      throws OpenApiRequestException {
    return getAllDocumentsForPipeline(aiResourceGroup, pipelineId, null, null, null);
  }

  /**
   * Get Pipeline Execution by ID
   *
   * <p>Retrieve details of a specific pipeline execution by its execution ID.
   *
   * <p><b>200</b> - Returns a response to execution id
   *
   * @param aiResourceGroup Resource Group ID
   * @param pipelineId The ID of the pipeline
   * @param executionId The ID of the execution
   * @return GetPipelineExecutionById
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public GetPipelineExecutionById getExecutionDetailsByIdForPipelineExecution(
      @Nonnull final String aiResourceGroup,
      @Nonnull final String pipelineId,
      @Nonnull final String executionId)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'aiResourceGroup' when calling getExecutionDetailsByIdForPipelineExecution")
          .statusCode(400);
    }

    // verify the required parameter 'pipelineId' is set
    if (pipelineId == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'pipelineId' when calling getExecutionDetailsByIdForPipelineExecution")
          .statusCode(400);
    }

    // verify the required parameter 'executionId' is set
    if (executionId == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'executionId' when calling getExecutionDetailsByIdForPipelineExecution")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/pipelines/{pipelineId}/executions/{executionId}"
            .replaceAll(
                "\\{" + "pipelineId" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(pipelineId)))
            .replaceAll(
                "\\{" + "executionId" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(executionId)));

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

    final TypeReference<GetPipelineExecutionById> localVarReturnType =
        new TypeReference<GetPipelineExecutionById>() {};

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
   * Get Document by ID for a Pipeline Execution
   *
   * <p>Retrieve details of a specific document associated with a pipeline execution.
   *
   * <p><b>200</b> - Returns a response to execution id and document id.
   *
   * @param aiResourceGroup Resource Group ID
   * @param pipelineId The ID of the pipeline
   * @param executionId The ID of the execution
   * @param documentId The ID of the document to get.
   * @return PipelineDocumentResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public PipelineDocumentResponse getDocumentByIdForPipelineExecution(
      @Nonnull final String aiResourceGroup,
      @Nonnull final String pipelineId,
      @Nonnull final String executionId,
      @Nonnull final String documentId)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'aiResourceGroup' when calling getDocumentByIdForPipelineExecution")
          .statusCode(400);
    }

    // verify the required parameter 'pipelineId' is set
    if (pipelineId == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'pipelineId' when calling getDocumentByIdForPipelineExecution")
          .statusCode(400);
    }

    // verify the required parameter 'executionId' is set
    if (executionId == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'executionId' when calling getDocumentByIdForPipelineExecution")
          .statusCode(400);
    }

    // verify the required parameter 'documentId' is set
    if (documentId == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'documentId' when calling getDocumentByIdForPipelineExecution")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/pipelines/{pipelineId}/executions/{executionId}/documents/{documentId}"
            .replaceAll(
                "\\{" + "pipelineId" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(pipelineId)))
            .replaceAll(
                "\\{" + "executionId" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(executionId)))
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

    final TypeReference<PipelineDocumentResponse> localVarReturnType =
        new TypeReference<PipelineDocumentResponse>() {};

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
   * Get Documents for a Pipeline Execution
   *
   * <p>Retrieve all documents associated with a specific pipeline execution. Optionally, filter the
   * results using query parameters.
   *
   * <p><b>200</b> - Returns a response to execution id
   *
   * @param aiResourceGroup (required) Resource Group ID
   * @param pipelineId (required) The ID of the pipeline
   * @param executionId (required) The ID of the execution
   * @param $top (optional) Number of results to display
   * @param $skip (optional) Number of results to be skipped from the ordered list of results
   * @param $count (optional) When the $count field is set to false, the response contains a count
   *     of the items present in the response. When the $count field is set to true, the response
   *     contains a count of all the items present on the server, and not just the ones in the
   *     response. When the $count field is not passed, it is false by default.
   * @return DocumentsStatusResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public DocumentsStatusResponse getDocumentsForPipelineExecution(
      @Nonnull final String aiResourceGroup,
      @Nonnull final String pipelineId,
      @Nonnull final String executionId,
      @Nullable final Integer $top,
      @Nullable final Integer $skip,
      @Nullable final Boolean $count)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'aiResourceGroup' when calling getDocumentsForPipelineExecution")
          .statusCode(400);
    }

    // verify the required parameter 'pipelineId' is set
    if (pipelineId == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'pipelineId' when calling getDocumentsForPipelineExecution")
          .statusCode(400);
    }

    // verify the required parameter 'executionId' is set
    if (executionId == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'executionId' when calling getDocumentsForPipelineExecution")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/pipelines/{pipelineId}/executions/{executionId}/documents"
            .replaceAll(
                "\\{" + "pipelineId" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(pipelineId)))
            .replaceAll(
                "\\{" + "executionId" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(executionId)));

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

    final TypeReference<DocumentsStatusResponse> localVarReturnType =
        new TypeReference<DocumentsStatusResponse>() {};

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
   * Get Documents for a Pipeline Execution
   *
   * <p>Retrieve all documents associated with a specific pipeline execution. Optionally, filter the
   * results using query parameters.
   *
   * <p><b>200</b> - Returns a response to execution id
   *
   * @param aiResourceGroup Resource Group ID
   * @param pipelineId The ID of the pipeline
   * @param executionId The ID of the execution
   * @return DocumentsStatusResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public DocumentsStatusResponse getDocumentsForPipelineExecution(
      @Nonnull final String aiResourceGroup,
      @Nonnull final String pipelineId,
      @Nonnull final String executionId)
      throws OpenApiRequestException {
    return getDocumentsForPipelineExecution(
        aiResourceGroup, pipelineId, executionId, null, null, null);
  }

  /**
   * Get Pipeline Executions
   *
   * <p>Retrieve all executions for a specific pipeline. Optionally, filter to get only the last
   * execution.
   *
   * <p><b>200</b> - Returns all executions
   *
   * @param aiResourceGroup (required) Resource Group ID
   * @param pipelineId (required) The ID of the pipeline
   * @param lastExecution (optional) Filter to get the last execution
   * @param $top (optional) Number of results to display
   * @param $skip (optional) Number of results to be skipped from the ordered list of results
   * @param $count (optional) When the $count field is set to false, the response contains a count
   *     of the items present in the response. When the $count field is set to true, the response
   *     contains a count of all the items present on the server, and not just the ones in the
   *     response. When the $count field is not passed, it is false by default.
   * @return GetPipelineExecutions
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public GetPipelineExecutions getAllExecutionsForPipeline(
      @Nonnull final String aiResourceGroup,
      @Nonnull final String pipelineId,
      @Nullable final Boolean lastExecution,
      @Nullable final Integer $top,
      @Nullable final Integer $skip,
      @Nullable final Boolean $count)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'aiResourceGroup' when calling getAllExecutionsForPipeline")
          .statusCode(400);
    }

    // verify the required parameter 'pipelineId' is set
    if (pipelineId == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'pipelineId' when calling getAllExecutionsForPipeline")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/pipelines/{pipelineId}/executions"
            .replaceAll(
                "\\{" + "pipelineId" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(pipelineId)));

    final StringJoiner localVarQueryStringJoiner = new StringJoiner("&");
    String localVarQueryParameterBaseName;
    final List<Pair> localVarQueryParams = new ArrayList<Pair>();
    final List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
    final Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    final Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(ApiClient.parameterToPair("lastExecution", lastExecution));
    localVarQueryParams.addAll(ApiClient.parameterToPair("$top", $top));
    localVarQueryParams.addAll(ApiClient.parameterToPair("$skip", $skip));
    localVarQueryParams.addAll(ApiClient.parameterToPair("$count", $count));
    if (aiResourceGroup != null)
      localVarHeaderParams.put("AI-Resource-Group", ApiClient.parameterToString(aiResourceGroup));

    final String[] localVarAccepts = {"application/json"};
    final String localVarAccept = ApiClient.selectHeaderAccept(localVarAccepts);
    final String[] localVarContentTypes = {};

    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<GetPipelineExecutions> localVarReturnType =
        new TypeReference<GetPipelineExecutions>() {};

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
   * Get Pipeline Executions
   *
   * <p>Retrieve all executions for a specific pipeline. Optionally, filter to get only the last
   * execution.
   *
   * <p><b>200</b> - Returns all executions
   *
   * @param aiResourceGroup Resource Group ID
   * @param pipelineId The ID of the pipeline
   * @return GetPipelineExecutions
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public GetPipelineExecutions getAllExecutionsForPipeline(
      @Nonnull final String aiResourceGroup, @Nonnull final String pipelineId)
      throws OpenApiRequestException {
    return getAllExecutionsForPipeline(aiResourceGroup, pipelineId, null, null, null, null);
  }

  /**
   * Get status of Pipeline
   *
   * <p>Get pipeline status by pipeline id
   *
   * <p><b>200</b> - Returns the pipeline status for an pipelineId.
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * @param aiResourceGroup Resource Group ID
   * @param pipelineId The ID of the pipeline to get status.
   * @return GetPipelineStatus
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public GetPipelineStatus getPipelineStatus(
      @Nonnull final String aiResourceGroup, @Nonnull final String pipelineId)
      throws OpenApiRequestException {
    final Object localVarPostBody = null;

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'aiResourceGroup' when calling getPipelineStatus")
          .statusCode(400);
    }

    // verify the required parameter 'pipelineId' is set
    if (pipelineId == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'pipelineId' when calling getPipelineStatus")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/pipelines/{pipelineId}/status"
            .replaceAll(
                "\\{" + "pipelineId" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(pipelineId)));

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

    final TypeReference<GetPipelineStatus> localVarReturnType =
        new TypeReference<GetPipelineStatus>() {};

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
   * Patch pipeline
   *
   * <p>Patch a pipeline by pipeline id
   *
   * <p><b>204</b> - No Content
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * @param aiResourceGroup Resource Group ID
   * @param pipelineId The ID of the pipeline to patch.
   * @param patchPipeline The value for the parameter patchPipeline
   * @return An OpenApiResponse containing the status code of the HttpResponse.
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public OpenApiResponse patchPipelineById(
      @Nonnull final String aiResourceGroup,
      @Nonnull final String pipelineId,
      @Nonnull final PatchPipeline patchPipeline)
      throws OpenApiRequestException {
    final Object localVarPostBody = patchPipeline;

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'aiResourceGroup' when calling patchPipelineById")
          .statusCode(400);
    }

    // verify the required parameter 'pipelineId' is set
    if (pipelineId == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'pipelineId' when calling patchPipelineById")
          .statusCode(400);
    }

    // verify the required parameter 'patchPipeline' is set
    if (patchPipeline == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'patchPipeline' when calling pipelineV1PipelineEndpointsPatchPipelineById")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath =
        "/pipelines/{pipelineId}"
            .replaceAll(
                "\\{" + "pipelineId" + "\\}",
                ApiClient.escapeString(ApiClient.parameterToString(pipelineId)));

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

  /**
   * Pipeline Search by Metadata
   *
   * <p>Search for pipelines based on metadata
   *
   * <p><b>200</b> - Returns pipelines for the tenant that matches the metadata
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * @param aiResourceGroup (required) Resource Group ID
   * @param searchPipeline (required) The value for the parameter searchPipeline
   * @param $top (optional) Number of results to display
   * @param $skip (optional) Number of results to be skipped from the ordered list of results
   * @param $count (optional) When the $count field is set to false, the response contains a count
   *     of the items present in the response. When the $count field is set to true, the response
   *     contains a count of all the items present on the server, and not just the ones in the
   *     response. When the $count field is not passed, it is false by default.
   * @return SearchPipelinesResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public SearchPipelinesResponse searchPipelinesByMetadata(
      @Nonnull final String aiResourceGroup,
      @Nonnull final SearchPipeline searchPipeline,
      @Nullable final Integer $top,
      @Nullable final Integer $skip,
      @Nullable final Boolean $count)
      throws OpenApiRequestException {
    final Object localVarPostBody = searchPipeline;

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'aiResourceGroup' when calling searchPipelinesByMetadata")
          .statusCode(400);
    }

    // verify the required parameter 'searchPipeline' is set
    if (searchPipeline == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'searchPipeline' when calling pipelineV1PipelineEndpointsSearchPipeline")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath = "/pipelines/search";

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
    final String[] localVarContentTypes = {"application/json"};
    final String localVarContentType = ApiClient.selectHeaderContentType(localVarContentTypes);

    final TypeReference<SearchPipelinesResponse> localVarReturnType =
        new TypeReference<SearchPipelinesResponse>() {};

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
   * Pipeline Search by Metadata
   *
   * <p>Search for pipelines based on metadata
   *
   * <p><b>200</b> - Returns pipelines for the tenant that matches the metadata
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * @param aiResourceGroup Resource Group ID
   * @param searchPipeline The value for the parameter searchPipeline
   * @return SearchPipelinesResponse
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public SearchPipelinesResponse searchPipelinesByMetadata(
      @Nonnull final String aiResourceGroup, @Nonnull final SearchPipeline searchPipeline)
      throws OpenApiRequestException {
    return searchPipelinesByMetadata(aiResourceGroup, searchPipeline, null, null, null);
  }

  /**
   * Pipeline Trigger
   *
   * <p>Manually trigger a pipeline
   *
   * <p><b>202</b> - Accepted
   *
   * <p><b>400</b> - The specification of the resource was incorrect
   *
   * @param aiResourceGroup Resource Group ID
   * @param manualPipelineTrigger The value for the parameter manualPipelineTrigger
   * @return An OpenApiResponse containing the status code of the HttpResponse.
   * @throws OpenApiRequestException if an error occurs while attempting to invoke the API
   */
  @Nonnull
  public OpenApiResponse manualTriggerPipeline(
      @Nonnull final String aiResourceGroup,
      @Nonnull final ManualPipelineTrigger manualPipelineTrigger)
      throws OpenApiRequestException {
    final Object localVarPostBody = manualPipelineTrigger;

    // verify the required parameter 'aiResourceGroup' is set
    if (aiResourceGroup == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'aiResourceGroup' when calling manualTriggerPipeline")
          .statusCode(400);
    }

    // verify the required parameter 'manualPipelineTrigger' is set
    if (manualPipelineTrigger == null) {
      throw new OpenApiRequestException(
              "Missing the required parameter 'manualPipelineTrigger' when calling pipelineV1PipelineEndpointsTriggerPipeline")
          .statusCode(400);
    }

    // create path and map variables
    final String localVarPath = "/pipelines/trigger";

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
}
