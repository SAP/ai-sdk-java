package com.sap.ai.sdk.foundationmodels.openaiwrapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import com.openai.client.OpenAIClient;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import com.sap.cloud.sdk.cloudplatform.connectivity.Header;
import java.net.URI;
import java.util.List;
import org.junit.jupiter.api.Test;

class OpenAiClientProviderTest {

  @Test
  void testWithCustomDestinationCreatesClient() {
    final var destination = mock(DefaultHttpDestination.class);

    doReturn(URI.create("https://api.ai.sap/v2/inference/deployments/d123/"))
        .when(destination)
        .getUri();
    doReturn(
            List.of(
                new Header("Authorization", "Bearer test-token-123"),
                new Header("AI-Resource-Group", "my-rg")))
        .when(destination)
        .getHeaders(any(URI.class));
    doReturn(destination).when(destination).asHttp();

    final OpenAIClient client = OpenAiClientProvider.withCustomDestination(destination);

    assertThat(client).isNotNull();
  }

  @Test
  void testExtractBearerToken() {
    final var destination = mock(DefaultHttpDestination.class);

    doReturn(URI.create("https://api.ai.sap/")).when(destination).getUri();
    doReturn(List.of(new Header("Authorization", "Bearer my-secret-token")))
        .when(destination)
        .getHeaders(any(URI.class));

    final String token = OpenAiClientProvider.extractBearerToken(destination);

    assertThat(token).isEqualTo("my-secret-token");
  }

  @Test
  void testExtractBearerTokenThrowsWhenMissing() {
    final var destination = mock(DefaultHttpDestination.class);

    doReturn(URI.create("https://api.ai.sap/")).when(destination).getUri();
    doReturn(List.of(new Header("AI-Resource-Group", "default")))
        .when(destination)
        .getHeaders(any(URI.class));

    assertThatThrownBy(() -> OpenAiClientProvider.extractBearerToken(destination))
        .isInstanceOf(IllegalStateException.class)
        .hasMessageContaining("No Bearer token found");
  }

  @Test
  void testGetResourceGroupHeader() {
    final var destination = mock(DefaultHttpDestination.class);

    doReturn(URI.create("https://api.ai.sap/")).when(destination).getUri();
    doReturn(List.of(new Header("AI-Resource-Group", "custom-rg")))
        .when(destination)
        .getHeaders(any(URI.class));

    final String rg = OpenAiClientProvider.getResourceGroupHeader(destination);

    assertThat(rg).isEqualTo("custom-rg");
  }

  @Test
  void testGetResourceGroupHeaderDefaultsToDefault() {
    final var destination = mock(DefaultHttpDestination.class);

    doReturn(URI.create("https://api.ai.sap/")).when(destination).getUri();
    doReturn(List.of(new Header("Authorization", "Bearer token")))
        .when(destination)
        .getHeaders(any(URI.class));

    final String rg = OpenAiClientProvider.getResourceGroupHeader(destination);

    assertThat(rg).isEqualTo("default");
  }
}
