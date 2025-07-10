package com.sap.ai.sdk.core.common;

import static com.sap.ai.sdk.core.JacksonConfiguration.getDefaultObjectMapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.annotations.Beta;
import io.vavr.control.Try;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.io.entity.EntityUtils;

/**
 * Parse incoming JSON responses and handles any errors. For internal use only.
 *
 * @param <T> The type of the response.
 * @param <R> The type of the error.
 * @since 1.1.0
 */
@Beta
@Slf4j
@RequiredArgsConstructor
public class ClientResponseHandler<T, R extends ClientError>
    implements HttpClientResponseHandler<T> {
  @Nonnull protected final Class<T> responseType;
  @Nonnull protected final Class<R> errorType;
  @Nonnull protected final ClientExceptionFactory<? extends ClientException, R> exceptionFactory;

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
  public ClientResponseHandler<T, R> objectMapper(@Nonnull final ObjectMapper jackson) {
    objectMapper = jackson;
    return this;
  }

  /**
   * Processes a {@link ClassicHttpResponse} and returns some value corresponding to that response.
   *
   * @param response The response to process
   * @return A model class instantiated from the response
   */
  @Nonnull
  @Override
  public T handleResponse(@Nonnull final ClassicHttpResponse response) {
    if (response.getCode() >= 300) {
      buildExceptionAndThrow(response);
    }
    return parseSuccess(response);
  }

  // The InputStream of the HTTP entity is closed by EntityUtils.toString
  @SuppressWarnings("PMD.CloseResource")
  @Nonnull
  private T parseSuccess(@Nonnull final ClassicHttpResponse response) {
    final HttpEntity responseEntity = response.getEntity();
    if (responseEntity == null) {
      throw exceptionFactory.create("Response was empty.", null);
    }
    val content = getContent(responseEntity);
    log.debug("Parsing response from JSON response: {}", content);
    try {
      return objectMapper.readValue(content, responseType);
    } catch (final JsonProcessingException e) {
      log.error("Failed to parse the following response: {}", content);
      throw exceptionFactory.create("Failed to parse response", e);
    }
  }

  @Nonnull
  private String getContent(@Nonnull final HttpEntity entity) {
    try {
      return EntityUtils.toString(entity, StandardCharsets.UTF_8);
    } catch (IOException | ParseException e) {
      throw exceptionFactory.create("Failed to read response content.", e);
    }
  }

  /**
   * Parse the error response and throw an exception.
   *
   * @param httpResponse The response to process
   * @throws ClientException if the response is an error (4xx/5xx)
   */
  @SuppressWarnings("PMD.CloseResource")
  protected void buildExceptionAndThrow(@Nonnull final ClassicHttpResponse httpResponse) {
    val baseException =
        exceptionFactory.create(
            "Request failed with status %s %s"
                .formatted(httpResponse.getCode(), httpResponse.getReasonPhrase()),
            null);
    val entity = httpResponse.getEntity();
    if (entity == null) {
      throw baseException;
    }
    val maybeContent = Try.of(() -> getContent(entity));
    if (maybeContent.isFailure()) {
      baseException.addSuppressed(maybeContent.getCause());
      throw baseException;
    }
    val content = maybeContent.get();
    if (content.isBlank()) {
      throw baseException;
    }
    log.error("The service responded with an HTTP error and the following content: {}", content);
    val contentType = ContentType.parse(entity.getContentType());
    if (!ContentType.APPLICATION_JSON.isSameMimeType(contentType)) {
      throw baseException;
    }
    val maybeError = Try.of(() -> objectMapper.readValue(content, errorType));
    if (maybeError.isFailure()) {
      baseException.addSuppressed(maybeError.getCause());
      throw baseException;
    }

    throw exceptionFactory.fromClientError(maybeError.get());
  }
}
