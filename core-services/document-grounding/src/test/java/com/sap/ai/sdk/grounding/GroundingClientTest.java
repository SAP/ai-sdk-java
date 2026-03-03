package com.sap.ai.sdk.grounding;

import static com.github.tomakehurst.wiremock.client.WireMock.anyUrl;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.grounding.client.PipelinesApi;
import com.sap.ai.sdk.grounding.client.RetrievalApi;
import com.sap.ai.sdk.grounding.client.VectorApi;
import com.sap.ai.sdk.grounding.model.CollectionsListResponse;
import com.sap.ai.sdk.grounding.model.DataRepositories;
import com.sap.ai.sdk.grounding.model.DataRepositoryType;
import com.sap.ai.sdk.grounding.model.DocumentResponse;
import com.sap.ai.sdk.grounding.model.Documents;
import com.sap.ai.sdk.grounding.model.GetPipelines;
import com.sap.ai.sdk.grounding.model.RetrievalKeyValueListPair;
import com.sap.ai.sdk.grounding.model.VectorDocumentKeyValueListPair;
import com.sap.ai.sdk.grounding.model.VectorKeyValueListPair;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

class GroundingClientTest {

  @RegisterExtension
  private static final WireMockExtension WM =
      WireMockExtension.newInstance().options(wireMockConfig().dynamicPort()).build();

  private final HttpDestination DESTINATION = DefaultHttpDestination.builder(WM.baseUrl()).build();
  private final AiCoreService SERVICE = new AiCoreService().withBaseDestination(DESTINATION);

  @Test
  void testPipelines() {
    final PipelinesApi api = new GroundingClient(SERVICE).pipelines();

    final GetPipelines allPipelines = api.getAllPipelines("resourceGroup");
    assertThat(allPipelines).isNotNull();
    assertThat(allPipelines.getResources()).isEmpty();
  }

  @Test
  void testVector() {
    final VectorApi api = new GroundingClient(SERVICE).vector();

    final CollectionsListResponse collections = api.getAllCollections("resourceGroup");
    assertThat(collections).isNotNull();
    assertThat(collections.getResources())
        .isNotNull()
        .hasSize(1)
        .satisfiesExactly(
            c -> {
              assertThat(c).isNotNull();
              assertThat(c.getId()).isNotNull();
              assertThat(c.getTitle()).isEqualTo("test-collection");
              assertThat(c.getEmbeddingConfig()).isNotNull();
              assertThat(c.getEmbeddingConfig().getModelName()).isEqualTo("text-embedding-ada-999");
              final var meta =
                  VectorKeyValueListPair.create().key("purpose").value("grounding test");
              assertThat(c.getMetadata()).isNotNull().containsExactly(meta);
            });

    final UUID collectionId = collections.getResources().get(0).getId();
    final Documents documents = api.getAllDocuments("resourceGroup", collectionId);
    assertThat(documents).isNotNull();
    final var documentMeta =
        VectorDocumentKeyValueListPair.create()
            .key("url")
            .value("http://hello.com", "123")
            .matchMode(VectorDocumentKeyValueListPair.MatchModeEnum.ANY);
    assertThat(documents.getResources())
        .isNotNull()
        .hasSize(1)
        .satisfiesExactly(
            d -> {
              assertThat(d).isNotNull();
              assertThat(d.getId()).isNotNull();
              assertThat(d.getMetadata()).isNotNull().containsExactly(documentMeta);
            });

    final UUID documentId = documents.getResources().get(0).getId();
    final DocumentResponse document =
        api.getDocumentById("resourceGroup", collectionId, documentId);
    assertThat(document).isNotNull();
    assertThat(document.getId()).isEqualTo(documentId);
    assertThat(document.getMetadata()).isNotNull().containsExactly(documentMeta);
    assertThat(document.getChunks())
        .isNotNull()
        .satisfiesExactly(
            d1 -> {
              assertThat(d1).isNotNull();
              assertThat(d1.getContent()).isNotEmpty();
              final var m1 = VectorKeyValueListPair.create().key("index").value("1");
              final var m2 =
                  VectorKeyValueListPair.create()
                      .key("sap.document-grounding/language")
                      .value("en");
              assertThat(d1.getMetadata()).isNotNull().containsExactly(m1, m2);
            },
            d2 -> {
              assertThat(d2).isNotNull();
              assertThat(d2.getContent()).isNotEmpty();
              final var m1 = VectorKeyValueListPair.create().key("index").value("2");
              final var m2 =
                  VectorKeyValueListPair.create()
                      .key("sap.document-grounding/language")
                      .value("en");
              assertThat(d2.getMetadata()).isNotNull().containsExactly(m1, m2);
            });
  }

  @Test
  void testRetrieval() {
    final RetrievalApi api = new GroundingClient(SERVICE).retrieval();

    DataRepositories repositories = api.getDataRepositories("resourceGroup");
    assertThat(repositories).isNotNull();
    assertThat(repositories.getResources())
        .isNotEmpty()
        .satisfiesExactly(
            r -> {
              assertThat(r).isNotNull();
              assertThat(r.getId()).isNotNull();
              assertThat(r.getTitle()).isEqualTo("test-collection");
              assertThat(r.getType()).isEqualTo(DataRepositoryType.VECTOR);
              final var meta =
                  RetrievalKeyValueListPair.create().key("purpose").value("grounding test");
              assertThat(r.getMetadata()).isNotNull().containsExactly(meta);
            },
            r2 -> {
              assertThat(r2).isNotNull();
              assertThat(r2.getId()).isNotNull();
              assertThat(r2.getTitle()).isEqualTo("This is another collection");
              assertThat(r2.getType()).isEqualTo(DataRepositoryType.VECTOR);
              assertThat(r2.getMetadata()).isNotNull().isEmpty();
            });
  }

  @Test
  void testCustomHeaders() {
    WM.stubFor(
        get(anyUrl())
            .withHeader("x-test-header", equalTo("test-value"))
            .willReturn(
                okJson(
                    """
                    {
                      "count": 0,
                      "resources": []
                    }
                    """)));

    new GroundingClient(SERVICE)
        .withHeader("x-test-header", "test-value")
        .pipelines()
        .getAllPipelines("resourceGroup");

    WM.verify(getRequestedFor(anyUrl()).withHeader("x-test-header", equalTo("test-value")));
  }
}
