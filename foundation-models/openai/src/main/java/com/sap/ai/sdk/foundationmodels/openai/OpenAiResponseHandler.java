package com.sap.ai.sdk.foundationmodels.openai;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.ai.sdk.foundationmodels.openai.model.OpenAiError;
import io.vavr.control.Try;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.io.entity.EntityUtils;

@Slf4j
@RequiredArgsConstructor
class OpenAiResponseHandler<T> implements HttpClientResponseHandler<T> {

  @Nonnull private final Class<T> responseType;
  @Nonnull private final ObjectMapper jackson;

  @Override
  public T handleResponse(@Nonnull final ClassicHttpResponse response)
      throws OpenAiClientException {
    if (response.getCode() >= 300) {
      buildExceptionAndThrow(response);
    }
    return parseResponse(response);
  }

  // The InputStream of the HTTP entity is closed by EntityUtils.toString
  @SuppressWarnings("PMD.CloseResource")
  @Nonnull
  private T parseResponse(@Nonnull final ClassicHttpResponse response) {
    final HttpEntity responseEntity = response.getEntity();
    if (responseEntity == null) {
      throw new OpenAiClientException("Response from OpenAI model was empty.");
    }
    final var content = getContent(responseEntity);
    try {
      return jackson.readValue(content, responseType);
    } catch (final JsonProcessingException e) {
      log.error("Failed to parse the following response from OpenAI model: {}", content);
      throw new OpenAiClientException("Failed to parse response from OpenAI model", e);
    }
  }

  @Nonnull
  private static String getContent(@Nonnull final HttpEntity entity) {
    try {
      return EntityUtils.toString(entity, StandardCharsets.UTF_8);
    } catch (IOException | ParseException e) {
      throw new OpenAiClientException("Failed to read response content.", e);
    }
  }

  // The InputStream of the HTTP entity is closed by EntityUtils.toString
  @SuppressWarnings("PMD.CloseResource")
  private void buildExceptionAndThrow(@Nonnull final ClassicHttpResponse response) {
    final var exception =
        new OpenAiClientException(
            "Request to OpenAI model failed with status %s %s "
                .formatted(response.getCode(), response.getReasonPhrase()));
    final var entity = response.getEntity();
    if (entity == null) {
      throw exception;
    }
    final var maybeContent = Try.of(() -> getContent(entity));
    if (maybeContent.isFailure()) {
      exception.addSuppressed(maybeContent.getCause());
      throw exception;
    }
    final var content = maybeContent.get();
    if (content.isBlank()) {
      throw exception;
    }

    log.error("OpenAI model responded with an HTTP error and the following content: {}", content);
    final var contentType = ContentType.parse(entity.getContentType());
    if (!ContentType.APPLICATION_JSON.isSameMimeType(contentType)) {
      throw exception;
    }

    final var maybeError = Try.of(() -> jackson.readValue(content, OpenAiError.class));
    if (maybeError.isFailure()) {
      exception.addSuppressed(maybeError.getCause());
      throw exception;
    }

    final var error = maybeError.get().getError();
    if (error == null) {
      throw exception;
    }
    final var message =
        "Request to OpenAI model failed with %s %s and error message: '%s'"
            .formatted(response.getCode(), response.getReasonPhrase(), error.getMessage());
    throw new OpenAiClientException(message);
  }
}
