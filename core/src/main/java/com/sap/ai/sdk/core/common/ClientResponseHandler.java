package com.sap.ai.sdk.core.common;

import static com.sap.ai.sdk.core.JacksonConfiguration.getDefaultObjectMapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.annotations.Beta;
import io.vavr.control.Try;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.function.BiFunction;
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
 * @param <E> The type of the exception to throw.
 * @since 1.1.0
 */
@Beta
@Slf4j
@RequiredArgsConstructor
public class ClientResponseHandler<T, E extends ClientException>
    implements HttpClientResponseHandler<T> {
  @Nonnull final Class<T> responseType;
  @Nonnull private final Class<? extends ClientError> errorType;
  @Nonnull final BiFunction<String, Throwable, E> exceptionConstructor;

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
  public ClientResponseHandler<T, E> objectMapper(@Nonnull final ObjectMapper jackson) {
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
      buildExceptionAndThrow(response);
    }
    return parseResponse(response);
  }

  // The InputStream of the HTTP entity is closed by EntityUtils.toString
  @SuppressWarnings("PMD.CloseResource")
  @Nonnull
  private T parseResponse(@Nonnull final ClassicHttpResponse response) throws E {
    final HttpEntity responseEntity = response.getEntity();
    if (responseEntity == null) {
      throw exceptionConstructor.apply("Response was empty.", null);
    }
    val content = getContent(responseEntity);
    try {
      return objectMapper.readValue(content, responseType);
    } catch (final JsonProcessingException e) {
      log.error("Failed to parse response to type {}", responseType);
      throw exceptionConstructor.apply("Failed to parse response", e);
    }
  }

  @Nonnull
  private String getContent(@Nonnull final HttpEntity entity) {
    try {
      return EntityUtils.toString(entity, StandardCharsets.UTF_8);
    } catch (IOException | ParseException e) {
      throw exceptionConstructor.apply("Failed to read response content", e);
    }
  }

  /**
   * Parse the error response and throw an exception.
   *
   * @param response The response to process
   */
  @SuppressWarnings("PMD.CloseResource")
  public void buildExceptionAndThrow(@Nonnull final ClassicHttpResponse response) throws E {
    val exception =
        exceptionConstructor.apply(
            "Request failed with status %s %s"
                .formatted(response.getCode(), response.getReasonPhrase()),
            null);
    val entity = response.getEntity();
    if (entity == null) {
      throw exception;
    }
    val maybeContent = Try.of(() -> getContent(entity));
    if (maybeContent.isFailure()) {
      exception.addSuppressed(maybeContent.getCause());
      throw exception;
    }
    val content = maybeContent.get();
    if (content.isBlank()) {
      throw exception;
    }

    log.error(
        "The service responded with an HTTP {} ({})",
        response.getCode(),
        response.getReasonPhrase());
    val contentType = ContentType.parse(entity.getContentType());
    if (!ContentType.APPLICATION_JSON.isSameMimeType(contentType)) {
      throw exception;
    }

    parseErrorAndThrow(content, exception);
  }

  /**
   * Parse the error response and throw an exception.
   *
   * @param errorResponse the error response, most likely a unique JSON class.
   * @param baseException a base exception to add the error message to.
   */
  public void parseErrorAndThrow(
      @Nonnull final String errorResponse, @Nonnull final E baseException) throws E {
    val maybeError = Try.of(() -> objectMapper.readValue(errorResponse, errorType));
    if (maybeError.isFailure()) {
      baseException.addSuppressed(maybeError.getCause());
      throw baseException;
    }

    val error = Objects.requireNonNullElse(maybeError.get().getMessage(), "");
    val message = "%s and error message: '%s'".formatted(baseException.getMessage(), error);
    throw exceptionConstructor.apply(message, baseException);
  }
}
