package com.sap.ai.sdk.core.common;

import static com.sap.ai.sdk.core.JacksonConfiguration.getDefaultObjectMapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.annotations.Beta;
import io.vavr.control.Try;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
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
  @Nonnull final Class<R> errorType;

  /** The factory to create exceptions for Http 4xx/5xx responses. */
  @Nonnull final ClientExceptionFactory<E, R> exceptionFactory;

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
      buildAndThrowException(response);
    }
    return parseSuccess(response);
  }

  // The InputStream of the HTTP entity is closed by EntityUtils.toString
  @SuppressWarnings("PMD.CloseResource")
  @Nonnull
  private T parseSuccess(@Nonnull final ClassicHttpResponse response) throws E {
    final HttpEntity responseEntity = response.getEntity();
    if (responseEntity == null) {
      throw exceptionFactory.build("The HTTP Response is empty").setHttpResponse(response);
    }

    val content =
        tryGetContent(responseEntity)
            .getOrElseThrow(
                e ->
                    exceptionFactory
                        .build("Failed to parse response entity.", e)
                        .setHttpResponse(response));
    try {
      return objectMapper.readValue(content, successType);
    } catch (final JsonProcessingException e) {
      log.error("Failed to parse response to type {}", successType);
      throw exceptionFactory.build("Failed to parse response", e).setHttpResponse(response);
    }
  }

  @Nonnull
  private Try<String> tryGetContent(@Nonnull final HttpEntity entity) {
    return Try.of(() -> EntityUtils.toString(entity, StandardCharsets.UTF_8));
  }

  /**
   * Process the error response and throw an exception.
   *
   * @param httpResponse The response to process
   * @throws ClientException if the response is an error (4xx/5xx)
   */
  @SuppressWarnings("PMD.CloseResource")
  protected void buildAndThrowException(@Nonnull final ClassicHttpResponse httpResponse) throws E {

    val entity = httpResponse.getEntity();

    if (entity == null) {
      val message = getErrorMessage(httpResponse, "The HTTP Response is empty");
      throw exceptionFactory.build(message).setHttpResponse(httpResponse);
    }
    val maybeContent = tryGetContent(entity);
    if (maybeContent.isFailure()) {
      val message = getErrorMessage(httpResponse, "Failed to read the response content");
      val baseException = exceptionFactory.build(message).setHttpResponse(httpResponse);
      baseException.addSuppressed(maybeContent.getCause());
      throw baseException;
    }
    val content = maybeContent.get();
    if (content == null || content.isBlank()) {
      val message = getErrorMessage(httpResponse, "Empty or blank response content");
      throw exceptionFactory.build(message).setHttpResponse(httpResponse);
    }

    log.error(
        "The service responded with an HTTP {} ({})",
        httpResponse.getCode(),
        httpResponse.getReasonPhrase());
    val contentType = ContentType.parse(entity.getContentType());
    if (!ContentType.APPLICATION_JSON.isSameMimeType(contentType)) {
      val message = getErrorMessage(httpResponse, "The response Content-Type is not JSON");
      throw exceptionFactory.build(message).setHttpResponse(httpResponse);
    }

    parseErrorResponseAndThrow(content, httpResponse);
  }

  /**
   * Parses the JSON content of an error response and throws a module specific exception.
   *
   * @param content The JSON content of the error response.
   * @param httpResponse The HTTP response that contains the error.
   * @throws ClientException if the response is an error (4xx/5xx)
   */
  protected void parseErrorResponseAndThrow(
      @Nonnull final String content, @Nonnull final ClassicHttpResponse httpResponse) throws E {
    val maybeClientError = Try.of(() -> objectMapper.readValue(content, errorType));
    if (maybeClientError.isFailure()) {
      val message = getErrorMessage(httpResponse, "Failed to parse the JSON error response");
      val baseException = exceptionFactory.build(message).setHttpResponse(httpResponse);
      baseException.addSuppressed(maybeClientError.getCause());
      throw baseException;
    }
    final R clientError = maybeClientError.get();
    val message = getErrorMessage(httpResponse, clientError.getMessage());
    throw exceptionFactory.build(message, clientError, null).setHttpResponse(httpResponse);
  }

  private static String getErrorMessage(
      @Nonnull final ClassicHttpResponse rsp, @Nullable final String additionalMessage) {
    val baseErrorMessage =
        "Request failed with status %d (%s)".formatted(rsp.getCode(), rsp.getReasonPhrase());

    val message = Optional.ofNullable(additionalMessage).orElse("");
    return message.isEmpty() ? baseErrorMessage : "%s: %s".formatted(baseErrorMessage, message);
  }
}
