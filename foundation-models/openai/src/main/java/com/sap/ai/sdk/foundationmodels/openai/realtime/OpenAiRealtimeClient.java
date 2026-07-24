package com.sap.ai.sdk.foundationmodels.openai.realtime;

import com.google.common.annotations.Beta;
import com.sap.ai.sdk.core.RealtimeParam;
import com.sap.ai.sdk.foundationmodels.openai.AudioInputChannel;
import com.sap.ai.sdk.foundationmodels.openai.AudioOutputChannel;
import com.sap.ai.sdk.foundationmodels.openai.TextInputChannel;
import com.sap.cloud.sdk.cloudplatform.connectivity.Destination;
import com.sap.cloud.sdk.cloudplatform.connectivity.Header;
import java.util.HashMap;
import javax.annotation.Nonnull;

/**
 * OpenAI client implementation of Realtime API. Abstracts technical implementation, transport and
 * threading and exposes business-level operations (high level interface)
 */
public class OpenAiRealtimeClient {

  private static final int PATH_BUFFER_SIZE =
      400; // existing URLs are ~120 symbols long, 400 has reasonable margin

  private final Destination destination;

  /**
   * Created OpenAI Realtime client for a specific destination
   * @param destination - destination to use
   */
  public OpenAiRealtimeClient(@Nonnull final Destination destination) {
    this.destination = destination;
  }

  /**
   * Creates realtime channel allowing to input text and voice it (receive audio output)
   *
   * <p>The input channel should be used with a try-with-resources block to ensure that the
   * underlying connection is closed.
   *
   * <p>Example:
   *
   * <pre>{@code
   * try (var textInputChannel = client.textToSpeech(audioOutputConsumer)) {
   *       textInputChannel.sendText("...");
   *       ....
   * }
   * }</pre>
   *
   * This API implements full duplex (input + output) communication channels. Application should
   * logically synchronize their state and close input channel when it is appropriate (e.g. last
   * part of the response has been received via output channel and application does not need to send
   * any other input). When input channel is closed, output channel will be closed automatically and
   * output consumer will not be called anymore.
   *
   * @param audioOutputConsumer - audio consumer of raw PCM mono 24000 Hz little endian output, 16
   *     bit depth
   * @param params - allows for various additional features (e.g. voice configuration or
   *     conversation turn recognition options)
   * @return input channel, allowing for text input
   */
  @Nonnull
  @Beta
  public TextInputChannel textToSpeech(
      @Nonnull final AudioOutputChannel audioOutputConsumer,
      @Nonnull final RealtimeParam... params) {
    final var extraHeaders = destination.asHttp().getHeaders();
    final var headers = new HashMap<String, String>(extraHeaders.size() + 1);
    for (final Header header : extraHeaders) {
      headers.put(header.getName(), header.getValue());
    }
    final var endpoint = getRealtimeEndpoint();

    return new TextToSpeechRealtimeClient(endpoint, headers, audioOutputConsumer, params);
  }

  /**
   * Creates realtime channel allowing for audio conversation with a model
   *
   * <p>The input channel should be used with a try-with-resources block to ensure that the
   * underlying connection is closed.
   *
   * <p>Example:
   *
   * <pre>{@code
   * try (var audioInputChannel = client.speechToSpeech(audioOutputConsumer)) {
   *       audioInputChannel.inputAudio(audioBytesData);
   *       ....
   * }
   * }</pre>
   *
   * This API implements full duplex (input + output) communication channels. Application should
   * logically synchronize their state and close input channel when it is appropriate (e.g. last
   * part of the response has been received via output channel and application does not need to send
   * any other input). When input channel is closed, output channel will be closed automatically and
   * output consumer will not be called anymore.
   *
   * @param audioOutputConsumer - audio consumer of raw PCM mono 24000 Hz little endian output, 16
   *     bit depth
   * @param params - optional configuration params
   * @return input channel, allowing for audio data input (bytes, PCM mono 24000 Hz little endian 16
   *     bit)
   */
  @Nonnull
  @Beta
  public AudioInputChannel speechToSpeech(
      @Nonnull final AudioOutputChannel audioOutputConsumer,
      @Nonnull final RealtimeParam... params) {
    final var extraHeaders = destination.asHttp().getHeaders();
    final var headers = new HashMap<String, String>(extraHeaders.size() + 1);
    for (final Header header : extraHeaders) {
      headers.put(header.getName(), header.getValue());
    }
    final var endpoint = getRealtimeEndpoint();

    return new SpeechToSpeechRealtimeClient(endpoint, headers, audioOutputConsumer, params);
  }

  private String getRealtimeEndpoint() {
    final var sb = new StringBuilder(PATH_BUFFER_SIZE);
    sb.append("wss://");
    final var pathParts = destination.asHttp().getUri().toString().split("//");
    if (pathParts.length != 2) {
      throw new IllegalArgumentException(
          "Invalid destination URI: " + destination.asHttp().getUri());
    }
    sb.append(pathParts[1].replaceFirst("^api\\.", "realtime."));
    sb.append("v1/realtime");
    return sb.toString();
  }
}
