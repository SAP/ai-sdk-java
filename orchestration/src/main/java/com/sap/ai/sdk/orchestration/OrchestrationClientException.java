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
import io.vavr.control.Option;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.StandardException;

/** Exception thrown by the {@link OrchestrationClient} in case of an error. */
@StandardException
public class OrchestrationClientException extends ClientException {
  private static final ObjectMapper MAPPER = getOrchestrationObjectMapper();

  /** Details about the filters that caused the exception. */
  @Setter(AccessLevel.PACKAGE)
  @Getter(AccessLevel.PACKAGE)
  @Accessors(chain = true)
  @Nonnull
  protected Map<String, Object> filterDetails = Map.of();

  /** Exception thrown during a streaming invocation. */
  @StandardException
  public static class Streaming extends OrchestrationClientException {
    static final ClientExceptionFactory<Streaming, OrchestrationError.Streaming> FACTORY =
        (message, clientError, cause) -> {
          final var details = extractInputFilterDetails(clientError);
          if (details.isEmpty()) {
            return new Streaming(message, cause).setClientError(clientError);
          }
          return new InputFilter(message, cause)
              .setFilterDetails(details)
              .setClientError(clientError);
        };

    @SuppressWarnings("unchecked")
    @Nonnull
    private static Map<String, Object> extractInputFilterDetails(
        @Nullable final OrchestrationError.Streaming error) {
      return Optional.ofNullable(error)
          .map(OrchestrationError.Streaming::getErrorResponse)
          .map(ErrorResponseStreaming::getError)
          .map(ErrorStreaming::getIntermediateResults)
          .map(ModuleResultsStreaming::getInputFiltering)
          .map(GenericModuleResult::getData)
          .filter(Map.class::isInstance)
          .map(map -> (Map<String, Object>) map)
          .orElseGet(Collections::emptyMap);
    }

    /**
     * Retrieves the {@link ErrorResponseStreaming} from the orchestration service, if available.
     *
     * @return The {@link ErrorResponseStreaming} object, or {@code null} if not available.
     * @since 1.10.0
     */
    @Beta
    @Nullable
    public ErrorResponseStreaming getErrorResponse() {
      return Option.of(getClientError())
          .map(OrchestrationError.Streaming::getErrorResponse)
          .getOrNull();
    }

    /**
     * Retrieves the client error details from the orchestration service, if available.
     *
     * @return The {@link OrchestrationError.Streaming} object, or {@code null} if not available.
     * @since 1.10.0
     */
    @Override
    public OrchestrationError.Streaming getClientError() {
      return super.getClientError() instanceof OrchestrationError.Streaming e ? e : null;
    }

    /** Exception thrown during a streaming invocation that contains input filter details. */
    @Beta
    @StandardException
    public static class InputFilter extends Streaming implements Filter.Input {
      @Nonnull
      @Override
      public Map<String, Object> getFilterDetails() {
        return super.getFilterDetails();
      }
    }

    /** Exception thrown during a streaming invocation that contains output filter details. */
    @Beta
    @StandardException
    public static class OutputFilter extends Streaming implements Filter.Output {
      @Nonnull
      @Override
      public Map<String, Object> getFilterDetails() {
        return super.getFilterDetails();
      }
    }
  }

  /** Exception thrown during a synchronous invocation. */
  @StandardException
  public static class Synchronous extends OrchestrationClientException {
    static final ClientExceptionFactory<Synchronous, OrchestrationError.Synchronous> FACTORY =
        (message, clientError, cause) -> {
          final var details = extractInputFilterDetails(clientError);
          if (details.isEmpty()) {
            return new Synchronous(message, cause).setClientError(clientError);
          }
          return new InputFilter(message, cause)
              .setFilterDetails(details)
              .setClientError(clientError);
        };

    @SuppressWarnings("unchecked")
    @Nonnull
    private static Map<String, Object> extractInputFilterDetails(
        @Nullable final OrchestrationError.Synchronous error) {
      return Optional.ofNullable(error)
          .map(OrchestrationError.Synchronous::getErrorResponse)
          .map(ErrorResponse::getError)
          .map(Error::getIntermediateResults)
          .map(ModuleResults::getInputFiltering)
          .map(GenericModuleResult::getData)
          .map(map -> (Map<String, Object>) map)
          .orElseGet(Collections::emptyMap);
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
      return Option.of(getClientError())
          .map(OrchestrationError.Synchronous::getErrorResponse)
          .getOrNull();
    }

    /**
     * Retrieves the client error details from the orchestration service, if available.
     *
     * @return The {@link OrchestrationError.Synchronous} object, or {@code null} if not available.
     * @since 1.10.0
     */
    @Override
    public OrchestrationError.Synchronous getClientError() {
      return super.getClientError() instanceof OrchestrationError.Synchronous e ? e : null;
    }

    /** Exception thrown during a synchronous invocation that contains input filter details. */
    @Beta
    @StandardException
    public static class InputFilter extends Synchronous implements Filter.Input {
      @Nonnull
      @Override
      public Map<String, Object> getFilterDetails() {
        return super.getFilterDetails();
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
            .map(e -> e.getError().getCode())
            .orElse(null);
      }
    }

    /** Exception thrown during a synchronous invocation that contains output filter details. */
    @Beta
    @StandardException
    public static class OutputFilter extends Synchronous implements Filter.Output {
      @Nonnull
      @Override
      public Map<String, Object> getFilterDetails() {
        return super.getFilterDetails();
      }
    }
  }

  /**
   * Interface representing the filter details that can be included in an orchestration error
   * response.
   */
  @Beta
  interface Filter {
    /**
     * Retrieves the filter details as a map.
     *
     * @return a map containing the filter details.
     */
    @Nonnull
    Map<String, Object> getFilterDetails();

    /**
     * Retrieves the Azure Content Safety input filter details, if available.
     *
     * @return the {@link AzureContentSafetyInput} object, or {@code null} if not available.
     */
    @Nullable
    default LlamaGuard38b getLlamaGuard38b() {
      return Optional.ofNullable(getFilterDetails().get("llama_guard_3_8b"))
          .map(obj -> MAPPER.convertValue(obj, LlamaGuard38b.class))
          .orElse(null);
    }

    /** Interface for input filters that can be included in an orchestration error response. */
    interface Input extends Filter {
      /**
       * Retrieves the Azure Content Safety input filter details, if available.
       *
       * @return the {@link AzureContentSafetyInput} object, or {@code null} if not available.
       */
      @Nullable
      default AzureContentSafetyInput getAzureContentSafety() {
        return Optional.ofNullable(getFilterDetails().get("azure_content_safety"))
            .map(obj -> MAPPER.convertValue(obj, AzureContentSafetyInput.class))
            .orElse(null);
      }
    }

    /** Interface for output filters that can be included in an orchestration error response. */
    interface Output extends Filter {
      /**
       * Retrieves the Azure Content Safety output filter details, if available.
       *
       * @return the {@link AzureContentSafetyOutput} object, or {@code null} if not available.
       */
      @Nullable
      default AzureContentSafetyOutput getAzureContentSafety() {
        return Optional.ofNullable(getFilterDetails().get("azure_content_safety"))
            .map(obj -> MAPPER.convertValue(obj, AzureContentSafetyOutput.class))
            .orElse(null);
      }
    }
  }

  @Override
  @Nullable
  public OrchestrationError getClientError() {
    return (OrchestrationError) super.getClientError();
  }
}
