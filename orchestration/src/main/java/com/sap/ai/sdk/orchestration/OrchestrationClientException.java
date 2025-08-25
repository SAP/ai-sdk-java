package com.sap.ai.sdk.orchestration;

import static com.sap.ai.sdk.orchestration.OrchestrationJacksonConfiguration.getOrchestrationObjectMapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.annotations.Beta;
import com.sap.ai.sdk.core.common.ClientException;
import com.sap.ai.sdk.core.common.ClientExceptionFactory;
import com.sap.ai.sdk.orchestration.model.AzureContentSafetyInput;
import com.sap.ai.sdk.orchestration.model.AzureContentSafetyOutput;
import com.sap.ai.sdk.orchestration.model.Error;
import com.sap.ai.sdk.orchestration.model.ErrorResponse;
import com.sap.ai.sdk.orchestration.model.ErrorResponseStreaming;
import com.sap.ai.sdk.orchestration.model.ErrorStreaming;
import com.sap.ai.sdk.orchestration.model.GenericModuleResult;
import com.sap.ai.sdk.orchestration.model.LlamaGuard38b;
import com.sap.ai.sdk.orchestration.model.ModuleResults;
import com.sap.ai.sdk.orchestration.model.ModuleResultsStreaming;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.StandardException;
import lombok.val;

/** Exception thrown by the {@link OrchestrationClient} in case of an error. */
@StandardException
public class OrchestrationClientException extends ClientException {

  static final ClientExceptionFactory<OrchestrationClientException, OrchestrationError> FACTORY =
      (message, clientError, cause) -> {
        val result = new OrchestrationClientException(message, cause);
        val details = extractModuleResults(clientError).map(GenericModuleResult::getData);
        if (details.orElse(null) instanceof Map<?, ?> m) {
          result.setFilterDetails((Map<String, Object>) m);
        }
        return result.setClientError(clientError);
      };

  private static final ObjectMapper OBJECT_MAPPER = getOrchestrationObjectMapper();

  /** Details about the filters that caused the exception. */
  @Accessors(chain = true)
  @Setter(AccessLevel.PACKAGE)
  @Getter
  @Nullable
  private Map<String, Object> filterDetails = null;

  @Nonnull
  static Optional<GenericModuleResult> extractModuleResults(@Nullable final OrchestrationError e) {
    if (e instanceof OrchestrationError.Synchronous synchronousError) {
      return Optional.of(synchronousError.getErrorResponse())
          .map(ErrorResponse::getError)
          .map(Error::getIntermediateResults)
          .map(ModuleResults::getInputFiltering);
    } else if (e instanceof OrchestrationError.Streaming streamingError) {
      return Optional.of(streamingError.getErrorResponse())
          .map(ErrorResponseStreaming::getError)
          .map(ErrorStreaming::getIntermediateResults)
          .map(ModuleResultsStreaming::getInputFiltering);
    }
    return Optional.empty();
  }

  @Override
  @Nullable
  public OrchestrationError getClientError() {
    return (OrchestrationError) super.getClientError();
  }

  /**
   * Retrieves the {@link ErrorResponse} from the orchestration service, if available.
   *
   * @return The {@link ErrorResponse} object, or {@code null} if not available.
   * @since 1.10.0
   */
  @Beta
  @Nullable
  public ErrorResponse getErrorResponse() {
    if (getClientError() instanceof OrchestrationError.Synchronous orchestrationError) {
      return orchestrationError.getErrorResponse();
    }
    return null;
  }

  /**
   * Retrieves the {@link ErrorResponseStreaming} from the orchestration service, if available.
   *
   * @return The {@link ErrorResponseStreaming} object, or {@code null} if not available.
   * @since 1.10.0
   */
  @Beta
  @Nullable
  public ErrorResponseStreaming getErrorResponseStreaming() {
    if (getClientError() instanceof OrchestrationError.Streaming orchestrationError) {
      return orchestrationError.getErrorResponse();
    }
    return null;
  }

  /**
   * Retrieves the HTTP status code from the original error response, if available.
   *
   * @return the HTTP status code, or {@code null} if not available
   * @since 1.10.0
   */
  @Beta
  @Nullable
  public Integer getStatusCode() {
    return Optional.ofNullable(getErrorResponse())
        .map(ErrorResponse::getError)
        .map(Error::getCode)
        .orElse(null);
  }

  /**
   * Retrieves LlamaGuard 3.8b details from {@code filterDetails}, if present.
   *
   * @return The LlamaGuard38b object, or {@code null} if not found or conversion fails.
   * @throws IllegalArgumentException if the conversion of filter details to {@link LlamaGuard38b}
   *     fails due to invalid content.
   */
  @Nullable
  public LlamaGuard38b getLlamaGuard38b() {
    return Optional.ofNullable(filterDetails)
        .map(details -> details.get("llama_guard_3_8b"))
        .map(obj -> OBJECT_MAPPER.convertValue(obj, LlamaGuard38b.class))
        .orElse(null);
  }

  /**
   * Retrieves Azure Content Safety input details from {@code filterDetails}, if present.
   *
   * @return The AzureContentSafetyInput object, or {@code null} if not found or conversion fails.
   * @throws IllegalArgumentException if the conversion of filter details to {@link
   *     AzureContentSafetyInput} fails due to invalid content.
   */
  @Nullable
  public AzureContentSafetyInput getAzureContentSafetyInput() {
    return Optional.ofNullable(filterDetails)
        .map(details -> details.get("azure_content_safety"))
        .map(obj -> OBJECT_MAPPER.convertValue(obj, AzureContentSafetyInput.class))
        .orElse(null);
  }

  /**
   * Retrieves Azure Content Safety output details from {@code filterDetails}, if present.
   *
   * @return The AzureContentSafetyOutput object, or {@code null} if not found or conversion fails.
   * @throws IllegalArgumentException if the conversion of filter details to {@link
   *     AzureContentSafetyOutput} fails due to invalid content.
   */
  @Nullable
  public AzureContentSafetyOutput getAzureContentSafetyOutput() {
    return Optional.ofNullable(filterDetails)
        .map(details -> details.get("azure_content_safety"))
        .map(obj -> OBJECT_MAPPER.convertValue(obj, AzureContentSafetyOutput.class))
        .orElse(null);
  }
}
