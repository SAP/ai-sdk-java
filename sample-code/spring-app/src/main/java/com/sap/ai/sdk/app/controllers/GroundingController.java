package com.sap.ai.sdk.app.controllers;

import static java.util.stream.Collectors.joining;

import com.sap.ai.sdk.grounding.GroundingClient;
import com.sap.ai.sdk.grounding.api.PipelinesApi;
import com.sap.ai.sdk.grounding.api.RetrievalApi;
import com.sap.ai.sdk.grounding.api.VectorApi;
import com.sap.ai.sdk.grounding.model.Collection;
import com.sap.ai.sdk.grounding.model.DataRepository;
import com.sap.ai.sdk.grounding.model.DocumentWithoutChunks;
import com.sap.ai.sdk.grounding.model.Pipeline;
import com.sap.ai.sdk.grounding.model.TextOnlyBaseChunk;
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
  private static final String RESOURCE_GROUP = "default";

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
