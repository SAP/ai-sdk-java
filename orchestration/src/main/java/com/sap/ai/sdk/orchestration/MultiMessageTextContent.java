package com.sap.ai.sdk.orchestration;

public record MultiMessageTextContent(String text) implements MultiMessageContent {
  private static final String type = "text";

  public String type() {
    return type;
  }
}
