package com.sap.ai.sdk.core.common;

import static com.sap.ai.sdk.core.JacksonConfiguration.getDefaultObjectMapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.annotations.Beta;
import io.vavr.control.Try;
import java.nio.charset.StandardCharsets;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.io.entity.EntityUtils;

/**
 * Parse incoming JSON responses and handles any errors. For internal use only.
 *
 * @param <T> The type of the successful response.
 * @param <E> The type of the exception to throw.
 * @param <R> The type of the error response.
 * @since 1.1.0
 */
@Beta
@Slf4j
@RequiredArgsConstructor
public class ClientResponseHandler<T, R extends ClientError, E extends ClientException>
    implements HttpClientResponseHandler<T> {
  /** The HTTP success response type */
  @Nonnull final Class<T> successType;

  /** The HTTP error response type */
  @Nonnull protected final Class<R> errorType;

  /** The factory to create exceptions for Http 4xx/5xx responses. */
  @Nonnull protected final ClientExceptionFactory<E, R> exceptionFactory;

  /** The parses for JSON responses, will be private once we can remove mixins */
  @Nonnull ObjectMapper objectMapper = getDefaultObjectMapper();

  /**
   * Set the {@link ObjectMapper} to use for parsing JSON responses.
   *
   * @param jackson The {@link ObjectMapper} to use
   * @return the current instance of {@link ClientResponseHandler} with the changed object mapper
   */
  @Beta
  @Nonnull
  public ClientResponseHandler<T, R, E> objectMapper(@Nonnull final ObjectMapper jackson) {
    objectMapper = jackson;
    return this;
  }

  /**
   * Processes a {@link ClassicHttpResponse} and returns some value corresponding to that response.
   *
   * @param response The response to process
   * @return A model class instantiated from the response
   * @throws E in case of a problem or the connection was aborted
   */
  @Nonnull
  @Override
  public T handleResponse(@Nonnull final ClassicHttpResponse response) throws E {
    if (response.getCode() >= 300) {
      buildException(response);
    }
    return parseSuccess(response);
  }

  // The InputStream of the HTTP entity is closed by EntityUtils.toString
  @SuppressWarnings("PMD.CloseResource")
  @Nonnull
  private T parseSuccess(@Nonnull final ClassicHttpResponse response) throws E {
    final HttpEntity responseEntity = response.getEntity();
    if (responseEntity == null) {
      throw exceptionFactory.build("Response was empty.", null);
    }

    val content =
        tryGetContent(responseEntity)
            .getOrElseThrow(e -> exceptionFactory.build("Failed to parse response entity.", e));
    log.debug("Parsing response from JSON response: {}", content);
    try {
      return objectMapper.readValue(content, successType);
    } catch (final JsonProcessingException e) {
      log.error("Failed to parse the following response: {}", content);
      throw exceptionFactory.build("Failed to parse response:", e);
    }
  }

  @Nonnull
  private Try<String> tryGetContent(@Nonnull final HttpEntity entity) {
    return Try.of(() -> EntityUtils.toString(entity, StandardCharsets.UTF_8));
  }

  /**
   * Parse the error response and throw an exception.
   *
   * @param httpResponse The response to process
   * @return An instance of the specific exception type returned by exceptionFactory
   */
  @SuppressWarnings("PMD.CloseResource")
  @Nonnull
  protected E buildException(@Nonnull final ClassicHttpResponse httpResponse) {
    val baseErrorMessage =
        "Request failed with status %d %s"
            .formatted(httpResponse.getCode(), httpResponse.getReasonPhrase());
    val baseException = exceptionFactory.build(baseErrorMessage, null);

    val entity = httpResponse.getEntity();

    if (entity == null) {
      return baseException;
    }
    val maybeContent = tryGetContent(entity);
    if (maybeContent.isFailure()) {
      baseException.addSuppressed(maybeContent.getCause());
      return baseException;
    }
    val content = maybeContent.get();
    if (content.isBlank()) {
      return baseException;
    }
    log.error("The service responded with an HTTP error and the following content: {}", content);
    val contentType = ContentType.parse(entity.getContentType());
    if (!ContentType.APPLICATION_JSON.isSameMimeType(contentType)) {
      return baseException;
    }
    return parseErrorAndThrow(content, baseErrorMessage, baseException);
  }

  @SuppressWarnings("PMD.CloseResource")
  public E parseErrorAndThrow(
      @Nonnull final String content, @Nonnull final String baseErrorMessage,
      @Nonnull final E baseException) {
    val maybeClientError = Try.of(() -> objectMapper.readValue(content, errorType));
    if (maybeClientError.isFailure()) {
      baseException.addSuppressed(maybeClientError.getCause());
      return baseException;
    }
    final R clientError = maybeClientError.get();
    val extendErrorMessage = "%s: %s".formatted(baseErrorMessage, clientError.getMessage());
    return exceptionFactory.buildFromClientError(extendErrorMessage, clientError);
  }
}
