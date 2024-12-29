package com.sap.ai.sdk.core.commons;

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
  @Nonnull private final Class<T> responseType;
  @Nonnull private final Class<? extends ClientError> errorType;
  @Nonnull private final BiFunction<String, Throwable, E> exceptionType;

  /** The parses for JSON responses, will be private once we can remove mixins */
  @Nonnull public ObjectMapper JACKSON = getDefaultObjectMapper();

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
      throw exceptionType.apply("Response was empty.", null);
    }
    val content = getContent(responseEntity);
    log.debug("Parsing response from JSON response: {}", content);
    try {
      return JACKSON.readValue(content, responseType);
    } catch (final JsonProcessingException e) {
      log.error("Failed to parse the following response: {}", content);
      throw exceptionType.apply("Failed to parse response", e);
    }
  }

  @Nonnull
  private String getContent(@Nonnull final HttpEntity entity) {
    try {
      return EntityUtils.toString(entity, StandardCharsets.UTF_8);
    } catch (IOException | ParseException e) {
      throw exceptionType.apply("Failed to read response content.", e);
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
        exceptionType.apply(
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

    log.error("The service responded with an HTTP error and the following content: {}", content);
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
    val maybeError = Try.of(() -> JACKSON.readValue(errorResponse, errorType));
    if (maybeError.isFailure()) {
      baseException.addSuppressed(maybeError.getCause());
      throw baseException;
    }

    val error = Objects.requireNonNullElse(maybeError.get().getMessage(), "");
    val message = "%s and error message: '%s'".formatted(baseException.getMessage(), error);
    throw exceptionType.apply(message, baseException);
  }
}