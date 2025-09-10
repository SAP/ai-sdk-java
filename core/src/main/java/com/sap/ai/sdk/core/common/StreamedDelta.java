package com.sap.ai.sdk.core.common;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Interface for streamed delta classes.
 *
 * <p>This interface defines a method to retrieve the content from a delta, which is a chunk in a
 * stream of data. Implementations of this interface should provide the logic to extract the
 * relevant content from the delta.
 */
public interface StreamedDelta {

  /**
   * Get the message content from the delta.
   *
   * <p>Note: If there are multiple choices only the first one is returned
   *
   * <p>Note: Some deltas do not contain any content
   *
   * @return the message content or empty string.
   */
  @Nonnull
  String getDeltaContent();

  /**
   * Reason for finish. The possible values are:
   *
   * <p>{@code stop}: API returned complete message, or a message terminated by one of the stop
   * sequences provided via the stop parameter
   *
   * <p>{@code length}: Incomplete model output due to max_tokens parameter or token limit
   *
   * <p>{@code function_call}: The model decided to call a function
   *
   * <p>{@code content_filter}: Omitted content due to a flag from our content filters
   *
   * <p>{@code null}: API response still in progress or incomplete
   *
   * @return the finish reason or null.
   */
  @Nullable
  String getFinishReason();

  /**
   * Indicates if the delta is an error of type {@link ClientError}
   *
   * @return true if the delta is an error, false otherwise.
   */
  boolean isError();
}
