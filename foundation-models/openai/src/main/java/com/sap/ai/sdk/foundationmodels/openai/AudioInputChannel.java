package com.sap.ai.sdk.foundationmodels.openai;

/**
 * Functional interface representing audio input channel (audio data consumer)
 * <p/>
 * Should be closed by application (try-with-resources) when not needed anymore
 */
public interface AudioInputChannel extends AutoCloseable {

    /**
     * This method is sequentially invoked by audio data provider to supply implementer (consumer)
     * with the audio data. Exact audio format (encoding, sampling rate, etc.) depends on the usage context
     *
     * @param rawBytesChunk binary data in the depending on the use case format
     */
    void inputAudio(byte[] rawBytesChunk);
}
