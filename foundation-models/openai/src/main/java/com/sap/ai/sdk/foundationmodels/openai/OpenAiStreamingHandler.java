package com.sap.ai.sdk.foundationmodels.openai;

import static com.sap.ai.sdk.foundationmodels.openai.OpenAiClient.JACKSON;
import static com.sap.ai.sdk.foundationmodels.openai.OpenAiResponseHandler.buildExceptionAndThrow;
import static com.sap.ai.sdk.foundationmodels.openai.OpenAiResponseHandler.parseErrorAndThrow;

import com.sap.ai.sdk.foundationmodels.openai.model.DeltaAggregatable;
import com.sap.ai.sdk.foundationmodels.openai.model.StreamedDelta;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpEntity;

@Slf4j
@RequiredArgsConstructor
class OpenAiStreamingHandler<D extends StreamedDelta, T extends DeltaAggregatable<D>> {

  @Nonnull private final Class<D> deltaType;
  @Nonnull private final Class<T> totalType;

  /**
   * Processes a {@link ClassicHttpResponse} and returns some value corresponding to that response.
   *
   * @param response The response to process
   * @return A {@link OpenAiStream} of a model class instantiated from the response
   * @throws OpenAiClientException in case of a problem or the connection was aborted
   */
  @Nonnull
  public OpenAiStream<D, T> handleResponse(@Nonnull final ClassicHttpResponse response)
      throws OpenAiClientException {
    if (response.getCode() >= 300) {
      buildExceptionAndThrow(response);
    }
    return parseResponse(response);
  }

  /**
   * @param response The response to process
   * @return A {@link OpenAiStream} of a model class instantiated from the response
   * @author stippi
   */
  private OpenAiStream<D, T> parseResponse(@Nonnull final ClassicHttpResponse response)
      throws OpenAiClientException {
    final HttpEntity responseEntity = response.getEntity();
    if (responseEntity == null) {
      throw new OpenAiClientException("Response from OpenAI model was empty.");
    }
    InputStream inputStream;
    try {
      inputStream = responseEntity.getContent();
    } catch (IOException e) {
      throw new OpenAiClientException("Failed to read response content.", e);
    }
    final var br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

    OpenAiStream<D, T> output = new OpenAiStream<D, T>();
    try {
      output.setTotalOutput(totalType.getDeclaredConstructor().newInstance());
    } catch (InstantiationException
        | IllegalAccessException
        | NoSuchMethodException
        | InvocationTargetException e) {
      throw new OpenAiClientException(e);
    }

    // https://developer.mozilla.org/en-US/docs/Web/API/Server-sent_events/Using_server-sent_events#event_stream_format
    Stream<D> deltaStream =
        br.lines()
            .filter(
                responseLine ->
                    // half of the lines are empty newlines, the last line is "data: [DONE]"
                    !responseLine.isEmpty() && !"data: [DONE]".equals(responseLine.trim()))
            .peek(
                responseLine -> {
                  if (!responseLine.startsWith("data: ")) {
                    parseErrorAndThrow(responseLine, new OpenAiClientException());
                  }
                })
            .map(
                responseLine -> {
                  String data = responseLine.substring(5).replace("delta", "message");
                  try {
                    D delta = JACKSON.readValue(data, deltaType);
                    output.addDelta(delta);
                    return delta;
                  } catch (IOException e) {
                    throw new RuntimeException(e);
                  }
                });
    return output.setDeltaStream(deltaStream);
  }
}
