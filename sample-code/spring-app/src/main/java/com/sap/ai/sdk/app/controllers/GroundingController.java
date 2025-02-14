package com.sap.ai.sdk.app.controllers;

import static com.sap.ai.sdk.foundationmodels.openai.OpenAiModel.TEXT_EMBEDDING_ADA_002;
import static java.time.LocalDate.now;
import static java.util.stream.Collectors.joining;

import com.sap.ai.sdk.grounding.GroundingClient;
import com.sap.ai.sdk.grounding.api.PipelinesApi;
import com.sap.ai.sdk.grounding.api.RetrievalApi;
import com.sap.ai.sdk.grounding.api.VectorApi;
import com.sap.ai.sdk.grounding.model.BaseDocument;
import com.sap.ai.sdk.grounding.model.Collection;
import com.sap.ai.sdk.grounding.model.CollectionRequest;
import com.sap.ai.sdk.grounding.model.DataRepository;
import com.sap.ai.sdk.grounding.model.DataRepositoryType;
import com.sap.ai.sdk.grounding.model.DocumentCreateRequest;
import com.sap.ai.sdk.grounding.model.DocumentKeyValueListPair;
import com.sap.ai.sdk.grounding.model.DocumentWithoutChunks;
import com.sap.ai.sdk.grounding.model.EmbeddingConfig;
import com.sap.ai.sdk.grounding.model.KeyValueListPair;
import com.sap.ai.sdk.grounding.model.Pipeline;
import com.sap.ai.sdk.grounding.model.ResultsInner1;
import com.sap.ai.sdk.grounding.model.RetrievalSearchFilter;
import com.sap.ai.sdk.grounding.model.RetrievalSearchInput;
import com.sap.ai.sdk.grounding.model.SearchConfiguration;
import com.sap.ai.sdk.grounding.model.TextOnlyBaseChunk;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** Endpoints for AI Core AiDeployment operations */
@Slf4j
@RestController
@SuppressWarnings("unused")
@RequestMapping("/grounding")
class GroundingController {

  private static final PipelinesApi CLIENT_PIPELINES = GroundingClient.create().pipelines();
  private static final RetrievalApi CLIENT_RETRIEVAL = GroundingClient.create().retrieval();
  private static final VectorApi CLIENT_VECTOR = GroundingClient.create().vector();
  private static final String RESOURCE_GROUP = "ai-sdk-java-e2e";

  /**
   * Stop all deployments with the Java specific configuration ID.
   *
   * <p>Only RUNNING deployments can be STOPPED
   */
  @GetMapping("/pipelines/list")
  Object getAllPipelines(
      @Nullable @RequestParam(value = "format", required = false) final String format) {
    final var pipelines = CLIENT_PIPELINES.getAllPipelines(RESOURCE_GROUP, 10, 0, true);
    log.info("Found {} pipelines", pipelines.getResources().size());

    if ("json".equals(format)) {
      return pipelines;
    }
    final var ids = pipelines.getResources().stream().map(Pipeline::getId).collect(joining(", "));
    return "Found pipelines with ids: " + ids;
  }

  /**
   * Delete all deployments with the Java specific configuration ID.
   *
   * <p>Only UNKNOWN and STOPPED deployments can be DELETED
   */
  @GetMapping("/retrieval/repositories")
  Object getAllRepositories(
      @Nullable @RequestParam(value = "format", required = false) final String format) {
    final var repositories = CLIENT_RETRIEVAL.getDataRepositories(RESOURCE_GROUP);
    log.info("Found {} data repositories", repositories.getResources().size());

    if ("json".equals(format)) {
      return repositories;
    }
    final var titles =
        repositories.getResources().stream().map(DataRepository::getTitle).collect(joining(", "));
    return "Found repositories with titles: " + titles;
  }

  /**
   * Delete all deployments with the Java specific configuration ID.
   *
   * <p>Only UNKNOWN and STOPPED deployments can be DELETED
   */
  @GetMapping("/retrieval/search")
  Object searchInDocuments(
      @Nullable @RequestParam(value = "format", required = false) final String format) {
    final var filter =
        RetrievalSearchFilter.create()
            .id("question")
            .dataRepositoryType(DataRepositoryType.VECTOR)
            .dataRepositories(List.of("*"))
            .searchConfiguration(SearchConfiguration.create().maxChunkCount(10));
    final var q = RetrievalSearchInput.create().query("When was the last upload?").filters(filter);
    final var results = CLIENT_RETRIEVAL.search(RESOURCE_GROUP, q);

    if ("json".equals(format)) {
      return results;
    }
    final var messages = results.getResults().stream().map(ResultsInner1::getMessage).toList();
    return "Found the following response(s): " + messages;
  }

  /** Get all deployments with the Java specific configuration ID. */
  @GetMapping("/vector/collections")
  Object getAllCollections(
      @Nullable @RequestParam(value = "format", required = false) final String format) {
    final var collections = CLIENT_VECTOR.getAllCollections(RESOURCE_GROUP);
    if ("json".equals(format)) {
      return collections;
    }
    final var items =
        collections.getResources().stream().map(Collection::getTitle).collect(joining(", "));
    return "The following collections are available: %s.".formatted(items);
  }

