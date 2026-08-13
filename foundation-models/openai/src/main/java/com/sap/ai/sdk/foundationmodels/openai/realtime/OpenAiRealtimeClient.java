package com.sap.ai.sdk.foundationmodels.openai.realtime;

import com.google.common.annotations.Beta;
import com.sap.ai.sdk.foundationmodels.openai.TextInputChannel;
import com.sap.cloud.sdk.cloudplatform.connectivity.Destination;
import com.sap.cloud.sdk.cloudplatform.connectivity.Header;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;

/**
 * OpenAI client implementation of Realtime API. Abstracts technical implementation, transport and
 * threading and exposes business-level operations (high level interface)
 */
@Beta
public class OpenAiRealtimeClient {

  static final int PATH_BUFFER_SIZE =
      400; // existing URLs are ~120 symbols long, 400 has reasonable margin

  final Destination destination;

  /**
   * Created OpenAI Realtime client for a specific destination
   *
   * @param destination - destination to use
   */
  @Beta
  public OpenAiRealtimeClient(@Nonnull final Destination destination) {
    this.destination = destination;
  }

  /**
   * Creates a realtime channel allowing to input text and voice it (receive audio output)
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
   * logically synchronize their state and close the input channel when it is appropriate (e.g. the
   * last part of the response has been received via the output channel and the application does not
   * need to send any other input). When the input channel is closed, the output channel will be
   * closed automatically and the output consumer will not be called anymore.
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
    return new TextToSpeechRealtimeClient(
        getRealtimeEndpoint(), buildRealtimeHeaders(), audioOutputConsumer, params);
  }

  /**
   * Creates a realtime channel allowing for audio conversation with a model.
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
   * This API implements full duplex (input + output) communication channels. An application should
   * logically synchronize their state and close the input channel when it is appropriate (e.g. the
   * last part of the response has been received via the output channel and the application does not
   * need to send any other input). When the input channel is closed, the output channel will be
   * closed automatically and the output consumer will not be called anymore.
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
    return new SpeechToSpeechRealtimeClient(
        getRealtimeEndpoint(), buildRealtimeHeaders(), audioOutputConsumer, params);
  }

  Map<String, String> buildRealtimeHeaders() {
    final var extraHeaders = destination.asHttp().getHeaders();
    final var headers = new HashMap<String, String>(extraHeaders.size() + 1);
    for (final Header header : extraHeaders) {
      headers.put(header.getName(), header.getValue());
    }
    return headers;
  }

  String getRealtimeEndpoint() {
    final var sb = new StringBuilder(PATH_BUFFER_SIZE);
    sb.append("wss://");
    final var pathParts = destination.asHttp().getUri().toString().split("//");
    if (pathParts.length != 2) {
      throw new IllegalArgumentException(
          "Invalid destination URI: " + destination.asHttp().getUri());
    }
    sb.append(pathParts[1].replaceFirst("^api\\.", "realtime."));
    sb.append("/v1/realtime");
    return sb.toString();
  }
}
