package com.sap.ai.sdk.orchestration;

import com.sap.cloud.sdk.cloudplatform.connectivity.Header;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class RealtimeChannelFactory {
  private static final String AI_RESOURCE_GROUP_HEADER_NAME = "AI-Resource-Group";
  private static final String AI_RESOURCE_GROUP_HEADER_VALUE = "default";
  private static final int PATH_BUFFER_SIZE =
      400; // existing URLs are ~120 symbols long, 400 has reasonable margin

  private final Supplier<HttpDestination> destinationSupplier;

  /**
   * Creates realtime channel allowing to input text and voice it (receive audio output)
   *
   * @param audioOutputConsumer - audio bytes consumer, where byte[] - chunks of raw PCM mono 24000
   *     Hz output and Boolean - marker if current audio reply has been finished (current response
   *     ended)
   * @return input channel, allowing for text input
   */
  public TextInputChannel textToSpeech(
      @Nonnull final BiConsumer<byte[], Boolean> audioOutputConsumer) {
    var destination = destinationSupplier.get();

    var extraHeaders = destination.getHeaders();
    var headers = new HashMap<String, String>(extraHeaders.size() + 1);
    headers.put(AI_RESOURCE_GROUP_HEADER_NAME, AI_RESOURCE_GROUP_HEADER_VALUE);
    for (Header header : extraHeaders) {
      headers.put(header.getName(), header.getValue());
    }

    var sb = new StringBuilder(PATH_BUFFER_SIZE);
    sb.append("wss://");
    var pathParts = destination.getUri().toString().split("//");
    if (pathParts.length != 2) {
      throw new IllegalArgumentException("Invalid destination URI: " + destination.getUri());
    }
    sb.append(pathParts[1].replaceFirst("^api\\.", "realtime."));
    sb.append("v1/realtime");
    var endpoint = sb.toString();

    return new TextToSpeechRealtimeClient(endpoint, headers, audioOutputConsumer);
  }
}
