package com.sap.ai.sdk.orchestration;

public sealed interface MultiMessageContent
    permits MultiMessageTextContent, MultiMessageImageContent {
  public String type();
}
