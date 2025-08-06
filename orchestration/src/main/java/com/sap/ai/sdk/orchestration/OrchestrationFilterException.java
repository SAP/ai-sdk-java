package com.sap.ai.sdk.orchestration;

import static com.sap.ai.sdk.orchestration.OrchestrationJacksonConfiguration.getOrchestrationObjectMapper;

import com.google.common.annotations.Beta;
import com.sap.ai.sdk.orchestration.model.AzureContentSafetyInput;
import com.sap.ai.sdk.orchestration.model.AzureContentSafetyOutput;
import com.sap.ai.sdk.orchestration.model.LlamaGuard38b;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.StandardException;

/** Base exception for errors occurring during orchestration filtering. */
@Beta
@StandardException(access = AccessLevel.PRIVATE)
public class OrchestrationFilterException extends OrchestrationClientException {

  /** Details about the filters that caused the exception. */
  @Accessors(chain = true)
  @Setter(AccessLevel.PACKAGE)
  @Getter @Nonnull protected Map<String, Object> filterDetails = Map.of();

  /**
   * Retrieves LlamaGuard 3.8b details from {@code filterDetails}, if present.
   *
   * @return The LlamaGuard38b object, or {@code null} if not found or conversion fails.
   * @throws IllegalArgumentException if the conversion of filter details to {@link LlamaGuard38b}
   *     fails due to invalid content.
   */
  @Nullable
  public LlamaGuard38b getLlamaGuard38b() {
    return Optional.ofNullable(filterDetails.get("llama_guard_3_8b"))
        .map(obj -> getOrchestrationObjectMapper().convertValue(obj, LlamaGuard38b.class))
        .orElse(null);
  }

  /** Exception thrown when an error occurs during input filtering. */
  @StandardException
  public static class Input extends OrchestrationFilterException {

    /**
     * Retrieves Azure Content Safety input details from {@code filterDetails}, if present.
     *
     * @return The AzureContentSafetyInput object, or {@code null} if not found or conversion fails.
     * @throws IllegalArgumentException if the conversion of filter details to {@link
     *     AzureContentSafetyInput} fails due to invalid content.
     */
    @Nullable
    public AzureContentSafetyInput getAzureContentSafetyInput() {
      return Optional.ofNullable(filterDetails.get("azure_content_safety"))
          .map(
              obj ->
                  getOrchestrationObjectMapper().convertValue(obj, AzureContentSafetyInput.class))
          .orElse(null);
    }
  }

  /**
   * Exception thrown when an error occurs during output filtering, specifically when the finish
   * reason is a content filter.
   */
  @StandardException
  public static class Output extends OrchestrationFilterException {

    /**
     * Retrieves Azure Content Safety output details from {@code filterDetails}, if present.
     *
     * @return The AzureContentSafetyOutput object, or {@code null} if not found or conversion
     *     fails.
     * @throws IllegalArgumentException if the conversion of filter details to {@link
     *     AzureContentSafetyOutput} fails due to invalid content.
     */
    @Nullable
    public AzureContentSafetyOutput getAzureContentSafetyOutput() {
      return Optional.ofNullable(filterDetails.get("azure_content_safety"))
          .map(
              obj ->
                  getOrchestrationObjectMapper().convertValue(obj, AzureContentSafetyOutput.class))
          .orElse(null);
    }
  }
}
