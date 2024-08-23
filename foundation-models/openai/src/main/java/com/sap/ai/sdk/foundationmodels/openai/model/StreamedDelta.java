package com.sap.ai.sdk.foundationmodels.openai.model;

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
   * Get the content from the delta.
   *
   * @return the content from the delta or null if no content is available.
   */
  @Nullable
  String getDeltaContent();
}
