package com.sap.ai.sdk.app.controllers;

import static java.time.LocalDate.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.sap.ai.sdk.grounding.model.CollectionsListResponse;
import com.sap.ai.sdk.grounding.model.DataRepositories;
import com.sap.ai.sdk.grounding.model.DocumentResponse;
import com.sap.ai.sdk.grounding.model.Documents;
import com.sap.ai.sdk.grounding.model.DocumentsListResponse;
import com.sap.ai.sdk.grounding.model.Pipelines;
import com.sap.ai.sdk.grounding.model.RetievalSearchResults;
import com.sap.cloud.sdk.services.openapi.core.OpenApiResponse;
import java.time.format.TextStyle;
import java.util.Locale;
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
  void testCreateDeleteCollection() {
    final var controller = new GroundingController();

    // (1) CREATE COLLECTION
    final var collectionId = controller.createCollection(JSON_FORMAT);
    final var collectionUuid = UUID.fromString(collectionId);
    assertThat(collectionId).isNotNull();

    // (2) SANITY CHECK: NO DOCUMENTS
    final var documentsEmpty = controller.getDocumentsByCollectionId(collectionUuid, JSON_FORMAT);
    assertThat(documentsEmpty).isInstanceOf(Documents.class);
    assertThat(((Documents) documentsEmpty).getCount()).isEqualTo(0);
    assertThat(((Documents) documentsEmpty).getResources()).isNotNull().isEmpty();

    // (3) UPLOAD A DOCUMENT
    final var documentCreated = controller.createDocument(collectionUuid, JSON_FORMAT);
    assertThat(documentCreated).isInstanceOf(DocumentsListResponse.class);
    assertThat(((DocumentsListResponse) documentCreated).getDocuments()).isNotNull().hasSize(1);

    // (4) SEARCH FOR DOCUMENTS
    Object search = controller.searchInDocuments(JSON_FORMAT);
    final var dayOfWeek = now().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);

    assertThat(search).isInstanceOf(RetievalSearchResults.class);
    assertThat(((RetievalSearchResults) search).getResults())
        .isNotNull()
        .isNotEmpty()
        .allSatisfy(
            r ->
                assertThat(r.getResults())
                    .isNotNull()
                    .isNotEmpty()
                    .allSatisfy(
                        doc ->
                            assertThat(doc.getDataRepository().getDocuments())
                                .isNotNull()
                                .isNotEmpty()
                                .allSatisfy(
                                    m ->
                                        assertThat(m.getChunks())
                                            .isNotNull()
                                            .isNotEmpty()
                                            .allSatisfy(
                                                chunk ->
                                                    assertThat(chunk.getContent())
                                                        .isNotNull()
                                                        .contains(dayOfWeek)))));

    // (5) DELETE COLLECTION
    Object deletion = controller.deleteDocuments(collectionUuid, JSON_FORMAT);
    assertThat(deletion).isInstanceOf(OpenApiResponse.class);
    assertThat(((OpenApiResponse) deletion).getStatusCode()).isEqualTo(202);

    // (6) SANITY CHECK: NO COLLECTION
    assertThatThrownBy(() -> controller.getDocumentsByCollectionId(collectionUuid, JSON_FORMAT))
        .hasMessageContaining("404 Not Found");
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
