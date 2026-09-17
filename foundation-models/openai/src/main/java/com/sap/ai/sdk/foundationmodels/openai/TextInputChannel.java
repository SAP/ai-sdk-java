package com.sap.ai.sdk.foundationmodels.openai;

import com.google.common.annotations.Beta;
import javax.annotation.Nonnull;

/**
 * Allows to input (send) text to the open channel, must be closed when not needed anymore (e.g.
 * try-with-resources)
 * <p>Note: This class is marked as {@link Beta} because it represents
 * newly introduced API which has not yet been sufficiently stabilized and has significant risk of changes
 */
@Beta
public interface TextInputChannel extends AutoCloseable {

  /**
   * Sends input text
   *
   * @param text text to send
   */
  @Beta
  void sendText(@Nonnull final String text);
}
