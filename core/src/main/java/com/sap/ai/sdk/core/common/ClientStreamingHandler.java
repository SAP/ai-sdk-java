package com.sap.ai.sdk.core.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.annotations.Beta;
import java.io.IOException;
import java.util.function.BiFunction;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ClassicHttpResponse;

/**
 * Parse incoming JSON responses and handles any errors. For internal use only.
 *
 * @param <D> The type of the response.
 * @param <E> The type of the exception to throw.
 * @since 1.2.0
 */
@Beta
@Slf4j
public class ClientStreamingHandler<D extends StreamedDelta, E extends ClientException>
    extends ClientResponseHandler<D, E> {

  /**
   * Set the {@link ObjectMapper} to use for parsing JSON responses.
   *
   * @param jackson The {@link ObjectMapper} to use
   * @return the current instance of {@link ClientStreamingHandler} with the changed object mapper
   */
  @Nonnull
  public ClientStreamingHandler<D, E> objectMapper(@Nonnull final ObjectMapper jackson) {
    super.objectMapper(jackson);
    return this;
  }

  /**
   * Creates a new instance of the {@link ClientStreamingHandler}.
   *
   * @param deltaType The type of the response.
   * @param errorType The type of the error.
   * @param exceptionType The type of the exception to throw.
   */
  public ClientStreamingHandler(
      @Nonnull final Class<D> deltaType,
      @Nonnull final Class<? extends ClientError> errorType,
      @Nonnull final BiFunction<String, Throwable, E> exceptionType) {
    super(deltaType, errorType, exceptionType);
  }

  /**
   * Processes a {@link ClassicHttpResponse} and returns a {@link Stream} of deltas corresponding to
   * that response.
   *
   * @param response The response to process
   * @return A {@link Stream} of a model class instantiated from the response
   * @throws E in case of a problem or the connection was aborted
   */
  @SuppressWarnings("PMD.CloseResource") // Stream is closed automatically when consumed
  @Nonnull
  public Stream<D> handleStreamingResponse(@Nonnull final ClassicHttpResponse response) throws E {
    if (response.getCode() >= 300) {
      super.buildExceptionAndThrow(response);
    }
    return IterableStreamConverter.lines(response.getEntity(), exceptionConstructor)
        // half of the lines are empty newlines, the last line is "data: [DONE]"
        .filter(line -> !line.isEmpty() && !"data: [DONE]".equals(line.trim()))
        .peek(
            line -> {
              if (!line.startsWith("data: ")) {
                final String msg = "Failed to parse response";
                super.parseErrorAndThrow(line, exceptionConstructor.apply(msg, null));
              }
            })
        .map(
            line -> {
              final String data = line.substring(5); // remove "data: "
              try {
                return objectMapper.readValue(data, responseType);
              } catch (final IOException e) { // exception message e gets lost
                log.error("Failed to parse delta chunk to type {}", responseType);
                throw exceptionConstructor.apply("Failed to parse delta chunk", e);
              }
            });
  }
}
