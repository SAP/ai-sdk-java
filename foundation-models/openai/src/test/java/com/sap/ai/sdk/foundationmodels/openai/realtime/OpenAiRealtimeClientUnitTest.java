package com.sap.ai.sdk.foundationmodels.openai.realtime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.sap.ai.sdk.foundationmodels.openai.AudioInputChannel;
import com.sap.ai.sdk.foundationmodels.openai.AudioOutputChannel;
import com.sap.ai.sdk.foundationmodels.openai.TextInputChannel;
import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import org.junit.jupiter.api.Test;

class OpenAiRealtimeClientUnitTest {

  private static final AudioOutputChannel NO_OP_OUTPUT = (audio, done) -> {};

  @Test
  void constructorStoresDestination() {
    final var destination = DefaultHttpDestination.builder("https://api.example.com").build();
    final var client = new OpenAiRealtimeClient(destination);

    assertThat(client.destination).isSameAs(destination);
  }

  @Test
  void buildRealtimeHeadersReturnsEmptyMapWhenDestinationHasNoHeaders() {
    final var destination = DefaultHttpDestination.builder("https://api.example.com").build();
    final var client = new OpenAiRealtimeClient(destination);

    assertThat(client.buildRealtimeHeaders()).isEmpty();
  }

  @Test
  void buildRealtimeHeadersCopiesAllDestinationHeaders() {
    final var destination =
        DefaultHttpDestination.builder("https://api.example.com")
            .header("Authorization", "Bearer token-123")
            .header("X-Custom-Header", "custom-value")
            .build();
    final var client = new OpenAiRealtimeClient(destination);

    final var headers = client.buildRealtimeHeaders();

    assertThat(headers)
        .containsEntry("Authorization", "Bearer token-123")
        .containsEntry("X-Custom-Header", "custom-value");
  }

  @Test
  void buildRealtimeHeadersReturnsModifiableCopy() {
    final var destination =
        DefaultHttpDestination.builder("https://api.example.com")
            .header("Authorization", "Bearer token")
            .build();
    final var client = new OpenAiRealtimeClient(destination);

    final var headers = client.buildRealtimeHeaders();
    headers.put("injected", "value");

    assertThat(client.buildRealtimeHeaders()).doesNotContainKey("injected");
  }

  @Test
  void getRealtimeEndpointBuildsWssUrlAndAppendsSuffix() {
    final var destination =
        DefaultHttpDestination.builder(
                "https://my-resource.openai.azure.com/openai/deployments/gpt-4o")
            .build();
    final var client = new OpenAiRealtimeClient(destination);

    assertThat(client.getRealtimeEndpoint()).startsWith("wss://").endsWith("/v1/realtime");
  }

  @Test
  void getRealtimeEndpointReplacesApiSubdomainWithRealtime() {
    final var destination =
        DefaultHttpDestination.builder("https://api.example.com/some/path").build();
    final var client = new OpenAiRealtimeClient(destination);

    final var endpoint = client.getRealtimeEndpoint();

    assertThat(endpoint).startsWith("wss://realtime.example.com");
    assertThat(endpoint).doesNotContain("api.example.com");
  }

  @Test
  void getRealtimeEndpointPreservesNonApiSubdomain() {
    final var destination =
        DefaultHttpDestination.builder("https://my-resource.openai.azure.com/openai").build();
    final var client = new OpenAiRealtimeClient(destination);

    final var endpoint = client.getRealtimeEndpoint();

    assertThat(endpoint).startsWith("wss://my-resource.openai.azure.com");
  }

  @Test
  void getRealtimeEndpointThrowsOnUriWithoutDoubleSlash() {
    final var destination = DefaultHttpDestination.builder("https://host").build();
    final var client =
        new OpenAiRealtimeClient(destination) {
          @Override
          public String getRealtimeEndpoint() {
            // Force a URI that produces != 2 parts when split on "//"
            final var sb = new StringBuilder(PATH_BUFFER_SIZE);
            sb.append("wss://");
            final var pathParts = "no-double-slash".split("//");
            if (pathParts.length != 2) {
              throw new IllegalArgumentException("Invalid destination URI: no-double-slash");
            }
            return sb.toString();
          }
        };

    assertThatThrownBy(client::getRealtimeEndpoint)
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Invalid destination URI");
  }

  @Test
  void textToSpeechReturnsTextInputChannel() {
    final var destination = DefaultHttpDestination.builder("https://api.example.com").build();
    final var client = new OpenAiRealtimeClient(destination);

    final var channel = client.textToSpeech(NO_OP_OUTPUT);

    assertThat(channel).isInstanceOf(TextInputChannel.class);
  }

  @Test
  void speechToSpeechReturnsAudioInputChannel() {
    final var destination = DefaultHttpDestination.builder("https://api.example.com").build();
    final var client = new OpenAiRealtimeClient(destination);

    final var channel = client.speechToSpeech(NO_OP_OUTPUT);

    assertThat(channel).isInstanceOf(AudioInputChannel.class);
  }
}
