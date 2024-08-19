package com.sap.ai.sdk.foundationmodels.openai.model;

import java.util.concurrent.Future;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.Setter;

@Getter
public class OpenAiChatCompletionStream implements AutoCloseable {
  @Setter private Stream<OpenAiChatCompletionOutput> delta;
  private Future<OpenAiChatCompletionOutput> total; // TODO: fill out "total" delta by delta

  @Override
  public void close() {
    delta.close();
    // TODO: fill out "total" or cancel it
  }
}
