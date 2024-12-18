package com.sap.ai.sdk.orchestration;

import static com.sap.ai.sdk.orchestration.OrchestrationClient.JACKSON;

import com.sap.ai.sdk.core.commons.ClientResponseHandler;
import java.io.IOException;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ClassicHttpResponse;

@Slf4j
@RequiredArgsConstructor
class OrchestrationStreamingHandler<D extends StreamedDelta> {

  @Nonnull private final Class<D> deltaType;
  private static final ClientResponseHandler<OrchestrationError, OrchestrationClientException>
      HANDLER =
          new ClientResponseHandler<>(
              OrchestrationError.class,
              OrchestrationError.class,
              OrchestrationClientException::new);

  static {
    HANDLER.JACKSON = JACKSON;
  }

  /**
   * @param response The response to process
   * @return A {@link Stream} of a model class instantiated from the response
   */
  @SuppressWarnings("PMD.CloseResource") // Stream is closed automatically when consumed
  @Nonnull
  Stream<D> handleResponse(@Nonnull final ClassicHttpResponse response)
      throws OrchestrationClientException {
    if (response.getCode() >= 300) {
      HANDLER.buildExceptionAndThrow(response);
    }
    return IterableStreamConverter.lines(response.getEntity())
        // half of the lines are empty newlines, the last line is "data: [DONE]"
        .peek(line -> log.info("Handler: {}", line))
        .filter(line -> !line.isEmpty() && !"data: [DONE]".equals(line.trim()))
        .peek(
            line -> {
              if (!line.startsWith("data: ")) {
                final String msg = "Failed to parse response from the Orchestration service";
                HANDLER.parseErrorAndThrow(line, new OrchestrationClientException(msg));
              }
            })
        .map(
            line -> {
              final String data = line.substring(5); // remove "data: "
              try {
                return JACKSON.readValue(data, deltaType);
              } catch (final IOException e) { // exception message e gets lost
                log.error(
                    "Failed to parse the following response from the Orchestration service: {}",
                    line);
                throw new OrchestrationClientException("Failed to parse delta message: " + line, e);
              }
            });
  }
}
