package com.sap.ai.sdk.foundationmodels.openai;

import static com.sap.ai.sdk.foundationmodels.openai.OpenAiClient.JACKSON;
import static com.sap.ai.sdk.foundationmodels.openai.OpenAiResponseHandler.buildExceptionAndThrow;
import static com.sap.ai.sdk.foundationmodels.openai.OpenAiResponseHandler.parseErrorAndThrow;

import com.sap.ai.sdk.foundationmodels.openai.model.StreamedDelta;
import java.io.IOException;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ClassicHttpResponse;

@Slf4j
@RequiredArgsConstructor
class OpenAiStreamingHandler<D extends StreamedDelta> {

  @Nonnull private final Class<D> deltaType;

  /**
   * @param response The response to process
   * @return A {@link Stream} of a model class instantiated from the response
   */
  @SuppressWarnings("PMD.CloseResource") // Stream is closed automatically when consumed
  @Nonnull
  Stream<D> handleResponse(@Nonnull final ClassicHttpResponse response)
      throws OpenAiClientException {
    if (response.getCode() >= 300) {
      buildExceptionAndThrow(response);
    }
    return IterableStreamConverter.lines(response.getEntity())
        // half of the lines are empty newlines, the last line is "data: [DONE]"
        .filter(line -> !line.isEmpty() && !"data: [DONE]".equals(line.trim()))
        .peek(
            line -> {
              if (!line.startsWith("data: ")) {
                final String msg = "Failed to parse response from OpenAI model";
                parseErrorAndThrow(line, new OpenAiClientException(msg));
              }
            })
        .map(
            line -> {
              final String data = line.substring(5); // remove "data: "
              try {
                return JACKSON.readValue(data, deltaType);
              } catch (final IOException e) {
                log.error("Failed to parse the following response from OpenAI model: {}", line);
                throw new OpenAiClientException("Failed to parse delta message: " + line, e);
              }
            });
  }
}
