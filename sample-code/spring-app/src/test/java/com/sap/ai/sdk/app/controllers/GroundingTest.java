package com.sap.ai.sdk.app.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import com.sap.ai.sdk.grounding.model.CollectionsListResponse;
import com.sap.ai.sdk.grounding.model.DataRepositories;
import com.sap.ai.sdk.grounding.model.DocumentResponse;
import com.sap.ai.sdk.grounding.model.Documents;
import com.sap.ai.sdk.grounding.model.Pipelines;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class GroundingTest {
  /** Java end-to-end test specific configuration ID. "name":"config-java-e2e-test" */
  public static final String CONFIG_ID = "67e8d039-c7f1-4179-9f8f-60d158a36b0e";

  private static final String JSON_FORMAT = "json";

  @Test
  void getPipelinesGetAll() {
    final var controller = new GroundingController();

    final var result = controller.getAllPipelines(JSON_FORMAT);
    assertThat(result).isInstanceOf(Pipelines.class);
    final var pipelinesList = ((Pipelines) result).getResources();
    final var pipelinesCount = ((Pipelines) result).getCount();

    // we don't have testable data yet, but the endpoint works without errors
    assertThat(pipelinesCount).isEqualTo(0);
    assertThat(pipelinesList).isEmpty();
  }

  @Test
  void getRepositoriesGetAll() {
    final var controller = new GroundingController();

    final var result = controller.getAllRepositories(JSON_FORMAT);
    assertThat(result).isInstanceOf(DataRepositories.class);
    final var repositoryList = ((DataRepositories) result).getResources();
    final var repositoryCount = ((DataRepositories) result).getCount();

    assertThat(repositoryCount).isGreaterThan(0);
    for (var repository : repositoryList) {
      assertThat(repository.getId()).isNotNull();
      assertThat(repository.getTitle()).isNotNull();
    }
  }

  @Test
  void getCollectionsGetAll() {
    final var controller = new GroundingController();

    final var result = controller.getAllCollections(JSON_FORMAT);
    assertThat(result).isInstanceOf(CollectionsListResponse.class);
    final var collectionsList = ((CollectionsListResponse) result).getResources();
    final var collectionsCount = ((CollectionsListResponse) result).getCount();

    assertThat(collectionsCount).isGreaterThan(0);
    for (var collection : collectionsList) {
      assertThat(collection.getId()).isNotNull();
      assertThat(collection.getTitle()).isNotEmpty();
      assertThat(collection.getEmbeddingConfig()).isNotNull();
      assertThat(collection.getEmbeddingConfig().getModelName()).isNotNull();
    }
  }

  @Test
  void testGetDocuments() {
    final var controller = new GroundingController();

    UUID collectionId = UUID.fromString("6ebdb977-f9f8-4200-8849-6559b4cff311");
    final var result = controller.getDocumentsByCollectionId(collectionId, JSON_FORMAT);
    assertThat(result).isInstanceOf(Documents.class);
    final var documentsList = ((Documents) result).getResources();
    final var documentsCount = ((Documents) result).getCount();

    assertThat(documentsCount).isGreaterThan(0);
    for (var document : documentsList) {
      assertThat(document.getId()).isNotNull();
    }
  }

  @Test
  void testGetDocumentById() {
    final var controller = new GroundingController();

    UUID collectionId = UUID.fromString("6ebdb977-f9f8-4200-8849-6559b4cff311");
    UUID documentId = UUID.fromString("500d1e93-270e-479e-abbd-83a2291e4f6d");
    final var result = controller.getDocumentChunksById(collectionId, documentId, JSON_FORMAT);
    assertThat(result).isInstanceOf(DocumentResponse.class);
    final var chunks = ((DocumentResponse) result).getChunks();

    assertThat(chunks).isNotEmpty();
    for (var chunk : chunks) {
      assertThat(chunk.getContent()).isNotEmpty();
      assertThat(chunk.getMetadata()).isNotNull().isNotEmpty();
    }
  }
}
