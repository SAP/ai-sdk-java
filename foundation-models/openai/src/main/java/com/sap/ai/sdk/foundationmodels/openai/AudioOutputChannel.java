package com.sap.ai.sdk.foundationmodels.openai;

/**
 * Functional interface representing audio output channel (audio data consumer)
 */
public interface AudioOutputChannel {

  /**
   * This method is sequentially invoked by audio data provider to supply implementer (consumer)
   * with the audio data. Exact audio format (encoding, sampling rate, etc.) depends on the usage
   * context
   *
   * @param rawBytesChunk binary data in the depending on the use case format
   * @param isLast true if this call logically concludes previous and this passed bytes data into a
   *     single logical entity (e.g. gets called at the end when all byte parts of a single message
   *     get passed)
   */
  void outputAudio(byte[] rawBytesChunk, boolean isLast);
}
