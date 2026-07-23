package com.sap.ai.sdk.foundationmodels.openai;

/**
 * Allows to input (send) text to the open channel, must be closed when
 * not needed anymore (e.g. try-with-resources)
 */
public interface TextInputChannel extends AutoCloseable {

  /**
   * Sends input text
   * @param text text to send
   */
  void sendText(String text);
}
