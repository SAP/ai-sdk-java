package com.sap.ai.sdk.grounding;

import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.core.DeploymentResolutionException;
import com.sap.ai.sdk.grounding.api.PipelinesApi;
import com.sap.ai.sdk.grounding.api.VectorApi;
import com.sap.ai.sdk.grounding.api.RetrievalApi;
import com.sap.ai.sdk.grounding.model.Collection;
import com.sap.ai.sdk.grounding.model.CollectionsListResponse;
import com.sap.ai.sdk.grounding.model.DataRepositories;
import com.sap.ai.sdk.grounding.model.DataRepository;
import com.sap.ai.sdk.grounding.model.DocumentResponse;
import com.sap.ai.sdk.grounding.model.DocumentWithoutChunks;
import com.sap.ai.sdk.grounding.model.Documents;
import com.sap.ai.sdk.grounding.model.Pipeline;
import com.sap.ai.sdk.grounding.model.Pipelines;
import com.sap.cloud.sdk.services.openapi.apiclient.ApiClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;

/** Client for interacting with OpenAI models. */
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class GroundingClient {
  private static final String DEFAULT_API_VERSION = "2024-02-01";
  //static final ObjectMapper JACKSON = getDefaultObjectMapper();
  // @Nonnull private final Destination destination;

  /**
   * Create a new OpenAI client for the given foundation model, using the default resource group.
   *
   * @param foundationModel the OpenAI model which is deployed.
   * @return a new OpenAI client.
   * @throws DeploymentResolutionException if no deployment for the given model was found in the
   *     default resource group.
   */
  @Nonnull
  public static void main(String[] args)
      throws DeploymentResolutionException {
    String RESOURCE_GROUP = "default";

    ApiClient apiClient = new AiCoreService().getApiClient();
    apiClient.setBasePath(apiClient.getBasePath()+"lm/document-grounding/");


    PipelinesApi pipelinesApi = new PipelinesApi(apiClient);
    Pipelines allPipelines = pipelinesApi.getAllPipelines(RESOURCE_GROUP, 10, 0, true);
    for (Pipeline pipeline : allPipelines.getResources()) {
      System.out.println(pipeline);
    }

    RetrievalApi retrievalApi = new RetrievalApi(apiClient);
    DataRepositories repositories = retrievalApi.getDataRepositories(RESOURCE_GROUP);
    for (DataRepository repository : repositories.getResources()) {
      System.out.println(repository);
      DataRepository repoById = retrievalApi.getDataRepositoryById(RESOURCE_GROUP, repository.getId());
      System.out.println(repoById);
    }
    System.out.println(repositories);


    VectorApi vectorApi = new VectorApi(apiClient);
    CollectionsListResponse allCollections = vectorApi.getAllCollections(RESOURCE_GROUP);
    for (Collection documentCollection : allCollections.getResources()) {
      System.out.println(documentCollection);
      Documents documents = vectorApi.getAllDocuments(RESOURCE_GROUP, documentCollection.getId(), 10, 0, true);
      System.out.println(documents);
      System.out.println();

      for (DocumentWithoutChunks aDefault1Resource : documents.getResources()) {
        DocumentResponse document = vectorApi.getDocumentById(RESOURCE_GROUP, documentCollection.getId(), aDefault1Resource.getId());
        System.out.println(document);
        System.out.println();
      }
    }
    System.out.println(allCollections);

  }
}
