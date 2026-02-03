package com.sap.ai.sdk.app.controllers;

import static com.sap.ai.sdk.app.controllers.GroundingController.RESOURCE_GROUP;
import static java.time.LocalDate.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.sap.ai.sdk.grounding.model.CollectionsListResponse;
import com.sap.ai.sdk.grounding.model.DataRepositories;
import com.sap.ai.sdk.grounding.model.DocumentResponse;
import com.sap.ai.sdk.grounding.model.Documents;
import com.sap.ai.sdk.grounding.model.DocumentsListResponse;
import com.sap.ai.sdk.grounding.model.GetPipelines;
import com.sap.ai.sdk.grounding.model.RetrievalSearchResults;
import com.sap.cloud.sdk.services.openapi.core.OpenApiResponse;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class GroundingTest {
  private static final String JSON_FORMAT = "json";

  @Test
  void testPipelinesGetAll() {
    final var controller = new GroundingController();

    // we don't have testable data yet, but the endpoint works without errors
    var result = controller.getAllPipelines(JSON_FORMAT, RESOURCE_GROUP);
    assertThat(result).isInstanceOf(GetPipelines.class);
    var pipelinesList = ((GetPipelines) result).getResources();
    assertThat(pipelinesList).isEmpty();
    var pipelinesCount = ((GetPipelines) result).getCount();
    assertThat(pipelinesCount).isEqualTo(0);
  }

  @Test
  void testRepositoriesGetAll() {
    final var controller = new GroundingController();

    final var result = controller.getAllRepositories(JSON_FORMAT);
    assertThat(result).isInstanceOf(DataRepositories.class);
    final var repositoryList = ((DataRepositories) result).getResources();
    final var repositoryCount = ((DataRepositories) result).getCount();

    assertThat(repositoryCount).isGreaterThan(0);
    for (final var repository : repositoryList) {
      assertThat(repository.getId()).isNotNull();
      assertThat(repository.getTitle()).isNotNull();
    }
  }

  @Test
  void testCreateDeleteCollection() {
    final var controller = new GroundingController();

    // (0) DELETE OLD COLLECTIONS
    controller.deleteCollections(JSON_FORMAT);

    // (1) CREATE COLLECTION
    final var collectionId = controller.createCollection(JSON_FORMAT);
    final var collectionUuid = UUID.fromString(collectionId);
    assertThat(collectionId).isNotNull();

    // (1.1) TEST COLLECTION LOOKUP
    this.testCollectionsGetAll();

    // (2) SANITY CHECK: NO DOCUMENTS IN COLLECTION
    final var documentsEmpty = controller.getDocumentsByCollectionId(collectionUuid, JSON_FORMAT);
    assertThat(documentsEmpty).isInstanceOf(Documents.class);
    assertThat(((Documents) documentsEmpty).getCount()).isEqualTo(0);
    assertThat(((Documents) documentsEmpty).getResources()).isNotNull().isEmpty();

    // (3) UPLOAD A DOCUMENT
    final var documentsCreated = controller.createDocument(collectionUuid, JSON_FORMAT);
    assertThat(documentsCreated).isInstanceOf(DocumentsListResponse.class);
    final var document = ((DocumentsListResponse) documentsCreated).getDocuments();
    assertThat(document).isNotNull().hasSize(1);

    // (3.1) TEST DOCUMENT LOOKUP
    this.testGetDocumentById(collectionUuid, document.get(0).getId());

    // (4) SEARCH FOR DOCUMENTS
    Object search = controller.searchInDocuments(JSON_FORMAT);
    assertThat(search).isInstanceOf(RetrievalSearchResults.class);
    final var dayOfWeek = now().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
    this.assertDocumentSearchResult((RetrievalSearchResults) search, dayOfWeek);

    // (5) CLEAN UP
    Object deletion = controller.deleteCollection(collectionUuid, JSON_FORMAT);
    assertThat(deletion).isInstanceOf(OpenApiResponse.class);
    assertThat(((OpenApiResponse) deletion).getStatusCode()).isEqualTo(202);

    // (6) SANITY CHECK: NO COLLECTION
    assertThatThrownBy(() -> controller.getDocumentsByCollectionId(collectionUuid, JSON_FORMAT))
        .hasMessageContaining("404 Not Found");
  }

  private void assertDocumentSearchResult(RetrievalSearchResults search, String dayOfWeek) {
    assertThat(search.getResults()).isNotEmpty();
    for (final var resultsByFilter : search.getResults()) {
      assertThat(resultsByFilter.getFilterId()).isEqualTo("question");
      assertThat(resultsByFilter.getResults()).isNotEmpty();
      for (final var result : resultsByFilter.getResults()) {
        assertThat(result.getDataRepository().getDocuments()).isNotEmpty();
        for (final var document : result.getDataRepository().getDocuments()) {
          assertThat(document.getChunks()).isNotEmpty();
          for (final var chunk : document.getChunks()) {
            assertThat(chunk.getContent()).contains(dayOfWeek);
          }
        }
      }
    }
  }

  void testCollectionsGetAll() {
    final var controller = new GroundingController();

    final var result = controller.getAllCollections(JSON_FORMAT);
    assertThat(result).isInstanceOf(CollectionsListResponse.class);
    final var collectionsList = ((CollectionsListResponse) result).getResources();
    final var collectionsCount = ((CollectionsListResponse) result).getCount();

    assertThat(collectionsCount).isGreaterThan(0);
    for (final var collection : collectionsList) {
      assertThat(collection.getId()).isNotNull();
      assertThat(collection.getTitle()).isNotEmpty();
      assertThat(collection.getEmbeddingConfig()).isNotNull();
      assertThat(collection.getEmbeddingConfig().getModelName()).isNotNull();
    }
  }

  void testGetDocumentById(UUID collectionId, UUID documentId) {
    final var controller = new GroundingController();

    final var result = controller.getDocumentChunksById(collectionId, documentId, JSON_FORMAT);
    assertThat(result).isInstanceOf(DocumentResponse.class);
    final var chunks = ((DocumentResponse) result).getChunks();

    assertThat(chunks).isNotEmpty();
    for (final var chunk : chunks) {
      assertThat(chunk.getContent()).isNotEmpty();
      assertThat(chunk.getMetadata()).isNotNull().isNotEmpty();
    }
  }
}
