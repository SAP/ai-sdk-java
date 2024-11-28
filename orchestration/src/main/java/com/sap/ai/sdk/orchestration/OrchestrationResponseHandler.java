package com.sap.ai.sdk.orchestration;

import static com.sap.ai.sdk.orchestration.OrchestrationClient.JACKSON;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sap.ai.sdk.orchestration.generated.ErrorResponse;
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

@Slf4j
@RequiredArgsConstructor
class OrchestrationResponseHandler<T> implements HttpClientResponseHandler<T> {
  @Nonnull private final Class<T> responseType;

  @Nonnull
  private static String getContent(@Nonnull final HttpEntity entity) {
    try {
      return EntityUtils.toString(entity, StandardCharsets.UTF_8);
    } catch (IOException | ParseException e) {
      throw new OrchestrationClientException("Failed to read response content.", e);
    }
  }

  // The InputStream of the HTTP entity is closed by EntityUtils.toString
  @SuppressWarnings("PMD.CloseResource")
  static void buildExceptionAndThrow(@Nonnull final ClassicHttpResponse response)
      throws OrchestrationClientException {
    val exception =
        new OrchestrationClientException(
            "Request to orchestration service failed with status %s %s"
                .formatted(response.getCode(), response.getReasonPhrase()));
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
        "The orchestration service responded with an HTTP error and the following content: {}",
        content);
    val contentType = ContentType.parse(entity.getContentType());
    if (!ContentType.APPLICATION_JSON.isSameMimeType(contentType)) {
      throw exception;
    }

    parseErrorAndThrow(content, exception);
  }

  /**
   * Parse the error response and throw an exception.
   *
   * @param errorResponse the error response, most likely a JSON of {@link ErrorResponse}.
   * @param baseException a base exception to add the error message to.
   */
  static void parseErrorAndThrow(
      @Nonnull final String errorResponse,
      @Nonnull final OrchestrationClientException baseException)
      throws OrchestrationClientException {
    val maybeError = Try.of(() -> JACKSON.readValue(errorResponse, ErrorResponse.class));
    if (maybeError.isFailure()) {
      baseException.addSuppressed(maybeError.getCause());
      throw baseException;
    }

    throw new OrchestrationClientException(
        "%s and error message: '%s'"
            .formatted(baseException.getMessage(), maybeError.get().getMessage()));
  }

  /**
   * Processes a {@link ClassicHttpResponse} and returns some value corresponding to that response.
   *
   * @param response The response to process
   * @return A model class instantiated from the response
   * @throws OrchestrationClientException in case of a problem or the connection was aborted
   */
  @Override
  public T handleResponse(@Nonnull final ClassicHttpResponse response)
      throws OrchestrationClientException {
    if (response.getCode() >= 300) {
      buildExceptionAndThrow(response);
    }
    val result = parseResponse(response);
    log.debug("Received the following response from orchestration service: {}", result);
    return result;
  }

  // The InputStream of the HTTP entity is closed by EntityUtils.toString
  @SuppressWarnings("PMD.CloseResource")
  @Nonnull
  private T parseResponse(@Nonnull final ClassicHttpResponse response)
      throws OrchestrationClientException {
    final HttpEntity responseEntity = response.getEntity();
    if (responseEntity == null) {
      throw new OrchestrationClientException("Response from Orchestration service was empty.");
    }
    val content = getContent(responseEntity);
    log.debug("Parsing response from JSON response: {}", content);
    try {
      return JACKSON.readValue(content, responseType);
    } catch (final JsonProcessingException e) {
      log.error("Failed to parse the following response from orchestration service: {}", content);
      throw new OrchestrationClientException(
          "Failed to parse response from orchestration service", e);
    }
  }
}
