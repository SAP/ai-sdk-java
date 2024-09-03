package com.sap.ai.sdk.foundationmodels.openai;

import static com.sap.ai.sdk.foundationmodels.openai.OpenAiClient.JACKSON;
import static com.sap.ai.sdk.foundationmodels.openai.OpenAiResponseHandler.buildExceptionAndThrow;
import static com.sap.ai.sdk.foundationmodels.openai.OpenAiResponseHandler.parseErrorAndThrow;

import com.sap.ai.sdk.foundationmodels.openai.model.StreamedDelta;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpEntity;

@Slf4j
@RequiredArgsConstructor
class OpenAiStreamingHandler<D extends StreamedDelta> {

  @Nonnull private final Class<D> deltaType;

  @Nonnull
  Stream<D> handleResponse(@Nonnull final ClassicHttpResponse response)
      throws OpenAiClientException {
    if (response.getCode() >= 300) {
      buildExceptionAndThrow(response);
    }
    return parseResponse(response);
  }

  /**
   * @param response The response to process
   * @return A {@link Stream} of a model class instantiated from the response
   * @author stippi
   */
  // The stream is closed by the user of the Stream
  @SuppressWarnings("PMD.CloseResource")
  private Stream<D> parseResponse(@Nonnull final ClassicHttpResponse response)
      throws OpenAiClientException {
    final HttpEntity responseEntity = response.getEntity();
    if (responseEntity == null) {
      throw new OpenAiClientException("Response from OpenAI model was empty.");
    }
    final InputStream inputStream;
    try {
      inputStream = responseEntity.getContent();
    } catch (IOException e) {
      throw new OpenAiClientException("Failed to read response content.", e);
    }
    final var br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

    // https://developer.mozilla.org/en-US/docs/Web/API/Server-sent_events/Using_server-sent_events#event_stream_format
    return br.lines()
        .filter(
            responseLine ->
                // half of the lines are empty newlines, the last line is "data: [DONE]"
                !responseLine.isEmpty() && !"data: [DONE]".equals(responseLine.trim()))
        .peek(
            responseLine -> {
              if (!responseLine.startsWith("data: ")) {
                parseErrorAndThrow(
                    responseLine,
                    new OpenAiClientException("Failed to parse response from OpenAI model"));
              }
            })
        .map(
            responseLine -> {
              final String data = responseLine.substring(5); // remove "data: "
              try {
                return JACKSON.readValue(data, deltaType);
              } catch (final IOException e) {
                log.error(
                    "Failed to parse the following response from OpenAI model: {}", responseLine);
                throw new OpenAiClientException(
                    "Failed to parse delta message: " + responseLine, e);
              }
            })
        .onClose(
            () -> {
              try {
                inputStream.close();
              } catch (IOException e) {
                log.error("Could not close HTTP input stream", e);
              }
            });
  }
}
