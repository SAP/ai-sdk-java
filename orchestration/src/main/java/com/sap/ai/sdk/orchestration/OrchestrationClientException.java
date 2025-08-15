package com.sap.ai.sdk.orchestration;

import com.google.common.annotations.Beta;
import com.sap.ai.sdk.core.common.ClientException;
import com.sap.ai.sdk.core.common.ClientExceptionFactory;
import com.sap.ai.sdk.orchestration.OrchestrationFilterException.Input;
import com.sap.ai.sdk.orchestration.model.Error;
import com.sap.ai.sdk.orchestration.model.ErrorResponse;
import com.sap.ai.sdk.orchestration.model.ErrorResponseStreaming;
import com.sap.ai.sdk.orchestration.model.ErrorStreaming;
import com.sap.ai.sdk.orchestration.model.GenericModuleResult;
import com.sap.ai.sdk.orchestration.model.ModuleResults;
import com.sap.ai.sdk.orchestration.model.ModuleResultsStreaming;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.experimental.StandardException;

/** Exception thrown by the {@link OrchestrationClient} in case of an error. */
@StandardException
public class OrchestrationClientException extends ClientException {

  static final ClientExceptionFactory<OrchestrationClientException, OrchestrationError> FACTORY =
      (message, clientError, cause) -> {
        var details = extractResults(clientError).map(GenericModuleResult::getData).orElse(null);
        if (details instanceof Map<?, ?> m) {
          return new Input(message, cause)
              .setFilterDetails((Map<String, Object>) m)
              .setClientError(clientError);
        }
        return new OrchestrationClientException(message, cause).setClientError(clientError);
      };

  @Nonnull
  static Optional<GenericModuleResult> extractResults(@Nullable final OrchestrationError error) {
    if (error instanceof OrchestrationError.Synchronous synchronousError) {
      return Optional.of(synchronousError.getErrorResponse())
          .map(ErrorResponse::getError)
          .map(Error::getIntermediateResults)
          .map(ModuleResults::getInputFiltering);
    } else if (error instanceof OrchestrationError.Streaming streamingError) {
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
}
