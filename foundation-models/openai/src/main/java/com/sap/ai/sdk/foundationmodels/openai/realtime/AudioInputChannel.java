package com.sap.ai.sdk.foundationmodels.openai.realtime;

import com.google.common.annotations.Beta;

/**
 * Functional interface representing audio input channel (used by audio data producer)
 *
 * <p>Should be closed by application (try-with-resources) when not needed anymore
 */
@Beta
public interface AudioInputChannel extends AutoCloseable {

  /**
   * This method is sequentially invoked by audio data provider to supply implementer (consumer)
   * with the audio data. Exact audio format (encoding, sampling rate, etc.) depends on the usage
   * context
   *
   * @param rawBytesChunk binary data in the depending on the use case format
   */
  @Beta
  void inputAudio(byte[] rawBytesChunk);
}
