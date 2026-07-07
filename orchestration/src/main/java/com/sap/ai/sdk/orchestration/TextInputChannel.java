package com.sap.ai.sdk.orchestration;

public interface TextInputChannel extends AutoCloseable {
  void sendText(String text);
}
