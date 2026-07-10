package com.sap.ai.sdk.foundationmodels.openai;

public interface TextInputChannel extends AutoCloseable {
    void sendText(String text);
}