  /** Get all deployments, including non-Java specific deployments */
  @GetMapping("/vector/collection/by-id/{id}/documents")
  Object getDocumentsByCollectionId(
      @Nonnull @PathVariable("id") final UUID collectionId,
      @Nullable @RequestParam(value = "format", required = false) final String format) {
    final var documents = CLIENT_VECTOR.getAllDocuments(RESOURCE_GROUP, collectionId);
    if ("json".equals(format)) {
      return documents;
    }
    final var ids = documents.getResources().stream().map(DocumentWithoutChunks::getId).toList();
    return "The following document ids are available: %s.".formatted(ids);
  }

  /** Get all deployments, including non-Java specific deployments */
  @GetMapping("/vector/collection/create")
  String createCollection(
      @Nullable @RequestParam(value = "format", required = false) final String format) {
    final var embeddingConfig = EmbeddingConfig.create().modelName(TEXT_EMBEDDING_ADA_002.name());
    final var request = CollectionRequest.create().embeddingConfig(embeddingConfig).title("e2e");
    final var documents = CLIENT_VECTOR.createCollection(RESOURCE_GROUP, request);
    final Map<String, List<String>> headers = documents.getHeaders();

    final var locationHeader = headers.get("Location").get(0);
    return locationHeader.replaceAll("^.*?/([a-f0-9-]+)/.*?$", "$1");
  }

  /** Get all deployments, including non-Java specific deployments */
  @GetMapping("/vector/collection/by-id/{id}/day")
  Object createDocument(
      @Nonnull @PathVariable("id") final UUID collectionId,
      @Nullable @RequestParam(value = "format", required = false) final String format) {
    final var dayOfWeek = now().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
    ;
    final var documentContent = "Last upload on " + dayOfWeek;

    final var chunkMeta = KeyValueListPair.create().key("context").value("day-of-week");
    final var chunk = TextOnlyBaseChunk.create().content(documentContent).metadata(chunkMeta);
    final var docMeta = DocumentKeyValueListPair.create().key("purpose").value("testing");
    final var doc = BaseDocument.create().chunks(chunk).metadata(docMeta);
    final var request = DocumentCreateRequest.create().documents(doc);
    final var response = CLIENT_VECTOR.createDocuments(RESOURCE_GROUP, collectionId, request);

    if ("json".equals(format)) {
      return response;
    }
    final var ids = response.getDocuments().stream().map(DocumentWithoutChunks::getId).toList();
    return "The following document ids are available: %s.".formatted(ids);
  }

  /** Get all deployments, including non-Java specific deployments */
  @GetMapping("/vector/collection/by-id/{id}/clear")
  Object deleteDocuments(
      @Nonnull @PathVariable("id") final UUID collectionId,
      @Nullable @RequestParam(value = "format", required = false) final String format) {
    final var dayOfWeek = now().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
    ;
    final var chunkMeta = KeyValueListPair.create().key("context").value("day-of-week");
    final var chunk =
        TextOnlyBaseChunk.create().content("Today is " + dayOfWeek).metadata(chunkMeta);
    final var docMeta = DocumentKeyValueListPair.create().key("purpose").value("testing");
    final var doc = BaseDocument.create().chunks(chunk).metadata(docMeta);
    final var request = DocumentCreateRequest.create().documents(doc);

    final var documents = CLIENT_VECTOR.getAllDocuments(RESOURCE_GROUP, collectionId);
    final var ids = documents.getResources().stream().map(DocumentWithoutChunks::getId).toList();
    log.info("Deleting collection {} with {} documents: {}", collectionId, ids.size(), ids);

    for (final var documentId : ids) {
      final var del = CLIENT_VECTOR.deleteDocumentById(RESOURCE_GROUP, collectionId, documentId);
      if (del.getStatusCode() >= 400) {
        final var msg = "Document {} could not be deleted, status code [{}], headers: {}";
        log.error(msg, documentId, del.getStatusCode(), del.getHeaders());
        throw new IllegalStateException("Document deletion failed for id " + documentId);
      }
    }
    final var response = CLIENT_VECTOR.deleteCollectionById(RESOURCE_GROUP, collectionId + "");

    if ("json".equals(format)) {
      return response;
    }
    return response.getStatusCode() >= 400 ? "Failed to delete collection" : "Deletion successful";
  }

  /** Get all deployments, including non-Java specific deployments */
  @GetMapping("/vector/collection/by-id/{collectionId}/document/by-id/{documentId}")
  Object getDocumentChunksById(
      @Nonnull @PathVariable("collectionId") final UUID collectionId,
      @Nonnull @PathVariable("documentId") final UUID documentId,
      @Nullable @RequestParam(value = "format", required = false) final String format) {
    final var document = CLIENT_VECTOR.getDocumentById(RESOURCE_GROUP, collectionId, documentId);
    if ("json".equals(format)) {
      return document;
    }
    final var ids = document.getChunks().stream().map(TextOnlyBaseChunk::getContent).toList();
    return "The following document ids are available: %s.".formatted(ids);
  }
}
