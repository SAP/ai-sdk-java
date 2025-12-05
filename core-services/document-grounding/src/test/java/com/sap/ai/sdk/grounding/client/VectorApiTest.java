package com.sap.ai.sdk.grounding.client;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.sap.ai.sdk.grounding.model.DocumentOutput;
import com.sap.ai.sdk.grounding.model.DocumentsChunk;
import com.sap.ai.sdk.grounding.model.ResultsInner1;
import com.sap.ai.sdk.grounding.model.TextSearchRequest;
import com.sap.ai.sdk.grounding.model.VectorChunk;
import com.sap.ai.sdk.grounding.model.VectorSearchResults;
import com.sap.cloud.sdk.services.openapi.apiclient.ApiClient;
import com.sap.cloud.sdk.services.openapi.core.OpenApiRequestException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

class VectorApiTest {

  private ApiClient apiClient;
  private VectorApi vectorApi;

  @BeforeEach
  void setUp() {
    apiClient = mock(ApiClient.class);
    vectorApi = spy(new VectorApi(apiClient)); // make it a spy to stub instance methods
  }

  @Test
  void testSearchReturnsResults() throws OpenApiRequestException {
    String aiResourceGroup = "test-group";
    TextSearchRequest request = mock(TextSearchRequest.class);
    VectorSearchResults expectedResults = mock(VectorSearchResults.class);

    when(apiClient.invokeAPI(
            anyString(),
            eq(HttpMethod.POST),
            any(),
            any(TextSearchRequest.class),
            any(),
            any(),
            anyList(),
            any(),
            any(String[].class),
            ArgumentMatchers.<ParameterizedTypeReference<VectorSearchResults>>any()))
        .thenReturn(expectedResults);

    VectorSearchResults actualResults = vectorApi.search(aiResourceGroup, request);

    assertSame(expectedResults, actualResults);

    verify(apiClient)
        .invokeAPI(
            anyString(),
            eq(HttpMethod.POST),
            any(),
            any(TextSearchRequest.class),
            any(),
            any(),
            anyList(),
            any(),
            any(String[].class),
            ArgumentMatchers.<ParameterizedTypeReference<VectorSearchResults>>any());
  }

  @Test
  void searchAndGetChunks_returnsFlattenedChunks_whenSearchHasResults()
      throws OpenApiRequestException {
    String aiResourceGroup = "test-group";
    TextSearchRequest request = mock(TextSearchRequest.class);

    // Leaf chunks
    VectorChunk chunk1 = mock(VectorChunk.class);
    VectorChunk chunk2 = mock(VectorChunk.class);

    // DocumentOutput -> List<VectorChunk>
    DocumentOutput documentOutput = mock(DocumentOutput.class);
    when(documentOutput.getChunks()).thenReturn(List.of(chunk1, chunk2));

    // DocumentsChunk -> List<DocumentOutput>
    DocumentsChunk documentsChunk = mock(DocumentsChunk.class);
    when(documentsChunk.getDocuments()).thenReturn(List.of(documentOutput));

    // ResultsInner1 -> List<DocumentsChunk>
    ResultsInner1 vectorSearchResult = mock(ResultsInner1.class);
    when(vectorSearchResult.getResults()).thenReturn(List.of(documentsChunk));

    // VectorSearchResults -> List<ResultsInner1>
    VectorSearchResults searchResults = mock(VectorSearchResults.class);
    when(searchResults.getResults()).thenReturn(List.of(vectorSearchResult));

    // Stub VectorApi.search(...) on the spy
    doReturn(searchResults).when(vectorApi).search(eq(aiResourceGroup), eq(request));

    List<VectorChunk> chunks = vectorApi.searchAndGetChunks(aiResourceGroup, request);

    assertNotNull(chunks);
    assertEquals(2, chunks.size());
    assertTrue(chunks.contains(chunk1));
    assertTrue(chunks.contains(chunk2));

    verify(vectorApi).search(aiResourceGroup, request);
  }
}
