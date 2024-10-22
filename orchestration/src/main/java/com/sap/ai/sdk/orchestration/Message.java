package com.sap.ai.sdk.orchestration;

import javax.annotation.Nonnull;

public interface Message {

  @Nonnull
  String type();

  @Nonnull
  String content();
}
