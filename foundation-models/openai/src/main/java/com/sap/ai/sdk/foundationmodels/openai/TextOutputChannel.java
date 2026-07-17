package com.sap.ai.sdk.foundationmodels.openai;

/**
 * Functional interface representing text output channel (text data consumer)
 *
 * <p>Should be closed by application (try-with-resources) when not needed anymore
 */
public interface TextOutputChannel {

  /**
   * This method is sequentially invoked by text data provider to supply implementer (consumer) with
   * the text data.
   *
   * @param textChunk chunk of the text (possibly partial content)
   * @param isLast true if this call logically concludes previous and this passed text data into a
   *     single logical entity (e.g. gets called at the end when all parts of a single message get
   *     passed)
   */
  void outputText(CharSequence textChunk, boolean isLast);
}
