package com.sap.ai.sdk.foundationmodels.openai;

import static com.sap.ai.sdk.foundationmodels.openai.OpenAiClient.JACKSON;
import static com.sap.ai.sdk.foundationmodels.openai.OpenAiResponseHandler.buildExceptionAndThrow;
import static com.sap.ai.sdk.foundationmodels.openai.OpenAiResponseHandler.parseErrorAndThrow;

import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiChatCompletionStream;
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

@Slf4j
@RequiredArgsConstructor
class OpenAiStreamingHandler<T> {

  @Nonnull private final Class<T> responseType;

  /**
   * Processes a {@link ClassicHttpResponse} and returns some value corresponding to that response.
   *
   * @param response The response to process
   * @return A {@link OpenAiChatCompletionStream} of a model class instantiated from the response
   * @throws OpenAiClientException in case of a problem or the connection was aborted
   */
  @Nonnull
  public OpenAiChatCompletionStream<T> handleResponse(@Nonnull final ClassicHttpResponse response)
      throws OpenAiClientException {
    if (response.getCode() >= 300) {
      buildExceptionAndThrow(response);
    }
    return parseResponse(response);
  }

  /**
   * @param response The response to process
   * @return A {@link OpenAiChatCompletionStream} of a model class instantiated from the response
   * @author stippi
   */
  private OpenAiChatCompletionStream<T> parseResponse(@Nonnull final ClassicHttpResponse response)
      throws OpenAiClientException {

    InputStream inputStream;
    try {
      inputStream = response.getEntity().getContent();
    } catch (IOException e) {
      throw new OpenAiClientException("Failed to read response content.", e);
    }
    var output = new OpenAiChatCompletionStream<T>();
    BufferedReader br =
        new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

    // TODO: set total
    Stream<T> deltaStream =
        br.lines()
            .filter(
                responseLine ->
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
                    return JACKSON.readValue(data, responseType);
                  } catch (IOException e) {
                    throw new RuntimeException(e);
                  }
                });
    return output.setDeltaStream(deltaStream);
  }
}
