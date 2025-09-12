package com.sap.ai.sdk.core.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.annotations.Beta;
import java.io.IOException;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ClassicHttpResponse;

/**
 * Parse incoming JSON responses and handles any errors. For internal use only.
 *
 * @param <D> The type of the response.
 * @param <E> The type of the exception to throw.
 * @param <R> The type of the error.
 * @since 1.2.0
 */
@Beta
@Slf4j
public class ClientStreamingHandler<
        D extends StreamedDelta, R extends ClientError, E extends ClientException>
    extends ClientResponseHandler<D, R, E> {

  /**
   * Set the {@link ObjectMapper} to use for parsing JSON responses.
   *
   * @param jackson The {@link ObjectMapper} to use
   * @return the current instance of {@link ClientStreamingHandler} with the changed object mapper
   */
  @Nonnull
  public ClientStreamingHandler<D, R, E> objectMapper(@Nonnull final ObjectMapper jackson) {
    super.objectMapper(jackson);
    return this;
  }

  /**
   * Creates a new instance of the {@link ClientStreamingHandler}.
   *
   * @param deltaType The type of the response.
   * @param errorType The type of the error.
   * @param exceptionFactory The factory to create exceptions.
   */
  public ClientStreamingHandler(
      @Nonnull final Class<D> deltaType,
      @Nonnull final Class<? extends R> errorType,
      @Nonnull final ClientExceptionFactory<E, R> exceptionFactory) {
    super(deltaType, errorType, exceptionFactory);
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
      super.buildAndThrowException(response);
    }

    return IterableStreamConverter.lines(response, exceptionFactory)
        // half of the lines are empty newlines, the last line is "data: [DONE]"
        .filter(line -> !line.isEmpty() && !"data: [DONE]".equals(line.trim()))
        .peek(
            line -> {
              if (!line.startsWith("data: ")) {
                final String msg = "Failed to parse response";
                throw exceptionFactory.build(msg).setHttpResponse(response);
              }
            })
        .map(
            line -> {
              final String data = line.substring(5); // remove "data: "
              try {
                final JsonNode jsonNode = objectMapper.readTree(data);
                if (jsonNode.has("error")) {
                  throwErrorType(response, data);
                }
                return objectMapper.treeToValue(jsonNode, successType);
              } catch (final IOException e) {
                log.error("Failed to parse delta chunk to type {}", successType);
                final String message = "Failed to parse delta chunk";
                throw exceptionFactory.build(message, e).setHttpResponse(response);
              }
            });
  }

  private void throwErrorType(final @Nonnull ClassicHttpResponse response, final String data)
      throws JsonProcessingException, E {
    final R error = objectMapper.readValue(data, errorType);
    final String msg =
        (error != null && error.getMessage() != null)
            ? error.getMessage()
            : "Error, unable to parse http response.";
    throw exceptionFactory.build(msg).setHttpResponse(response);
  }
}
